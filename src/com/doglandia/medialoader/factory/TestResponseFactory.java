package com.doglandia.medialoader.factory;

import com.doglandia.medialoader.model.ResourceGroup;
import com.doglandia.medialoader.model.ResourcesResponse;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tdk10 on 2/20/2016.
 */
public class TestResponseFactory implements ResponseFactory {

    private Gson gson;

    public TestResponseFactory(){
        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    @Override
    public String createGetResourcesResponse() {
        ResourcesResponse resourcesResponse = new ResourcesResponse();

        List<ResourceGroup> resourceGroups = new ArrayList<ResourceGroup>();
        resourceGroups.add(new ResourceGroup("C:/Users/tdk10/Downloads/test_dir","dir1"));

        resourcesResponse.setResourceGroups(resourceGroups);

        return gson.toJson(resourcesResponse, ResourcesResponse.class);

    }

    @Override
    public void doResourceResponse() {

    }
}
