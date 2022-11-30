package controller

import controller.command.Command
import view.graphics.GraphicsView

class GraphicsController(private val view: GraphicsView): AbstractController() {
    override fun getCommand(): Command {
        val commandStr = view.getAction()
        return factory.createCommand(commandStr)
    }
}