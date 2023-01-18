package net.mapper

import me.ippolitov.fit.snakes.SnakesProto.*

class PlayerMapper {
    companion object {
        fun toDto(gamePlayer: model.GamePlayer): GamePlayer {
            val nodeRole = NodeRole.valueOf(gamePlayer.nodeRole.toString())
            val playerType = PlayerType.valueOf(gamePlayer.playerType.toString())

            val builder = GamePlayer.newBuilder()
                .setName(gamePlayer.name)
                .setId(gamePlayer.id)
                .setRole(nodeRole)
                .setScore(gamePlayer.score)
                .setType(playerType)

            if (gamePlayer.ipAddress != null) {
                builder.ipAddress = gamePlayer.ipAddress
            }

            if (gamePlayer.port != null) {
                builder.port = gamePlayer.port!!
            }

            return builder.build()
        }

        fun toEntity(gamePlayer: GamePlayer): model.GamePlayer {
            val nodeRole = model.NodeRole.valueOf(gamePlayer.role.toString())
            val playerType = model.PlayerType.valueOf(gamePlayer.type.toString())
            val id = gamePlayer.id
            val name = gamePlayer.name
            val score = gamePlayer.score

            val entity =  model.GamePlayer(name, nodeRole, playerType)

            entity.ipAddress = gamePlayer.ipAddress
            entity.port = gamePlayer.port
            entity.id = id
            entity.score = score

            return entity
        }
    }
}