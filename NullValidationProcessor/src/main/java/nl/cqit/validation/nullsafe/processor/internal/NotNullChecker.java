package nl.cqit.validation.nullsafe.processor.internal;

import com.sun.source.tree.*;
import com.sun.source.util.Trees;
import nl.cqit.validation.nullsafe.processor.internal.nullstate.NullState;
import nl.cqit.validation.nullsafe.processor.internal.nullstate.NullStates;

import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import java.util.HashMap;
import java.util.Map;

public class NotNullChecker {

    private final Trees trees;
    private final Elements elements;
    private final ElementUtils elementUtils = new ElementUtils();
    private final AstPrinter astPrinter;

    private final NullStates nullStates;

    public NotNullChecker(Trees trees, Elements elements) {
        this.trees = trees;
        this.elements = elements;
        nullStates = new NullStates(trees, elements);
        astPrinter = new AstPrinter(trees, elements);
    }

    public void checkMethodParameter(VariableElement targetParameterElem, ExecutableElement methodElem) {
    }

    public void checkLocalVariable(VariableElement elementElem, ExecutableElement methodElem) {
    }

    public void checkReturnType(ExecutableElement methodElem) {
        if (elementUtils.isAbstract(methodElem) || elementUtils.isRecordComponentMethod(methodElem)) {
            return;
        }
        final MethodTree method = trees.getTree(methodElem);
        Map<Name, NullState> nullStates = new HashMap<>();
        initParameterNullStates(methodElem, nullStates);
        final CompilationUnitTree compilationUnit = trees.getPath(methodElem).getCompilationUnit();
        method.getBody().getStatements().forEach(statement -> {
            System.out.println("Stmnt: " + statement);
            astPrinter.printStatement(statement, compilationUnit, 0);
        });

    }

    private void initParameterNullStates(ExecutableElement methodElem, Map<Name, NullState> nullStateMap) {
        methodElem.getParameters().forEach(element -> {
            nullStateMap.put(element.getSimpleName(), nullStates.getNullState(element));
        });
    }
}
