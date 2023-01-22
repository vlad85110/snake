package net.mapper

import me.ippolitov.fit.snakes.SnakesProto
import model.GamePlayer

import model.GameState
import model.field.`object`.Snake
import model.math.Point

class GameStateMapper {
    companion object {
        fun toDto(gameState: GameState): SnakesProto.GameState {
            val builder = SnakesProto.GameState.newBuilder()

            for (i in gameState.snakes.indices) {
                builder.addSnakes(SnakeMapper.toDto(gameState.snakes[i]))
            }

            for (i in gameState.foods.indices) {
                builder.addFoods(CordMapper.toDto(gameState.foods[i]))
            }

            val players = SnakesProto.GamePlayers.newBuilder()
            for (i in gameState.players.indices) {
                players.addPlayers(PlayerMapper.toDto(gameState.players[i]))
            }

            builder.players = players.build()

            builder.stateOrder = gameState.stateOrder

            return builder.build()
        }

        fun toEntity(gameState: SnakesProto.GameState): GameState {
            val stateOrder = gameState.stateOrder

            val snakesDto = gameState.snakesList
            val snakesEntity = ArrayList<Snake>()
            for (snakeDto in snakesDto) {
                snakesEntity.add(SnakeMapper.toEntity(snakeDto))
            }

            val foodsDto = gameState.foodsList
            val foodsEntity = ArrayList<Point>()
            for (foodDto in foodsDto) {
                foodsEntity.add(CordMapper.toEntity(foodDto))
            }

            val playersDto = gameState.players.playersList
            val playersEntity = ArrayList<GamePlayer>()
            for (playerDto in playersDto) {
                playersEntity.add(PlayerMapper.toEntity(playerDto))
            }

            return GameState(stateOrder, snakesEntity, foodsEntity, playersEntity)
        }
    }
}