package controller.command

import client.ClientState
import controller.command.args.CommandArgs
import model.math.Vector
import model.message.SteerMessage

abstract class MoveSnakeCommand: Command {
    override fun run(commandArgs: CommandArgs): ClientState {
        val isLocalGame = commandArgs.isLocalGame

        if (isLocalGame) {
            commandArgs.server?.moveSnake(commandArgs.gameNameState.value!!, commandArgs.playerName, getVector())
        } else {
            try {
                val steerMessage = SteerMessage(getVector())
                val gameName = commandArgs.gameNameState.value!!
                steerMessage.senderId = commandArgs.playerIdState.value
                commandArgs.netModule?.sendSteerMessage(steerMessage, gameName)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return ClientState.IN_GAME
    }

    abstract fun getVector(): Vector
}