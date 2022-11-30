package controller.command

import model.Player

abstract class AbstractCommand(override val player: Player): Command {}