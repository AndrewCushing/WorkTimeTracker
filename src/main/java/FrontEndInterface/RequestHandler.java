package FrontEndInterface;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class RequestHandler implements HttpHandler {

    private static RequestHandler singleton;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()){
            case "GET":
                GetHandler.handle(exchange);
                break;
            case "POST":
                PostHandler.handle(exchange);
                break;
            default:
                exchange.close();
        }
    }

    private RequestHandler(){}

    public static RequestHandler makeRequestHandler(){
        if (singleton == null){
            singleton = new RequestHandler();
        }
        return singleton;
    }
}
