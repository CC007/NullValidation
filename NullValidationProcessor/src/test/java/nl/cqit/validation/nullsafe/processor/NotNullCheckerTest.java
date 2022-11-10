package nl.cqit.validation.nullsafe.processor;

import com.karuslabs.elementary.junit.Labels;
import com.karuslabs.elementary.junit.Tools;
import com.karuslabs.elementary.junit.ToolsExtension;
import com.karuslabs.elementary.junit.annotations.Introspect;
import com.karuslabs.elementary.junit.annotations.Label;
import nl.cqit.validation.nullsafe.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Set;

@NotNull
@Introspect
@ExtendWith(ToolsExtension.class)
@Label("testType")
class NotNullCheckerTest {

    NotNullChecker notNullChecker = new NotNullChecker();
    
    @NotNull
    @Label("notNullType")
    class NotNullType {
        void methodWithNotNullType( NotNullType notNullType) {}
    }

    @Test
    void processRound_NotNullType(Labels labels) {
        Assertions.assertTrue(notNullChecker.checkNotNull(Set.copyOf(labels.all().values())));
    }
    
}
