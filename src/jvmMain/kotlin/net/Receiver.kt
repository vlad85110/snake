package net

import me.ippolitov.fit.snakes.SnakesProto.GameMessage
import model.GameAnnouncement
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.MulticastSocket
import kotlin.concurrent.thread


class Receiver(private val socket: DatagramSocket, private val multicastSocket: MulticastSocket) {
    val announcements: MutableMap<Endpoint, GameAnnouncement> = HashMap()

    //Todo хранить что от кого пришло
    fun run() {
        thread {
            while (true) {
                receiveMulticastMessage()
            }
        }
    }

    fun receiveMessage() {

    }

    private fun receiveMulticastMessage(): Pair<Endpoint, GameMessage>  {
        val array = ByteArray(1000)
        val datagramPacket = DatagramPacket(array, array.size)

        try {
            multicastSocket.receive(datagramPacket)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val message = GameMessage.parseFrom(datagramPacket.data)

        return Pair(Endpoint(datagramPacket.address.toString(), datagramPacket.port), message)
    }
}