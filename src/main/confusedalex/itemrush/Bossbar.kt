package itemrush

import net.md_5.bungee.api.ChatColor.AQUA
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.TimeUnit


class Bossbar(private val gameManager: GameManager) {

    fun createBossBar(seconds: Long) {
        val bossBar = Bukkit.getServer().createBossBar("Zeit verbleibend: ", BarColor.PURPLE, BarStyle.SOLID)
        Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer)
        var time = seconds

        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                if (time <= 0) {
                    bossBar.removeAll()
                    gameManager.switchGameState(GameStates.END)
                    cancel()
                    return
                }

                val minute = TimeUnit.SECONDS.toMinutes(time)
                val second = TimeUnit.SECONDS.toSeconds(time) - TimeUnit.SECONDS.toMinutes(time) * 60

                if (second < 10) {
                    bossBar.setTitle("$AQUA Zeit verbleibend: $minute:0$second")
                } else {
                    bossBar.setTitle("$AQUA Zeit verbleibend: $minute:$second")
                }
                bossBar.progress = (time.toDouble() / seconds.toDouble())
                time--
            }
        }
        bukkitRunnable.runTaskTimer(gameManager.itemRush, 0, 20)
    }
}