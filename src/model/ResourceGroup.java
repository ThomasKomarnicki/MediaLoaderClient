package model;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tdk10 on 2/20/2016.
 */
public class ResourceGroup {

    transient private String[] extensions = new String[]{"mp4","avi","mov"};

    private String name;
    private String groupName;

    private List<MediaResource> resourceList;

    /**
     *
     * @param fullPath local path to the directory
     * @param groupName fake location of the file, used to identify this group
     */

    public ResourceGroup(String fullPath, String groupName){
        this.groupName = groupName;
        File directory = new File(fullPath);
        this.name = directory.getName();
        resourceList = new ArrayList<MediaResource>();

        for(Object object : FileUtils.listFiles(directory, extensions, true)){
            File file = (File) object;
            resourceList.add(new MediaResource(file,directory, groupName));
        }

    }
}
