package model;

import java.io.File;

/**
 * Created by tdk10 on 2/28/2016.
 */
public class FileSelection {

    private File file;

    private boolean includeSubDirectories;
    private boolean listSubDirectoriesIndependently;

    public File getFile() {
        return file;
    }

    public boolean includeSubDirectories() {
        return includeSubDirectories;
    }

    public boolean listSubDirectoriesIndependently(){
        return listSubDirectoriesIndependently;
    }
}
