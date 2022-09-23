package nl.cqit.validation.nullsafe.processor.internal;

import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.Trees;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

public class VariableChecker {
    
    public void checkMethodParameter(VariableElement targetParameterElem, ExecutableElement methodElem, Trees trees){
        final MethodTree method = trees.getTree(methodElem);
        final Tree targetParameter = trees.getTree(targetParameterElem);
        System.out.println("Method " + method.getName());
        System.out.println(" Parameters:");
        for (VariableTree parameter : method.getParameters()) {
            final Tree type = parameter.getType();
            System.out.print(" - " + type.getClass().getName() +  " " + parameter.getName());
            if(parameter.equals(targetParameter)) {
                System.out.println(" <--");
            }
        }
//        method.getBody()
        
    }

    public void checkLocalVariable(VariableElement element, ExecutableElement method, Trees trees){
    }
}
