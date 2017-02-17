package com.shepherdjerred.pmcbump.config;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesConfig implements Config {

    private final static Logger LOGGER = Logger.getLogger(PropertiesConfig.class.getName());

    private String username;
    private String password;
    private String resourceId;
    private String memberId;

    private Properties properties = new Properties();

    public void loadConfig() {
        createDefaultPropertiesFiles();
        loadProperties();
        loadValuesFromProperties();
    }

    private void createDefaultPropertiesFiles() {
        File file = new File("config.properties");
        if (!file.exists()) {
            try (OutputStream outputFile = new FileOutputStream("config.properties"); InputStream defaultProperties = getClass().getResourceAsStream("/config.properties")) {
                properties.load(defaultProperties);
                properties.store(outputFile, null);
                LOGGER.log(Level.INFO, "Default config.properties file created. Edit the config with your information and re-run this application");
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadProperties() {
        try (InputStream inputFile = new FileInputStream("config.properties")) {
            properties.load(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadValuesFromProperties() {
        username = properties.getProperty("username");
        password = properties.getProperty("password");
        resourceId = properties.getProperty("resourceId");
        memberId = properties.getProperty("memberId");

        LOGGER.log(Level.FINER, username);
        LOGGER.log(Level.FINER, password);
        LOGGER.log(Level.FINER, resourceId);
        LOGGER.log(Level.FINER, memberId);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getMemberId() {
        return memberId;
    }

}
