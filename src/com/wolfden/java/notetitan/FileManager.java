package com.wolfden.java.notetitan;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

	private static File activeFile;
	private static FileManager instance;

	static {
		try {
			instance = new FileManager();
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public FileManager() {

	}

	public static FileManager getInstance() {
		return instance;
	}

	/**
	 * @return instance of the file the that the user is working on
	 */
	public static File getActiveFile() {
		if (activeFile != null) {
			return activeFile;
		} else {
			throw new NullPointerException("No active file");
		}
	}

	/**
	 * Set the file in which the user is editing
	 * 
	 * @param newFile
	 */
	public static void setActiveFile(File newFile) {
		if (newFile != null) {
			activeFile = newFile;
		} else {
			throw new NullPointerException("Active file cannot be null");
		}
	}

	/**
	 * Saves the currently active file
	 * 
	 * @throws IOException
	 *             if file cannot be saved
	 */
	public static void saveFile(String data) throws IOException {
		if (activeFile != null) {
			FileWriter mFileWriter = new FileWriter(activeFile);
			mFileWriter.write(data);
			mFileWriter.close();
		} else {
			throw new NullPointerException("No active file");
		}
	}
	

	public static void saveFileAs(String path, String data) throws IOException {
		activeFile = createFileFromPath(path);
		saveFile(data);
	}

	public static String openFile(String path) throws IOException {
		return openFile(createFileFromPath(path));
	}

	public static String openFile(File file) throws IOException {
		activeFile = file; // set the current file context
		BufferedInputStream inputStream = new BufferedInputStream(
				new FileInputStream(activeFile));
		StringBuffer buf = new StringBuffer();
		int c;
		while ((c = inputStream.read()) != -1) {
			buf.append((char) c);
		}
		inputStream.close();
		return buf.toString();
	}

	private static File createFileFromPath(String path) throws IOException {
		File file = new File(path);
//		if (file.exists())
			return file;
//		else
//			throw new IOException();
	}
	
	public static String getFileName(){
		return (activeFile != null) ? activeFile.getName() : null;
	}

}
