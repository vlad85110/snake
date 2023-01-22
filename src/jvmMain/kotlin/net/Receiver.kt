package net

import com.google.protobuf.ByteString
import me.ippolitov.fit.snakes.SnakesProto.GameMessage
import model.GameAnnouncement
import net.mapper.GameAnnouncementMapper
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.MulticastSocket
import kotlin.concurrent.thread


class Receiver(private val socket: DatagramSocket, private val multicastSocket: MulticastSocket) {
    val announcements: MutableMap<Endpoint, GameAnnouncement> = HashMap()
    val gameEndpointMap: MutableMap<String, Endpoint> = HashMap()

    fun run() {
        thread {
            while (true) {
                val pair = receiveMulticastMessage()
                val endpoint = pair.first
                val message = pair.second

                if (message.hasAnnouncement()) {
                    for (i in message.announcement.gamesList) {
                        val entity = GameAnnouncementMapper.toEntity(i)
                        val masterEndpoint = Endpoint(entity.master.ipAddress!!, entity.master.port!!)
                        announcements[endpoint] = entity
                        gameEndpointMap[entity.gameName] = masterEndpoint
                    }
                }
            }
        }
    }

    fun receiveMessage(): Pair<Endpoint, GameMessage> {
        return receiveMessage(socket)
    }

    private fun receiveMulticastMessage(): Pair<Endpoint, GameMessage> {
        return receiveMessage(multicastSocket)
    }

    private fun receiveMessage(socket: DatagramSocket): Pair<Endpoint, GameMessage> {
        val array = ByteArray(1000)
        val datagramPacket = DatagramPacket(array, array.size)

        try {
            socket.receive(datagramPacket)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val message = GameMessage.parseFrom(ByteString.copyFrom(array, 0, datagramPacket.length))
        return Pair(Endpoint(datagramPacket.address.hostName, datagramPacket.port), message)
    }
}