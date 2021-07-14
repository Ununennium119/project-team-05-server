package control.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import control.DataManager;
import control.Receiver;
import control.message.LoginMenuMessage;
import model.User;

public class DataController {


    public static void parseCommand(String commandName, JsonObject infoObject, Receiver receiver) {
        JsonObject responseObject;
        switch (commandName) {
            case "get_user_by_token":
                responseObject = getUserByToken(infoObject);
                break;
            case "get_shop_items":
                responseObject = getShopItems(infoObject);
                break;
            default:
                responseObject = new JsonObject();
                responseObject.addProperty("message", String.valueOf(LoginMenuMessage.ERROR));
        }
        receiver.sendResponse(responseObject.toString());
    }


    private static JsonObject getUserByToken(JsonObject infoObject) {
        JsonObject responseObject = new JsonObject();
        String token = infoObject.get("token").getAsString();
        DataManager dataManager = DataManager.getInstance();
        User user = dataManager.getUserByToken(token);
        Gson gson = new GsonBuilder().serializeNulls().create();
        responseObject.add("data", gson.toJsonTree(user));
        return responseObject;
    }


    private static JsonObject getShopItems(JsonObject infoObject) {
        JsonObject responseObject = new JsonObject();
        String token = infoObject.get("token").getAsString();
        DataManager dataManager = DataManager.getInstance();
        User user = dataManager.getUserByToken(token);
        if (user == null) {
            responseObject.add("data", null);
            return responseObject;
        }
        JsonObject items = dataManager.getShopItems(user);
        responseObject.add("data", items);
        return responseObject;
    }
}
