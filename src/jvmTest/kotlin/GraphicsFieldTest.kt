import model.math.Point
import model.math.Rectangle
import model.math.Vector
import org.junit.Assert
import org.junit.Test

class GraphicsFieldTest {

    @Test
    fun pointTest() {
        val startRectangle = Rectangle(Point(0, 0), Point(4, 4))
        val newRectangle = startRectangle.move(Vector.RIGHT, 30)

        Assert.assertEquals(newRectangle.point1, Point(1, 0))
    }
}