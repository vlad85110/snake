package net

import me.ippolitov.fit.snakes.SnakesProto.GameMessage
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.MulticastSocket

class Sender(private val socket: DatagramSocket, private val multicastSocket: MulticastSocket) {
    fun sendMessage(message: GameMessage, endpoint: Endpoint) {
        val data = message.toByteArray()
        val packet = DatagramPacket(data, data.size)

        packet.address = InetAddress.getByName(endpoint.address)
        packet.port = endpoint.port
        socket.send(packet)
        println("send")
    }

    fun sendMulticastMessage(message: GameMessage) {
        val bytes = message.toByteArray()
        val datagramPacket = DatagramPacket(bytes, bytes.size)

        datagramPacket.address = InetAddress.getByName("239.255.255.254")
        datagramPacket.port = 4446

        try {
            multicastSocket.send(datagramPacket)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun sendAckMessage() {

    }
}