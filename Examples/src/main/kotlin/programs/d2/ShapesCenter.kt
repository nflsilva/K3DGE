package programs.d2

import core.CoreEngine
import core.CoreEngineDelegate
import core.entity.Entity2D
import core.entity.component2d.ShapeComponent
import org.joml.Vector2f
import render.common.dto.ColorData
import render.renderer2d.dto.Shape
import ui.dto.InputStateData

fun main(args: Array<String>) {

    val engine = CoreEngine()
    val gameLogic = ShapesCenter(engine)
    engine.delegate = gameLogic
    engine.start()

    println("Done!")
}

class ShapesCenter(private val engine: CoreEngine) : CoreEngineDelegate {

    private class Circle(positionX: Float, positionY: Float, color: ColorData):
        Entity2D(Vector2f(positionX, positionY), 0.0f, Vector2f(50f)) {
        init {
            addComponent(ShapeComponent(Shape.Type.CIRCLE, color))
        }
    }

    private class Square(positionX: Float, positionY: Float, color: ColorData):
        Entity2D(Vector2f(positionX, positionY), 0.0f, Vector2f(50f)) {
        init {
            addComponent(ShapeComponent(Shape.Type.SQUARE, color))
        }
    }

    private class Donut(positionX: Float, positionY: Float, color: ColorData):
        Entity2D(Vector2f(positionX, positionY), 0.0f, Vector2f(50f)) {
        init {
            addComponent(ShapeComponent(Shape.Type.DONUT, color))
        }
    }

    override fun onStart() {}

    override fun onUpdate(elapsedTime: Double, input: InputStateData) {

        val w = 1280f
        val h = 720f

        val bl = Circle(0f, 0f, ColorData(10f, 0f, 0f, 0f))
        engine.addEntity(bl)

        val tr = Donut(w, h, ColorData(0f, 10f, 0f, 0f))
        engine.addEntity(tr)

        val ct = Square(w/2, h/2, ColorData(0f, 0f, 10f, 0f))
        engine.addEntity(ct)

    }

    override fun onFrame() {}
    override fun onCleanUp() {}

}