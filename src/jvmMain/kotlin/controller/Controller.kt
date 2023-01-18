package controller

import controller.command.Command
import controller.command.args.CommandArgs

interface Controller {
    fun getCommand(): Pair<Command, CommandArgs>
}