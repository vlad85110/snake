package controller.command

import client.ClientState
import controller.command.args.CommandArgs
import controller.command.args.NewGameArgs
import model.GamePlayer
import model.NodeRole
import server.Game

class NewGame: Command {
    override fun run(commandArgs: CommandArgs): ClientState {
        val args = commandArgs as NewGameArgs
        val server = commandArgs.server
        val game = Game(args.gameName, args.gameConfig, args.canJoin)
        val player = GamePlayer(args.playerName, NodeRole.MASTER)

        server?.runNewGame(game)
        game.joinPlayer(player)

        val gameView = server?.getGameView(game.gameName)
        args.view?.setGameView(gameView)
        args.gameNameState.value = args.gameName

        return ClientState.IN_GAME
    }
}