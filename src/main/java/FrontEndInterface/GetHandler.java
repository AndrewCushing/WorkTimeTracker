package FrontEndInterface;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class GetHandler {

    public static void handle(HttpExchange exchange) throws IOException {
        String respText = "<h1>Hello world!</h1><p>Goodbye world</p>";
        exchange.sendResponseHeaders(200, respText.getBytes().length);
        Headers headers = exchange.getResponseHeaders();
        OutputStream output = exchange.getResponseBody();
        output.write(respText.getBytes());
        output.flush();
        exchange.close();
    }



}
