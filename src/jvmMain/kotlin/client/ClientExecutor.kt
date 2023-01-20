package client
import androidx.compose.runtime.mutableStateOf
import config.ConfigParser
import controller.Controller
import controller.GraphicsController
import controller.command.NewGame
import exception.IncorrectCommandException
import model.GameAnnouncement
import model.GameState
import net.SnakeNetModule
import server.Server
import server.SnakeServer
import view.View
import view.graphics.GraphicsView
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

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
    private val playerName: String = "Vlad"
    private var inGame = false
    private var isLocalGame = false
    private val gameNameState = mutableStateOf<String?>(null)

    private val messageReceiverThread = Executors.newSingleThreadScheduledExecutor()
    private val messageReceiver = MessageReceiver(gameState, view, netModule, startNewGame =  {
//        val game = Game()
//        server.runNewGame(game)
    })

    fun run() {
        server.run()
        val scannerThread = Executors.newSingleThreadScheduledExecutor()
        val period = configParser.gameScanPeriod
        scannerThread.scheduleAtFixedRate(gameScanner, period, period, TimeUnit.MILLISECONDS)

        var state: ClientState
        var isContinue = true
        while (isContinue) {
            try {
                val pair = controller.getCommand()
                val command = pair.first
                val args = pair.second

                args.playerName = playerName
                args.view = view
                args.inGame = inGame
                args.isLocalGame = isLocalGame
                args.gameNameState = gameNameState

                state = command.run(args)
                when (state) {
                    ClientState.IN_GAME -> {
                        if (command is NewGame) {
                           isLocalGame = true
                        }
                        inGame = true
                    }

                    ClientState.MAIN_MENU -> {
                        inGame = false
                    }
                    ClientState.EXIT -> {
                        isContinue = false
                    }
                }
            } catch(e: IncorrectCommandException) {
                System.err.println("Incorrect command ${e.message}")
            }
        }
    }
}