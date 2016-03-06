package com.doglandia.medialoader.handlers;

import com.doglandia.medialoader.factory.StandardResponseFactory;
import com.doglandia.medialoader.model.ResourceGroup;
import com.doglandia.medialoader.model.ResourcesResponse;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tdk10 on 2/21/2016.
 */
public class MediaResourceHandler extends AbstractHandler {

    List<ContextHandler> handlers;
    List<ResourceGroup> resourceGroups;

    public MediaResourceHandler(){
        handlers = new ArrayList<ContextHandler>();
        resourceGroups = new ArrayList<ResourceGroup>();

        StandardResponseFactory responseFactory = new StandardResponseFactory(null);
        setResourceResponse(responseFactory.createResponseFromFileSelection());
    }

    public void setResourceResponse(ResourcesResponse resourcesResponse) {
        if(handlers.size() > 0){
            return;
        }
        handlers.clear();

        resourceGroups.clear();
        resourceGroups.addAll(resourcesResponse.getResourceGroups());
        for(ResourceGroup resourceGroup : resourceGroups){


            ContextHandler contextHandler = new ContextHandler("/"+resourceGroup.getGroupName());

            GzipHandler gzipHandler = new GzipHandler();
            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setResourceBase(resourceGroup.getFullPath());
            resourceHandler.setMimeTypes(new MimeTypes());
            resourceHandler.setDirectoriesListed(true);
            gzipHandler.setHandler(resourceHandler);

            contextHandler.setHandler(gzipHandler);
            handlers.add(contextHandler);

        }
//        ContextHandler contextHandler = new ContextHandler();
//        contextHandler.setContextPath("/dir1");
//
//        ResourceHandler resourceHandler = new ResourceHandler();
//        resourceHandler.setResourceBase("C:/Users/tdk10/Downloads/test_dir");
//        resourceHandler.setDirectoriesListed(false);

//        contextHandler.setHandler(resourceHandler);

    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {

        if (handlers!=null && isStarted())
        {
            for (int i=0;i<handlers.size();i++)
            {
                if(handlerShouldHandle(handlers.get(i), target)) {
//                    handlers.get(i).handle(target, baseRequest, request, response);
                    ResourceGroup resourceGroup = resourceGroups.get(i);
                    baseRequest.setContextPath("/"+resourceGroup.getGroupName());
                    baseRequest.setPathInfo(baseRequest.getPathInfo().replace(resourceGroup.getGroupName(),"").replace("//","/"));
                    handlers.get(i).getHandler().handle(target, baseRequest, request, response);
                    if (baseRequest.isHandled()) {
                        return;
                    }
                }
            }
        }
    }

    private boolean handlerShouldHandle(ContextHandler contextHandler, String target){
        return target.contains(contextHandler.getContextPath());
    }
}
