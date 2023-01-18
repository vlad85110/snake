import model.math.Point
import model.math.Vector
import org.junit.Assert
import org.junit.Test

class GraphicsFieldTest {

    @Test
    fun pointTest() {
        val size = 10

        var point = Point(0,1);
        var nextPoint = point.nextPoint(Vector.LEFT, size)
        Assert.assertEquals(nextPoint, Point(size - 1, 1))

        point = Point(9, 1)
        nextPoint = point.nextPoint(Vector.RIGHT, size)
        Assert.assertEquals(nextPoint, Point(0, 1))

        point = Point(0, 0)
        nextPoint = point.nextPoint(Vector.UP, size)
        Assert.assertEquals(nextPoint, Point(0, size - 1))
    }
}