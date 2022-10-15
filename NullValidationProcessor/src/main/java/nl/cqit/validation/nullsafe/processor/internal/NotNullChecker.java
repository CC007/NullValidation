package nl.cqit.validation.nullsafe.processor.internal;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import nl.cqit.validation.nullsafe.annotations.NotNull;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import java.util.HashMap;
import java.util.Map;

public class NotNullChecker {

    private final Trees trees;
    private final Elements elements;

    public NotNullChecker(Trees trees, Elements elements) {
        this.trees = trees;
        this.elements = elements;
    }

    public void checkMethodParameter(VariableElement targetParameterElem, ExecutableElement methodElem) {
        final MethodTree method = trees.getTree(methodElem);
        final Tree targetParameter = trees.getTree(targetParameterElem);
        System.out.println("Method " + method.getName());
        System.out.println(" Parameters:");
        for (VariableTree parameter : method.getParameters()) {
            final Tree type = parameter.getType();
            System.out.print(" - " + type.getClass().getName() + " " + parameter.getName());
            if (parameter.equals(targetParameter)) {
                System.out.println(" <--");
            }
        }
//        method.getBody()

    }

    public void checkLocalVariable(VariableElement elementElem, ExecutableElement methodElem) {
    }

    public void checkReturnType(ExecutableElement methodElem) {
        if (isAbstract(methodElem) || isRecordComponentMethod(methodElem)) {
            return;
        }
        final MethodTree method = trees.getTree(methodElem);
        Map<Name, NullState> nullStates = new HashMap<>();
        initParameterNullStates(methodElem, nullStates);
        final CompilationUnitTree compilationUnit = trees.getPath(methodElem).getCompilationUnit();
        for (StatementTree statement : method.getBody().getStatements()) {
            final TreePath statementPath = trees.getPath(compilationUnit, statement);
            for (Tree tree : statementPath) {
                System.out.print(tree + " -> ");
            }
            System.out.println("<root>");
            System.out.println();
        }

    }

    private boolean isRecordComponentMethod(ExecutableElement methodElem) {
        if (!(methodElem.getEnclosingElement() instanceof TypeElement typeElement)) {
            return false;
        }
        return typeElement.getRecordComponents().stream()
                .anyMatch(recordComponentElement -> recordComponentElement.getSimpleName().equals(methodElem.getSimpleName()));
    }

    private boolean isAbstract(ExecutableElement methodElem) {
        if (methodElem.getModifiers().contains(Modifier.ABSTRACT)) {
            return true;
        }
        return methodElem.getEnclosingElement().getKind().equals(ElementKind.INTERFACE) && !methodElem.getModifiers().contains(Modifier.DEFAULT);
    }

    private void initParameterNullStates(ExecutableElement methodElem, Map<Name, NullState> nullStates) {
        methodElem.getParameters().forEach(element -> {
            if (element.getAnnotation(NotNull.class) == null) {
                nullStates.put(element.getSimpleName(), NullState.NULLABLE);
            } else {
                nullStates.put(element.getSimpleName(), NullState.NOT_NULL);
            }
        });
    }
}
