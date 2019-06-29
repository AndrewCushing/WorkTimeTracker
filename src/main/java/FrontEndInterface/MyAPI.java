package FrontEndInterface;

import Businessware.Config;
import Businessware.LogWriter;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyAPI {

    public static void main(String[] args) throws IOException {
        LogWriter.prepareLogs("-------------------------------------------------------------------------------");
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(Config.SERVERPORT), 0);
            LogWriter.prepareLogs("Created Http server using port number " + Config.SERVERPORT);
            server.createContext(Config.REGISTER_PATH, RequestHandler.makeRequestHandler(false, true));
            LogWriter.prepareLogs("Added handler for " + Config.REGISTER_PATH);
            server.createContext(Config.LOGIN_PATH, RequestHandler.makeRequestHandler(false, true));
            LogWriter.prepareLogs("Added handler for " + Config.LOGIN_PATH);
            server.createContext(Config.ADD_ENTRY_PATH, RequestHandler.makeRequestHandler(false, true));
            LogWriter.prepareLogs("Added handler for " + Config.ADD_ENTRY_PATH);
            server.createContext(Config.GET_PROJECTS_PATH, RequestHandler.makeRequestHandler(false, true));
            LogWriter.prepareLogs("Added handler for " + Config.GET_PROJECTS_PATH);
            server.createContext(Config.SUMMARY_PATH, RequestHandler.makeRequestHandler(false, true));
            LogWriter.prepareLogs("Added handler for " + Config.SUMMARY_PATH);
            server.createContext(Config.GET_ALL_ENTRIES, RequestHandler.makeRequestHandler(false, true));
            LogWriter.prepareLogs("Added handler for " + Config.GET_ALL_ENTRIES);
            server.setExecutor(null); // creates a default executor, who knows why?
            LogWriter.prepareLogs("Added default executor");
            server.start();
            LogWriter.prepareLogs("Started server successfully").run();
        } catch (Exception e) {
            LogWriter.prepareLogs("Error while creating/starting server");
            LogWriter.prepareLogs(e.getMessage()).run();
        }
    }
}