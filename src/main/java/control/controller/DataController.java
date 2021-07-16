package control.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import control.DataManager;
import control.Receiver;
import control.message.LoginMenuMessage;
import model.Deck;
import model.ScoreboardItem;
import model.User;

import java.util.ArrayList;

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
            case "get_decks":
                responseObject = getDecks(infoObject);
                break;
            case "get_deck_info":
                responseObject = getDeckInfo(infoObject);
                break;
            case "get_addable_cards":
                responseObject = getAddableCards(infoObject);
                break;
            case "get_scoreboard_items":
                responseObject = getScoreboardItems(infoObject);
                break;
            default:
                responseObject = new JsonObject();
                responseObject.addProperty("message", "ERROR");
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


    private static JsonObject getDecks(JsonObject infoObject) {
        JsonObject responseObject = new JsonObject();
        String token = infoObject.get("token").getAsString();
        DataManager dataManager = DataManager.getInstance();
        User user = dataManager.getUserByToken(token);
        if (user == null) {
            responseObject.add("data", null);
            return responseObject;
        }
        JsonArray decks = dataManager.getUserDecks(user);
        responseObject.add("data", decks);
        return responseObject;
    }


    private static JsonObject getDeckInfo(JsonObject infoObject) {
        JsonObject responseObject = new JsonObject();
        String token = infoObject.get("token").getAsString();
        DataManager dataManager = DataManager.getInstance();
        User user = dataManager.getUserByToken(token);
        if (user == null) {
            responseObject.add("data", null);
            return responseObject;
        }
        String deckName = infoObject.get("deck_name").getAsString();
        Deck deck = user.getDeckByName(deckName);
        if (deck == null) {
            responseObject.add("data", null);
            return responseObject;
        }
        JsonObject deckInfo = dataManager.getDeckInfo(deck);
        responseObject.add("data", deckInfo);
        return responseObject;
    }


    private static JsonObject getAddableCards(JsonObject infoObject) {
        JsonObject responseObject = new JsonObject();
        String token = infoObject.get("token").getAsString();
        DataManager dataManager = DataManager.getInstance();
        User user = dataManager.getUserByToken(token);
        if (user == null) {
            responseObject.add("data", null);
            return responseObject;
        }
        String deckName = infoObject.get("deck_name").getAsString();
        Deck deck = user.getDeckByName(deckName);
        if (deck == null) {
            responseObject.add("data", null);
            return responseObject;
        }
        JsonArray addableCards = dataManager.getAddableCards(deck, user);
        responseObject.add("data", addableCards);
        return responseObject;
    }


    private static JsonObject getScoreboardItems(JsonObject infoObject) {
        JsonObject responseObject = new JsonObject();
        String token = infoObject.get("token").getAsString();
        DataManager dataManager = DataManager.getInstance();
        User user = dataManager.getUserByToken(token);
        if (user == null) {
            responseObject.add("data", null);
            return responseObject;
        }
        ArrayList<ScoreboardItem> scoreboardItems = dataManager.getScoreboardItems(user);
        Gson gson = new Gson();
        responseObject.add("data", gson.toJsonTree(scoreboardItems).getAsJsonArray());
        return responseObject;
    }
}
