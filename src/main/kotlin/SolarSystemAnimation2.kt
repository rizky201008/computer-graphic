import java.awt.*
import java.awt.geom.AffineTransform
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.cos
import kotlin.math.sin

class SolarSystemAnimation2 : JFrame() {
    companion object {
        const val WIDTH = 500
        const val HEIGHT = 500
    }

    init {
        title = "Solar System Animation 2"
        defaultCloseOperation = EXIT_ON_CLOSE
        contentPane = SolarSystemPanel2()
        pack()
        isVisible = true
    }
}

class SolarSystemPanel2 : JPanel() {

    private val sunRadius = 50
    private val planetRadius = 10
    private val planetOrbitRadius = 200

    private var planetAngle = 0.0

    init {
        preferredSize = Dimension(SolarSystemAnimation2.WIDTH, SolarSystemAnimation2.HEIGHT)
        background = Color.BLACK

        Thread {
            while (true) {
                updatePlanetPosition()
                repaint()
                Thread.sleep(10)
            }
        }.start()
    }

    private fun updatePlanetPosition() {
        // Update the planet angle based on its rotation around the sun
        val planetRotationSpeed = 2 * Math.PI / 365
        planetAngle += planetRotationSpeed

        if (planetAngle >= 2 * Math.PI) {
            planetAngle -= 2 * Math.PI
        }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2d = g as Graphics2D

        val centerX = width / 2
        val centerY = height / 2

        // Set the rendering hints for smoother graphics
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)

        // Draw the sun at the center of the panel
        g2d.color = Color.YELLOW
        g2d.fillOval(centerX - sunRadius, centerY - sunRadius, 2 * sunRadius, 2 * sunRadius)

        // Calculate the position of the planet based on its orbit and rotation angle
        val planetX = centerX + (planetOrbitRadius * cos(planetAngle)).toInt()
        val planetY = centerY + (planetOrbitRadius * sin(planetAngle)).toInt()

        // Save the current transform of the graphics context
        val savedTransform = g2d.transform

        // Translate and rotate the graphics context to draw the planet
        val transform = AffineTransform()
        transform.translate(planetX.toDouble(), planetY.toDouble())
        transform.rotate(-planetAngle)
        g2d.transform = transform

        // Draw the planet
        g2d.color = Color.BLUE
        g2d.fillOval(-planetRadius, -planetRadius, 2 * planetRadius, 2 * planetRadius)

        // Restore the previous transform of the graphics context
        g2d.transform = savedTransform

        // Calculate the position of the closest point to the sun on the planet
        val closestPointX = centerX + ((planetOrbitRadius - planetRadius) * cos(planetAngle)).toInt()
        val closestPointY = centerY + ((planetOrbitRadius - planetRadius) * sin(planetAngle)).toInt()

        // Draw a red dot to represent the closest point to the sun on the planet
        g2d.color = Color.RED
        g2d.fillOval(closestPointX - 2, closestPointY - 2, 4, 4)
    }
}

fun main() {
    SolarSystemAnimation2()
}