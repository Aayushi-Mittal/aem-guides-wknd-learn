package com.adobe.aem.guides.wknd.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.adobe.aem.guides.wknd.core.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * THIN servlet: it only deals with HTTP (routing, resolver, response). ALL the
 * "how to get products" logic lives in ProductService. This keeps the servlet
 * testable, the logic reusable, and each layer with one responsibility.
 */
@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "cq:Page",
        selectors = "productlist",
        extensions = "json",
        methods = HttpConstants.METHOD_GET)
public class ProductListServlet extends SlingSafeMethodsServlet {

    @Reference                       // inject the OSGi service
    private transient ProductService productService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Delegate to the service; the servlet adds no business logic.
        Object products = productService.getProducts(request.getResourceResolver());
        response.getWriter().write(new ObjectMapper().writeValueAsString(products));
    }
}
