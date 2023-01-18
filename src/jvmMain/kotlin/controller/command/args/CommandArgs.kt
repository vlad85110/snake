package controller.command.args

import net.ClientNetModule
import server.Server

open class CommandArgs(
    var server: Server? = null,
    var netModule: ClientNetModule? = null
)