package com.flexio.parser;

public final class Configuration {

    private String dataSourceDirectory;

    private String processedDirectory;

	private String rulesSourceDirectory;

	private String databaseHost;
	
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

    public String getProcessedDirectory() {
		return processedDirectory;
	}

	public void setProcessedDirectory(String processedDirectory) {
		this.processedDirectory = processedDirectory;
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
    
    public String getDatabaseHost() {
		return databaseHost;
	}

	public void setDatabaseHost(String databaseHost) {
		this.databaseHost = databaseHost;
	}

	public String toString() {
    	StringBuilder sb = new StringBuilder();
		sb.append(String.format("\nData source directory: %s", this.dataSourceDirectory));
		sb.append(String.format("\nProcessed data directory: %s", this.processedDirectory));
		sb.append(String.format("\nRules directory: %s", this.rulesSourceDirectory));
		sb.append(String.format("\nDatabase host: %s", this.databaseHost));
		sb.append(String.format("\nDatabase schema: %s", this.databaseSchema));
		sb.append(String.format("\nDatabase table: %s", this.databaseTable));
		sb.append(String.format("\nDatabase username: %s", this.databaseUsername));
		sb.append("\n");
		return sb.toString();
    }
}
