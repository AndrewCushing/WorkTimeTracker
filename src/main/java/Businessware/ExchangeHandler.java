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

    protected static void respond(String responseText, HttpExchange exchange, int responseCode) throws IOException {
        exchange.sendResponseHeaders(responseCode, responseText.getBytes().length);
        exchange.getResponseBody().write(responseText.getBytes());
        exchange.getResponseBody().flush();
    }

    protected static void respond(HttpExchange exchange, int responseCode) throws IOException {
        exchange.sendResponseHeaders(responseCode, -1);
        exchange.getResponseBody().flush();
    }

    protected static String[] getValues(HttpExchange exchange) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        String body = br.readLine();
        LogWriter.prepareLogs("Received: " + body);
        return body.split(":");
    }

}