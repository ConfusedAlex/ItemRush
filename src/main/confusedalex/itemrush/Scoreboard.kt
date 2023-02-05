package itemrush

import net.md_5.bungee.api.ChatColor.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot


class Scoreboard(private val gameManager: GameManager) {

    fun setScoreboard(p: Player) {
        val board = Bukkit.getScoreboardManager()?.newScoreboard
        val obj = board?.registerNewObjective("ItemRush", "dummy")
        obj?.displayName = "ItemRush"

        obj?.getScore("$GOLD 1. ${gameManager.getPlayerNameByScoreboardPlace(1)} $WHITE ${gameManager.getPlayerPointsByScoreboardPlace(1)}")?.score = 14
        obj?.getScore("$GOLD 2. ${gameManager.getPlayerNameByScoreboardPlace(2)} $WHITE ${gameManager.getPlayerPointsByScoreboardPlace(2)}")?.score = 13
        obj?.getScore("$GOLD 3. ${gameManager.getPlayerNameByScoreboardPlace(3)} $WHITE ${gameManager.getPlayerPointsByScoreboardPlace(3)}")?.score = 12

        obj?.getScore("$GREEN Items: $WHITE ${gameManager.pointMap[p.uniqueId]}")?.score = 10

        println(p.name)
        println(gameManager.pointMap[p.uniqueId])

        obj?.displaySlot = DisplaySlot.SIDEBAR

        if (board != null) {
            p.scoreboard = board
        }
    }

    fun updateScoreboard() {

        for (p: Player in Bukkit.getOnlinePlayers()) {
            setScoreboard(p)
        }

    }

}