package com.doglandia.medialoader.model;

import java.io.File;

/**
 * Created by tdk10 on 2/28/2016.
 */
public class FileSelection {

    private transient File file;

    private String directoryPath;

    private boolean includeSubDirs;

    public File getFile() {
        if(file == null){
            file = new File(directoryPath);
        }
        return file;
    }

    public boolean includeSubDirectories() {
        return includeSubDirs;
    }


}
