package server

import config.ConfigParser
import model.GamePlayer
import model.message.AnnouncementMessage
import model.message.StateMessage
import net.Endpoint
import net.ServerNetModule
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class SnakeServer(private val netModule: ServerNetModule, private val configParser: ConfigParser): Server {
    val games: MutableMap<String, Game> = HashMap()
    private val endpoints = HashMap<GamePlayer, Endpoint>()
    private val players = HashMap<Endpoint, GamePlayer>()
    private var deputy: GamePlayer? = null

    private val announcerThread: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    private val announcerRunnable = {
        val message = AnnouncementMessage()
        games.forEach { e ->
            message.gameAnnouncements.add(e.value.announcement)
        }
        netModule.sendAnnouncementMessage(message)
    }

    private val messageReceiverThread: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    private val messageReceiver = MessageReceiver(netModule, endpoints, players, games)

    override fun runNewGame(game: Game) {
        games[game.gameName] = game
        game.sendUpdate = { player, state ->
            if (player.ipAddress != null && player.port != null) {
                netModule.sendStateMessage(StateMessage(state), Endpoint(player.ipAddress!!, player.port!!))
            }
        }
        game.run()
    }

    override fun stopGame(name: String) {
        games[name]?.stop()
    }

    override fun run() {
        val announceRate = configParser.gameAnnouncePeriod
        announcerThread.scheduleAtFixedRate(announcerRunnable, announceRate, announceRate, TimeUnit.MILLISECONDS)
    }

    fun stop() {
        announcerThread.shutdown()
        val deputy = deputy
        val endpoint = endpoints[deputy]
        if (deputy != null && endpoint != null) {
            netModule.sendRoleChangeMessage(endpoint)
        }

        //todo rolechangeMessage
    }
}