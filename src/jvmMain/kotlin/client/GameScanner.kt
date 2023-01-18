package client

import model.GameAnnouncement
import net.ClientNetModule

class GameScanner(
    private val netModule: ClientNetModule,
    val games: MutableMap<String, GameAnnouncement>,
    private val update: () -> Unit
) : Runnable {
    override fun run() {
        val announcements = netModule.gameAnnouncements

        announcements.forEach { gameAnnouncement ->
            games[gameAnnouncement.gameName] = gameAnnouncement
        }

        update()
    }
}