package factory;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.FileSelection;
import model.ResourceGroup;
import model.ResourcesResponse;
import model.UserFileSelections;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tdk10 on 2/20/2016.
 */
public class StandardResponseFactory implements ResponseFactory {

    private Gson gson;

    public StandardResponseFactory(){
        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }


    @Override
    public String createGetResourcesResponse() {
        ResourcesResponse resourcesResponse = createResponseFromFileSelection();

        return gson.toJson(resourcesResponse, ResourcesResponse.class);
    }

    @Override
    public void doResourceResponse() {

    }

    private ResourcesResponse createResponseFromFileSelection(UserFileSelections userFileSelections){
        ResourcesResponse resourcesResponse = new ResourcesResponse();

        List<ResourceGroup> resourceGroups = new ArrayList<ResourceGroup>();

        for(FileSelection fileSelection : userFileSelections.getSelections()){

        }
        resourceGroups.add(new ResourceGroup("C:/Users/tdk10/Downloads/test_dir","dir1"));

        resourcesResponse.setResourceGroups(resourceGroups);

        return resourcesResponse;

    }
    
    private List<ResourceGroup> createResourceGroupsFromFileSelection(FileSelection fileSelection){
        List<ResourceGroup> groups = new ArrayList<>();
        
        // if should only include immediate video files in directory
            groups.add(new ResourceGroup(fileSelection.getFile(),fileSelection.getFile().getName()));
        
        // else if should include all videos in subdirectories
            // add all files in parent and subdirectories to new group
            
        // else if should include all files but in different directories
            
        
        return groups;
    }
}
