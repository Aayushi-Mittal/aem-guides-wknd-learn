/*
 *  Copyright 2024 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.adobe.aem.guides.wknd.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.guides.wknd.core.models.EmployeeCard;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class })
class EmployeeCardImplTest {

    private final AemContext ctx = new AemContext();

    @BeforeEach
    void setUp() {
        // Register our model class with the mock Sling runtime.
        ctx.addModelsForClasses(EmployeeCardImpl.class);
        // Load the JCR fixture into the mock repository under /content.
        ctx.load().json("/com/adobe/aem/guides/wknd/core/models/impl/EmployeeCardImplTest.json", "/content");
    }

    private EmployeeCard adapt(String path) {
        ctx.currentResource(path);
        return ctx.currentResource().adaptTo(EmployeeCard.class);
    }

    @Test
    void allPropertiesPresent() {
        EmployeeCard card = adapt("/content/full");
        assertEquals("Jane Doe", card.getName());
        assertEquals("Senior Engineer", card.getDesignation());
        assertEquals("Builds delightful things.", card.getDescription());
        assertEquals("/content/dam/wknd/jane.jpg", card.getImagePath());
        assertEquals("https://www.linkedin.com/in/jane", card.getLinkedInUrl());
        assertTrue(card.isShowLinkedIn());
        assertFalse(card.isEmpty());
    }

    @Test
    void emptyResourceIsEmpty() {
        EmployeeCard card = adapt("/content/empty");
        assertTrue(card.isEmpty());
    }

    @Test
    void missingNameIsEmpty() {
        EmployeeCard card = adapt("/content/no-name");
        assertTrue(card.isEmpty());
    }

    @Test
    void checkboxOnButNoUrlHidesButton() {
        EmployeeCard card = adapt("/content/checkbox-on-no-url");
        assertFalse(card.isShowLinkedIn());
    }

    @Test
    void urlPresentButCheckboxOffHidesButton() {
        EmployeeCard card = adapt("/content/url-but-checkbox-off");
        assertFalse(card.isShowLinkedIn());
    }
}
