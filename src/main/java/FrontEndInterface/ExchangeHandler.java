package FrontEndInterface;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public abstract class ExchangeHandler {

    public static void respond(String responseText, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, responseText.getBytes().length);
        exchange.getResponseBody().write(responseText.getBytes());
        exchange.getResponseBody().flush();
    }

}