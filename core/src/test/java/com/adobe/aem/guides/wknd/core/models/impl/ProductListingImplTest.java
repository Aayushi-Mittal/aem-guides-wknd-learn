package com.adobe.aem.guides.wknd.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.aem.guides.wknd.core.models.ProductListing;
import com.adobe.aem.guides.wknd.core.services.ProductSearchService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ProductListingImplTest {

    private final AemContext ctx = new AemContext();

    @Mock private ProductSearchService searchService;

    @BeforeEach
    void setUp() {
        ctx.registerService(ProductSearchService.class, searchService);
        ctx.addModelsForClasses(ProductListingImpl.class);
        ctx.build().resource("/content/pl",
                "sling:resourceType", "wknd/components/product-listing",
                "title", "Hiking Gear",
                "category", "hiking");
        ctx.currentResource("/content/pl");
    }

    @Test
    void delegatesToSearchServiceWithCategory() {
        when(searchService.search(any(), eq("hiking"))).thenReturn(Collections.emptyList());

        ProductListing model = ctx.currentResource().adaptTo(ProductListing.class);
        assertEquals("Hiking Gear", model.getTitle());
        model.getProducts();

        verify(searchService).search(any(), eq("hiking"));
    }
}
