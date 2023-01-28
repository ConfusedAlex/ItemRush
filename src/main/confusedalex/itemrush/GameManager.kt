package itemrush

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import java.util.*
import java.util.logging.Level

class GameManager {
    var pointMap = HashMap<UUID, Int?>()
    val itemMap: HashMap<UUID, ArrayList<String>> = HashMap()
    val scoreboard: Scoreboard = Scoreboard(this)

    fun getPlayerByScoreboardPlace (i: Int) : Int? {
        val sortedPointMap = pointMap.toSortedMap(compareByDescending { it }).toList()
        val size = sortedPointMap.size - 1

        if (i > size) {
            return sortedPointMap[size].second
        }

        return sortedPointMap[i].second
    }

    fun switchGameState(gameStates: GameStates) {
        if (gameStates == GameStates.STARTING) {
            val wc = WorldCreator(Random().nextInt().toString())
            val mapSeed = Random().nextLong()
            wc.seed(mapSeed)
            wc.environment(World.Environment.NORMAL)
            wc.type(WorldType.NORMAL)

            val world = wc.createWorld()
            if (world == null) {
                Bukkit.getLogger().log(Level.SEVERE,"World is null")
                return
            }

            world.worldBorder.setSize(200.0, 0)

            for (player in Bukkit.getOnlinePlayers()) {
                player.teleport(world.spawnLocation)
                pointMap[player.uniqueId] = 0
                itemMap[player.uniqueId] = ArrayList<String>()

                scoreboard.setScoreboard(player)
            }
        }

    }

}
