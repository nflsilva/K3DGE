package k3dge.render.renderer2d

import k3dge.configuration.EngineConfiguration
import k3dge.render.common.dto.ColorData
import k3dge.render.renderer2d.model.SpriteBatch
import k3dge.render.renderer2d.dto.Sprite
import k3dge.render.common.dto.TransformData
import k3dge.render.renderer2d.shader.SpriteShader
import k3dge.render.common.dto.ShaderUniformData
import k3dge.render.common.shader.Shader
import k3dge.render.renderer2d.dto.Circle
import k3dge.render.renderer2d.model.CircleBatch
import k3dge.render.renderer2d.shader.CircleShader
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30.*

class Renderer2D(private val configuration: EngineConfiguration) {

    private lateinit var spriteBatches: MutableList<SpriteBatch>
    private lateinit var spriteShader: SpriteShader

    private lateinit var circleBatches: MutableList<CircleBatch>
    private lateinit var circleShader: CircleShader

    private var maxTextureSlots: Int = 0

    private var zoom = 0.75F
    private val bottom = 0.0F
    private val left = 0.0F
    private val top = configuration.resolutionHeight.toFloat() * (1.0F - zoom)
    private val right = configuration.resolutionWidth.toFloat() * (1.0F - zoom)

    fun onStart() {
        val mtsb = BufferUtils.createIntBuffer(1)
        glGetIntegerv(GL_MAX_TEXTURE_IMAGE_UNITS, mtsb)
        maxTextureSlots = mtsb.get()

        spriteBatches = mutableListOf(SpriteBatch(DEFAULT_BATCH_SIZE, maxTextureSlots))
        spriteShader = SpriteShader()

        circleBatches = mutableListOf(CircleBatch(DEFAULT_BATCH_SIZE))
        circleShader = CircleShader()

    }
    fun onFrame() {
        glViewport(0, 0, configuration.resolutionWidth, configuration.resolutionHeight)
        glEnable(GL_CULL_FACE)
        glCullFace(GL_BACK)
        render()
    }
    fun onUpdate() {
        spriteBatches.forEach {  it.clear() }
        circleBatches.forEach {  it.clear() }
    }
    fun onCleanUp(){
        for(batch in spriteBatches){
            batch.cleanUp()
        }
    }

    fun renderQuad(data: Sprite, transform: TransformData) {
        if(isVisible(transform, data.spriteSize.value)) {
            addToSuitableBatch(data, transform)
        }

        val bl = Circle(
            Vector3f(0.0f),
            10.0f,
            ColorData(1.0f, 0.0f, 0.0f, 1.0f))

        val c = Circle(
            Vector3f(640.0f, 360.0f, 0.0f),
            10.0f,
            ColorData(0.0f, 1.0f, 0.0f, 1.0f))

        val tr = Circle(
            Vector3f(1280.0f, 720.0f, 0.0f),
            10.0f,
            ColorData(0.0f, 0.0f, 1.0f, 1.0f))

        val transform1 = TransformData(
            Vector3f(0.0f),
            Vector3f(0.0f),
            Vector3f(1.0f),
        )

        circleBatches[0].addCircle(bl, transform1)
        circleBatches[0].addCircle(c, transform1)
        circleBatches[0].addCircle(tr, transform1)

    }

    fun renderCircle(data: Circle, transform: TransformData){

    }

    private fun isVisible(transform: TransformData, size: Int): Boolean {
        return transform.position.x > left - DEFAULT_SCREEN_RENDER_MARGINS &&
                transform.position.x + size < right + DEFAULT_SCREEN_RENDER_MARGINS &&
                transform.position.y < top + DEFAULT_SCREEN_RENDER_MARGINS &&
                transform.position.y + size > bottom - DEFAULT_SCREEN_RENDER_MARGINS
    }
    private fun addToSuitableBatch(data: Sprite, transform: TransformData) {
        var suitableBatch: SpriteBatch? = null
        for(batch in spriteBatches) {
            if(batch.isSpriteFull()) { continue }
            if(batch.hasTexture(data.texture) || !batch.isTextureFull()) {
                suitableBatch = batch
                break
            }
        }

        if(suitableBatch == null) {
            suitableBatch = SpriteBatch(DEFAULT_BATCH_SIZE, maxTextureSlots)
            spriteBatches.add(suitableBatch)
        }

        suitableBatch.addSprite(data, transform)
    }
    private fun prepareShader(shader: Shader, uniformData: ShaderUniformData) {
        shader.bind()
        shader.updateUniforms(uniformData)
    }
    private fun render() {
        val projectionMatrix: Matrix4f = Matrix4f().setOrtho2D(left, right, bottom, top)
        renderCircles(projectionMatrix)
        renderSprites(projectionMatrix)
    }
    private fun renderSprites(projectionMatrix: Matrix4f) {
        for(batch in spriteBatches){
            batch.bind()
            prepareShader(
                spriteShader,
                ShaderUniformData(
                    projectionMatrix = projectionMatrix,
                    textureSlots = batch.getTextureSlots()))
            glDrawElements(GL_TRIANGLES, batch.nIndices, GL_UNSIGNED_INT, 0)
            batch.unbind()
        }
    }
    private fun renderCircles(projectionMatrix: Matrix4f) {
        for(batch in circleBatches){
            batch.bind()
            prepareShader(
                circleShader,
                ShaderUniformData(projectionMatrix = projectionMatrix))
            glDrawElements(GL_POINTS, batch.nIndices, GL_UNSIGNED_INT, 0)
            batch.unbind()
        }
    }

    companion object {
        const val DEFAULT_BATCH_SIZE: Int = 10000
        const val DEFAULT_SCREEN_RENDER_MARGINS: Int  = 100
        //const val DEFAULT_SPRITE_SIZE: Int  = 16
    }

}