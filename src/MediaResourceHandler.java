import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tdk10 on 2/21/2016.
 */
public class MediaResourceHandler extends HandlerCollection {

    public MediaResourceHandler(){
        ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/dir1");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("C:/Users/tdk10/Downloads/test_dir");
        resourceHandler.setDirectoriesListed(false);

        contextHandler.setHandler(resourceHandler);

        setHandlers(new Handler[]{contextHandler});
    }
}
