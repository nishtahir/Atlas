package com.wolfden.java.atlastext;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;

import static junit.framework.TestCase.*;

/**
 * Created by Nish on 8/24/15.
 */
public class FileManagerTest {
    FileManager fileManagerInstance;

    @Before
    public void setUp(){
        fileManagerInstance = FileManager.getInstance();
    }

    /**
     * Resets FileManager instance before each test such that the tests do not affect each other.
     *
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @Before
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = FileManager.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void testGetInstance_DoesNotReturnNull() throws Exception {
        assertNotNull(fileManagerInstance);
    }

    @Test(expected = NullPointerException.class)
    public void testGetActiveFile_WithNullActiveFile_ThrowsNullPointerException() {
        fileManagerInstance.getActiveFile();
    }

    @Test
    public void testGetActiveFile_WithActiveFileSet_ReturnsCorrectActiveFile() {
        String testFileName = "Sample";
        fileManagerInstance.setActiveFile(new File(testFileName));
        File file = fileManagerInstance.getActiveFile();
        String fileName = file.getName();
        assertEquals(testFileName, fileName);
    }

    @Test(expected = NullPointerException.class)
    public void testSetActiveFile_WhenAttemptToSetToNull_ThrowsNullPointerException(){
        fileManagerInstance.setActiveFile(null);
    }

    @Test
    public void testSaveFile(){
    }

    @Test
    public void testSaveFileAs(){

    }

    @Test(expected = FileNotFoundException.class)
    public void testOpenFile_WithFileThatDoesNotExist_ThrowsFileNotFoundException() throws IOException {
        File file = new File("fileThatDoesNotExist");
        fileManagerInstance.openFile(file);
    }

    @Test
    public void testGetActiveFileName_WithNoActiveFile_ReturnsNull(){
        String activeFileName = fileManagerInstance.getActiveFileName();
        assertNull(activeFileName);
    }

    @Test
    public void testGetActiveFileName_WithActiveFile_ReturnsCorrectFileName(){
        File file = new File("TestFile");
        fileManagerInstance.setActiveFile(file);
        String activeFileName = fileManagerInstance.getActiveFileName();
        assertEquals(file.getName(), activeFileName);
    }
}