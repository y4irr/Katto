package rip.katz.api.holograms.example

import org.bukkit.Bukkit
import rip.katz.api.Katto
import rip.katz.api.holograms.HologramManager
import rip.katz.api.holograms.Holograms

class Example {

    init {
        Katto.get().hologramManager.hologramCreation(
            Bukkit.getWorld("world"), 0.0, 0.0, 0.0,
            5,
            "Hello world!",
            mutableListOf("", "&aThis is a hello world!", "Say Hi! to the hologram",
                "",
                "Spaced text!",
                "Can we do a animated text? >.<"),
            false,
            5)
    }
}