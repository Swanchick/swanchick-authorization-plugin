package swanchickg.swanchicka;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import swanchickg.swanchicka.models.PlayerAuthorizationModel;
import swanchickg.swanchicka.utils.PlayerAuthorization;

import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {
    private final List<Player> players = new ArrayList<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        players.add(player);

        if (PlayerAuthorization.playerExist(player.getUniqueId().toString())){
            player.sendMessage("Что бы авторизоватся напишите: /login <password>");
        } else {
            player.sendMessage("Что бы создать авторизацию, пожалуйста введите: /regist <password> <password>");
        }

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        event.setCancelled(players.contains(player));
    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if (!players.contains(player)) return;
        players.remove(player);
    }

    public void registPlayer(Player player, String password){
        PlayerAuthorization.addNewAuthorizationModel(player.getName(), player.getUniqueId().toString(), password);

        if (!players.contains(player)) return;

        players.remove(player);
        player.sendMessage("Вы успешно создали авторизацию!");
    }

    public void loginPlayer(Player player, String password){
        PlayerAuthorizationModel model = PlayerAuthorization.getModelByUuid(player.getUniqueId().toString());

        if (model == null) return;

        if (!model.getPlayerPassword().equalsIgnoreCase(password)) return;

        players.remove(player);
        player.sendMessage("Вы успешно зареестрировались!");
    }
}
