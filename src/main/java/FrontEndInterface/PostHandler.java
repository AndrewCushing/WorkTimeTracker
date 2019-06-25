package FrontEndInterface;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class PostHandler {

    public static void handle(HttpExchange exchange) throws IOException {
        exchange.close();
    }

}
