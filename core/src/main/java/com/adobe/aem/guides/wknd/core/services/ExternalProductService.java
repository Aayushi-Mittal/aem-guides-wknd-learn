package com.adobe.aem.guides.wknd.core.services;

import com.adobe.aem.guides.wknd.core.models.Product;

/** Fetches a product from an external HTTP API. Never returns null. */
public interface ExternalProductService {
    Product getProduct(String id);
}
