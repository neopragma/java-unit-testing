package com.neopragma.rpn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RPNTest {

    private RPN rpn;

    @BeforeEach
    public void initializeCalculator() {
        this.rpn = new RPN();
    }

    @Test
    public void it_returns_the_last_number_entered() {
        rpn.enter("5");
        assertEquals("2.0", rpn.enter("2"));
    }

    @Test
    public void it_adds_2_numbers_and_returns_the_result() {
        rpn.enter("5.987");
        rpn.enter("2.0005");
        Double result = Double.parseDouble(rpn.enter("+"));
        assertEquals(7.9875, result, 0.2D);
    }

    @Test
    public void it_subtracts_2_numbers_and_returns_the_result() {
        rpn.enter("5.4");
        rpn.enter("3.1");
        Double result = Double.parseDouble(rpn.enter("-"));
        assertEquals(2.3, result, 0.2D);
    }

    @Test
    public void it_multiplies_2_numbers_and_returns_the_result() {
        rpn.enter("15.01");
        rpn.enter("6.4");
        Double result = Double.parseDouble(rpn.enter("*"));
        assertEquals(96.064, result, 0.2D);
    }

    @Test
    public void it_divides_2_numbers_and_returns_the_result() {
        rpn.enter("15");
        rpn.enter("3");
        Double result = Double.parseDouble(rpn.enter("/"));
        assertEquals(5, result, 0.2D);

    }

    @Test
    public void it_returns_the_modulus_of_2_numbers() {
        rpn.enter("100");
        rpn.enter("7");
        Double result = Double.parseDouble(rpn.enter("%"));
        assertEquals(2, result, 0.2D);
    }

    @Test
    public void it_performs_exponentiation_and_returns_the_result() {
        rpn.enter("10");
        rpn.enter("3");
        Double result = Double.parseDouble(rpn.enter("P"));
        assertEquals(1000, result, 0.2D);
    }

    @Test
    public void it_performs_complex_expressions_1() {
        rpn.enter("4");
        rpn.enter("13");
        rpn.enter("-");
        rpn.enter("10");
        Double result = Double.parseDouble(rpn.enter("*"));
        assertEquals(-90, result, 0.2D);
    }

    @Test
    public void it_performs_complex_expressions_2() {
        rpn.enter("4");
        rpn.enter("7.5");
        rpn.enter("2");
        rpn.enter("-");
        Double result = Double.parseDouble(rpn.enter("*"));
        assertEquals(22, result, 0.2D);
    }

    @Test
    public void it_throws_IllegalArgumentException_when_input_is_not_valid() {
        Exception thrown = assertThrows(IllegalArgumentException.class, () -> {
            rpn.enter("a");
        });
        assertEquals("Unrecognized input value: a", thrown.getMessage());
    }

    @Test
    public void C_command_clears_the_calculator_memory() {
        rpn.enter("5");
        rpn.enter("2");
        Double result = Double.parseDouble(rpn.enter("C"));
        assertEquals(0.0, result, 0.2D);
    }
}
