package controller.command

import client.ClientState
import controller.command.args.CommandArgs

class Leave: Command {
    //todo check if in net

    override fun run(commandArgs: CommandArgs): ClientState {
        val view = commandArgs.view
        view?.setGameView(null)

        return ClientState.MAIN_MENU
    }
}