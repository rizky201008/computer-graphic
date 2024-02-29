import java.awt.*
import java.awt.geom.RoundRectangle2D
import javax.swing.*

class RoundedRectangle : JFrame() {
    init {
        title = "Rounded Rectangle"
        setSize(400, 400)
        isVisible = true
        defaultCloseOperation = EXIT_ON_CLOSE
    }

    override fun paint(g: Graphics) {
        super.paint(g)
        val g2d = g as Graphics2D

        // Create a rounded rectangle
        val roundRect = RoundRectangle2D.Float(50f, 50f, 300f, 200f, 50f, 50f)

        // Draw the rounded rectangle
        g2d.draw(roundRect)
    }
}

fun main() {
    RoundedRectangle()
}
