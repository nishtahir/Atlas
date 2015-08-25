package com.wolfden.java.atlastext;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;

/**
 *
 */
public class FileManager {
    private static FileManager instance;
    private File activeFile;

    private FileManager() {
    }

    /**
     * @return
     */
    public static FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }
        return instance;
    }

    /**
     * @return instance of the file the that the user is working on
     */
    public File getActiveFile() {
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
    public void setActiveFile(File newFile) {
        if (newFile != null) {
            activeFile = newFile;
        } else {
            throw new NullPointerException("Active file cannot be null");
        }
    }

    /**
     * Saves the currently active file
     *
     * @throws IOException if file cannot be saved
     */
    public void saveFile(String data) throws IOException {
        if (activeFile != null) {
            FileWriter mFileWriter = new FileWriter(activeFile);
            mFileWriter.write(data);
            mFileWriter.close();
        } else {
            throw new NullPointerException("No active file");
        }
    }

    /**
     * @param path
     * @param data
     * @throws IOException
     */
    public void saveFileAs(String path, String data) throws IOException, FileAlreadyExistsException {
        activeFile = createFileFromPath(path);
        saveFile(data);
    }

    /**
     * @param path
     * @return
     * @throws IOException
     */
    public String openFile(String path) throws IOException,
            FileNotFoundException {
        return openFile(createFileFromPath(path));
    }

    /**
     * @param file
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    public String openFile(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        activeFile = file; // set the current file context
        BufferedInputStream inputStream = new BufferedInputStream(
                new FileInputStream(activeFile));
        StringBuilder contentBuffer = new StringBuilder();
        int c;
        while ((c = inputStream.read()) != -1) {
            contentBuffer.append((char) c);
        }
        inputStream.close();
        return contentBuffer.toString();
    }

    /**
     * @param path
     * @return
     * @throws FileAlreadyExistsException
     */
    private static File createFileFromPath(String path)
            throws FileAlreadyExistsException {
        File file = new File(path);
        if (file.exists()) {
            return file;
        } else {
            throw new FileAlreadyExistsException(
                    "A file already exists with that name");
        }
    }

    /**
     * @return
     */
    public String getActiveFileName() {
        return (activeFile != null) ? activeFile.getName() : null;
    }

}
