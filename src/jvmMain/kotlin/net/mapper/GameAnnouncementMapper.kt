package net.mapper

import me.ippolitov.fit.snakes.SnakesProto
import me.ippolitov.fit.snakes.SnakesProto.GamePlayers
import model.GameAnnouncement
import model.GamePlayer

class GameAnnouncementMapper {
    companion object {
        fun toDto(gameAnnouncement: GameAnnouncement): SnakesProto.GameAnnouncement {
            val players = GamePlayers.newBuilder()

            for (i in gameAnnouncement.gamePlayers.indices) {
                val player = gameAnnouncement.gamePlayers[i]
                val dto = PlayerMapper.toDto(player)
                players.addPlayers(dto)
            }

            val builder = SnakesProto.GameAnnouncement.newBuilder()
                .setPlayers(players)
                .setCanJoin(gameAnnouncement.canJoin)
                .setConfig(GameConfigMapper.toDto(gameAnnouncement.gameConfig))
                .setGameName(gameAnnouncement.gameName)

            return builder.build()
        }

        fun toEntity(gameAnnouncement: SnakesProto.GameAnnouncement): GameAnnouncement {
            val players = ArrayList<GamePlayer>()

            for (i in gameAnnouncement.players.playersList) {
                players.add(PlayerMapper.toEntity(i))
            }

            return GameAnnouncement(players, GameConfigMapper.toEntity(gameAnnouncement.config),
                gameAnnouncement.canJoin, gameAnnouncement.gameName)
        }
    }
}