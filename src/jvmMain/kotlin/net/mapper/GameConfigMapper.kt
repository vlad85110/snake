package net.mapper

import me.ippolitov.fit.snakes.SnakesProto
import model.GameConfig

class GameConfigMapper {
    companion object {
        fun toDto(gameConfig: GameConfig): SnakesProto.GameConfig {
            val builder = SnakesProto.GameConfig.newBuilder()
                .setFoodStatic(gameConfig.foodStatic)
                .setHeight(gameConfig.height)
                .setWidth(gameConfig.width)
                .setStateDelayMs(gameConfig.stateDelayMs)

            return builder.build()
        }

        fun toEntity(gameConfig: SnakesProto.GameConfig): GameConfig {
            return GameConfig(gameConfig.width, gameConfig.height, gameConfig.foodStatic, gameConfig.stateDelayMs)
        }
    }
}