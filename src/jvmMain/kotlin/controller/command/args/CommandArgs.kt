package controller.command.args

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import net.ClientNetModule
import server.Server
import view.View

open class CommandArgs(
    var server: Server? = null,
    var netModule: ClientNetModule? = null,
    var view: View? = null,
    var playerName: String = "123",
    var inGame: Boolean = false,
    var isLocalGame: Boolean = false,
    var playerIdState: MutableState<Int?> = mutableStateOf(null),
    var gameNameState: MutableState<String?> = mutableStateOf(null),
    var receiverThreadState: MutableState<Thread?> = mutableStateOf(null),
    var messageReceiverRunnable: Runnable? = null
)