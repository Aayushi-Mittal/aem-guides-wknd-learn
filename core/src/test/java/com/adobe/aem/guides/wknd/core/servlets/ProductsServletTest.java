package com.adobe.aem.guides.wknd.core.servlets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

class ProductsServletTest {

    @Test
    void buildsExpectedData() {
        Map<String, Object> data = new ProductsServlet().buildData();
        assertEquals("AEM", data.get("name"));
        assertEquals("6.5", data.get("version"));
    }
}
