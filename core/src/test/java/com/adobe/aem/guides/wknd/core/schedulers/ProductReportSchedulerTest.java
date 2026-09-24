package com.adobe.aem.guides.wknd.core.schedulers;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.aem.guides.wknd.core.services.ProductService;

@ExtendWith(MockitoExtension.class)
class ProductReportSchedulerTest {

    @Mock private ProductService productService;
    private final ProductReportScheduler scheduler = new ProductReportScheduler();

    private ProductReportScheduler.Config config(boolean enabled) {
        ProductReportScheduler.Config c = org.mockito.Mockito.mock(ProductReportScheduler.Config.class);
        when(c.enabled()).thenReturn(enabled);
        return c;
    }

    @BeforeEach
    void inject() throws Exception {
        java.lang.reflect.Field f = ProductReportScheduler.class.getDeclaredField("productService");
        f.setAccessible(true);
        f.set(scheduler, productService);
    }

    @Test
    void runsWhenEnabled() {
        scheduler.activate(config(true));
        when(productService.getProductsAsService()).thenReturn(Collections.emptyList());
        scheduler.run();
        verify(productService, times(1)).getProductsAsService();
    }

    @Test
    void skipsWhenDisabled() {
        scheduler.activate(config(false));
        scheduler.run();
        verify(productService, never()).getProductsAsService();
    }
}
