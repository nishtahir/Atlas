package com.wolfden.java.atlas.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.wolfden.java.atlas.FileManager;
import com.wolfden.java.atlas.util.FileUtils;

public class TestFileUtils {
	private File testFile;
	private String testPath = "test/testFile.txt";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testFile = new File(testPath);
	}
	
	@Test
	public void testFileManagerNotNull(){
		assertNotNull(FileManager.getInstance());
	}

	@Test
	public void testGetFileNotNull() {
		File testFile = FileUtils.getFile(testPath);
		assertNotNull(testFile);
	}	
	
	@Test
	public void testGetFileContents() throws IOException {
		assertEquals("Test content", FileUtils.getFileContents(testFile));
	}	
}
