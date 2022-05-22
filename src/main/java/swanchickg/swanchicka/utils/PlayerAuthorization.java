package swanchickg.swanchicka.utils;

import com.google.gson.Gson;
import swanchickg.swanchicka.SwanchickA;
import swanchickg.swanchicka.models.PlayerAuthorizationModel;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayerAuthorization {
    private static ArrayList<PlayerAuthorizationModel> playerAuthorizationModels = new ArrayList<PlayerAuthorizationModel>();

    public static void addNewAuthorizationModel(String playerName, String playerUuid, String playerPassword){
        PlayerAuthorizationModel model = new PlayerAuthorizationModel(playerName, playerUuid, playerPassword);
        playerAuthorizationModels.add(model);

        try {
            saveData();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void loadPlayerModels() throws IOException {
        Gson gson = new Gson();

        File file = new File(SwanchickA.getSwancraftPlugin().getDataFolder().getAbsolutePath() + "/authorization.json");

        if (file.exists()){
            FileReader fileReader = new FileReader(file);

            PlayerAuthorizationModel[] models = gson.fromJson(fileReader, PlayerAuthorizationModel[].class);

            playerAuthorizationModels = new ArrayList<>(Arrays.asList(models));
        }

        System.out.println("Load models!");
        System.out.println(playerAuthorizationModels.toArray().length);
    }

    public static boolean playerExist(String playerUuid) {
        for (PlayerAuthorizationModel model : playerAuthorizationModels){
            if (model.getPlayerUuid().equalsIgnoreCase(playerUuid)){
                return true;
            }
        }

        return false;
    }

    public static PlayerAuthorizationModel getModelByUuid(String uuid) {
        for (PlayerAuthorizationModel model : playerAuthorizationModels){
            if (model.getPlayerUuid().equalsIgnoreCase(uuid)){
                return model;
            }
        }

        return null;
    }

    public static void saveData() throws IOException {
        Gson gson = new Gson();

        File file = new File(SwanchickA.getSwancraftPlugin().getDataFolder().getAbsolutePath() + "/authorization.json");

        Writer writer = new FileWriter(file.getPath(), false);

        gson.toJson(playerAuthorizationModels, writer);

        writer.close();
        System.out.println("Player Authorization was saved successfully!");
    }
}
