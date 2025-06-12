package com.neopragma.rpn;

import java.text.DecimalFormat;
import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import org.assertj.core.api.Assertions;

public class RPNPropertyTest {

    private static final DecimalFormat PLAIN_FORMAT
            = new DecimalFormat("0.########");

    @Property
    void singleNumericInputShouldSetStackTop(@ForAll double value) {
        RPN rpn = new RPN();
        String result = rpn.enter(PLAIN_FORMAT.format(value));
        Assertions.assertThat(Double.parseDouble(result)).isEqualTo(value);
    }

    @Property
    void additionShouldBeCorrect(
            @ForAll double a,
            @ForAll double b
    ) {
        RPN rpn = new RPN();
        rpn.enter(PLAIN_FORMAT.format(a));
        rpn.enter(PLAIN_FORMAT.format(b));
        String result = rpn.enter("+");
        Assertions.assertThat(Double.parseDouble(result)).isEqualTo(a + b);
    }

    @Property
    void subtractionShouldBeCorrect(@ForAll double a, @ForAll double b) {
        RPN rpn = new RPN();
        rpn.enter(PLAIN_FORMAT.format(a));
        rpn.enter(PLAIN_FORMAT.format(b));
        String result = rpn.enter("-");
        Assertions.assertThat(Double.parseDouble(result)).isEqualTo(a - b);
    }

    @Property
    void multiplicationShouldBeCorrect(@ForAll double a, @ForAll double b) {
        RPN rpn = new RPN();
        rpn.enter(PLAIN_FORMAT.format(a));
        rpn.enter(PLAIN_FORMAT.format(b));
        String result = rpn.enter("*");
        Assertions.assertThat(Double.parseDouble(result)).isEqualTo(a * b);
    }

    @Property
    void divisionShouldBeCorrect(@ForAll double a, @ForAll @DoubleRange(min = -10D, max = Double.MAX_VALUE) double b) {
        RPN rpn = new RPN();
        rpn.enter(PLAIN_FORMAT.format(a));
        rpn.enter(PLAIN_FORMAT.format(b));
        String result = rpn.enter("/");
        Assertions.assertThat(Double.parseDouble(result)).isCloseTo(a / b, Assertions.offset(10D));
    }

    @Property
    void modulusShouldBeCorrect(@ForAll double a, @ForAll @DoubleRange(min = -10D) double b) {
        RPN rpn = new RPN();
        rpn.enter(PLAIN_FORMAT.format(a));
        rpn.enter(PLAIN_FORMAT.format(b));
        String result = rpn.enter("%");
        Assertions.assertThat(Double.parseDouble(result)).isCloseTo(a % b, Assertions.offset(10D));
    }

    @Property
    void powerShouldBeCorrect(@ForAll double a, @ForAll double b) {
        RPN rpn = new RPN();
        rpn.enter(PLAIN_FORMAT.format(a));
        rpn.enter(PLAIN_FORMAT.format(b));
        String result = rpn.enter("p");
        Assertions.assertThat(Double.parseDouble(result)).isCloseTo(Math.pow(a, b), Assertions.offset(10D));
    }

    @Property
    void clearShouldResetStack(@ForAll double a) {
        RPN rpn = new RPN();
        rpn.enter(PLAIN_FORMAT.format(a));
        String result = rpn.enter("c");
        Assertions.assertThat(Double.parseDouble(result)).isEqualTo(0.0D);
    }

    @Property
    void invalidInputThrowsException(@ForAll @AlphaChars @NotBlank String invalid) {
        Assume.that(!invalid.matches("[-+*/%Pp]"));
        Assume.that(!invalid.matches("^-?\\d*\\.?\\d+$"));

        RPN rpn = new RPN();
        Throwable thrown = Assertions.catchThrowable(() -> rpn.enter(invalid));
        Assertions.assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unrecognized input value");
    }
}
