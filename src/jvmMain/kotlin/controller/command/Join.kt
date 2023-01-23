package controller.command

import client.ClientState
import controller.command.args.CommandArgs
import controller.command.args.JoinArgs

class Join: Command {
    override fun run(commandArgs: CommandArgs): ClientState {
        val joinArgs = commandArgs as JoinArgs
        val joinMessage = joinArgs.joinMessage

        commandArgs.receiverThreadState.value?.interrupt()
        val id = commandArgs.netModule!!.sendJoinMessage(joinMessage)

        if (commandArgs.messageReceiverRunnable != null) {
            commandArgs.receiverThreadState.value = Thread(commandArgs.messageReceiverRunnable)
            commandArgs.receiverThreadState.value?.start()
        }

        return if (id >= 0) {
            commandArgs.playerIdState.value = id
            commandArgs.gameNameState.value = joinArgs.joinMessage.gameName
            ClientState.IN_GAME
        } else {
            ClientState.MAIN_MENU
        }
    }
}