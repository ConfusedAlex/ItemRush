package itemrush

import itemrush.GameStates.LOBBY
import itemrush.GameStates.RUNNING
import net.md_5.bungee.api.ChatColor.GOLD
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.scheduler.BukkitRunnable
import java.util.*


class GameManager (val itemRush: ItemRush) {
    var gameState = LOBBY
    var pointMap = HashMap<UUID, Int?>()
    val itemMap: HashMap<UUID, ArrayList<String>> = HashMap()
    val scoreboard: Scoreboard = Scoreboard(this)
    private val bossbar = Bossbar(this)
    var running = false
    private val seeds = listOf(42259133905528715, 3088695298922539727, -856662231300496405, -3864653749869810919)


    fun getPlayerPointsByScoreboardPlace (i: Int) : Int? {
        val sortedPointMap = pointMap.entries.sortedByDescending { it.value }.associate { it.toPair() }.toList()
        val size = sortedPointMap.size - 1

        println(sortedPointMap)

        if (i > size) {
            return sortedPointMap[size].second
        }

        return sortedPointMap[i-1].second
    }

    fun getPlayerNameByScoreboardPlace (i: Int) : String? {
        val sortedPointMap = pointMap.entries.sortedByDescending { it.value }.associate { it.toPair() }.toList()
        val size = sortedPointMap.size - 1

        println(sortedPointMap)

        if (i > size) {
            return Bukkit.getPlayer(sortedPointMap[size].first)?.name
        }

        return Bukkit.getPlayer(sortedPointMap[i-1].first)?.name
    }

    fun switchGameState(gameStates: GameStates) {
        gameState = gameStates

        when (gameStates) {
            LOBBY -> {

            }

            GameStates.STARTING -> {
                for (player in Bukkit.getOnlinePlayers()) {
                    itemRush.world.let { player.teleport(it.spawnLocation) }
                    player.inventory.clear()
                    pointMap[player.uniqueId] = 0
                    itemMap[player.uniqueId] = ArrayList<String>()

                    scoreboard.setScoreboard(player)
                }

                val bukkitRunnable = object: BukkitRunnable(){
                    var seconds = 10
                    override fun run() {
                        if (seconds == 0) {
                            running = true
                            switchGameState(RUNNING)
                            cancel()
                            return
                        }

                        for (player in Bukkit.getOnlinePlayers()) {
                            player.sendTitle(seconds.toString(),  "", 5, 10, 5)
                            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f)
                        }

                        seconds--
                    }
                }
                bukkitRunnable.runTaskTimer(itemRush, 0, 20)

            }
            RUNNING -> {
                for (player in Bukkit.getOnlinePlayers()) {
                    player.sendTitle("Sammel so viele Items wie du kannst!",  "Lauf Pirat, Lauf!", 5, 30, 5)
                }
                bossbar.createBossBar(30)

            }

            GameStates.END -> {
                for (player in Bukkit.getOnlinePlayers()) {
                    player.sendTitle("$GOLD 1. ${getPlayerNameByScoreboardPlace(1)}",
                        "$GOLD 2. ${getPlayerNameByScoreboardPlace(2)} 3. ${getPlayerNameByScoreboardPlace(3)}", 10, 200, 10)
                }

                Bukkit.getServer().shutdown()

            }
        }

    }

}
