package rip.katz.api.holograms

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player

class HologramManager {

    private var holograms: MutableSet<Holograms> = mutableSetOf()

    init {
        println("[Katto] Started Holograms System!")
    }
    fun hologramCreation(world: World, x: Double, y: Double, z: Double, updateDelay: Int, name: String, list: MutableList<String>, updatable: Boolean, distance: Int) {
        val hologram = object : Holograms(Location(world, x, y, z), updateDelay, name, distance) {
            override fun update() {

            }

            override fun updateLines() {
                for (s in list) {
                    this.lines.add(s)
                }
            }

        }

        hologram.updatable = updatable
        holograms.add(hologram)
        hologram.start()
    }

    fun show(player: Player) {
        holograms.forEach { it.show(player) }
    }

    fun hide(player: Player) {
        holograms.forEach { it.hide(player) }
    }

    fun showHologram(hologramName: String, player: Player) {
        for (s in holograms) {
            if (s.name == hologramName) {
                s.show(player)
                break
            }
        }
    }

    fun hideHologram(hologramName: String, player: Player) {
        for (s in holograms) {
            if (s.name == hologramName) {
                s.hide(player)
                break
            }
        }
    }

}