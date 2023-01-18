package net.mapper.message

import me.ippolitov.fit.snakes.SnakesProto
import me.ippolitov.fit.snakes.SnakesProto.GameMessage.SteerMsg
import model.math.Vector
import model.message.SteerMessage

class SteerMessageMapper {
    companion object {
        fun toDto(steerMessage: SteerMessage): SteerMsg {
            return SteerMsg.newBuilder()
                .setDirection(SnakesProto.Direction.valueOf(steerMessage.vector.toString()))
                .build()
        }

        fun toEntity(steerMsg: SteerMsg): SteerMessage {
            return SteerMessage(Vector.valueOf(steerMsg.direction.toString()))
        }
    }
}