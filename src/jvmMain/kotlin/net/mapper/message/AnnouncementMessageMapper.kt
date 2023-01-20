package net.mapper.message

import me.ippolitov.fit.snakes.SnakesProto.GameMessage.AnnouncementMsg
import model.message.AnnouncementMessage
import net.mapper.GameAnnouncementMapper

class AnnouncementMessageMapper {
    companion object {
        fun map(announcementMessage: AnnouncementMessage): AnnouncementMsg {
            val builder = AnnouncementMsg.newBuilder()

            for (i in announcementMessage.gameAnnouncements.indices) {
                val gameAnnouncement = announcementMessage.gameAnnouncements[i]
                builder.setGames(i, GameAnnouncementMapper.map(gameAnnouncement))
            }


            return builder.build()
        }
    }
}