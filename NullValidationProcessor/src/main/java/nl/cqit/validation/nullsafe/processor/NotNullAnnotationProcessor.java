package nl.cqit.validation.nullsafe.processor;


import com.google.auto.service.AutoService;
import com.sun.source.util.Trees;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("nl.cqit.validation.nullsafe.annotations.NotNull")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class NotNullAnnotationProcessor extends AbstractProcessor {

    private Trees trees;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        trees = Trees.instance(jbUnwrap(ProcessingEnvironment.class, processingEnv));
    }

    private static <T> T jbUnwrap(Class<? extends T> iface, T wrapper) {
        T unwrapped = null;
        try {
            final Class<?> apiWrappers = wrapper.getClass().getClassLoader().loadClass("org.jetbrains.jps.javac.APIWrappers");
            final Method unwrapMethod = apiWrappers.getDeclaredMethod("unwrap", Class.class, Object.class);
            unwrapped = iface.cast(unwrapMethod.invoke(null, iface, wrapper));
        }
        catch (Throwable ignored) {}
        return unwrapped != null? unwrapped : wrapper;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            final Map<Element, Element> parentElements = annotatedElements.stream()
                    .collect(Collectors.toMap(Function.identity(), Element::getEnclosingElement));
            for (TypeElement element : ElementFilter.typesIn(annotatedElements)) {
                processTypeNotNull(element);
            }
            for (VariableElement element : ElementFilter.fieldsIn(annotatedElements)) {
                processFieldNotNull(element);
            }
            for (ExecutableElement element : ElementFilter.methodsIn(annotatedElements)) {
                processReturnTypeNotNull(element);
            }

            for (Map.Entry<Element, Element> element : parentElements.entrySet()) {
                if (ElementKind.METHOD.equals(element.getValue().getKind())) {
                    final VariableElement parameter = (VariableElement) element.getKey();
                    final ExecutableElement method = (ExecutableElement) element.getValue();
                    processMethodParameterNotNull(parameter, method);
                }
            }
        }
        return true;
    }

    private void processTypeNotNull(TypeElement element) {
        // TODO implement
    }

    private void processFieldNotNull(VariableElement element) {
        // TODO implement
    }

    private void processReturnTypeNotNull(ExecutableElement element) {
        // TODO implement
    }

    private void processMethodParameterNotNull(VariableElement element, ExecutableElement methodElement) {
        // TODO implement
    }

    private void processLocalVariableNotNull(VariableElement element) {
        // TODO implement
    }
}
