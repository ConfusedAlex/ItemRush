package itemrush

import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.inventory.InventoryClickEvent


class Events(private val itemRush: ItemRush) : Listener {

    @EventHandler()
    fun onFoodLevelChange(event: FoodLevelChangeEvent) {
        if (event.entityType != EntityType.PLAYER) return
        event.isCancelled = true
    }

    @EventHandler()
    fun onPlayerItemPickUpEvent(event: EntityPickupItemEvent) {
        val itemMap = itemRush.gameManager.itemMap
        val pointMap = itemRush.gameManager.pointMap
        val uuid = event.entity.uniqueId
        val itemName : String = event.item.itemStack.type.toString()

        if (event.entityType != EntityType.PLAYER) return
        if (itemMap[uuid]?.contains(itemName) == true) return

        itemMap[uuid]?.add(itemName)

        var points = pointMap[uuid]
        pointMap[uuid] = points?.plus(1)
        points = pointMap[uuid]

        println(itemName)
        println(points)

        val player: Player = event.entity as Player

        itemRush.gameManager.scoreboard.updateScoreboard(player)
        player.sendTitle(points.toString(), "", 20, 60, 20)
    }

    @EventHandler
    fun onInventoryClickEvent(event: CraftItemEvent) {
        val itemMap = itemRush.gameManager.itemMap
        val pointMap = itemRush.gameManager.pointMap
        val player: Player = event.view.player as Player
        val uuid = player.uniqueId
        val itemName : String = event.recipe.result.type.toString()

        if (itemMap[uuid]?.contains(itemName) == true) return

        itemMap[uuid]?.add(itemName)

        var points = pointMap[uuid]
        pointMap[uuid] = points?.plus(1)
        points = pointMap[uuid]

        println(itemName)
        println(points)

        itemRush.gameManager.scoreboard.updateScoreboard(player)
        player.sendTitle(points.toString(), "", 20, 60, 20)
    }
}