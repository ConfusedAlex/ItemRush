package itemrush

import itemrushfirst.Scoreboard
import net.md_5.bungee.api.ChatColor.GOLD
import org.bukkit.*
import org.bukkit.entity.Boss
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import java.util.logging.Level

class GameManager (val itemRush: ItemRush) {
    var pointMap = HashMap<UUID, Int?>()
    val itemMap: HashMap<UUID, ArrayList<String>> = HashMap()
    val scoreboard: Scoreboard = Scoreboard(this)
    val bossbar = Bossbar(this)
    var running = false
    private val seeds = listOf(42259133905528715, 3088695298922539727, -856662231300496405)


    fun getPlayerPointsByScoreboardPlace (i: Int) : Int? {
        val sortedPointMap = pointMap.entries.sortedBy { it.value }.associate { it.toPair() }.toList()
        val size = sortedPointMap.size - 1

        println(sortedPointMap)

        if (i > size) {
            return sortedPointMap[size].second
        }

        return sortedPointMap[i-1].second
    }

    fun getPlayerNameByScoreboardPlace (i: Int) : String? {
        val sortedPointMap = pointMap.entries.sortedBy { it.value }.associate { it.toPair() }.toList()
        val size = sortedPointMap.size - 1

        println(sortedPointMap)

        if (i > size) {
            return Bukkit.getPlayer(sortedPointMap[size].first)?.name
        }

        return Bukkit.getPlayer(sortedPointMap[i-1].first)?.name
    }

    fun switchGameState(gameStates: GameStates) {
        if (gameStates == GameStates.STARTING) {
            val wc = WorldCreator(Random().nextInt().toString())
            val mapSeed = seeds[Random().nextInt(1, seeds.size)]
            wc.seed(mapSeed)
            wc.environment(World.Environment.NORMAL)
            wc.type(WorldType.NORMAL)

            val world = wc.createWorld()
            if (world == null) {
                Bukkit.getLogger().log(Level.SEVERE,"World is null")
                return
            }

            world.worldBorder.setSize(200.0, 0)
            world.time = 6000
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)

            for (player in Bukkit.getOnlinePlayers()) {
                player.teleport(world.spawnLocation)
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
                        switchGameState(GameStates.RUNNING)
                        cancel()
                        return
                    }

                    for (player in Bukkit.getOnlinePlayers()) {
                        player.sendTitle(seconds.toString(),  "", 5, 10, 5)
                    }

                    seconds--
                }
            }
            bukkitRunnable.runTaskTimer(itemRush, 0, 20)

        }

        if (gameStates == GameStates.RUNNING) {
            for (player in Bukkit.getOnlinePlayers()) {
                player.sendTitle("Sammel so viele Items wie du kannst!",  "Lauf Pirat, Lauf!", 5, 30, 5)
            }
            bossbar.createBossBar(5*60)

        }

        if (gameStates == GameStates.END) {
            for (player in Bukkit.getOnlinePlayers()) {
                player.sendTitle("$GOLD 1. ${getPlayerNameByScoreboardPlace(3)}",
                    "$GOLD 2. ${getPlayerNameByScoreboardPlace(2)} 3. ${getPlayerNameByScoreboardPlace(1)}", 10, 200, 10)
            }
        }

    }

}
