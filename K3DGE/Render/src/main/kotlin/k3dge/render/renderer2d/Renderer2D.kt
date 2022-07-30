package k3dge.render.renderer2d

import k3dge.configuration.EngineConfiguration
import k3dge.render.renderer2d.model.SpriteBatch
import k3dge.render.renderer2d.dto.Sprite
import k3dge.render.common.dto.TransformData
import k3dge.render.renderer2d.shader.SpriteShader
import k3dge.render.common.dto.ShaderUniformData
import k3dge.render.common.shader.Shader
import k3dge.render.renderer2d.dto.Point
import k3dge.render.renderer2d.model.ParticlesBatch
import k3dge.render.renderer2d.shader.ParticleShader
import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30.*

class Renderer2D(private val configuration: EngineConfiguration) {

    private lateinit var spriteBatches: MutableList<SpriteBatch>
    private lateinit var spriteShader: SpriteShader

    private lateinit var pointsBatches: MutableList<ParticlesBatch>
    private lateinit var particleShader: ParticleShader

    private var maxTextureSlots: Int = 0

    private var zoom = 0.0F
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

        particleShader = ParticleShader()
        pointsBatches = mutableListOf(ParticlesBatch(DEFAULT_BATCH_SIZE))

    }
    fun onFrame() {
        glViewport(0, 0, configuration.resolutionWidth, configuration.resolutionHeight)
        glEnable(GL_CULL_FACE)
        glCullFace(GL_BACK)
        draw()
    }
    fun onUpdate() {
        spriteBatches.forEach {  it.clear() }
        pointsBatches.forEach {  it.clear() }
    }
    fun onCleanUp(){
        for(batch in spriteBatches){
            batch.cleanUp()
        }
    }

    fun renderSprite(data: Sprite, transform: TransformData) {
        if(isVisible(transform, data.spriteSize.value)) {
            addToSuitableBatch(data, transform)
        }
    }

    fun renderPoint(data: Point, transform: TransformData){

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
            if(batch.isFull()) { continue }
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
    private fun draw() {
        val projectionMatrix: Matrix4f = Matrix4f().setOrtho2D(left, right, bottom, top)
        drawParticles(projectionMatrix)
        drawSprites(projectionMatrix)
    }
    private fun drawSprites(projectionMatrix: Matrix4f) {
        for(batch in spriteBatches){
            batch.bind()
            prepareShader(
                spriteShader,
                ShaderUniformData(
                    projectionMatrix = projectionMatrix,
                    textureSlots = batch.getTextureSlots()))
            glDrawElements(GL_TRIANGLES, batch.nIndexes, GL_UNSIGNED_INT, 0)
            batch.unbind()
        }
    }
    private fun drawParticles(projectionMatrix: Matrix4f) {
        for(batch in pointsBatches){
            batch.bind()
            prepareShader(
                particleShader,
                ShaderUniformData(projectionMatrix = projectionMatrix))
            glDrawElements(GL_POINTS, batch.nEntities, GL_UNSIGNED_INT, 0)
            batch.unbind()
        }
    }

    companion object {
        const val DEFAULT_BATCH_SIZE: Int = 10000
        const val DEFAULT_SCREEN_RENDER_MARGINS: Int  = 100
        //const val DEFAULT_SPRITE_SIZE: Int  = 16
    }

}