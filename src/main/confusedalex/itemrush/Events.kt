package itemrush

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerMoveEvent


class Events(private val itemRush: ItemRush) : Listener {

    @EventHandler
    fun playerMoveEvent (event: PlayerMoveEvent) {
        if (itemRush.gameManager.running) return
        event.isCancelled = true
    }

    @EventHandler()
    fun onFoodLevelChange(event: FoodLevelChangeEvent) {
        if (event.entityType != EntityType.PLAYER) return
        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerDamageEvent(event: EntityDamageEvent) {
        if (event.entityType != EntityType.PLAYER) return

        event.isCancelled = true
    }

    @EventHandler()
    fun onPlayerItemPickUpEvent(event: EntityPickupItemEvent) {
        val itemMap = itemRush.gameManager.itemMap
        val pointMap = itemRush.gameManager.pointMap
        val uuid = event.entity.uniqueId
        val itemName : String = event.item.itemStack.type.name

        if (event.entityType != EntityType.PLAYER) return
        if (itemMap[uuid]?.contains(itemName) == true) return

        itemMap[uuid]?.add(itemName)

        var points = pointMap[uuid]
        pointMap[uuid] = points?.plus(1)
        points = pointMap[uuid]

        println(itemName)
        println(points)

        val player: Player = event.entity as Player

        itemRush.gameManager.scoreboard.updateScoreboard()
        player.sendTitle(points.toString(), "", 20, 60, 20)
        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
    }

    @EventHandler
    fun onInventoryClickEvent(event: InventoryClickEvent) {
        val itemMap = itemRush.gameManager.itemMap
        val pointMap = itemRush.gameManager.pointMap
        val player: Player = event.view.player as Player
        val uuid = player.uniqueId
        val itemName: String = event.currentItem?.type!!.name

        if (itemName == "AIR") return

        if (itemMap[uuid]?.contains(itemName) == true) return

        itemMap[uuid]?.add(itemName)

        var points = pointMap[uuid]
        pointMap[uuid] = points?.plus(1)
        points = pointMap[uuid]

        println(itemName)
        println(points)

        itemRush.gameManager.scoreboard.updateScoreboard()
        player.sendTitle(points.toString(), "", 20, 60, 20)
        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
    }
}