package rip.katz.api;

import org.bukkit.plugin.java.JavaPlugin;
import rip.katz.api.menu.ButtonListener;

import java.util.Arrays;

public class Katto extends JavaPlugin {


    @Override
    public void onEnable() {
        registerListeners();
    }

    @Override
    public void onDisable() {
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
