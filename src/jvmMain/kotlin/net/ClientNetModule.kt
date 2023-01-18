package net


import model.GameAnnouncement
import model.message.GameMessage
import model.message.SteerMessage

interface ClientNetModule {
    fun sendJoinMessage(gameName: String)
    fun receiveGameMessage(): Pair<Endpoint, GameMessage>
    fun sendSteerMessage(steerMessage: SteerMessage)
    val gameAnnouncements: List<GameAnnouncement>
}