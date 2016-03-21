package com.doglandia.medialoader.handlers;

import com.doglandia.medialoader.factory.FileBasedResponseFactory;
import com.doglandia.medialoader.factory.ResponseFactory;
import com.doglandia.medialoader.factory.UserSelectionResponseFactory;
import com.doglandia.medialoader.model.UserFileSelections;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by tdk10 on 2/20/2016.
 */
public class ResourceQueryHandler extends AbstractHandler {

    private ResponseFactory responseFactory;


    public ResourceQueryHandler(MediaResourceHandler mediaResourceHandler){
        responseFactory = new FileBasedResponseFactory(mediaResourceHandler);
    }


    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException {

        if (baseRequest.isHandled()) {
            return;
        }

        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = response.getWriter();
        out.write(responseFactory.createGetResourcesResponse());


        baseRequest.setHandled(true);
    }
}
