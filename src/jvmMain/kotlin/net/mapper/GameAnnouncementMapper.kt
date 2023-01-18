package net.mapper

import me.ippolitov.fit.snakes.SnakesProto
import me.ippolitov.fit.snakes.SnakesProto.GamePlayers
import model.GameAnnouncement

class GameAnnouncementMapper {
    companion object {
        fun map(gameAnnouncement: GameAnnouncement): SnakesProto.GameAnnouncement {
            val players = GamePlayers.newBuilder()

            for (i in gameAnnouncement.gamePlayers.indices) {
                val player = gameAnnouncement.gamePlayers[i]
                players.setPlayers(i, PlayerMapper.toDto(player))
            }

            val builder = SnakesProto.GameAnnouncement.newBuilder()
                .setPlayers(players)
                .setCanJoin(gameAnnouncement.canJoin)
                .setConfig(GameConfigMapper.map(gameAnnouncement.gameConfig))
                .setGameName(gameAnnouncement.gameName)

            return builder.build()
        }
    }
}