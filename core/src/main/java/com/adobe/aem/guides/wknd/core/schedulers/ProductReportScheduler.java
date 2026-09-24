package com.adobe.aem.guides.wknd.core.schedulers;

import java.util.List;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.core.models.Product;
import com.adobe.aem.guides.wknd.core.services.ProductService;

/**
 * Sling whiteboard scheduler: any Runnable service registered with a
 * "scheduler.expression" property is run on that cron. Here we periodically
 * read products (via the service, which uses the service-user resolver) and log.
 */
@Designate(ocd = ProductReportScheduler.Config.class)
@Component(service = Runnable.class, immediate = true)
public class ProductReportScheduler implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ProductReportScheduler.class);

    @ObjectClassDefinition(name = "WKND Product Report Scheduler")
    public @interface Config {
        // The underscore becomes a dot -> the service property "scheduler.expression"
        @AttributeDefinition(name = "Cron expression")
        String scheduler_expression() default "0 0/1 * * * ?"; // every minute

        @AttributeDefinition(name = "Allow concurrent runs")
        boolean scheduler_concurrent() default false;

        @AttributeDefinition(name = "Enabled")
        boolean enabled() default true;
    }

    @Reference
    private ProductService productService;

    private boolean enabled;

    @Activate
    @Modified
    protected void activate(Config config) {
        this.enabled = config.enabled();
    }

    @Override
    public void run() {
        if (!enabled) {
            return; // a simple kill-switch without unscheduling
        }
        // Delegates to the service (service-user resolver + repo read) - scheduler stays thin.
        List<Product> products = productService.getProductsAsService();
        String names = products.stream().map(Product::getName).collect(Collectors.joining(", "));
        LOG.info("[WKND Scheduler] Found {} products: {}", products.size(), names);
    }
}
