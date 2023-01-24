package itemrush

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class Commands(private val itemRush: ItemRush) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) TODO("HELP")
        val firstArg = args[0]

        if (firstArg == "start") {
            itemRush.gameManager.switchGameState(GameStates.STARTING)
            return true
        }
        return false
    }
}
