package nl.cqit.validation.nullsafe.processor.internal;

import com.karuslabs.elementary.Results;
import com.karuslabs.elementary.junit.JavacExtension;
import com.karuslabs.elementary.junit.annotations.Module;
import com.karuslabs.elementary.junit.annotations.ModulePath;
import com.karuslabs.elementary.junit.annotations.ProcessorPath;
import com.karuslabs.elementary.junit.annotations.Processors;
import nl.cqit.validation.nullsafe.processor.NotNullAnnotationProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(JavacExtension.class)
@Module("test")
@ModulePath("nl.cqit.validation:null-safe-annotations:1.0-SNAPSHOT")
@ProcessorPath("nl.cqit.validation:null-safe-annotations:1.0-SNAPSHOT")
@Processors(NotNullAnnotationProcessor.class)
class NotNullCheckerTest {

    @Test
    void checkReturnType(Results results) {
        // verify
        results.find().errors().forEach(System.out::println);
        assertThat(results.find().errors().count(), is(0));
    }
}