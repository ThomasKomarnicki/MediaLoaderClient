package com.doglandia.medialoader.factory;

import com.doglandia.medialoader.handlers.MediaResourceHandler;
import com.doglandia.medialoader.model.FileSelection;
import com.doglandia.medialoader.model.ResourceGroup;
import com.doglandia.medialoader.model.ResourcesResponse;
import com.doglandia.medialoader.model.UserFileSelections;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tdk10 on 2/20/2016.
 */
public class StandardResponseFactory implements ResponseFactory {

    private Gson gson;

    private MediaResourceHandler mediaResourceHandler;

    public StandardResponseFactory(MediaResourceHandler mediaResourceHandler){
        this.mediaResourceHandler = mediaResourceHandler;
        gson = new GsonBuilder().create();
    }


    @Override
    public String createGetResourcesResponse() {
        ResourcesResponse resourcesResponse = createResponseFromFileSelection();
        if(mediaResourceHandler != null) {
            mediaResourceHandler.setResourceResponse(resourcesResponse);
        }

        return gson.toJson(resourcesResponse, ResourcesResponse.class);
    }

    @Override
    public void doResourceResponse() {


    }

    private static File getSelectionsFile(){
        String home = System.getProperty("user.home");
        return new File(home+File.separator+"AppData"+File.separator+"Roaming"+File.separator+"MediaLoader"+File.separator+"user_file_selections.json");
    }

    private UserFileSelections loadUserFileSelection(){

        File selectionsFile =  getSelectionsFile();
        try {
            UserFileSelections userFileSelections = gson.fromJson(readFile(selectionsFile.getPath()),UserFileSelections.class);
            return userFileSelections;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResourcesResponse createResponseFromFileSelection(){
        ResourcesResponse resourcesResponse = new ResourcesResponse();

        List<ResourceGroup> resourceGroups = new ArrayList<ResourceGroup>();

        UserFileSelections userFileSelections = loadUserFileSelection();
        if(userFileSelections != null) {
            for (FileSelection fileSelection : userFileSelections.getSelections()) {
                resourceGroups.addAll(createResourceGroupsFromFileSelection(fileSelection));
            }
        }

        resourcesResponse.setResourceGroups(resourceGroups);

        return resourcesResponse;

    }

    // need better way to determine names and groups
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

    private String readFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader( new FileReader(path));;
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while( ( line = reader.readLine() ) != null ) {
                stringBuilder.append( line );
                stringBuilder.append( ls );
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }
}
