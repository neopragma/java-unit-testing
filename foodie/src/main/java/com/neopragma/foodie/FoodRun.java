package com.neopragma.foodie;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodRun {

    public static void main(String[] args) {
        String productCode = null;
        if (args.length > 0) {
            productCode = args[0];
        } else {
            System.out.println("Please provide a product code.");
            System.exit(1);
        }
        Foodie foodie = new Foodie(productCode);
        String productInfoAsJSON = foodie.getProductInfo();
        FoodStorage foodStorage = new FoodStorage();
        foodStorage.store(productCode, productInfoAsJSON);
        String[] foodData = foodStorage.retrieve(productCode);
        if (foodData == null) {
            System.out.println("No information about product " + productCode + " is in the database.");
            System.exit(1);
        }
        System.out.println("Product Id: " + foodData[0]);
        System.out.println("Product Status: " + foodData[1]);
        // Keywords are stored in the database like this: ["alpha","beta"].
        // We want to pull out the individual keywords without any quotation marks or brackets.
        String keywordListAsString =
                foodData[2].replaceAll("(^\\[)|(\\]$)|\"","");
        String[] keywordArray = keywordListAsString.split(",");
        for (String keyword : keywordArray) {
            System.out.println("- " + keyword);
        }
        System.out.println("Main Ingredient: " + foodData[3].split(":")[1]);
        System.out.println("Processing: " + foodData[4].split(":")[1]);
        System.out.println("Is Vegan? " + foodData[5]);

        System.exit(0);
    }
}
