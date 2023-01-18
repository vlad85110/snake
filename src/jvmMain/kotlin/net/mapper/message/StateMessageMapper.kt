package net.mapper.message

import me.ippolitov.fit.snakes.SnakesProto.GameMessage.StateMsg
import model.message.StateMessage
import net.mapper.GameStateMapper

class StateMessageMapper {
    companion object {
        fun toDto(stateMessage: StateMessage): StateMsg {
            return StateMsg.newBuilder().setState(GameStateMapper.toDto(stateMessage.gameState)).build()
        }

        fun toEntity(stateMsg: StateMsg): StateMessage {
            return StateMessage(GameStateMapper.toEntity(stateMsg.state))
        }
    }
}