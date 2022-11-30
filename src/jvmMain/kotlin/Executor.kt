import controller.Controller
import controller.GraphicsController
import model.Player
import model.field.Field
import model.field.FieldUpdater
import view.View
import view.graphics.GraphicsView

class Executor {
    private val field = Field(30)
    private val views: MutableMap<Player, View> = HashMap()
    private val controllers: MutableSet<Controller> = HashSet()
    private val fieldUpdater = FieldUpdater(field, 200, views)

    init {
        val view = GraphicsView(field)
        views[Player("vlad")] = view
        controllers.add(GraphicsController(view))
    }

    fun run() {
        var isContinue = true
        while (isContinue) {
            for (controller in controllers) {
                val command = controller.getCommand()

                val playerView = views[command.player]
                if (playerView != null) {
                    isContinue = command.run(fieldUpdater, playerView)
                }
            }
        }
    }
}