package nl.cqit.validation.nullsafe.processor.internal.nullstate;

import com.karuslabs.elementary.junit.Labels;
import com.karuslabs.elementary.junit.Tools;
import com.karuslabs.elementary.junit.ToolsExtension;
import com.karuslabs.elementary.junit.annotations.Introspect;
import com.karuslabs.elementary.junit.annotations.Label;
import nl.cqit.validation.nullsafe.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.lang.model.element.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@NotNull
@Introspect
@ExtendWith(ToolsExtension.class)
class NullStatesTest {

    NullStates nullStates = new NullStates(Tools.trees(), Tools.elements());
    
    @NotNull
    class NotNullType {
        void methodWithNotNullType(@Label("notNullType") NotNullType notNullType) {}
    }

    @Test
    void getNullState_NotNullType(Labels labels) {
        // prepare

        // execute
        var actual = nullStates.getNullState((VariableElement) labels.get("notNullType"));

        // verify
        assertThat(actual, is(NullState.NOT_NULL));
    }
    
}
