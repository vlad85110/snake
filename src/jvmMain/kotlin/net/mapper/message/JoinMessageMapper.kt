package net.mapper.message

import me.ippolitov.fit.snakes.SnakesProto
import me.ippolitov.fit.snakes.SnakesProto.GameMessage.JoinMsg
import me.ippolitov.fit.snakes.SnakesProto.NodeRole
import model.PlayerType
import model.message.JoinMessage

class JoinMessageMapper {
    companion object {
        fun toDto(joinMessage: JoinMessage): JoinMsg {
            val playerType = SnakesProto.PlayerType.valueOf(joinMessage.playerType.toString())
            val nodeRole = NodeRole.valueOf(joinMessage.nodeRole.toString())

            val builder = JoinMsg.newBuilder()
                .setGameName(joinMessage.gameName)
                .setPlayerName(joinMessage.playerName)
                .setPlayerType(playerType)
                .setRequestedRole(nodeRole)

            return builder.build()
        }

        fun toEntity(joinMsg: JoinMsg): JoinMessage {
            val playerType = PlayerType.valueOf(joinMsg.playerType.toString())
            val nodeRole = model.NodeRole.valueOf(joinMsg.requestedRole.toString())
            val gameName = joinMsg.gameName
            val playerName = joinMsg.playerName

            return JoinMessage(playerType, playerName, gameName, nodeRole)
        }
    }
}