package com.adobe.aem.guides.wknd.core.services;

import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;

import com.adobe.aem.guides.wknd.core.models.Product;

/** Business/repository layer: knows HOW to read products. */
public interface ProductService {

    /** Reads using a caller-provided (e.g. request) resolver. */
    List<Product> getProducts(ResourceResolver resolver);

    /** Reads using the service's OWN service-user resolver (no request needed). */
    List<Product> getProductsAsService();
}
