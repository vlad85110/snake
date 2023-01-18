package controller.command

import controller.command.args.CommandArgs

interface Command {
    fun run(commandArgs: CommandArgs): Boolean
}