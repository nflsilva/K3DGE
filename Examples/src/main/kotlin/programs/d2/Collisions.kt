package programs.d2

import core.CoreEngine
import core.CoreEngineDelegate
import core.entity.Entity2D
import core.entity.component2d.Collision2DBoxComponent
import core.entity.component2d.ShapeComponent
import org.joml.Vector2f
import org.joml.Vector4f
import physics.physics2d.dto.CollisionResult
import render.common.dto.ColorData
import render.renderer2d.dto.Shape
import ui.dto.InputStateData

fun main(args: Array<String>) {

    val engine = CoreEngine()
    val gameLogic = Collisions(engine)
    engine.delegate = gameLogic
    engine.start()

    println("Done!")
}

class Collisions(private val engine: CoreEngine) : CoreEngineDelegate {

    private val movingBodies: MutableList<Body> = mutableListOf()
    private val gravity: Vector2f = Vector2f(0f, -1000f)

    private class Body(
        positionX: Float,
        positionY: Float,
        width: Float,
        private val height: Float,
        type: Shape.Type,
        color: ColorData):
        Entity2D(Vector2f(positionX, positionY), 0.0f, Vector2f(width, height)) {

        private var boundedByX : Float? = null
        private var boundedByY : Float? = null

        init {
            addComponent(ShapeComponent(type, color))
            addComponent(Collision2DBoxComponent { this.onCollisionCallback(it) })
        }

        private val acceleration: Vector2f = Vector2f().zero()
        var oldPosition = Vector2f(positionX, positionY)

        fun step(dt: Double){

            var currentPosition = Vector2f(transform.getPos().x, transform.getPos().y)
            val velocity = Vector2f(currentPosition.x, currentPosition.y).sub(oldPosition)

            oldPosition = Vector2f(currentPosition.x, currentPosition.y)

            acceleration.mul(dt.toFloat() * dt.toFloat())
            currentPosition = currentPosition.add(velocity).add(acceleration)

            if(boundedByY != null){
                currentPosition.y = boundedByY!! + height / 2
                boundedByY = null
            }

            transform.setPosition(Vector4f(currentPosition, 0f, 0f))

            acceleration.zero()
        }

        fun accelerate(value: Vector2f){
            acceleration.add(value)
        }

        fun onCollisionCallback(collisions: MutableList<CollisionResult>){
            collisions.forEach {

                boundedByY = it.lastValidPosition.y
            }
        }

    }

    override fun onStart() {

        val w = 1280f
        val h = 720f

        //val leftBall = Body(w / 4, 720f, 10f, 10f, Shape.Type.CIRCLE, ColorData(0.9f, 0.4f, 0.4f, 1.0f))
        val middleBall = Body(w / 2, 720f, 10f, 10f, Shape.Type.CIRCLE, ColorData(0.4f, 0.9f, 0.4f, 1.0f))
        //val rightBall = Body(3 * w / 4, 720f, 10f, 10f, Shape.Type.CIRCLE, ColorData(0.4f, 0.4f, 0.9f, 1.0f))

        //movingBodies.add(leftBall)
        movingBodies.add(middleBall)
        //movingBodies.add(rightBall)

        //addEntity(leftBall)
        engine.addEntity(middleBall)
        //engine.addEntity(rightBall)

        val wall = Body(w / 2, 5f, w / 2, 5f, Shape.Type.SQUARE, ColorData(0.75f, 0.75f, 0.75f, 1.0f))
        engine.addEntity(wall)

    }

    override fun onUpdate(elapsedTime: Double, input: InputStateData) {

        movingBodies.forEach { body ->
            body.accelerate(gravity)

            body.step(elapsedTime)
        }

    }

    override fun onFrame() {}
    override fun onCleanUp() {}

}