package FrontEndInterface;

import Businessware.LogWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class RequestHandler implements HttpHandler {

    private boolean ACCEPTGET = false;
    private boolean ACCEPTPOST = false;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        LogWriter.prepareLogs("Received request of type " + requestMethod);
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        LogWriter.prepareLogs("Added response headers to allow all origins").run();
        switch (requestMethod){
            case "GET":
                if (ACCEPTGET){
                    GetHandler.handle(exchange);
                } else {
                    rejectRequest(exchange);
                }
                break;
            case "PUT":
                if (!ACCEPTPOST){
                    rejectRequest(exchange);
                } else {
                    switch(exchange.getRequestURI().toString()){
                        case "/api/register":
                            UserCreator.handle(exchange);
                        default:
                            rejectRequest(exchange);
                    }
                }
                break;
            case "OPTIONS":
                OptionsHandler.handle(exchange);
                break;
            default:
                rejectRequest(exchange);
        }
    }

    private RequestHandler(){}

    public static RequestHandler makeRequestHandler(boolean whetherToAcceptGET, boolean whetherTOAcceptPOST){
        RequestHandler jeff = new RequestHandler();
        jeff.ACCEPTGET = whetherToAcceptGET;
        jeff.ACCEPTPOST = whetherTOAcceptPOST;
        return jeff;
    }

    private void rejectRequest(HttpExchange exchange){
        LogWriter.prepareLogs("This type of request is not valid for the current address. Closing connection.").run();
        exchange.close();
    }
}
