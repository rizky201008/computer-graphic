import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.cos
import kotlin.math.sin

class SolarSystemAnimation : JFrame() {

    init {
        title = "Solar System Animation"
        defaultCloseOperation = EXIT_ON_CLOSE
        contentPane = SolarSystemPanel()
        pack()
        isVisible = true
    }
}

class SolarSystemPanel : JPanel() {

    private val sunRadius = 50
    private val planetRadius = 10
    private val planetOrbitRadius = 200

    private var planetAngle = 0.0

    init {
        preferredSize = Dimension(500, 500)
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

        val centerX = width / 2
        val centerY = height / 2

        // Draw the sun at the center of the panel
        g.color = Color.YELLOW
        g.fillOval(centerX - sunRadius, centerY - sunRadius, 2 * sunRadius, 2 * sunRadius)

        // Calculate the position of the planet based on its orbit and rotation angle
        val planetX = centerX + (planetOrbitRadius * cos(planetAngle)).toInt()
        val planetY = centerY + (planetOrbitRadius * sin(planetAngle)).toInt()

        // Draw the planet
        g.color = Color.BLUE
        g.fillOval(planetX - planetRadius, planetY - planetRadius, 2 * planetRadius, 2 * planetRadius)

        // Calculate the position of the closest point to the sun on the planet
        val closestPointX = centerX + ((planetOrbitRadius - planetRadius) * cos(planetAngle)).toInt()
        val closestPointY = centerY + ((planetOrbitRadius - planetRadius) * sin(planetAngle)).toInt()

        // Draw a red dot to represent the closest point to the sun on the planet
        g.color = Color.RED
        g.fillOval(closestPointX - 2, closestPointY - 2, 4, 4)
    }
}

fun main() {
    SolarSystemAnimation()
}