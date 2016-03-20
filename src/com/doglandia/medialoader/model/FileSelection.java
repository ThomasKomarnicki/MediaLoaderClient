package com.doglandia.medialoader.model;

import java.io.File;

/**
 * Created by tdk10 on 2/28/2016.
 */
public class FileSelection {

    private transient File file;

    private String filePath;

    private boolean includeSubDirectories;

    public File getFile() {
        if(file == null){
            file = new File(filePath);
        }
        return file;
    }

    public boolean includeSubDirectories() {
        return includeSubDirectories;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
        file = new File(filePath);
    }

    public void setIncludeSubDirectories(boolean includeSubDirectories) {
        this.includeSubDirectories = includeSubDirectories;
    }
}
