package nl.cqit.validation.nullsafe.processor.internal;

import com.karuslabs.elementary.Results;
import com.karuslabs.elementary.junit.JavacExtension;
import com.karuslabs.elementary.junit.annotations.Options;
import com.karuslabs.elementary.junit.annotations.Processors;
import nl.cqit.validation.nullsafe.processor.NotNullAnnotationProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(JavacExtension.class)
@Options(
        "-d .\\unimportant\\because\\inmemory\\but\\mandatory" +
        " --module-source-path .\\src\\test\\resources" +
        " --module test" +
        " --module-path ..\\NullValidationAnnotations\\target\\null-safe-annotations-1.0-SNAPSHOT.jar" +
        " --processor-path ..\\NullValidationAnnotations\\target\\null-safe-annotations-1.0-SNAPSHOT.jar")
@Processors(NotNullAnnotationProcessor.class)
class NotNullCheckerTest {

    @Test
    void checkReturnType(Results results) {
        // verify
        results.find().errors().forEach(System.out::println);
        assertThat(results.find().errors().count(), is(0));
    }
}