package com.wolfden.java.notetitan;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.custom.StyledText;

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
}
