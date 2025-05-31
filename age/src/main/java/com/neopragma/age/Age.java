package com.neopragma.age;

public class Age {

    private static final String INVALID_AGE_MESSAGE =  "Invalid age: %s";

    public String categorizeByAge(int age) {
        if (age > 0 && age <= 9) return "Child";
        if (age > 9 && age <= 18) return "Adolescent";
        if (age > 18 && age <= 65) return "Adult";
        if (age > 65 && age <= 150) return "Golden Age";
        throw new RuntimeException(
                String.format(INVALID_AGE_MESSAGE, age));
    }
}
