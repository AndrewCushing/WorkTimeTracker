package FrontEndInterface;

import Businessware.Config;
import Businessware.LogWriter;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class MyAPI {

    public static void main(String[] args) {
        LogWriter.prepareLogs("-------------------------------------------------------------------------------");
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(Config.SERVERPORT), 0);
            LogWriter.prepareLogs("Created Http server using port number " + Config.SERVERPORT);
            addPath(server, Config.REGISTER_PATH, false, true);
            addPath(server, Config.LOGIN_PATH, false, true);
            addPath(server, Config.ADD_ENTRY_PATH, false, true);
            addPath(server, Config.GET_PROJECTS_PATH, false, true);
            addPath(server, Config.SUMMARY_PATH, false, true);
            addPath(server, Config.GET_ALL_ENTRIES, false, true);
            addPath(server, Config.REGISTER_PATH, false, true);
            addPath(server, Config.DELETE_ENTRY, false, true);
            addPath(server, Config.UPDATE_ENTRY, false, true);
            server.setExecutor(null); // creates a default executor, who knows why?
            LogWriter.prepareLogs("Added default executor");
            server.start();
            LogWriter.prepareLogs("Started server successfully").run();
        } catch (Exception e) {
            LogWriter.prepareLogs("Error while creating/starting server");
            LogWriter.prepareLogs(e.getMessage()).run();
        }
    }

    private static void addPath(HttpServer server, String path, boolean acceptGet, boolean acceptPut){
        server.createContext(path, RequestHandler.makeRequestHandler(acceptGet, acceptPut));
        LogWriter.prepareLogs("Added handler for " + path);
    }
}