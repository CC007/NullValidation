package nl.cqit.validation.nullsafe.processor;


import nl.cqit.validation.nullsafe.annotations.NotNull;

import javax.lang.model.element.*;
import javax.lang.model.util.ElementFilter;
import java.io.File;
import java.util.Arrays;
import java.util.Set;

public class NotNullChecker {

    public boolean checkNotNull(Set<? extends Element> annotatedElements) {
        System.out.println("Elements found: " + annotatedElements.size());
        annotatedElements.forEach(System.out::println);
        final Set<TypeElement> typeElements = ElementFilter.typesIn(annotatedElements);
        System.out.println("Type elements found: " + typeElements.size());
        boolean success = true;
        for (TypeElement element : typeElements) {
            if (element.getAnnotation(NotNull.class) != null) {
                System.out.println("  - Element with NotNull annotation!");
            } else {
                System.out.println("  - Element WITHOUT NotNull annotation!");
                success = false;
            }
            printElement(element, 6);
        }

        System.out.println("classpath: ");
        final String[] classpathElems = System.getProperty("java.class.path").split(File.pathSeparator);
        for (String classpathElem : classpathElems) {
            System.out.println("  - " + classpathElem);
        }
        System.out.println();
        return success;
    }

    void printElement(Element element, int level) {
        System.out.println(" ".repeat(level) + "Element type: " + element.getClass().getName() + "(" + Arrays.toString(element.getClass().getInterfaces()) + ")");
        if (!element.getModifiers().isEmpty()) {
            System.out.println(" ".repeat(level) + "Modifiers: " + element.getModifiers());
        }
        if (!element.getAnnotationMirrors().isEmpty()) {
            System.out.println(" ".repeat(level) + "Annotations: ");
            for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
                System.out.println(" ".repeat(level + 2) + "- Annotation: " + annotationMirror);
                printAnnotationMirror(annotationMirror, level + 6);
            }
        }
        System.out.println(" ".repeat(level) + "Simple name: " + element.getSimpleName());

        switch (element) {
            case TypeElement typeElem -> {
                System.out.println(" ".repeat(level) + "Qualified name: " + typeElem.getQualifiedName());
                if (!typeElem.getTypeParameters().isEmpty()) {
                    System.out.println(" ".repeat(level) + "Type parameters: " + typeElem.getTypeParameters());
                }
                if (!typeElem.getSuperclass().toString().equals("java.lang.Object")) {
                    System.out.println(" ".repeat(level) + "Super class: " + typeElem.getSuperclass());
                }
                if (!typeElem.getInterfaces().isEmpty()) {
                    System.out.println(" ".repeat(level) + "Interfaces: " + typeElem.getInterfaces());
                }
                System.out.println(" ".repeat(level) + "Nesting kind: " + typeElem.getNestingKind());
            }
            case PackageElement packageElem -> {
                System.out.println(" ".repeat(level) + "Qualified name: " + packageElem.getQualifiedName());
                System.out.println(" ".repeat(level) + "Module: " + packageElem.getEnclosingElement());
                printElement(packageElem.getEnclosingElement(), level + 2);
            }
            case ModuleElement moduleElem -> {
                System.out.println(" ".repeat(level) + "Qualified name: " + moduleElem.getQualifiedName());
            }
            default -> System.out.println(" ".repeat(level) + "Unsupported element: " + element);
        }
    }


    void printAnnotationMirror(AnnotationMirror annotationMirror, int level) {
        System.out.println(" ".repeat(level) + "Annotation class: " + annotationMirror.getAnnotationType());
        if (!annotationMirror.getElementValues().isEmpty()) {
            System.out.println(" ".repeat(level) + "Annotation values: ");
            for (var annotationValueEntry : annotationMirror.getElementValues().entrySet()) {
                System.out.println(" ".repeat(level + 2) + "- Name: " + annotationValueEntry.getKey().getSimpleName());
                final AnnotationValue defaultValue = annotationValueEntry.getKey().getDefaultValue();
                if (defaultValue != null) {
                    System.out.println(" ".repeat(level + 6) + "Default value: " + defaultValue.getValue());
                }
                System.out.println(" ".repeat(level + 6) + "Value: " + annotationValueEntry.getValue().getValue());
            }
        }
    }
}
