package programs.d2.v2

import k3dge.core.CoreEngine
import k3dge.core.CoreEngineDelegate
import k3dge.core.entity.Entity2D
import k3dge.core.entity.component2d.ShapeComponent
import k3dge.render.common.dto.ColorData
import k3dge.render.renderer2d.dto.Shape
import k3dge.ui.dto.InputStateData
import org.joml.Random
import org.joml.Vector2f
import org.joml.Vector4f

val engine = CoreEngine()
val shapes = mutableListOf<Ball>()

fun main(args: Array<String>) {

    val gameLogic = GameLogic()
    engine.delegate = gameLogic
    engine.start()

    println("Done!")
}

class Ball(positionX: Float, positionY: Float, radius: Float):
    Entity2D(Vector2f(positionX, positionY), 0.0f, Vector2f(radius)) {

    private val acceleration: Vector2f = Vector2f().zero()
    var oldPosition = Vector2f(positionX, positionY)

    init {
        val rr = Random().nextFloat()
        val rg = Random().nextFloat()
        val rb = Random().nextFloat()
        addComponent(ShapeComponent(Shape.Type.DONUT, ColorData(rr, rg, rb, 1.0f)))
    }

    fun step(dt: Double){

        var currentPosition = Vector2f(transform.getPos().x, transform.getPos().y)
        val velocity = Vector2f(currentPosition.x, currentPosition.y).sub(oldPosition)

        oldPosition = Vector2f(currentPosition.x, currentPosition.y)

        acceleration.mul(dt.toFloat() * dt.toFloat())
        currentPosition = currentPosition.add(velocity).add(acceleration)

        transform.setPosition(Vector4f(currentPosition, 0f, 0f))

        acceleration.zero()
    }

    fun accelerate(value: Vector2f){
        acceleration.add(value)
    }
}

class GameLogic : CoreEngineDelegate {

    private val gravity: Vector2f = Vector2f(0f, -1000f)
    private var lastPrintOn: Int = 0

    override fun onStart() {


    }
    override fun onUpdate(elapsedTime: Double, input: InputStateData) {

        val rx = Random().nextFloat() * 1280
        val ry = Random().nextFloat() * 720
        val e = Ball(rx, ry, Random().nextFloat() * 25f + 10f)
        engine.addEntity(e)
        shapes.add(e)

        shapes.forEach { shape ->
            shape.accelerate(gravity)

            if(shape.transform.getPos().y <= 10f){
                shape.accelerate(Vector2f(0f, 5000f))
            }

            shape.step(elapsedTime)
        }

        if(shapes.size >= lastPrintOn + 100) {
            println(shapes.size.toString() + " shapes")
            lastPrintOn = shapes.size
        }

    }
    override fun onFrame() {}
    override fun onCleanUp() {}

}