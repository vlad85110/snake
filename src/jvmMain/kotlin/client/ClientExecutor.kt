package client
import config.ConfigParser
import controller.Controller
import controller.GraphicsController
import exception.IncorrectCommandException
import model.GameAnnouncement
import model.GameState
import net.SnakeNetModule
import server.Server
import server.SnakeServer
import view.View
import view.graphics.GraphicsView
import java.util.concurrent.Executors

class ClientExecutor {
    private val configParser = ConfigParser()
    private val view: View = GraphicsView()
    private val netModule: SnakeNetModule = SnakeNetModule(configParser.multicastAddress, configParser.multicastPort)
    private val server: Server = SnakeServer(netModule, configParser)
    private val controller: Controller = GraphicsController(view as GraphicsView, netModule, server)
    private val gameAnnouncements: MutableMap<String, GameAnnouncement> = HashMap()
    private val gameScanner = GameScanner(netModule, gameAnnouncements) {
        view.updateGameList(gameAnnouncements.values.toList())
    }
    private val gameState: GameState? = null

    private val messageReceiverThread = Executors.newSingleThreadScheduledExecutor()
    private val messageReceiver = MessageReceiver(gameState, view, netModule, startNewGame =  {
//        val game = Game()
//        server.runNewGame(game)
    })

    fun run() {
//        val scannerThread = Executors.newSingleThreadScheduledExecutor()
//        val period = configParser.gameScanPeriod
//        scannerThread.scheduleAtFixedRate(gameScanner, period, period, TimeUnit.MILLISECONDS)

        var isContinue = true
        while (isContinue) {
            try {
                val pair = controller.getCommand()
                val command = pair.first
                val args = pair.second
                isContinue = command.run(args)
            } catch(e: IncorrectCommandException) {
                System.err.println("Incorrect command ${e.message}")
            }
        }
    }
}