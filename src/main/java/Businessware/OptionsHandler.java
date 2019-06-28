package Businessware;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class OptionsHandler {

    public static void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS, PUT");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }

}
