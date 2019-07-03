package FrontEndInterface;

import Businessware.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class RequestHandler implements HttpHandler {

    private boolean ACCEPTGET = false;
    private boolean ACCEPTPUT = false;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        LogWriter.prepareLogs("Received request of type " + requestMethod + " for address "
                + exchange.getRequestURI().toString());
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
                if (!ACCEPTPUT){
                    rejectRequest(exchange);
                } else {
                    switch(exchange.getRequestURI().toString()){
                        case Config.REGISTER_PATH:
                            UserCreator.handle(exchange);
                            break;
                        case Config.LOGIN_PATH:
                            CredentialsChecker.handle(exchange);
                            break;
                        case Config.ADD_ENTRY_PATH:
                            EntryInserter.handle(exchange);
                            break;
                        case Config.GET_PROJECTS_PATH:
                            GetProjectsList.handle(exchange);
                            break;
                        case Config.SUMMARY_PATH:
                            GetProjectSummary.handle(exchange);
                            break;
                        case Config.GET_ALL_ENTRIES:
                            GetAllEntries.handle(exchange);
                            break;
                        case Config.DELETE_ENTRY:
                            DeleteEntry.handle(exchange);
                            break;
                        case Config.UPDATE_ENTRY:
                            UpdateEntry.handle(exchange);
                            break;
                        case Config.GET_FILTERED_ENTRIES:
                            GetFilteredProjectEntries.handle(exchange);
                            break;
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

    public static RequestHandler makeRequestHandler(boolean whetherToAcceptGET, boolean whetherToAcceptPUT){
        RequestHandler jeff = new RequestHandler();
        jeff.ACCEPTGET = whetherToAcceptGET;
        jeff.ACCEPTPUT = whetherToAcceptPUT;
        return jeff;
    }

    private void rejectRequest(HttpExchange exchange){
        LogWriter.prepareLogs("This type of request is not valid for the current address. Closing connection.").run();
        exchange.close();
    }
}
