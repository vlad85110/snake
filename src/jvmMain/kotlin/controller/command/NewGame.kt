package controller.command

import controller.command.args.CommandArgs
import controller.command.args.NewGameArgs
import server.Game

class NewGame: Command {
    override fun run(commandArgs: CommandArgs): Boolean {
        val args = commandArgs as NewGameArgs
        val server = commandArgs.server
        val game = Game(args.gameName, args.gameConfig, args.canJoin)
        server?.runNewGame(game)
        return false
    }
}