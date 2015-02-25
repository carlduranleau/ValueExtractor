package com.flexio.parser;

public class Rule {

    private String fieldName = null;
    private String pattern = null;
    private String type = null;

    public String getFieldName() {

        return this.fieldName;
    }

    public void setFieldName(
        final String fieldName) {

        this.fieldName = fieldName;
    }

    public String getPattern() {

        return this.pattern;
    }

    public void setPattern(
        final String pattern) {

        this.pattern = pattern;
    }

    public String getType() {

        return this.type;
    }

    public void setType(
        final String type) {

        this.type = type;
    }

    public Rule(final String pFieldName, final String pPattern) {
    	this(pFieldName, pPattern, null);
    }
    
    public Rule(final String pFieldName, final String pPattern, final String pType) {

        this.fieldName = pFieldName;
        this.pattern = pPattern;
        this.type = pType;
    }
}
