package com.adobe.aem.guides.wknd.core.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import com.adobe.aem.guides.wknd.core.models.Product;
import com.adobe.aem.guides.wknd.core.services.ProductService;

@Component(service = ProductService.class)
public class ProductServiceImpl implements ProductService {

    protected static final String BASE_PATH = "/content/wknd-demo/products";

    @Override
    public List<Product> getProducts(ResourceResolver resolver) {
        List<Product> products = new ArrayList<>();

        Resource root = resolver.getResource(BASE_PATH);
        if (root == null) {
            return products; // path missing -> empty, never null
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
