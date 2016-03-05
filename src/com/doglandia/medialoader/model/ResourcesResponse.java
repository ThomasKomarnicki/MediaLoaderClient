package com.doglandia.medialoader.model;

import com.doglandia.medialoader.model.ResourceGroup;

import java.util.List;

/**
 * Created by tdk10 on 2/20/2016.
 */
public class ResourcesResponse {

    List<ResourceGroup> resourceGroups;

    public ResourcesResponse(){

    }

    public void setResourceGroups(List<ResourceGroup> resourceGroups) {
        this.resourceGroups = resourceGroups;
    }
}
