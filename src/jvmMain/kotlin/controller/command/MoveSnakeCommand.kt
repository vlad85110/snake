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
            commandArgs.netModule?.sendSteerMessage(SteerMessage(getVector()))
        }
        return ClientState.IN_GAME
    }

    abstract fun getVector(): Vector
}