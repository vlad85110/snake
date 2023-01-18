package controller.command

import controller.command.args.CommandArgs
import model.GamePlayer
import model.math.Vector
import model.message.SteerMessage

abstract class MoveSnakeCommand(gamePlayer: GamePlayer): AbstractCommand() {
    override fun run(commandArgs: CommandArgs): Boolean {
        commandArgs.netModule?.sendSteerMessage(SteerMessage(getVector()))
        return true
    }

    abstract fun getVector(): Vector
}