package com.doglandia.medialoader.handlers;

import com.doglandia.medialoader.factory.StandardResponseFactory;
import com.doglandia.medialoader.model.ResourceGroup;
import com.doglandia.medialoader.model.ResourcesResponse;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tdk10 on 2/21/2016.
 */
public class MediaResourceHandler extends HandlerCollection {

    private boolean handlersSet = false;

    public MediaResourceHandler(){
        super(true);

        StandardResponseFactory responseFactory = new StandardResponseFactory(null);
        setResourceResponse(responseFactory.createResponseFromFileSelection());
    }

    public void setResourceResponse(ResourcesResponse resourcesResponse) {

        List<ContextHandler> handlerList = new ArrayList<ContextHandler>();

        for(ResourceGroup resourceGroup : resourcesResponse.getResourceGroups()){
            ContextHandler contextHandler = new ContextHandler();
            contextHandler.setContextPath("/"+resourceGroup.getGroupName());

            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setResourceBase(resourceGroup.getFullPath());
            resourceHandler.setDirectoriesListed(true);

            contextHandler.setHandler(resourceHandler);
            handlerList.add(contextHandler);

        }


//        ContextHandler contextHandler = new ContextHandler();
//        contextHandler.setContextPath("/dir1");
//
//        ResourceHandler resourceHandler = new ResourceHandler();
//        resourceHandler.setResourceBase("C:/Users/tdk10/Downloads/test_dir");
//        resourceHandler.setDirectoriesListed(false);

//        contextHandler.setHandler(resourceHandler);

        ContextHandler[] handlers = new ContextHandler[handlerList.size()];
        for(int i = 0; i < handlerList.size(); i++){
            handlers[i] = handlerList.get(i);
        }

        if(!handlersSet) {
            setHandlers(handlers);
            handlersSet = true;
        }

    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        Handler[] handlers = getHandlers();

        if (handlers!=null && isStarted())
        {
            for (int i=0;i<handlers.length;i++)
            {
                handlers[i].handle(target,baseRequest, request, response);
                if ( baseRequest.isHandled())
                    return;
            }
        }
    }
}
