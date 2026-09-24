package com.adobe.aem.guides.wknd.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.adobe.aem.guides.wknd.core.models.Product;

class ExternalProductServiceImplTest {

    private final ExternalProductServiceImpl service = new ExternalProductServiceImpl();

    @Test
    void mapsExternalJsonToProduct() {
        Map<String, Object> data = new HashMap<>();
        data.put("title", "iPhone");
        data.put("price", 999);
        data.put("category", "smartphones");

        Product p = service.toProduct(data);
        assertEquals("iPhone", p.getName());
        assertEquals("999", p.getPrice());
        assertEquals("smartphones", p.getCategory());
    }

    @Test
    void fallbackNeverNull() {
        Product p = service.fallback("42");
        assertEquals("Product 42 (unavailable)", p.getName());
        assertEquals("-", p.getPrice());
    }
}
