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

import com.adobe.aem.guides.wknd.core.services.ProductSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;

/** GET page.productsearch.json?category=hiking */
@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "cq:Page",
        selectors = "productsearch",
        extensions = "json",
        methods = HttpConstants.METHOD_GET)
public class ProductSearchServlet extends SlingSafeMethodsServlet {

    @Reference
    private transient ProductSearchService searchService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String category = request.getParameter("category"); // may be null -> return all
        Object products = searchService.search(request.getResourceResolver(), category);
        response.getWriter().write(new ObjectMapper().writeValueAsString(products));
    }
}
