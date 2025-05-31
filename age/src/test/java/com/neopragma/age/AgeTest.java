package com.neopragma.age;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AgeTest {

    private Age age;

    @BeforeEach
    public void beforeEachExample() {
        this.age = new Age();
    }

    // Sample test case showing the Arrange, Act, Assert pattern
    @Test
    public void an_8_yo_is_a_child() {
        // Arrange - set up the preconditions for the example
        String expected = "Child";

        // Act - invoke the SUT
        String actual = age.categorizeByAge(8);

        // Assert - check the result
        assertEquals(expected, actual);
    }

    // Sample test case without the comments
    @Test
    public void a_10_yo_is_an_adolescent() {
        String expected = "Adolescent";
        String actual = age.categorizeByAge(10);
        assertEquals(expected, actual);
    }

    // Sample test case compressed to a single line
    @Test
    public void a_70_yo_is_in_a_golden_age() {
        assertEquals("Golden Age", age.categorizeByAge(70));
    }

    // Sample test case showing how to define multiple examples
    // that have the same pattern but have different input values
    // and expected results.
    // This is a parameterized test case using CsvSource.
    @ParameterizedTest
    @CsvSource({"8,Child", "10, Adolescent", "21, Adult", "70, Golden Age"})
    public void check_age_categories_using_csv_source(String input, String expected) {
        int inputValue = Integer.parseInt(input);
        assertEquals(expected, age.categorizeByAge(inputValue));
    }

    @ParameterizedTest
    @MethodSource("provideValuesForAgeTest")
    public void check_age_categories_using_method_source(int inputValue, String expected) {
        assertEquals(expected, age.categorizeByAge(inputValue));
    }
    private static Stream<Arguments> provideValuesForAgeTest() {
        return Stream.of(
                Arguments.of(8, "Child"),
                Arguments.of(10, "Adolescent"),
                Arguments.of(21, "Adult"),
                Arguments.of(70, "Golden Age")
        );
    }
}
