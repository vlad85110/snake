package client

import model.GameState
import model.NodeRole
import model.message.RoleChangeMessage
import model.message.StateMessage
import net.ClientNetModule
import view.View

class MessageReceiver(
    private val gameState: GameState?,
    private val view: View,
    private val netModule: ClientNetModule,
    private val startNewGame: () -> Unit
) : Runnable {
    override fun run() {
        while (!Thread.currentThread().isInterrupted) {
            val pair = netModule.receiveGameMessage()
            val endpoint = pair.first

            when (val message = pair.second) {
                is StateMessage -> {
                    view.updateGameState(message.gameState)
                }

                is RoleChangeMessage -> {
                    when (val role = message.receiverRole) {
                        NodeRole.MASTER -> {
                            if (message.senderRole == NodeRole.MASTER) {
                                startNewGame()
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }

}