package swanchickg.swanchicka;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import swanchickg.swanchicka.utils.PlayerAuthorization;

import java.io.IOException;

public final class SwanchickA extends JavaPlugin {
    private PlayerListener playerListener;
    private static SwanchickA swancraftPlugin;

    @Override
    public void onEnable() {
        System.out.println("Plugin was Started!");
        swancraftPlugin = this;
        playerListener = new PlayerListener();
        try {
            PlayerAuthorization.loadPlayerModels();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        getServer().getPluginManager().registerEvents(playerListener, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player)sender;

        String commandName = command.getName();

        if (commandName.equalsIgnoreCase("regist")){
            if (PlayerAuthorization.playerExist(player.getUniqueId().toString())) {
                player.sendMessage("Вы уже зарегестрированы!");
                return false;
            }

            if (args.length != 2) return false;

            if (args[0].equalsIgnoreCase(args[1])){
                playerListener.registPlayer(player, args[0]);
            }
        } else if (commandName.equalsIgnoreCase("login")) {
            if (!PlayerAuthorization.playerExist(player.getUniqueId().toString())) {
                player.sendMessage("Вы еще не зарегестрированы!");
                return false;
            }

            if (args.length != 1) return false;

            playerListener.loginPlayer(player, args[0]);
        }

        return true;
    }
    public static SwanchickA getSwancraftPlugin(){
        return swancraftPlugin;
    }
}
