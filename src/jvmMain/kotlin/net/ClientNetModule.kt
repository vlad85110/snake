package net


import model.GameAnnouncement
import model.message.GameMessage
import model.message.JoinMessage
import model.message.SteerMessage

interface ClientNetModule {
    fun sendJoinMessage(joinMessage: JoinMessage): Int
    fun receiveGameMessage(): Pair<Endpoint, GameMessage>
    fun sendSteerMessage(steerMessage: SteerMessage, gameName: String)
    val gameAnnouncements: List<GameAnnouncement>
}