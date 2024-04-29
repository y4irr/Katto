package rip.katz.api.holograms

import net.minecraft.server.v1_8_R3.EntityArmorStand
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import rip.katz.api.Katto
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.pow

abstract class Holograms(val location: Location, val time: Int, var name: String? = null, val distance: Int) {

    private val armorStands: MutableMap<EntityArmorStand, String> = ConcurrentHashMap()
    val lines: MutableList<String> = ArrayList()
    private var actualTime: Int = time
    var updatable: Boolean = true

    fun start() {
        update()
        updateLines()

        if (updatable) {
            object : BukkitRunnable() {
                override fun run() {
                    try {
                        tick()
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                }
            }.runTaskTimer(Katto.get(), 20L, 20L * time.toLong())
        } else {
            tick()
        }
    }

    abstract fun update()

    abstract fun updateLines()

    open fun tick() {
        actualTime--

        if (actualTime < 1) {
            actualTime = time
            update()
        }

        lines.clear()
        updateLines()

        val onlinePlayers = Bukkit.getOnlinePlayers()
        onlinePlayers.forEach { player ->
            if (isPlayerInRange(player, location)) {
                hide(player)
                show(player)
            } else {
                hide(player)
            }
        }
    }

    open fun show(player: Player) {
        armorStands.forEach { (armorStand, line) ->
            armorStand.customName = line
            val connection = (player as CraftPlayer).handle.playerConnection
            connection.sendPacket(PacketPlayOutSpawnEntityLiving(armorStand))
            connection.sendPacket(PacketPlayOutEntityMetadata(armorStand.id, armorStand.dataWatcher, true))
        }
    }

    open fun hide(player: Player) {
        armorStands.keys.forEach { armorStand ->
            (player as CraftPlayer).handle.playerConnection.sendPacket(PacketPlayOutEntityDestroy(armorStand.id))
        }
    }

    private fun isPlayerInRange(player: Player, location: Location): Boolean {
        val playerLocation = player.location
        val distanceSquared =
            (playerLocation.x - location.x).pow(2) + (playerLocation.y - location.y).pow(2) + (playerLocation.z - location.z).pow(2)
        return distanceSquared <= (distance * distance)
    }
}