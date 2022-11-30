package view.graphics

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import model.Action
import model.field.Field
import view.View
import view.graphics.component.FieldView
import java.util.*
import kotlin.concurrent.thread

class GraphicsView(private val field: Field): View {
    private var actionQueue = LinkedList<Action>()
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
                FieldView(field)
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun keyEvent(key: Key) {
        val  action = when (key) {
            Key.DirectionLeft -> Action.LEFT
            Key.DirectionUp -> Action.UP
            Key.DirectionDown -> Action.DOWN
            Key.DirectionRight -> Action.RIGHT
            else -> null
        }

        if (action != null) {
            setAction(action)
        }
    }

    private fun setAction(action: Action) {
        synchronized(actionLock) {
            actionQueue.add(action)
            actionLock.notify()
        }
    }

    fun getAction(): Action {
        var action: Action?

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

        return action as Action
    }

    override fun updateField(field: Field) {

    }

    override fun showLoseMessage() {
        TODO("Not yet implemented")
    }
}