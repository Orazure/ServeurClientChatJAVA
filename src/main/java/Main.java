import java.io.Writer;

import com.google.gson.stream.JsonWriter;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import com.google.gson.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class Main {
    public static void main(String[] args) {

        Server monServer= new Server();
        monServer.waitConnection();
        // write your code here

    }
}
