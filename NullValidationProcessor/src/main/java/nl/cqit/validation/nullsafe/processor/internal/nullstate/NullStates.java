package nl.cqit.validation.nullsafe.processor.internal.nullstate;

import com.sun.source.util.Trees;
import nl.cqit.validation.nullsafe.annotations.NotNull;
import nl.cqit.validation.nullsafe.annotations.NotNullByDefault;
import nl.cqit.validation.nullsafe.annotations.Nullable;
import nl.cqit.validation.nullsafe.annotations.NullableByDefault;

import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.Optional;

public class NullStates {
    private final Trees trees;
    private final Elements elements;

    public NullStates(Trees trees, Elements elements) {
        this.elements = elements;
        this.trees = trees;
    }

    public NullState getNullState(VariableElement variableElement) {
        final TypeElement typeElement = elements.getTypeElement(variableElement.asType().toString());
        final Optional<NullState> typeNullState = getElementNullState(typeElement);
        final Optional<NullState> variableNullState = getElementNullState(variableElement);
        
        typeNullState.ifPresent(tns -> variableNullState.ifPresent(vns ->  {
            if (tns == NullState.NOT_NULL && hasAnnotation(variableElement, Nullable.class)) {
                throw new UnsupportedOperationException("You can't mark a variable as nullable if the type is non-null");
            }
        }));
        return typeNullState.or(() -> variableNullState).orElse(NullState.NULLABLE);
    }

    private Optional<NullState> getElementNullState(Element element) {
        if (element instanceof ModuleElement moduleElement) {
            return getModuleNullState(moduleElement);
        }
        if (hasAnnotation(element, NotNull.class)) {
            return Optional.of(NullState.NOT_NULL);
        }
        if (hasAnnotation(element, Nullable.class)) {
            return Optional.of(NullState.NULLABLE);
        }
        return getPackageNullState(element);
    }

    private static Optional<NullState> getModuleNullState(ModuleElement moduleElement) {
        if (hasAnnotation(moduleElement, NotNullByDefault.class)) {
            return Optional.of(NullState.NOT_NULL);
        }
        if (hasAnnotation(moduleElement, NullableByDefault.class)) {
            return Optional.of(NullState.NULLABLE);
        }
        return Optional.empty();
    }

    private Optional<NullState> getPackageNullState(Element element) {
        final ModuleElement moduleElement = elements.getModuleOf(element);
        final PackageElement currentPackageElement = elements.getPackageOf(element);
        return moduleElement.getEnclosedElements().stream()
                .map(PackageElement.class::cast)
                .filter(packageElement -> element.getSimpleName().toString().startsWith(packageElement.getSimpleName().toString()))
                .sorted(Comparator.comparingInt((Element a) -> a.getSimpleName().length()))
                .flatMap(packageElement -> {
                    final boolean isCurrentPackage = currentPackageElement.equals(packageElement);
                    final Optional<NullState> packageNullState = getPackageNullState(packageElement, isCurrentPackage);
                    System.out.println(packageElement.getQualifiedName());
                    return packageNullState.stream();
                })
                .findFirst()
                .or(() -> getModuleNullState(moduleElement));
    }

    private static Optional<NullState> getPackageNullState(PackageElement packageElement, boolean isCurrentPackage) {
        final NotNullByDefault notNullByDefault = packageElement.getAnnotation(NotNullByDefault.class);
        if (notNullByDefault != null) {
            if (isCurrentPackage || notNullByDefault.applyToSubPackages()) {
                return Optional.of(NullState.NOT_NULL);
            }
        }
        final NullableByDefault nullableByDefault = packageElement.getAnnotation(NullableByDefault.class);
        if (nullableByDefault != null) {
            if (isCurrentPackage || nullableByDefault.applyToSubPackages()) {
                return Optional.of(NullState.NULLABLE);
            }
        }
        return Optional.empty();
    }

    private static boolean hasAnnotation(Element element, Class<? extends Annotation> annotationType) {
        return element.getAnnotation(annotationType) != null;
    }
}