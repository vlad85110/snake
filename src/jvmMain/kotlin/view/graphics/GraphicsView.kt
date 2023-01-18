package view.graphics

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import controller.command.args.CommandArgs
import controller.command.args.NewGameArgs
import model.Action
import model.GameAnnouncement
import model.GameConfig
import model.GameState
import model.field.Field
import view.View
import view.graphics.component.MainScreen
import java.util.*
import kotlin.concurrent.thread

class GraphicsView : View {
    private var actionQueue = LinkedList<Pair<Action, CommandArgs>>()
    private val actionLock = Object()

    init {
        run()
    }

    private fun run() {
        thread {
            singleWindowApplication(
                onPreviewKeyEvent = {
                    if (it.type == KeyEventType.KeyUp) {
                        keyEvent(it.key)
                    }
                    true
                }, state = WindowState(width = 800.dp, height = 800.dp)
            ) {
                MainScreen() {
                    setAction(Action.NEW_GAME, NewGameArgs("vlad", GameConfig(), true))
                }
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun keyEvent(key: Key) {
        val action = when (key) {
            Key.DirectionLeft -> Action.LEFT
            Key.DirectionUp -> Action.UP
            Key.DirectionDown -> Action.DOWN
            Key.DirectionRight -> Action.RIGHT
            else -> null
        }

        if (action != null) {
            setAction(action, CommandArgs())
        }
    }

    private fun setAction(action: Action, args: CommandArgs) {
        synchronized(actionLock) {
            actionQueue.add(Pair(action, args))
            actionLock.notify()
        }
    }

    fun getAction(): Pair<Action, CommandArgs> {
        var action: Pair<Action, CommandArgs>?

        synchronized(actionLock) {
            do {
                action = try {
                    actionQueue.remove()
                } catch (e: NoSuchElementException) {
                    actionLock.wait()
                    null
                }
            } while (action == null)
        }

        return action as Pair<Action, CommandArgs>
    }

    override fun updateField(field: Field) {

    }

    override fun showLoseMessage() {
        TODO("Not yet implemented")
    }

    override fun updateGameList(games: List<GameAnnouncement>) {
        TODO("Not yet implemented")
    }

    override fun updateGameState(state: GameState) {
        TODO("Not yet implemented")
    }
}