package itemrush

import net.md_5.bungee.api.ChatColor.GOLD
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Score
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team
import java.awt.Color.*


class Scoreboard(private val gameManager: GameManager) {

    fun setScoreboard(p: Player) {
        val board = Bukkit.getScoreboardManager()?.newScoreboard
        val obj = board?.registerNewObjective("ItemRush", "dummy")
        obj?.displaySlot = DisplaySlot.SIDEBAR
        obj?.displayName = "ItemRush!"

        obj?.getScore("$GOLD 1. $WHITE ${gameManager.getPlayerByScoreboardPlace(1)}")?.score = 10
        obj?.getScore("$GOLD 2. $WHITE ${gameManager.getPlayerByScoreboardPlace(2)}")?.score = 11
        obj?.getScore("$GOLD 3. $WHITE ${gameManager.getPlayerByScoreboardPlace(3)}")?.score = 12

        obj?.getScore("$GREEN Items: $WHITE ${gameManager.pointMap[p.uniqueId]}")?.score = 14


        if (board != null) {
            p.scoreboard = board
        }
    }

    fun updateScoreboard(p: Player) {

        val board: Scoreboard = p.scoreboard

        board.getTeam("itemCounter")?.prefix = "$GOLD ${gameManager.pointMap[p.uniqueId]}"

    }

}