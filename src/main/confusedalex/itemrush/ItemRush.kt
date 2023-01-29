package itemrush

import org.bukkit.Bukkit
import org.bukkit.WorldCreator
import org.bukkit.plugin.java.JavaPlugin

class ItemRush : JavaPlugin() {
    val gameManager = GameManager(this)

    @Override
    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(Events(this), this)
        this.getCommand("itemrush")?.setExecutor(Commands(this))
    }

    @Override
    override fun onDisable() {

    }

}