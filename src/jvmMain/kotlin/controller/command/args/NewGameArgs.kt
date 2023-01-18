package controller.command.args

import model.GameConfig

class NewGameArgs(
    val gameName: String,
    val gameConfig: GameConfig,
    val canJoin: Boolean
): CommandArgs()