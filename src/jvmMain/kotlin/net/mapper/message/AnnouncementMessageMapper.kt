package net.mapper.message

import me.ippolitov.fit.snakes.SnakesProto.GameMessage.AnnouncementMsg
import model.message.AnnouncementMessage
import net.mapper.GameAnnouncementMapper

class AnnouncementMessageMapper {
    companion object {
        fun toDto(announcementMessage: AnnouncementMessage): AnnouncementMsg {
            val builder = AnnouncementMsg.newBuilder()

            for (i in announcementMessage.gameAnnouncements.indices) {
                val gameAnnouncement = announcementMessage.gameAnnouncements[i]
                builder.addGames(GameAnnouncementMapper.toDto(gameAnnouncement))
            }

            return builder.build()
        }

        fun toEntity(announcementMsg: AnnouncementMsg): AnnouncementMessage {
            val announcementMessage = AnnouncementMessage()
            for (i in announcementMsg.gamesList) {
                announcementMessage.gameAnnouncements.add(GameAnnouncementMapper.toEntity(i))
            }
            return announcementMessage
        }
    }
}