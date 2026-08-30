package com.adobe.aem.guides.wknd.core.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.core.models.Product;
import com.adobe.aem.guides.wknd.core.services.ProductService;

@Component(service = ProductService.class)
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    protected static final String BASE_PATH = "/content/wknd-demo/products";
    /** Must match the left side of the user.mapping config. */
    protected static final String SUBSERVICE = "product-read";

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    public List<Product> getProducts(ResourceResolver resolver) {
        return readProducts(resolver);
    }

    @Override
    public List<Product> getProductsAsService() {
        Map<String, Object> params = Collections.singletonMap(
                ResourceResolverFactory.SUBSERVICE, SUBSERVICE);

        // try-with-resources GUARANTEES the resolver is closed (it is Closeable).
        try (ResourceResolver resolver = resolverFactory.getServiceResourceResolver(params)) {
            return readProducts(resolver);
        } catch (LoginException e) {
            // Mapping/user missing -> fail safe, don't break the caller.
            LOG.error("Could not get service resolver for subservice {}", SUBSERVICE, e);
            return Collections.emptyList();
        }
    }

    private List<Product> readProducts(ResourceResolver resolver) {
        List<Product> products = new ArrayList<>();
        Resource root = resolver.getResource(BASE_PATH);
        if (root == null) {
            return products; // path missing OR not readable by this user -> empty
        }
        for (Resource child : root.getChildren()) {
            Product product = child.adaptTo(Product.class);
            if (product != null) {
                products.add(product);
            }
        }
        return products;
    }
}
