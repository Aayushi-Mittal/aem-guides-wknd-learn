package com.adobe.aem.guides.wknd.core.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Registered by RESOURCE TYPE (not by path).
 * Only fires for: GET  +  resourceType wknd/components/page  +  selector "products"  +  extension "json".
 * So  page.products.json  hits this;  page.json  does NOT.
 */
@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "wknd/components/page",
        selectors = "products",
        extensions = "json",
        methods = HttpConstants.METHOD_GET)
public class ProductsServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(new ObjectMapper().writeValueAsString(buildData()));
    }

    /** Logic separated from I/O so it is trivially unit-testable. */
    Map<String, Object> buildData() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("name", "AEM");
        data.put("version", "6.5");
        return data;
    }
}
