package nl.cqit.validation.nullsafe.processor.internal;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;
import nl.cqit.validation.nullsafe.processor.NotNullAnnotationProcessor;
import org.junit.jupiter.api.Test;

import static com.google.testing.compile.CompilationSubject.assertThat;

class NotNullCheckerTest {

    @Test
    void checkReturnType() {
        // prepare
        final Compiler compiler = Compiler.javac()
                .withOptions(
                        "--module-source-path", ".\\src\\test\\resources",
                        "--module", "test",
                        "-d", ".\\src\\test\\resources\\classes",
                        "--module-path", "..\\NullValidationAnnotations\\target\\null-safe-annotations-1.0-SNAPSHOT.jar",
                        "--processor-path", "..\\NullValidationAnnotations\\target\\null-safe-annotations-1.0-SNAPSHOT.jar"
                )
                .withProcessors(new NotNullAnnotationProcessor());

        // execute
        Compilation compilation = compiler.compile(
                JavaFileObjects.forResource("test/NullableClassWithNonNullMethod.java")
        );

        // verify
        assertThat(compilation).succeededWithoutWarnings();
    }
}