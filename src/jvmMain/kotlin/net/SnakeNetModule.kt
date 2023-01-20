package net

import me.ippolitov.fit.snakes.SnakesProto
import model.GameAnnouncement
import model.message.AnnouncementMessage
import model.message.GameMessage
import model.message.StateMessage
import model.message.SteerMessage
import net.mapper.message.AnnouncementMessageMapper
import net.mapper.message.StateMessageMapper
import net.mapper.message.SteerMessageMapper
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.net.MulticastSocket

class SnakeNetModule(address: String, port: Int): ServerNetModule, ClientNetModule {
    private val multicastSocket: MulticastSocket = MulticastSocket()
    private val socket = DatagramSocket()
    private val receiver: Receiver = Receiver(socket, multicastSocket)

    init {
        val socketAddress = InetSocketAddress(address, port)
        multicastSocket.joinGroup(socketAddress, multicastSocket.networkInterface)
        receiver.run()
    }

    private val sender: Sender = Sender(socket, multicastSocket)
    override fun sendAnnouncementMessage(message: AnnouncementMessage) {
        val dto = AnnouncementMessageMapper.map(message)
        val builder = SnakesProto.GameMessage.newBuilder().setAnnouncement(dto)
        sender.sendMulticastMessage(builder.build())
        //todo transport control
    }

    override fun sendJoinMessage(gameName: String) {
        TODO("Not yet implemented")
    }

    override fun sendSteerMessage(steerMessage: SteerMessage) {
        val dto = SteerMessageMapper.toDto(steerMessage)
        val builder = SnakesProto.GameMessage.newBuilder().setSteer(dto)
//        sender.sendMessage(builder.build())
    }

    override val gameAnnouncements: List<GameAnnouncement>
        get() = TODO()

    override fun receiveGameMessage(): Pair<Endpoint, GameMessage> {
        TODO()
    }

    override fun sendAckMessage(endpoint: Endpoint) {
        TODO("Not yet implemented")
    }

    override fun sendErrorMessage(endpoint: Endpoint) {
        TODO("Not yet implemented")
    }

    override fun sendRoleChangeMessage(endpoint: Endpoint) {
        TODO("Not yet implemented")
    }

    override fun sendStateMessage(stateMessage: StateMessage, endpoint: Endpoint) {
        val dto = StateMessageMapper.toDto(stateMessage)
        val message = SnakesProto.GameMessage.newBuilder().setState(dto).build()
        sender.sendMessage(message, endpoint)
    }
}