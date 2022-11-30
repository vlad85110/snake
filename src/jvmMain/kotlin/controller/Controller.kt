package controller

import controller.command.Command

interface Controller {
    fun getCommand(): Command
}