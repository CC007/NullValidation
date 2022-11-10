package nl.cqit.validation.nullsafe.processor.internal;

import javax.lang.model.element.*;

public class ElementUtils {

    public boolean isRecordComponentMethod(ExecutableElement methodElem) {
        if (!(methodElem.getEnclosingElement() instanceof TypeElement typeElement)) {
            return false;
        }
        return typeElement.getRecordComponents().stream()
                .anyMatch(recordComponentElement -> recordComponentElement.getSimpleName().equals(methodElem.getSimpleName()));
    }

    public boolean isAbstract(ExecutableElement methodElem) {
        if (methodElem.getModifiers().contains(Modifier.ABSTRACT)) {
            return true;
        }
        return methodElem.getEnclosingElement().getKind().equals(ElementKind.INTERFACE) && !methodElem.getModifiers().contains(Modifier.DEFAULT);
    }
}