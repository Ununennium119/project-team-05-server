package control.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import control.DataManager;
import control.Receiver;
import control.message.LoginMenuMessage;
import control.message.ProfileMenuMessage;
import model.User;

public class ProfileMenuController {


    public static void parseCommand(String commandName, JsonObject infoObject, Receiver receiver) {
        JsonObject responseObject;
        switch (commandName) {
            case "change_nickname":
                responseObject = changeNickname(infoObject);
                break;
            case "change_password":
                responseObject = changePassword(infoObject);
                break;
            default:
                responseObject = new JsonObject();
                responseObject.addProperty("message", String.valueOf(LoginMenuMessage.ERROR));
        }
        receiver.sendResponse(responseObject.toString());
    }


    public static JsonObject changeNickname(JsonObject infoObject) {
        JsonObject responseObject = new JsonObject();
        String token = infoObject.get("token").getAsString();
        DataManager dataManager = DataManager.getInstance();
        User user = dataManager.getUserByToken(token);
        if (user == null) {
            responseObject.addProperty("message", String.valueOf(ProfileMenuMessage.ERROR));
            return responseObject;
        }
        String newNickname = infoObject.get("new_nickname").getAsString();
        if (newNickname.contains(" ")) {
            responseObject.addProperty("message", String.valueOf(ProfileMenuMessage.NICKNAME_CONTAIN_WHITESPACE));
            return responseObject;
        }
        if (dataManager.getUserByNickname(newNickname) != null) {
            responseObject.addProperty("message", String.valueOf(ProfileMenuMessage.NICKNAME_EXISTS));
            return responseObject;
        }
        user.setNickname(newNickname);
        responseObject.addProperty("message", String.valueOf(ProfileMenuMessage.NICKNAME_CHANGED));
        return responseObject;
    }


    public static JsonObject changePassword(JsonObject infoObject) {
        JsonObject responseObject = new JsonObject();
        String token = infoObject.get("token").getAsString();
        DataManager dataManager = DataManager.getInstance();
        User user = dataManager.getUserByToken(token);
        if (user == null) {
            responseObject.addProperty("message", String.valueOf(ProfileMenuMessage.ERROR));
            return responseObject;
        }
        String currentPassword = infoObject.get("current_password").getAsString();
        if (!user.getPassword().equals(currentPassword)) {
            responseObject.addProperty("message", String.valueOf(ProfileMenuMessage.INVALID_CURRENT_PASSWORD));
            return responseObject;
        }
        String newPassword = infoObject.get("new_password").getAsString();
        if (newPassword.contains(" ")) {
            responseObject.addProperty("message", String.valueOf(ProfileMenuMessage.PASSWORD_CONTAIN_WHITESPACE));
            return responseObject;
        }
        if (currentPassword.equals(newPassword)) {
            responseObject.addProperty("message", String.valueOf(ProfileMenuMessage.SAME_NEW_AND_CURRENT_PASSWORD));
            return responseObject;
        }
        user.setPassword(newPassword);
        responseObject.addProperty("message", String.valueOf(ProfileMenuMessage.PASSWORD_CHANGED));
        return responseObject;
    }
}
