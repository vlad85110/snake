package controller.command

import client.ClientState
import controller.command.args.CommandArgs

interface Command {
    fun run(commandArgs: CommandArgs): ClientState
}