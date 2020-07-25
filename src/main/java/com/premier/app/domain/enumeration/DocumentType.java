package com.premier.app.domain.enumeration;

/**
 * The DocumentType enumeration.
 */
public enum DocumentType {
    PROFILE("Profile"),
    BLOG("Blog"),
    PASSPORT("Passport"),
    CV("Cv"),
    ENTRY("Entry");

    private final String value;

    DocumentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
