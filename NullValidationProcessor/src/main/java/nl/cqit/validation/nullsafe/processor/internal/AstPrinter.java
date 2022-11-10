package nl.cqit.validation.nullsafe.processor.internal;

import com.sun.source.tree.*;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;

import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.Elements;
import java.util.Arrays;

public class AstPrinter {
    private final Trees trees;
    private final Elements elements;

    public AstPrinter(Trees trees, Elements elements) {
        this.trees = trees;
        this.elements = elements;
    }

    void printStatement(StatementTree statement, CompilationUnitTree compilationUnit, int level) {
        if (level > 50) {
            System.out.println("LEVEL LIMIT REACHED!!");
            return;
        }
        System.out.println("  Tree type: " + statement.getClass().getName() + "(" + Arrays.toString(statement.getClass().getInterfaces()) + ")");
        final TreePath statementPath = trees.getPath(compilationUnit, statement);
        final Element statementElement = trees.getElement(statementPath);
        if (statementElement != null) {
            System.out.println(" ".repeat(level + 2) + "Element: " + statementElement);
            printElement(statementElement, level + 4);
        }

        switch (statement) {
            case VariableTree variable -> {
                System.out.println(" ".repeat(level + 2) + "Type: " + variable.getType());
                System.out.println(" ".repeat(level + 2) + "Name: " + variable.getName());
                System.out.println(" ".repeat(level + 2) + "Init: " + variable.getInitializer());
                printExpression(variable.getInitializer(), compilationUnit, level + 4);
            }
            case ExpressionStatementTree expressionStatement -> {
                System.out.println(" ".repeat(level + 2) + "Expr: " + expressionStatement.getExpression());
                printExpression(expressionStatement.getExpression(), compilationUnit, level + 4);
            }
            case ReturnTree returnStatement -> {
                final TypeMirror returnType = ((ExecutableElement) trees.getElement(statementPath.getParentPath().getParentPath())).getReturnType();
                System.out.println(" ".repeat(level + 2) + "Type: " + returnType);
                System.out.println(" ".repeat(level + 2) + "Type type: " + returnType.getClass().getName() + "(" + Arrays.toString(returnType.getClass().getInterfaces()) + ")");
                final Element returnElement = elements.getTypeElement(returnType.toString());
                System.out.println(" ".repeat(level + 2) + "Element: " + returnElement);
                System.out.println(" ".repeat(level + 2) + "Element type: " + returnElement.getClass().getName() + "(" + Arrays.toString(returnElement.getClass().getInterfaces()) + ")");
                final ModuleElement moduleElement = elements.getModuleOf(returnElement);
                System.out.println(" ".repeat(level + 2) + "Module name: " + moduleElement.getSimpleName());
                System.out.println(" ".repeat(level + 2) + "Return expr: " + returnStatement.getExpression());
                printExpression(returnStatement.getExpression(), compilationUnit, 4);
            }
            default -> System.out.println("  Unsupported statement: " + statement);
        }
    }

    void printExpression(ExpressionTree expression, CompilationUnitTree compilationUnit, int level) {
        if (level > 50) {
            System.out.println("LEVEL LIMIT REACHED!!");
            return;
        }
        System.out.println(" ".repeat(level) + "Tree type: " + expression.getClass().getName() + "(" + Arrays.toString(expression.getClass().getInterfaces()) + ")");
        final TreePath expressionPath = trees.getPath(compilationUnit, expression);
        final Element expressionElement = trees.getElement(expressionPath);
        if (expressionElement != null) {
            System.out.println(" ".repeat(level) + "Element: " + expressionElement);
            printElement(expressionElement, level + 2);
        }

        switch (expression) {
            case AssignmentTree assignment -> {
                System.out.println(" ".repeat(level) + "Var: " + assignment.getVariable());
                printExpression(assignment.getVariable(), compilationUnit, level + 2);
                System.out.println(" ".repeat(level) + "Expr: " + assignment.getExpression());
                printExpression(assignment.getExpression(), compilationUnit, level + 2);
            }
            case IdentifierTree identifier -> {
                System.out.println(" ".repeat(level) + "Name: " + identifier.getName());
            }
            case MethodInvocationTree methodInvocation -> {
                System.out.println(" ".repeat(level) + "MethodIdent: " + methodInvocation.getMethodSelect());
                printExpression(methodInvocation.getMethodSelect(), compilationUnit, level + 2);
                System.out.println(" ".repeat(level) + "Args: ");

                for (ExpressionTree argument : methodInvocation.getArguments()) {
                    System.out.println(" ".repeat(level + 2) + "Expr: " + argument);
                    printExpression(argument, compilationUnit, level + 4);
                }
            }
            case MemberSelectTree memberSelect -> {
                System.out.println(" ".repeat(level) + "Ident: " + memberSelect.getIdentifier());
                System.out.println(" ".repeat(level) + "Caller expr: " + memberSelect.getExpression());
                printExpression(memberSelect.getExpression(), compilationUnit, level + 2);
            }
            case LiteralTree literal -> {
                System.out.println(" ".repeat(level) + "Value: " + literal.getValue());
            }
            default -> System.out.println(" ".repeat(level) + "Unsupported expression: " + expression);
        }
    }

