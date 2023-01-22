package model.message

import model.NodeRole
import model.PlayerType

class JoinMessage(
    val playerType: PlayerType,
    val playerName: String,
    val gameName: String,
    val nodeRole: NodeRole
): GameMessage()