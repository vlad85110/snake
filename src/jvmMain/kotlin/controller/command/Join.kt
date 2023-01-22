package controller.command

import client.ClientState
import controller.command.args.CommandArgs
import controller.command.args.JoinArgs

class Join: Command {
    override fun run(commandArgs: CommandArgs): ClientState {
        val joinArgs = commandArgs as JoinArgs
        val joinMessage = joinArgs.joinMessage
        val id = commandArgs.netModule!!.sendJoinMessage(joinMessage)

        return if (id >= 0) {
            commandArgs.playerIdState.value = id
            ClientState.IN_GAME
        } else {
            println(2)
            ClientState.MAIN_MENU
        }
    }
}