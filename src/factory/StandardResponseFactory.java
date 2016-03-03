package factory;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.FileSelection;
import model.ResourceGroup;
import model.ResourcesResponse;
import model.UserFileSelections;
import org.apache.commons.io.FileUtils;

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

    private UserFileSelections loadUserFileSelection(){
        // load from file probably?

        String home = System.getProperty("user.home");
        File selectionsFile = new File(home+File.separator+"Documents"+File.separator+".ml_file_selections.json"); // todo change?
        try {
            UserFileSelections userFileSelections = gson.fromJson(readFile(selectionsFile.getPath()),UserFileSelections.class);
            return userFileSelections;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ResourcesResponse createResponseFromFileSelection(){
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
