package com.wolfden.java.atlastext.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.regex.Matcher;

import org.eclipse.swt.custom.StyledText;

/**
 *
 */
public class FileUtils {
	private static InputStream inputStream;

	/**
	 * @param path
	 * @return file object of the given path
	 */
	public static File getFile(String path) {
		return new File(path);
	}

	/**
	 * 
	 * @param file
	 * @return String content contained in a given file
	 * @throws IOException
	 */
	public static String getFileContents(File file) throws IOException {
		inputStream = new BufferedInputStream(new FileInputStream(file));
		StringBuffer buf = new StringBuffer();
		int c;
		while ((c = inputStream.read()) != -1) {
			buf.append((char) c);
		}
		return buf.toString();
	}

	/**
	 * 
	 * @param file
	 * @param editor
	 * @throws IOException
	 */
	public void loadFileIntoEditor(File file, StyledText editor)
			throws IOException {
		String content = getFileContents(file);
		editor.setText(content);
	}

	/**
	 * 
	 * @param path
	 * @param editor
	 * @throws IOException
	 */
	public static void loadFileIntoEditor(String path, StyledText editor)
			throws IOException {
		String content = getFileContents(getFile(path));
		editor.setText(content);
	}

	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static Properties getPropertiesFromFile(String file)
			throws IOException {
		InputStream inputStream = new FileInputStream(file);
		Properties props = new Properties();
		props.load(inputStream);
		inputStream.close();
		return props;
	}

	/**
     *
     * @param properties
     * @param file
     * @throws IOException
     */
    public static void writePropertiesToFile(Properties properties, String file)
            throws IOException {
        OutputStream output = new FileOutputStream(file);
        writePropertiesToFile(properties, output);
    }

    /**
     *
     * @param properties
     * @param file
     * @throws IOException
     */
    public static void writePropertiesToFile(Properties properties, File file)
            throws IOException {
        OutputStream output = new FileOutputStream(file);
        writePropertiesToFile(properties, output);
    }

    private static void writePropertiesToFile(Properties properties, OutputStream output) throws IOException {
        properties.store(output, null);
        output.close();
    }

    /**
     * @param path path separated with '/'
     * @return path compliant with local file system
     */
    public static String getSystemCompatiblePath(String path){
        return path.replaceAll("//", Matcher.quoteReplacement(File.separator));
    }
}
