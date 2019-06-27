package FrontEndInterface;

import Businessware.Config;
import Businessware.LogWriter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Map;

public class MyAPI {

    public static void main(String[] args) throws IOException {
        LogWriter.prepareLogs("-------------------------------------------------------------------------------");
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(Config.SERVERPORT), 0);
            LogWriter.prepareLogs("Created Http server using port number " + Config.SERVERPORT);
            HttpContext context = server.createContext("/api/register", RequestHandler.makeRequestHandler(true, true));
            LogWriter.prepareLogs("Added handler for /api/hello");
            server.setExecutor(null); // creates a default executor
            LogWriter.prepareLogs("Added default executor");
            server.start();
            LogWriter.prepareLogs("Started server successfully").run();
        } catch (Exception e){
            LogWriter.prepareLogs("Error while creating/starting server");
            LogWriter.prepareLogs(e.getMessage()).run();
        }
    }

    public static Map<String, String> splitQuery(String query) {
        if (query == null || query.equals("")) {
            return Collections.emptyMap();
        }
        String sub = query.substring(query.indexOf('&'));
        return Collections.emptyMap();
    }

}
