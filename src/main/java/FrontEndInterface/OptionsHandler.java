package FrontEndInterface;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class OptionsHandler {

    public static void handle(HttpExchange exchange) throws IOException {
        String respText = "['GET','OPTIONS','POST']";
        exchange.sendResponseHeaders(200, respText.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(respText.getBytes());
        output.flush();
        exchange.close();
    }

}
