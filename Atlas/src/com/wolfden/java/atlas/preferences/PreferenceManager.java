package com.wolfden.java.atlas.preferences;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.wolfden.java.atlas.util.FileUtils;

public class PreferenceManager {
	private static final String CONFIG_FILE = "config.properties";
	public static final String KEY_VERSION = "version";
	public static final String KEY_THEME = "theme";
	public static final String KEY_LANGUAGE = "language";
	public static final String KEY_WINDOW_HEIGHT = "windowHeight";
	public static final String KEY_WINDOW_WIDTH = "windowWidth";

	private static PreferenceManager instance;
	private Properties properties;
	InputStream input = null;

	/**
	 * 
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
		try {
			properties = FileUtils.getPropertiesFromFile(CONFIG_FILE);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Returns associated preference for the given key
	 * 
	 * @param key
	 *            preference to return
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


	public void setPreference(String key, int value) {
		setPreference(key, String.valueOf(value));
	}
	/**
	 * Save the preferences to file. This will overwrite existing preferences.
	 */
	public void commit() {
		try {
			FileUtils.writePropertiesTofile(properties, CONFIG_FILE);
		} catch (IOException e) {
			//TODO - Display error message 
			e.printStackTrace();
		}
	}


}
