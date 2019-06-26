package FrontEndInterface;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Map;

public class MyAPI {

    public static void main(String[] args) throws IOException {
        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        HttpContext context = server.createContext("/api/hello", RequestHandler.makeRequestHandler());
//        context.setAuthenticator(new Authenticator("blah"));
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static Map<String, String> splitQuery(String query) {
        if (query == null || query.equals("")) {
            return Collections.emptyMap();
        }
        String sub = query.substring(query.indexOf('&'));
        return Collections.emptyMap();
    }

}