    void printElement(Element element, int level) {
        System.out.println(" ".repeat(level) + "Element type: " + element.getClass().getName() + "(" + Arrays.toString(element.getClass().getInterfaces()) + ")");
        final TreePath elementPath = trees.getPath(element);
        if (elementPath != null) {
            final CompilationUnitTree compilationUnit = elementPath.getCompilationUnit();
            if (compilationUnit.getPackageName() != null) {
                System.out.println(" ".repeat(level) + "Package expr: " + compilationUnit.getPackageName());
                printPackageExpression(compilationUnit.getPackage().getPackageName(), level + 2);
            }
        }
        if (!element.getModifiers().isEmpty()) {
            System.out.println(" ".repeat(level) + "Modifiers: " + element.getModifiers());
        }
        if (!element.getAnnotationMirrors().isEmpty()) {
            System.out.println(" ".repeat(level) + "Annotations: ");
            for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
                System.out.println(" ".repeat(level + 2) + "Annotation: " + annotationMirror);
                printAnnotationMirror(annotationMirror, level + 4);
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

    void printPackageExpression(ExpressionTree packageName, int level) {
        if (level > 50) {
            System.out.println("LEVEL LIMIT REACHED!!");
            return;
        }
        System.out.println(" ".repeat(level) + "Tree type: " + packageName.getClass().getName() + "(" + Arrays.toString(packageName.getClass().getInterfaces()) + ")");
        final PackageElement packageElement = elements.getPackageElement(packageName.toString());
        System.out.println(" ".repeat(level) + "Element: " + packageElement);
        printPackageElement(packageElement, level + 2);
        switch (packageName) {
            case MemberSelectTree memberSelect -> {
                System.out.println(" ".repeat(level) + "Ident: " + memberSelect.getIdentifier());
                System.out.println(" ".repeat(level) + "Expr: " + memberSelect.getExpression());
                printPackageExpression(memberSelect.getExpression(), level + 2);
            }
            case IdentifierTree identifier -> {
                System.out.println(" ".repeat(level) + "Name: " + identifier.getName());
            }
            default -> System.out.println(" ".repeat(level) + "Unsupported package expression: " + packageName);
        }
    }

    void printPackageElement(PackageElement packageElem, int level) {
        System.out.println(" ".repeat(level) + "Element type: " + packageElem.getClass().getName() + "(" + Arrays.toString(packageElem.getClass().getInterfaces()) + ")");
        if (!packageElem.getModifiers().isEmpty()) {
            System.out.println(" ".repeat(level) + "Modifiers: " + packageElem.getModifiers());
        }
        if (!packageElem.getAnnotationMirrors().isEmpty()) {
            System.out.println(" ".repeat(level) + "Annotations: ");
            for (AnnotationMirror annotationMirror : packageElem.getAnnotationMirrors()) {
                System.out.println(" ".repeat(level + 2) + "Annotation: " + annotationMirror);
                printAnnotationMirror(annotationMirror, level + 4);
            }
        }
        System.out.println(" ".repeat(level) + "Simple name: " + packageElem.getSimpleName());
        System.out.println(" ".repeat(level) + "Qualified name: " + packageElem.getQualifiedName());
        System.out.println(" ".repeat(level) + "Module: " + packageElem.getEnclosingElement());
        printElement(packageElem.getEnclosingElement(), level + 2);

    }

    void printAnnotationMirror(AnnotationMirror annotationMirror, int level) {
        System.out.println(" ".repeat(level) + "Annotation class: " + annotationMirror.getAnnotationType());
        if (!annotationMirror.getElementValues().isEmpty()) {
            System.out.println(" ".repeat(level) + "Annotation values: ");
            for (var annotationValueEntry : annotationMirror.getElementValues().entrySet()) {
                System.out.println(" ".repeat(level + 2) + "Name: " + annotationValueEntry.getKey().getSimpleName());
                System.out.println(" ".repeat(level + 4) + "Default value: " + annotationValueEntry.getKey().getDefaultValue().getValue());
                System.out.println(" ".repeat(level + 4) + "Value: " + annotationValueEntry.getValue().getValue());
            }
        }
    }
}