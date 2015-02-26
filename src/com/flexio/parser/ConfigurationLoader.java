package com.flexio.parser;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationLoader {

    final static private String CONFIG_DATABASE_SCHEMA = "databaseSchema";

    final static private String CONFIG_DATABASE_TABLE = "databaseTable";

    final static private String CONFIG_DATABASE_USERNAME = "databaseUser";

    final static private String CONFIG_DATABASE_PASSWORD = "databasePassword";

    final static private String CONFIG_DATA_DIRECTORY = "sourceDataPath";

    final static private String CONFIG_PROCESSED_DIRECTORY = "processedDataPath";

    final static private String CONFIG_RULES_DIRECTORY = "sourceRulesPath";

    public static Configuration load(
        final String pConfigurationPath)
        throws Exception {

        if (pConfigurationPath == null || pConfigurationPath.equals("")) {
            throw new Exception("Invalid configuration path");
        }

        final Configuration config = new Configuration();
        FileInputStream fis = null;

        try {
            final Properties prop = new Properties();
            fis = new FileInputStream(pConfigurationPath);

            prop.load(fis);

            config.setDataSourceDirectory(prop.getProperty(ConfigurationLoader.CONFIG_DATA_DIRECTORY, ""));
            config.setProcessedDirectory(prop.getProperty(ConfigurationLoader.CONFIG_PROCESSED_DIRECTORY, ""));
            config.setRulesSourceDirectory(prop.getProperty(ConfigurationLoader.CONFIG_RULES_DIRECTORY, ""));
            config.setDatabaseSchema(prop.getProperty(ConfigurationLoader.CONFIG_DATABASE_SCHEMA, ""));
            config.setDatabaseTable(prop.getProperty(ConfigurationLoader.CONFIG_DATABASE_TABLE, ""));
            config.setDatabaseUsername(prop.getProperty(ConfigurationLoader.CONFIG_DATABASE_USERNAME, ""));
            config.setDatabasePassword(prop.getProperty(ConfigurationLoader.CONFIG_DATABASE_PASSWORD, ""));

        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        return config;
    }
}
