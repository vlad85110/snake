package server

import model.GamePlayer
import model.message.JoinMessage
import model.message.SteerMessage
import net.Endpoint
import net.ServerNetModule

class MessageReceiver(
    private val netModule: ServerNetModule,
    private val endpoints: MutableMap<GamePlayer, Endpoint>,
    val players: MutableMap<Int, GamePlayer>,
    val games: MutableMap<String, Game>,
    private val gamesPlayersMap: MutableMap<Int, String>,
    private val ackPlayers: MutableSet<Int>
): Runnable {

    private var idCreator: Int = 0
    override fun run() {
        while (!Thread.currentThread().isInterrupted) {
            val pair = netModule.receiveGameMessage()
            val endpoint = pair.first

            when (val message = pair.second) {
                is JoinMessage -> {
                    val player = GamePlayer(message.playerName, message.nodeRole, message.playerType)
                    player.ipAddress = endpoint.address
                    player.port = endpoint.port

                    val success = games[message.gameName]?.joinPlayer(player)
                    if (success != null) {
                        if (!success) {
                            netModule.sendErrorMessage(endpoint)
                        } else {
                            netModule.sendAckMessage(endpoint, ++idCreator)
                            player.id = idCreator
                            ackPlayers.add(player.id)
                            endpoints[player] = endpoint
                            gamesPlayersMap[player.id] = message.gameName
                            players[idCreator] = player
                        }
                    } else {
                        netModule.sendErrorMessage(endpoint)
                    }
                }

                is SteerMessage -> {
                    val gameName = gamesPlayersMap[message.senderId]
                    if (gameName != null) {
                        games[gameName]?.moveSnake(players[message.senderId]!!.name, message.vector)
                    }

                }

                else -> {}
            }

        }
    }
}