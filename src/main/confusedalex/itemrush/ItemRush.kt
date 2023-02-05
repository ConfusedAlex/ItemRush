package itemrush

import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.plugin.java.JavaPlugin
import java.io.File


class ItemRush : JavaPlugin() {
    val gameManager = GameManager(this)
    lateinit var world: World

    @Override
    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(Events(this), this)
        this.getCommand("itemrush")?.setExecutor(Commands(this))

//        Bukkit.getServer().createWorld(WorldCreator("1"))
//        val source = Bukkit.getWorld("1")
        val sourceFolder = File("./1")

        val targetFolder = File("./1_temp")
        targetFolder.deleteRecursively()
        targetFolder.delete()
        targetFolder.mkdir()


        sourceFolder.copyRecursively(targetFolder, true)

        Bukkit.getServer().createWorld(WorldCreator("1_temp"))

        world = Bukkit.getWorld("1_temp")!!

        world?.worldBorder?.setSize(200.0, 0)
        world?.time = 6000
        world?.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)

        println(Bukkit.getWorlds().toString())
    }

    @Override
    override fun onDisable() {

    }

}