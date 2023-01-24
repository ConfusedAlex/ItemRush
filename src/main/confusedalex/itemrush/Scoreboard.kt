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

        val itemCounter: Team? = board?.registerNewTeam("itemCounter")
        itemCounter?.addEntry("$GREEN Items:")
        itemCounter!!.prefix = "$YELLOW + ${gameManager.pointMap[p.uniqueId]}"
//        obj?.getScore("$GREEN Items:")?.score = 12

        p.scoreboard = board
    }

    fun updateScoreboard(p: Player) {

        val board: Scoreboard = p.scoreboard

        board.getTeam("itemCounter")?.prefix = "$GOLD ${gameManager.pointMap[p.uniqueId]}"

    }

}