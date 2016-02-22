package model;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * Created by tdk10 on 2/20/2016.
 */
public class MediaResource {

    private String name;
    private String location;

    transient private String extension;

    /**
     *
     * @param file
     * @param sourceDirectory The user chosen directory
     * @param linkPath name representing the sourceDirectory this file exists in
     */
    public MediaResource(File file, File sourceDirectory, String linkPath) {
        name = file.getName();
        location = "media/" +linkPath + getFilePathUnderSourceDir(file, sourceDirectory);

        extension = FilenameUtils.getExtension(file.getPath());
    }

    private String getFilePathUnderSourceDir(File file, File sourceDirectory){
//        String[] sections = file.getPath().split(sourceDirectory.getPath());
//        assert file.getPath().contains(sourceDirectory.getPath());
//        return sections[1];

        return file.getPath().replace(sourceDirectory.getPath(),"/").replace("\\","");
    }
}
