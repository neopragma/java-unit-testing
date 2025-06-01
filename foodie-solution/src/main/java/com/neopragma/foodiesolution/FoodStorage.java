package com.neopragma.foodiesolution;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class FoodStorage {

    private final String connectionString = "jdbc:sqlite:../data/food4thot";
    private final static String SEPARATOR = ", ";
    private String[] retrievedData;

    protected void store(String productId, String productInfoAsJSON) {
        JSONObject infoObject = new JSONObject(productInfoAsJSON);
        String productStatus = infoObject.getString("status_verbose");
        if (productStatus.equals("product not found")) {
            System.out.println("No product found for productId " + productId);
        }
        JSONObject productInfo = infoObject.getJSONObject("product");
        String keywords = productInfo.getJSONArray("_keywords").toString();
        JSONArray ingredients = productInfo.getJSONArray("ingredients");
        JSONObject mainIngredientInfo = ingredients.getJSONObject(0);
        String mainIngredient = mainIngredientInfo.getString("id");
        String processing = mainIngredientInfo.getString("processing");
        String vegan = mainIngredientInfo.getString("vegan");
        boolean isVegan = vegan.equals("yes");

        String insertStatement = "INSERT INTO food_items(" +
                "product_id, product_status, keywords, main_ingredient," +
                "processing, is_vegan) " +
                "VALUES ("
                + quoted(productId) +
                SEPARATOR +
                quoted(productStatus) +
                SEPARATOR +
                quoted(keywords) +
                SEPARATOR +
                quoted(mainIngredient) +
                SEPARATOR +
                quoted(processing) +
                SEPARATOR +
                isVegan + ")";

        try (var conn = DriverManager.getConnection(connectionString);
             var preparedStatement = conn.prepareStatement(insertStatement)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected String[] retrieve(String productId) {
        var selectStatement =
                "SELECT product_id, product_status, keywords, main_ingredient, processing, is_vegan FROM food_items WHERE product_id = " + quoted(productId);
        try (var conn = DriverManager.getConnection(connectionString);
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(selectStatement)) {
            retrievedData = null;
            if (rs.next()) {
                retrievedData = new String[6];
                retrievedData[0] = rs.getString("product_id");
                retrievedData[1] = rs.getString("product_status");
                retrievedData[2] = rs.getString("keywords");
                retrievedData[3] = rs.getString("main_ingredient");
                retrievedData[4] = rs.getString("processing");
                boolean isVegan = rs.getBoolean("is_vegan");
                retrievedData[5] = isVegan ? "Yes" : "No";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retrievedData;
    }

    private String quoted(String unquotedValue) {
        return "'" + unquotedValue + "'";
    }

}
