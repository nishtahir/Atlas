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
    public void setUp() throws Exception {
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

    @Test
    public void testGetActiveFile_WithNullActiveFile_ThrowsNullPointerException() {

        try {
            fileManagerInstance.getActiveFile();
            fail("Null pointer exception not thrown");
        } catch (NullPointerException npe) {

        }
    }

    @Test
    public void testGetActiveFile_WithActiveFileSet_ReturnsCorrectActiveFile() {
        String testFileName = "Sample";
        fileManagerInstance.setActiveFile(new File(testFileName));
        File file = fileManagerInstance.getActiveFile();
        String fileName = file.getName();
        assertEquals(testFileName, fileName);
    }

    @Test
    public void testSetActiveFile_WhenAttemptToSetToNull_ThrowsNullPointerException() throws Exception {
        try {
            fileManagerInstance.setActiveFile(null);
            fail("NullPointerException was not thrown");
        } catch (NullPointerException npe) {

        }
    }

    @Test
    public void testSaveFile() throws Exception {
    }

    @Test
    public void testSaveFileAs() throws Exception {

    }

    @Test
    public void testOpenFile_WithFileThatDoesNotExist_ThrowsFileNotFoundException() {
        File file = new File("fileThatDoesNotExist");
        try {
            fileManagerInstance.openFile(file);
            fail("Attempted to open a File that does not Exist");
        } catch (FileNotFoundException fnfe) {

        } catch (IOException ioe) {

        }
    }

    @Test
    public void testGetActiveFileName_WithNoActiveFile_ReturnsNull() throws Exception {
        String activeFileName = fileManagerInstance.getActiveFileName();
        assertNull(activeFileName);
    }

    @Test
    public void testGetActiveFileName_WithActiveFile_ReturnsCorrectFileName() throws Exception {
        File file = new File("TestFile");
        fileManagerInstance.setActiveFile(file);
        String activeFileName = fileManagerInstance.getActiveFileName();
        assertEquals(file.getName(), activeFileName);
    }
}