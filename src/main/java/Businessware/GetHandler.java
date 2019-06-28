package Businessware;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static Businessware.UserCreator.respond;

public class GetHandler {

    public static void handle(HttpExchange exchange) throws IOException {
        respond("<h1>Hello world!</h1><p>Goodbye world</p>", exchange);
        exchange.close();
    }



}
