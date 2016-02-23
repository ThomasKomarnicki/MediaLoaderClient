
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;

public class SimplestServer {

    public static void main( String[] args ) throws Exception
    {
        Server server = new Server(8080);


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

        setHandlers(server);

        server.start();
        server.join();
    }

    private static void setHandlers(Server server){
        ContextHandler dataContextHandler = new ContextHandler("/data");
        dataContextHandler.setHandler(new ResourceQueryHandler());

        ContextHandler mediaContextHandler = new ContextHandler("/media");
        mediaContextHandler.setHandler(new MediaResourceHandler());

        ContextHandler pingContextHandler = new ContextHandler("/media");
        pingContextHandler.setHandler(new PingResourceHandler());

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{dataContextHandler, mediaContextHandler, pingContextHandler});

        server.setHandler(handlerList);
    }
}
