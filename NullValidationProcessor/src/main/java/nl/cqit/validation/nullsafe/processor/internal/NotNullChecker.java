package nl.cqit.validation.nullsafe.processor.internal;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import nl.cqit.validation.nullsafe.annotations.NotNull;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import java.util.HashMap;
import java.util.Map;

public class NotNullChecker {

    private final Trees trees;

    public NotNullChecker(Trees trees) {
        this.trees = trees;
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
        Map<Name, NullState> nullStates = new HashMap<>();
        initParameterNullStates(methodElem, nullStates);
        
        final MethodTree method = trees.getTree(methodElem);
        final CompilationUnitTree compilationUnit = trees.getPath(methodElem).getCompilationUnit();
        method.getParameters().get(0).getModifiers().getAnnotations().get(0).getAnnotationType();
        for (StatementTree statement : method.getBody().getStatements()) {
            final TreePath statementPath = trees.getPath(compilationUnit, statement);
            for (Tree tree : statementPath) {
                System.out.print(tree + " -> ");
            }
            System.out.println();
        };

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
