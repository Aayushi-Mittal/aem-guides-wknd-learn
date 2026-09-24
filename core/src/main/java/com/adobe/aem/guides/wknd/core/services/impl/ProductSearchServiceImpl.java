package com.adobe.aem.guides.wknd.core.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.adobe.aem.guides.wknd.core.models.Product;
import com.adobe.aem.guides.wknd.core.services.ProductSearchService;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;

@Component(service = ProductSearchService.class)
public class ProductSearchServiceImpl implements ProductSearchService {

    protected static final String BASE_PATH = "/content/wknd-demo/products";

    @Reference
    private QueryBuilder queryBuilder;

    @Override
    public List<Product> search(ResourceResolver resolver, String category) {
        // QueryBuilder runs against a JCR Session, obtained from the resolver.
        Session session = resolver.adaptTo(Session.class);

        // A QueryBuilder query is just a map of "predicates" (key=value rules).
        Map<String, String> predicates = new HashMap<>();
        predicates.put("path", BASE_PATH);          // under this tree
        predicates.put("type", "nt:unstructured");   // of this node type
        if (StringUtils.isNotBlank(category)) {       // optional property match
            predicates.put("property", "category");
            predicates.put("property.value", category);
        }
        predicates.put("p.limit", "20");             // cap results (never unbounded)

        Query query = queryBuilder.createQuery(PredicateGroup.create(predicates), session);
        SearchResult result = query.getResult();

        List<Product> products = new ArrayList<>();
        // getResources() gives Resources via the query's resolver - no checked exceptions.
        Iterator<Resource> hits = result.getResources();
        while (hits.hasNext()) {
            Product product = hits.next().adaptTo(Product.class);
            if (product != null) {
                products.add(product);
            }
        }
        return products;
    }
}
