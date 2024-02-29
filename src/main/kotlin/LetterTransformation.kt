import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.geom.GeneralPath
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities

class LetterTransformation : JFrame() {

    companion object {
        private const val WIDTH = 400
        private const val HEIGHT = 400
        private const val NUM_STEPS = 100
    }

    init {
        title = "Letter Transformation Animation"
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(WIDTH, HEIGHT)
        setLocationRelativeTo(null)

        val panel = object : JPanel() {
            override fun paintComponent(g: Graphics) {
                super.paintComponent(g)

                val g2d = g.create() as Graphics2D
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

                // Clear the panel
                g2d.color = Color.WHITE
                g2d.fillRect(0, 0, width, height)

                // Calculate the intermediate shapes
                val shapeA = createLetterA()
                val shapeB = createLetterB()
                val intermediateShapes = calculateIntermediateShapes(shapeA, shapeB)

                // Draw the intermediate shapes
                for (i in 0 until NUM_STEPS) {
                    val shape = intermediateShapes[i]

                    // Set the color based on the step
                    val completion = i.toFloat() / (NUM_STEPS - 1)
                    g2d.color = Color(1f - completion, 0f, completion)

                    // Draw the shape
                    g2d.fill(shape)
                }

                g2d.dispose()
            }
        }

        add(panel)
    }

    private fun createLetterA(): GeneralPath {
        val path = GeneralPath()

        val width = WIDTH / 4
        val height = HEIGHT / 2

        path.moveTo(width.toFloat(), 0f)
        path.lineTo((width * 2).toFloat(), 0f)
        path.lineTo((width * 3).toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), height.toFloat())
        path.closePath()

        return path
    }

    private fun createLetterB(): GeneralPath {
        val path = GeneralPath()

        val width = WIDTH / 4
        val height = HEIGHT / 2

        path.moveTo(width.toFloat(), 0f)
        path.lineTo((width * 3).toFloat(), 0f)
        path.quadTo((width * 4).toFloat(), 0f, (width * 4).toFloat(), height / 2.toFloat())
        path.quadTo((width * 4).toFloat(), height.toFloat(), (width * 3).toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), height.toFloat())
        path.quadTo(0f, height.toFloat(), 0f, height / 2.toFloat())
        path.quadTo(0f, 0f, width.toFloat(), 0f)

        return path
    }

    private fun calculateIntermediateShapes(shapeA: GeneralPath, shapeB: GeneralPath): Array<GeneralPath> {
        val intermediateShapes = arrayOfNulls<GeneralPath>(NUM_STEPS)

        for (i in 0 until NUM_STEPS) {
            val completion = i.toFloat() / (NUM_STEPS - 1)

            val intermediateShape = GeneralPath()
            val iteratorA = shapeA.getPathIterator(null)
            val iteratorB = shapeB.getPathIterator(null)

            val coordsA = FloatArray(6)
            val coordsB = FloatArray(6)

            while (!iteratorA.isDone && !iteratorB.isDone) {
                val typeA = iteratorA.currentSegment(coordsA)
                val typeB = iteratorB.currentSegment(coordsB)

                if (typeA != typeB) {
                    throw IllegalArgumentException("Incompatible shapes")
                }

                val x = (1 - completion) * coordsA[0] + completion * coordsB[0]
                val y = (1 - completion) * coordsA[1] + completion * coordsB[1]

                if (i == 0) {
                    intermediateShape.moveTo(x, y)
                } else {
                    intermediateShape.lineTo(x, y)
                }

                iteratorA.next()
                iteratorB.next()
            }

            intermediateShapes[i] = intermediateShape
        }

        return intermediateShapes.requireNoNulls()
    }

}

fun main() {
    SwingUtilities.invokeLater {
        val animation = LetterTransformation()
        animation.isVisible = true
    }
}
