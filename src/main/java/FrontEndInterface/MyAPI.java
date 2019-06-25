package FrontEndInterface;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class MyAPI {

    public static void main(String[] args) throws IOException {
        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/api/hello", RequestHandler.makeRequestHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }



}
