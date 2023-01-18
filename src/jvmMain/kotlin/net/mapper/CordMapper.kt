package net.mapper
import me.ippolitov.fit.snakes.SnakesProto.GameState.Coord
import model.math.Point

class CordMapper {
    companion object {
        fun toDto(point: Point): Coord {
            val builder = Coord.newBuilder()
                .setX(point.x)
                .setY(point.y)

            return builder.build()
        }

        fun toEntity(cord: Coord): Point {
            return Point(cord.x, cord.y)
        }

    }
}