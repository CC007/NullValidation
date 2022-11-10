package test.subpackage;

import nl.cqit.validation.nullsafe.annotations.NotNull;

@NotNull
public class ClassWithMethod {
    
    public static String getHelloStatic(String name) {
        return "Hello " + name;
    }
}
