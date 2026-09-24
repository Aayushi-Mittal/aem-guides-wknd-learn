package com.adobe.aem.guides.wknd.core.services.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.core.models.Product;
import com.adobe.aem.guides.wknd.core.services.ExternalProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(service = ExternalProductService.class)
@Designate(ocd = ExternalProductServiceConfig.class)
public class ExternalProductServiceImpl implements ExternalProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ExternalProductServiceImpl.class);

    private String apiBaseUrl;
    private int connectTimeout;
    private int readTimeout;

    @Activate
    @Modified
    protected void activate(ExternalProductServiceConfig config) {
        this.apiBaseUrl = config.apiBaseUrl();
        this.connectTimeout = config.connectTimeout();
        this.readTimeout = config.readTimeout();
    }

    @Override
    public Product getProduct(String id) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(apiBaseUrl + "/products/" + id);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(connectTimeout);   // don't hang forever on connect
            conn.setReadTimeout(readTimeout);          // ...or on read

            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                Map<String, Object> data = new ObjectMapper().readValue(readBody(conn), Map.class);
                return toProduct(data);
            }
            // 404 / 500 / anything non-200: log and fall back
            LOG.warn("External API returned {} for product {}", status, id);
            return fallback(id);
        } catch (Exception e) {
            // timeout, DNS, connection refused, bad JSON, ... -> never break the page
            LOG.error("External API call failed for product {}: {}", id, e.getMessage());
            return fallback(id);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private String readBody(HttpURLConnection conn) throws java.io.IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader r = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    /** Maps the external JSON shape to our Product. Pure logic -> unit-testable. */
    Product toProduct(Map<String, Object> data) {
        return new SimpleProduct(
                String.valueOf(data.get("title")),
                String.valueOf(data.get("price")),
                String.valueOf(data.get("category")));
    }

    /** Safe default so a failed call never breaks rendering. */
    Product fallback(String id) {
        return new SimpleProduct("Product " + id + " (unavailable)", "-", "-");
    }

    /** Plain, resource-free Product value object. */
    static final class SimpleProduct implements Product {
        private final String name;
        private final String price;
        private final String category;

        SimpleProduct(String name, String price, String category) {
            this.name = name;
            this.price = price;
            this.category = category;
        }

        @Override public String getName() { return name; }
        @Override public String getPrice() { return price; }
        @Override public String getCategory() { return category; }
    }
}
