package net.mapper

import me.ippolitov.fit.snakes.SnakesProto
import me.ippolitov.fit.snakes.SnakesProto.Direction
import model.SnakeState
import model.field.`object`.Snake
import model.math.Point
import model.math.Vector

class SnakeMapper {
    companion object {
        fun toDto(snake: Snake): SnakesProto.GameState.Snake{
            val snakeState = SnakesProto.GameState.Snake.SnakeState.valueOf(snake.state.toString())
            val direction = Direction.valueOf(snake.currentVector.toString())

            val builder = SnakesProto.GameState.Snake.newBuilder()
                .setState(snakeState)
                .setHeadDirection(direction)
                .setPlayerId(snake.gamePlayerId)

            for (i in snake.points.indices) {
                val newPoint = if (i == 0) {
                    snake.points[i]
                } else {
                    snake.points[i] - snake.points[i - 1]
                }

                builder.addPoints(CordMapper.toDto(newPoint))
            }

            return builder.build()
        }

        fun toEntity(snakeDto: SnakesProto.GameState.Snake): Snake {
            val snakeState = SnakeState.valueOf(snakeDto.state.toString())
            val vector = Vector.valueOf(snakeDto.headDirection.toString())
            val playerId = snakeDto.playerId

            val pointsDto = snakeDto.pointsList
            val pointsEntity = ArrayList<Point>()
            for (i in pointsDto.indices) {
                val newPoint = if (i == 0) {
                    CordMapper.toEntity(pointsDto[i])
                } else {
                    CordMapper.toEntity(pointsDto[i]) + CordMapper.toEntity(pointsDto[i - 1])
                }

                 pointsEntity.add(newPoint)
            }

            return Snake(playerId, snakeState,vector, pointsEntity)
        }
    }

}