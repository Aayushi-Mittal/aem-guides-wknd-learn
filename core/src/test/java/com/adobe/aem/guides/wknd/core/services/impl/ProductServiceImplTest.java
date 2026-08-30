package com.adobe.aem.guides.wknd.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.guides.wknd.core.models.Product;
import com.adobe.aem.guides.wknd.core.models.impl.ProductImpl;
import com.adobe.aem.guides.wknd.core.services.ProductService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class ProductServiceImplTest {

    private final AemContext ctx = new AemContext();
    private ProductService service;

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(ProductImpl.class);
        service = ctx.registerInjectActivateService(new ProductServiceImpl());
    }

    @Test
    void readsProductsFromRepository() {
        ctx.build().resource("/content/wknd-demo/products")
                .siblingsMode()
                .resource("p1", "name", "Tent", "price", "199", "category", "camping")
                .resource("p2", "name", "Boots", "price", "89", "category", "hiking");

        List<Product> products = service.getProducts(ctx.resourceResolver());
        assertEquals(2, products.size());
        assertEquals("Tent", products.get(0).getName());
        assertEquals("hiking", products.get(1).getCategory());
    }

    @Test
    void emptyWhenPathMissing() {
        ctx.build().resource("/content/nothing-here");
        assertEquals(0, service.getProducts(ctx.resourceResolver()).size());
    }
}
