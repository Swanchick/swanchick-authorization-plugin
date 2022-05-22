package swanchickg.swanchicka.models;

public class PlayerAuthorizationModel {
    public String playerName;
    public String playerUuid;
    public String playerPassword;

    public PlayerAuthorizationModel(String playerName, String playerUuid, String playerPassword){
        this.playerName = playerName;
        this.playerUuid = playerUuid;
        this.playerPassword = playerPassword;
    }

    public String getPlayerName(){
        return playerName;
    }

    public String getPlayerUuid(){
        return playerUuid;
    }

    public String getPlayerPassword(){
        return playerPassword;
    }
}
