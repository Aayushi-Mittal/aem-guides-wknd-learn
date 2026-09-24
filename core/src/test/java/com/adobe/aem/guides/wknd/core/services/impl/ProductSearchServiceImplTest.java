package com.adobe.aem.guides.wknd.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.aem.guides.wknd.core.models.Product;
import com.adobe.aem.guides.wknd.core.models.impl.ProductImpl;
import com.adobe.aem.guides.wknd.core.services.ProductSearchService;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ProductSearchServiceImplTest {

    private final AemContext ctx = new AemContext();

    @Mock private QueryBuilder queryBuilder;
    @Mock private Query query;
    @Mock private SearchResult result;

    private ProductSearchService service;

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(ProductImpl.class);
        ctx.registerService(QueryBuilder.class, queryBuilder);
        service = ctx.registerInjectActivateService(new ProductSearchServiceImpl());

        // two product resources the "query" will return
        Resource r1 = ctx.create().resource("/content/wknd-demo/products/p1",
                "name", "Boots", "price", "89", "category", "hiking");
        Resource r2 = ctx.create().resource("/content/wknd-demo/products/p2",
                "name", "Backpack", "price", "129", "category", "hiking");

        when(queryBuilder.createQuery(any(), any())).thenReturn(query);
        when(query.getResult()).thenReturn(result);
        when(result.getResources()).thenReturn(Arrays.asList(r1, r2).iterator());
    }

    @Test
    void mapsQueryHitsToProducts() {
        List<Product> products = service.search(ctx.resourceResolver(), "hiking");
        assertEquals(2, products.size());
        assertEquals("Boots", products.get(0).getName());
    }
}
