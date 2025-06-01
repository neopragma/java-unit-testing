package com.neopragma.foodiesolution;

import org.junit.jupiter.api.Test;
import java.net.http.HttpRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoodieApiTests {

    @Test
    public void the_HttpRequest_has_method_GET() {
        HttpRequest request = new Foodie().createRequest("fake product code");
        assertEquals("GET", request.method());
    }

    @Test
    public void the_HttpRequest_specifies_HTTPS() {
        HttpRequest request = new Foodie().createRequest("fake product code");
        assertEquals("https", request.uri().getScheme());
    }

    @Test
    public void the_product_id_is_urlencoded() {
        HttpRequest request = new Foodie().createRequest("fake product code");
        String path = request.uri().getPath();
        String[] segments = path.split("/");
        String lastSegment = segments[segments.length - 1];
        assertEquals("fake+product+code", lastSegment);
    }
}
