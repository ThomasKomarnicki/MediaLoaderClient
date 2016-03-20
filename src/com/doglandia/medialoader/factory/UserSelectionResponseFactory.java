package com.doglandia.medialoader.factory;

import com.doglandia.medialoader.model.FileSelection;
import com.doglandia.medialoader.model.ResourceGroup;
import com.doglandia.medialoader.model.ResourcesResponse;
import com.doglandia.medialoader.model.UserFileSelections;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tdk10 on 3/19/2016.
 */
public class UserSelectionResponseFactory implements ResponseFactory {

    private UserFileSelections userFileSelections;

    private Gson gson;

    public UserSelectionResponseFactory(UserFileSelections userFileSelections){
        this.userFileSelections = userFileSelections;
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

    public ResourcesResponse createResponseFromFileSelection(){
        ResourcesResponse resourcesResponse = new ResourcesResponse();

        List<ResourceGroup> resourceGroups = new ArrayList<ResourceGroup>();

        if(userFileSelections != null) {
            for (FileSelection fileSelection : userFileSelections.getSelections()) {
                resourceGroups.addAll(createResourceGroupsFromFileSelection(fileSelection));
            }
        }

        resourcesResponse.setResourceGroups(resourceGroups);

        return resourcesResponse;

    }

    private List<ResourceGroup> createResourceGroupsFromFileSelection(FileSelection fileSelection){
        List<ResourceGroup> groups = new ArrayList<ResourceGroup>();

        // if should only include immediate video files in directory
        if(!fileSelection.includeSubDirectories()) {
            groups.add(new ResourceGroup(fileSelection.getFile().getPath(), fileSelection.getFile().getName(), false));
        }
        else if(fileSelection.includeSubDirectories()) {
            // else if should include all videos in subdirectories
            // add all files in parent and subdirectories to new group
            groups.add(new ResourceGroup(fileSelection.getFile().getPath(), fileSelection.getFile().getName(), true));

        }

        return groups;
    }
}
