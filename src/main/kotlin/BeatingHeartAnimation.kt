import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.geom.AffineTransform
import java.awt.geom.Path2D

class BeatingHeartAnimation : JFrame() {
    private val animationPanel: AnimationPanel

    init {
        title = "Beating Heart Animation"
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(600, 400)

        animationPanel = AnimationPanel()
        add(animationPanel)

        isVisible = true

        Timer(50, animationPanel).start() // Adjust the delay as needed for smoother animation
    }

    inner class AnimationPanel : JPanel(), ActionListener {
        private var xPos = 0
        private var direction = 1
        private var scale = 1.0
        private val heartShape: Shape

        init {
            val heartPath = Path2D.Double()
            heartPath.moveTo(0.0, 0.0)
            heartPath.curveTo(-1.0, -1.5, -2.5, -1.5, -3.0, 0.0)
            heartPath.curveTo(-3.0, 1.8, -1.5, 3.5, 0.0, 5.0)
            heartPath.curveTo(1.5, 3.5, 3.0, 1.8, 3.0, 0.0)
            heartPath.curveTo(2.5, -1.5, 1.0, -1.5, 0.0, 0.0)

            heartShape = AffineTransform.getScaleInstance(20.0, 20.0).createTransformedShape(heartPath)
        }

        override fun actionPerformed(e: ActionEvent?) {
            // Update heart's position along the line
            xPos += direction * 5
            if (xPos >= width - 50 || xPos <= 0) {
                direction *= -1
            }

            // Update heart's scale to simulate beating effect
            if (scale == 1.0) {
                scale = 0.9
            } else {
                scale = 1.0
            }

            // Repaint the panel
            repaint()
        }

        override fun paintComponent(g: Graphics?) {
            super.paintComponent(g)
            val g2d = g as Graphics2D
            g2d.color = Color.RED

            // Calculate position of heart along the line
            val yPos = height / 2

            // Apply scale transformation to heart shape to simulate beating effect
            val heartTransform = AffineTransform.getTranslateInstance(xPos.toDouble(), yPos.toDouble())
            heartTransform.scale(scale, scale)

            // Draw heart at calculated position with scale transformation
            g2d.fill(heartTransform.createTransformedShape(heartShape))
        }
    }
}

fun main() {
    BeatingHeartAnimation()
}
