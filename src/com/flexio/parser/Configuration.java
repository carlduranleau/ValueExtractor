package com.flexio.parser;

public final class Configuration {

    private String dataSourceDirectory;

    private String rulesSourceDirectory;

    private String databaseSchema;

    private String databaseTable;

    private String databaseUsername;

    private String databasePassword;

    public String getDataSourceDirectory() {

        return this.dataSourceDirectory;
    }

    public void setDataSourceDirectory(
        final String dataSourceDirectory) {

        this.dataSourceDirectory = dataSourceDirectory;
    }

    public String getRulesSourceDirectory() {

        return this.rulesSourceDirectory;
    }

    public void setRulesSourceDirectory(
        final String rulesSourceDirectory) {

        this.rulesSourceDirectory = rulesSourceDirectory;
    }

    public String getDatabaseSchema() {

        return this.databaseSchema;
    }

    public void setDatabaseSchema(
        final String databaseSchema) {

        this.databaseSchema = databaseSchema;
    }

    public String getDatabaseTable() {

        return this.databaseTable;
    }

    public void setDatabaseTable(
        final String databaseTable) {

        this.databaseTable = databaseTable;
    }

    public String getDatabaseUsername() {

        return this.databaseUsername;
    }

    public void setDatabaseUsername(
        final String databaseUsername) {

        this.databaseUsername = databaseUsername;
    }

    public String getDatabasePassword() {

        return this.databasePassword;
    }

    public void setDatabasePassword(
        final String databasePassword) {

        this.databasePassword = databasePassword;
    }
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
		sb.append(String.format("\nData source directory: %s", this.dataSourceDirectory));
		sb.append(String.format("\nRules directory: %s", this.rulesSourceDirectory));
		sb.append(String.format("\nDatabase schema: %s", this.databaseSchema));
		sb.append(String.format("\nDatabase table: %s", this.databaseTable));
		sb.append(String.format("\nDatabase username: %s", this.databaseUsername));
		sb.append("\n");
		return sb.toString();
    }
}
