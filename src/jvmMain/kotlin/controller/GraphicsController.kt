package controller

import controller.command.Command
import controller.command.args.CommandArgs
import net.ClientNetModule
import server.Server
import view.graphics.GraphicsView

class GraphicsController(
    private val view: GraphicsView,
    private val netModule: ClientNetModule,
    private val server: Server
) : AbstractController() {
    override fun getCommand(): Pair<Command, CommandArgs> {
        val commandStr = view.getAction()
        val commandName = commandStr.first.toString()
        val commandArgs = commandStr.second
        commandArgs.netModule = netModule
        commandArgs.server = server
        return Pair(factory.createCommand(commandName), commandArgs)
    }
}