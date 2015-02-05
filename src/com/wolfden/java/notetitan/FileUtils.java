package com.wolfden.java.notetitan;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.custom.StyledText;


public class FileUtils {
	public static File getFile(String path){
		return new File(path);
	}
	
	public static String getFileContents(File file) throws IOException{
	    InputStream in = new BufferedInputStream(new FileInputStream(file));
	    StringBuffer buf = new StringBuffer();
	    int c;
	    while ((c = in.read()) != -1) {
	      buf.append((char) c);
		}
	    return buf.toString();
	}
	
	public void loadFileIntoEditor(File file, StyledText editor) throws IOException{
		String content = getFileContents(file);
		editor.setText(content);
	}
	
	public static void loadFileIntoEditor(String path, StyledText editor) throws IOException{
		String content = getFileContents(getFile(path));
		editor.setText(content);
	}
}
