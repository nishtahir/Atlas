package com.wolfden.java.atlastext.preferences;

import com.wolfden.java.atlastext.utils.FileUtils;

import java.io.*;
import java.util.Properties;


public class PreferenceManager {
    private static final String CONFIG_FILE_PATH = FileUtils.getSystemCompatiblePath("res/config.properties");
    private static final String DEFAULT_CONFIG = "\n" +
            "version=0.1\n" +
            "windowWidth=640\n" +
            "windowHeight=480\n" +
            "theme=melody\n" +
            "language=PlainText\n";

    public static final String KEY_VERSION = "version";
    public static final String KEY_THEME = "theme";
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_WINDOW_HEIGHT = "windowHeight";
    public static final String KEY_WINDOW_WIDTH = "windowWidth";

    private static PreferenceManager instance;
    private Properties properties;

    /**
     * @return
     */
    public static PreferenceManager getInstance() {
        if (instance == null) {
            instance = new PreferenceManager();
        }
        return instance;
    }

    /**
     * Initializes PreferenceManager. Must be called before PreferenceManager
     * can be used
     */
    public void init() {
        File configFile = new File(CONFIG_FILE_PATH);
        try {
            //Try to create the properties file if it doesn't already exist
            if(configFile.createNewFile()){
                properties = new Properties();
                properties.load(new StringReader(DEFAULT_CONFIG));
                FileUtils.writePropertiesToFile(properties, configFile);
                return;
            }
            //read contents from properties file
            properties = FileUtils.getPropertiesFromFile(CONFIG_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns associated preference for the given key
     *
     * @param key preference to return
     * @return value associated with the key
     */
    public String getPreference(String key) {
        if (properties == null) {
            throw new IllegalStateException(
                    "Preference manager has not been initialized with init()");
        }
        return properties.getProperty(key);
    }

    /**
     * Sets the value of a preference associated with the given key
     *
     * @param key
     * @param value
     */
    public void setPreference(String key, String value) {
        if (properties == null) {
            throw new IllegalStateException(
                    "Preference manager has not been initialized with init()");
        }

        properties.setProperty(key, value);
    }

    /**
     *
     * @param key
     * @param value
     */
    public void setPreference(String key, int value) {
        setPreference(key, String.valueOf(value));
    }

    /**
     * Save the preferences to file. This will overwrite existing preferences.
     */
    public void commit() {
        try {
            FileUtils.writePropertiesToFile(properties, CONFIG_FILE_PATH);
        } catch (IOException e) {
            //TODO - Display error message
            e.printStackTrace();
        }
    }
}
