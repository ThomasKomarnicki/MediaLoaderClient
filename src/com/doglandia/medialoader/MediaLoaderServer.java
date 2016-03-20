package com.doglandia.medialoader;

import com.doglandia.medialoader.handlers.MediaResourceHandler;
import com.doglandia.medialoader.handlers.PingResourceHandler;
import com.doglandia.medialoader.handlers.ResourceQueryHandler;
import com.doglandia.medialoader.model.FileSelection;
import com.doglandia.medialoader.model.UserFileSelections;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

import java.util.ArrayList;
import java.util.List;

public class MediaLoaderServer {

//    public MediaLoaderServer(){
//        Server server = new Server(8988);
//        setHandlers(server);
//
//        try {
//            server.start();
//            server.join();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public MediaLoaderServer(UserFileSelections userFileSelections){
        Server server = new Server(8988);


        MediaResourceHandler mediaResourceHandler = new MediaResourceHandler(userFileSelections);
        ContextHandler mediaContextHandler = new ContextHandler("/media");
        mediaContextHandler.setHandler(mediaResourceHandler);

        ContextHandler dataContextHandler = new ContextHandler("/data");
        dataContextHandler.setHandler(new ResourceQueryHandler(userFileSelections));

        ContextHandler pingContextHandler = new ContextHandler("/ping");
        pingContextHandler.setHandler(new PingResourceHandler());

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{ dataContextHandler, mediaContextHandler, pingContextHandler});

        server.setHandler(handlerList);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static UserFileSelections createDirectorySelections(String[] args){
        UserFileSelections userFileSelections = new UserFileSelections();
        List<FileSelection> selections = new ArrayList<FileSelection>();

        FileSelection currentFileSelection = null;
        for(int i = 0; i < args.length; i++){
            if(currentFileSelection == null){
                currentFileSelection = new FileSelection();
                currentFileSelection.setFilePath(args[i]);
            }else{
                Boolean includeSubDirs = Boolean.valueOf(args[i]);
                currentFileSelection.setIncludeSubDirectories(includeSubDirs);
                selections.add(currentFileSelection);
                currentFileSelection = null;
            }
        }

        userFileSelections.setSelections(selections);

        return userFileSelections;
    }

    public static void main(String[] args) throws Exception {

//        if(args == null || args.length == 0){
//            new MediaLoaderServer();
//        }else {
            UserFileSelections userFileSelections = createDirectorySelections(args);

            new MediaLoaderServer(userFileSelections);
//        }

//        Server server = new Server(8988);


//        server.setHandler(new HelloHandler("Please Help", "noooo"));
//        ResourceHandler resource_handler = new ResourceHandler();
//        resource_handler.setDirectoriesListed(true);
//        resource_handler.setWelcomeFiles(new String[]{ "index.html" });
//        resource_handler.setResourceBase("C:\\Users\\tdk10\\Downloads");
//
//
//        // Add the ResourceHandler to the server.
//        GzipHandler gzip = new GzipHandler();
//        server.setHandler(gzip);
//        HandlerList handlers = new HandlerList();
//        handlers.setHandlers(new Handler[] { new ResourceQueryHandler(), new MediaResourceHandler()});
//        gzip.setHandler(handlers);

//        setHandlers(server);
//        setTestHandlers(server);

//        server.start();
//        server.join();


    }

//    private static void setHandlers(Server server){
//        MediaResourceHandler mediaResourceHandler = new MediaResourceHandler();
//        ContextHandler mediaContextHandler = new ContextHandler("/media");
//        mediaContextHandler.setHandler(mediaResourceHandler);
//
//        ContextHandler dataContextHandler = new ContextHandler("/data");
//        dataContextHandler.setHandler(new ResourceQueryHandler(mediaResourceHandler));
//
//        ContextHandler pingContextHandler = new ContextHandler("/ping");
//        pingContextHandler.setHandler(new PingResourceHandler());
//
//        HandlerList handlerList = new HandlerList();
//        handlerList.setHandlers(new Handler[]{ dataContextHandler, mediaContextHandler, pingContextHandler});
//
//        server.setHandler(handlerList);
//    }

    private static void setTestHandlers(Server server){
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("C:/Users/tdk10/Downloads/The Walking Dead/season3");
        resourceHandler.setDirectoriesListed(true);
        server.setHandler(resourceHandler);
    }
}
