package net

import model.message.AnnouncementMessage
import model.message.GameMessage
import model.message.StateMessage

interface ServerNetModule {
    fun sendAnnouncementMessage(message: AnnouncementMessage)
    fun receiveGameMessage(): Pair<Endpoint, GameMessage>
    fun sendAckMessage(endpoint: Endpoint, playerId: Int?)
    fun sendErrorMessage(endpoint: Endpoint)
    fun sendRoleChangeMessage(endpoint: Endpoint)
    fun sendStateMessage(stateMessage: StateMessage, endpoint: Endpoint)
}