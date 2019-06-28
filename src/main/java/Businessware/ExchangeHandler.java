package Businessware;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

abstract class ExchangeHandler {

    protected static void respond(String responseText, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, responseText.getBytes().length);
        exchange.getResponseBody().write(responseText.getBytes());
        exchange.getResponseBody().flush();
    }

    protected static String[] getValues(HttpExchange exchange) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        return br.readLine().split(":");
    }

}