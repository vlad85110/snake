package net

import exception.NoSuchGameException
import me.ippolitov.fit.snakes.SnakesProto
import me.ippolitov.fit.snakes.SnakesProto.GameMessage.AckMsg
import model.GameAnnouncement
import model.message.*
import net.mapper.message.AnnouncementMessageMapper
import net.mapper.message.JoinMessageMapper
import net.mapper.message.StateMessageMapper
import net.mapper.message.SteerMessageMapper
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.MulticastSocket

class SnakeNetModule(address: String, port: Int): ServerNetModule, ClientNetModule {
    private val multicastSocket: MulticastSocket = MulticastSocket(port)
    private val socket = DatagramSocket()
    private val receiver: Receiver
    private val sender: Sender

    init {
        val socketAddress = InetSocketAddress(address, port)
        multicastSocket.joinGroup(socketAddress, multicastSocket.networkInterface)
        this.receiver = Receiver(socket, multicastSocket)
        this.sender = Sender(socket, multicastSocket)
        receiver.run()
    }


    override fun sendAnnouncementMessage(message: AnnouncementMessage) {
        message.gameAnnouncements.forEach { e ->
            e.master.ipAddress = InetAddress.getLocalHost().hostAddress
            e.master.port = socket.localPort
        }

        val dto = AnnouncementMessageMapper.toDto(message)
        val builder = SnakesProto.GameMessage.newBuilder().setAnnouncement(dto)
        builder.msgSeq = 1
        try {
            val messageDto = builder.build()
            sender.sendMulticastMessage(messageDto)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun sendJoinMessage(joinMessage: JoinMessage): Int {
        val dto = JoinMessageMapper.toDto(joinMessage)
        val endpoint = receiver.gameEndpointMap[joinMessage.gameName]
        if (endpoint != null) {
            sender.sendMessage(SnakesProto.GameMessage.newBuilder().setJoin(dto).setMsgSeq(1).build(), endpoint)
            val message = receiver.receiveMessage().second

            return if (message.hasAck()) {
                message.receiverId
            } else if (message.hasState()) {
                -1
            } else {
                1
            }
        } else {
            throw NoSuchGameException(null)
        }
    }

    override fun sendSteerMessage(steerMessage: SteerMessage, gameName: String) {
        val dto = SteerMessageMapper.toDto(steerMessage)
        val message = SnakesProto.GameMessage.newBuilder().setSteer(dto).setMsgSeq(1)
            .setSenderId(steerMessage.senderId!!).build()

        sender.sendMessage(message, receiver.gameEndpointMap[gameName]!!)
    }

    override val gameAnnouncements: List<GameAnnouncement>
        get() {
            return receiver.announcements.values.toList()
        }

    override fun receiveGameMessage(): Pair<Endpoint, GameMessage> {
        val pair = receiver.receiveMessage()
        val dto = pair.second

        if (dto.hasJoin()) {
            return Pair(pair.first, JoinMessageMapper.toEntity(dto.join))
        }

        if (dto.hasState()) {
            return Pair(pair.first, StateMessageMapper.toEntity(dto.state))
        }

        if (dto.hasSteer()) {
            return Pair(pair.first, SteerMessageMapper.toEntity(dto.steer, dto.senderId, null))
        }

        return Pair(pair.first, GameMessage())
    }

    override fun sendAckMessage(endpoint: Endpoint, playerId: Int?) {
        val dto = AckMsg.newBuilder().build()
        val message = SnakesProto.GameMessage.newBuilder().setAck(dto).setMsgSeq(1)
        if (playerId != null) {
            message.receiverId = playerId
        }

        sender.sendMessage(message.build(), endpoint)
    }

    override fun sendErrorMessage(endpoint: Endpoint) {
        TODO("Not yet implemented")
    }

    override fun sendRoleChangeMessage(endpoint: Endpoint) {
        TODO("Not yet implemented")
    }

    override fun sendStateMessage(stateMessage: StateMessage, endpoint: Endpoint) {
        val dto = StateMessageMapper.toDto(stateMessage)
        val message = SnakesProto.GameMessage.newBuilder().setState(dto).setMsgSeq(1)
        sender.sendMessage(message.build(), endpoint)
    }
}