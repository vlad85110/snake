package server

import model.GamePlayer
import model.message.JoinMessage
import model.message.SteerMessage
import net.Endpoint
import net.ServerNetModule

class MessageReceiver(
    private val netModule: ServerNetModule,
    private val endpoints: MutableMap<GamePlayer, Endpoint>,
    val players: MutableMap<Endpoint, GamePlayer>,
    val games: MutableMap<String, Game>
): Runnable {
    override fun run() {
        val pair =  netModule.receiveGameMessage()
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
                        //todo send about join
                        netModule.sendAckMessage(endpoint)
                        endpoints[player] = endpoint
                        players[endpoint] = player
                    }
                } else {
                    netModule.sendErrorMessage(endpoint)
                }
            }

            is SteerMessage -> {
                //games[message]?.moveSnake(players[endpoint]!!)
            }

            else -> {}
        }

        //todo в отдельный класс
    }
}