package render.renderer2d

import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30.*
import render.common.dto.ShaderUniformData
import render.common.shader.Shader
import render.renderer2d.dto.Particle
import render.renderer2d.dto.Shape
import render.renderer2d.dto.Sprite
import render.renderer2d.dto.Transform2DData
import render.renderer2d.model.ParticlesBatch
import render.renderer2d.model.ShapesBatch
import render.renderer2d.model.SpriteBatch
import render.renderer2d.shader.ParticleShader
import render.renderer2d.shader.ShapeShader
import render.renderer2d.shader.SpriteShader
import tools.configuration.EngineConfiguration

class Renderer2D(private val configuration: EngineConfiguration) {

    private lateinit var spriteBatches: MutableList<SpriteBatch>
    private lateinit var spriteShader: SpriteShader

    private lateinit var particleBatches: MutableList<ParticlesBatch>
    private lateinit var particleShader: ParticleShader

    private lateinit var shapeBatches: MutableList<ShapesBatch>
    private lateinit var shapeShader: ShapeShader

    private var maxTextureSlots: Int = 0

    private var zoom = 0.0F
    private val bottom = 0.0F
    private val left = 0.0F
    private val top = configuration.resolutionHeight.toFloat() * (1.0F - zoom)
    private val right = configuration.resolutionWidth.toFloat() * (1.0F - zoom)

    companion object {
        const val DEFAULT_BATCH_SIZE: Int = 10000
        const val DEFAULT_SCREEN_RENDER_MARGINS: Int = 100
        //const val DEFAULT_SPRITE_SIZE: Int  = 16
    }

    fun onStart() {
        val mtsb = BufferUtils.createIntBuffer(1)
        glGetIntegerv(GL_MAX_TEXTURE_IMAGE_UNITS, mtsb)
        maxTextureSlots = mtsb.get()

        spriteBatches = mutableListOf()
        spriteShader = SpriteShader()

        particleShader = ParticleShader()
        particleBatches = mutableListOf()

        shapeShader = ShapeShader()
        shapeBatches = mutableListOf()

    }

    fun onFrame() {
        glViewport(0, 0, configuration.resolutionWidth, configuration.resolutionHeight)
        glEnable(GL_CULL_FACE)
        glCullFace(GL_BACK)
        draw()
    }

    fun onUpdate() {
        spriteBatches.forEach { it.clear() }
        particleBatches.forEach { it.clear() }
        shapeBatches.forEach { it.clear() }
    }

    fun onCleanUp() {
        for (batch in spriteBatches) {
            batch.cleanUp()
        }
    }

    fun renderSprite(data: Sprite, transform: Transform2DData) {
        if (isVisible(transform, data.size.value)) {
            addToSuitableSpriteBatch(data, transform)
        }
    }

    fun renderParticle(data: Particle, transform: Transform2DData) {
        if (isVisible(transform, data.size)) {
            addToSuitableParticleBatch(data, transform)
        }
    }

    fun renderShape(data: Shape, transform: Transform2DData) {
        if (isVisible(transform, 0f)) {
            addToSuitableShapeBatch(data, transform)
        }
    }

    private fun isVisible(transform: Transform2DData, size: Float): Boolean {
        return transform.position.x > left - DEFAULT_SCREEN_RENDER_MARGINS &&
                transform.position.x + size < right + DEFAULT_SCREEN_RENDER_MARGINS &&
                transform.position.y < top + DEFAULT_SCREEN_RENDER_MARGINS &&
                transform.position.y + size > bottom - DEFAULT_SCREEN_RENDER_MARGINS
    }

    private fun addToSuitableSpriteBatch(data: Sprite, transform: Transform2DData) {
        var suitableBatch: SpriteBatch? = null
        for (batch in spriteBatches) {
            if (batch.isFull()) {
                continue
            }
            if (batch.hasTexture(data.texture) || !batch.isTextureFull()) {
                suitableBatch = batch
                break
            }
        }

        if (suitableBatch == null) {
            suitableBatch = SpriteBatch(DEFAULT_BATCH_SIZE, maxTextureSlots)
            spriteBatches.add(suitableBatch)
        }

        suitableBatch.addSprite(data, transform)
    }

    private fun addToSuitableParticleBatch(data: Particle, transform: Transform2DData) {
        var suitableBatch: ParticlesBatch? = null
        for (batch in particleBatches) {
            if (batch.isFull()) {
                continue
            }
            suitableBatch = batch
            break
        }

        if (suitableBatch == null) {
            suitableBatch = ParticlesBatch(DEFAULT_BATCH_SIZE)
            particleBatches.add(suitableBatch)
        }

        suitableBatch.addParticle(data, transform)
    }

    private fun addToSuitableShapeBatch(data: Shape, transform: Transform2DData) {
        var suitableBatch: ShapesBatch? = null
        for (batch in shapeBatches) {
            if (batch.isFull()) {
                continue
            }
            suitableBatch = batch
            break
        }


        if (suitableBatch == null) {
            suitableBatch = ShapesBatch(DEFAULT_BATCH_SIZE)
            shapeBatches.add(suitableBatch)
        }

        suitableBatch.addShape(data, transform)
    }

    private fun prepareShader(shader: Shader, uniformData: ShaderUniformData) {
        shader.bind()
        shader.updateUniforms(uniformData)
    }

    private fun draw() {
        val projectionMatrix: Matrix4f = Matrix4f().setOrtho2D(left, right, bottom, top)
        drawParticles(projectionMatrix)
        drawSprites(projectionMatrix)
        drawShapes(projectionMatrix)
    }

    private fun drawSprites(projectionMatrix: Matrix4f) {
        for (batch in spriteBatches) {
            batch.bind()
            prepareShader(
                spriteShader,
                ShaderUniformData(
                    projectionMatrix = projectionMatrix,
                    textureSlots = batch.getTextureSlots()
                )
            )
            glDrawElements(GL_TRIANGLES, batch.nIndexes, GL_UNSIGNED_INT, 0)
            batch.unbind()
        }
    }

    private fun drawParticles(projectionMatrix: Matrix4f) {
        for (batch in particleBatches) {
            batch.bind()
            prepareShader(
                particleShader,
                ShaderUniformData(projectionMatrix = projectionMatrix)
            )
            glDrawElements(GL_POINTS, batch.nIndexes, GL_UNSIGNED_INT, 0)
            batch.unbind()
        }
    }

    private fun drawShapes(projectionMatrix: Matrix4f) {
        for (batch in shapeBatches) {
            batch.bind()
            prepareShader(
                shapeShader,
                ShaderUniformData(projectionMatrix = projectionMatrix)
            )
            glDrawElements(GL_TRIANGLES, batch.nIndexes, GL_UNSIGNED_INT, 0)
            batch.unbind()
        }
    }

}