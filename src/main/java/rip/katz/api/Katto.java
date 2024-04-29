package rip.katz.api;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import rip.katz.api.holograms.HologramManager;
import rip.katz.api.menu.ButtonListener;

import java.util.Arrays;

@Getter
public class Katto extends JavaPlugin {

    private HologramManager hologramManager;

    @Override
    public void onEnable() {
        registerListeners();

        hologramManager = new HologramManager();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(e -> {
            hologramManager.hide(e.getPlayer());
            //HIDE == DESTROY
        });

        super.onDisable();
    }

    private void registerListeners() {
        Arrays.asList(new ButtonListener())
                .forEach( e ->
                getServer().getPluginManager().registerEvents(e, this)
        );
    }

    public static Katto get() {
        return getPlugin(Katto.class);
    }
}
