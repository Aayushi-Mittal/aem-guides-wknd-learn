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
package com.adobe.aem.guides.wknd.core.models;

/**
 * Sling Model contract for the Employee Card component.
 *
 * The interface is the "view contract": it declares only what the HTL template
 * is allowed to read. The implementation (EmployeeCardImpl) decides HOW each
 * value is produced. Coding to an interface keeps HTL decoupled from the impl
 * and makes the model easy to mock in unit tests.
 */
public interface EmployeeCard {

    /** @return the employee's full name (required field). */
    String getName();

    /** @return the employee's job title / designation. */
    String getDesignation();

    /** @return a short description / bio. */
    String getDescription();

    /** @return the path of the employee's image in the DAM, or null if none. */
    String getImagePath();

    /** @return the employee's LinkedIn profile URL. */
    String getLinkedInUrl();

    /**
     * Derived flag. Returns true ONLY when the author both ticked the
     * "Show LinkedIn" checkbox AND supplied a non-blank URL. This keeps the
     * "should I render the button?" decision in Java, not in HTL.
     *
     * @return true if the LinkedIn button should be rendered.
     */
    boolean isShowLinkedIn();

    /**
     * @return true when the component has nothing meaningful to render, so the
     *         HTL can show an authoring placeholder instead of empty markup.
     */
    boolean isEmpty();
}
