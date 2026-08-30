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

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.guides.wknd.core.models.EmployeeCard;

/**
 * Implementation of the {@link EmployeeCard} Sling Model.
 *
 * A Sling Model is a plain Java class that Sling can "adapt" a Resource (or a
 * request) into. The @Model annotation tells Sling: when someone asks to adapt
 * a {@link Resource} into an {@link EmployeeCard}, instantiate this class and
 * fill its @-injected fields from the resource's properties (the JCR node the
 * author's dialog saved).
 */
@Model(
        // We adapt from a Resource (the component's JCR node). Adapting from
        // Resource (rather than SlingHttpServletRequest) is enough here because
        // we only read stored properties; we need no request/page context.
        adaptables = { Resource.class },
        // The interface(s) this model can be adapted to. HTL's data-sly-use
        // asks for the interface, and Sling returns this implementation.
        adapters = { EmployeeCard.class },
        // Binds this model to a resource type so HTL can use the interface name
        // and Sling still resolves the correct impl.
        resourceType = { EmployeeCardImpl.RESOURCE_TYPE },
        // If a property is missing, inject null / leave default instead of
        // failing to construct the whole model. This is what makes missing
        // fields "safe" rather than fatal.
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class EmployeeCardImpl implements EmployeeCard {

    /** Must match the folder path under /apps for the component. */
    protected static final String RESOURCE_TYPE = "wknd/components/employee-card";

    // @ValueMapValue reads a single property from the resource's ValueMap.
    // By default it uses the field name ("name") as the property name, so this
    // reads the "name" property that the dialog saved via name="./name".
    @ValueMapValue
    private String name;

    @ValueMapValue
    private String designation;

    @ValueMapValue
    private String description;

    // The dialog stores the DAM path under "fileReference" (the standard
    // property name used by the pathfield below), so we override the default.
    @ValueMapValue(name = "fileReference")
    private String imagePath;

    @ValueMapValue
    private String linkedInUrl;

    // A checkbox stores a boolean. With OPTIONAL injection, an unchecked/absent
    // checkbox leaves this as the Java default: false.
    @ValueMapValue
    private boolean showLinkedIn;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDesignation() {
        return designation;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String getLinkedInUrl() {
        return linkedInUrl;
    }

    @Override
    public boolean isShowLinkedIn() {
        // Business rule lives in Java, not HTL: only show the button when the
        // author opted in AND actually provided a URL.
        return showLinkedIn && StringUtils.isNotBlank(linkedInUrl);
    }

    @Override
    public boolean isEmpty() {
        // With no name we consider the card empty and let HTL render a
        // placeholder so authors see a drop target in the editor.
        return StringUtils.isBlank(name);
    }
}
