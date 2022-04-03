package k3dge.render.renderer2d

import k3dge.configuration.EngineConfiguration
import k3dge.render.renderer2d.dto.SpriteBatchRenderData
import k3dge.render.renderer2d.dto.SpriteRenderData
import k3dge.render.renderer2d.shader.SpriteShader
import k3dge.render.renderer3d.dto.ShaderUniformData
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30.*

class Renderer2D(private val configuration: EngineConfiguration) {

    private lateinit var spriteBatches: MutableList<SpriteBatchRenderData>
    private lateinit var spriteShader: SpriteShader

    private var maxTextureSlots: Int = 0
    private var zoom = 1.0F
    private val bottom = 0.0F
    private val left = 0.0F
    private val top = configuration.resolutionHeight.toFloat() * zoom
    private val right = configuration.resolutionWidth.toFloat() * zoom

    fun onStart() {
        val mtsb = BufferUtils.createIntBuffer(1)
        glGetIntegerv(GL_MAX_TEXTURE_IMAGE_UNITS, mtsb)
        maxTextureSlots = mtsb.get()

        spriteBatches = mutableListOf(SpriteBatchRenderData(
            DEFAULT_BATCH_SIZE,
            maxTextureSlots,
            DEFAULT_SPRITE_SIZE))
        spriteShader = SpriteShader()
    }
    fun onFrame() {
        glViewport(0, 0, configuration.resolutionWidth, configuration.resolutionHeight)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glEnable(GL_CULL_FACE)
        glCullFace(GL_BACK)

        drawBatches()
    }
    fun onUpdate() {
        for(batch in spriteBatches){
            batch.clear()
        }
    }
    fun onCleanUp(){
        for(batch in spriteBatches){
            batch.cleanUp()
        }
    }

    fun renderSprite(data: SpriteRenderData) {

        val isVisible =
                    data.position.x > left - DEFAULT_SCREEN_RENDER_MARGINS &&
                    data.position.x + DEFAULT_SPRITE_SIZE < right + DEFAULT_SCREEN_RENDER_MARGINS &&
                    data.position.y < top + DEFAULT_SCREEN_RENDER_MARGINS &&
                    data.position.y + DEFAULT_SPRITE_SIZE > bottom - DEFAULT_SCREEN_RENDER_MARGINS

        if(!isVisible) { return }

        var suitableBatch: SpriteBatchRenderData? = null
        for(batch in spriteBatches) {
            if(batch.isSpriteFull()) { continue }

            if(batch.hasTexture(data.texture.id) || !batch.isTextureFull()) {
                suitableBatch = batch
                break
            }
        }
        if(suitableBatch == null) {
            suitableBatch = SpriteBatchRenderData(
                DEFAULT_BATCH_SIZE,
                maxTextureSlots,
                DEFAULT_SPRITE_SIZE)
            spriteBatches.add(suitableBatch)
        }

        suitableBatch.addSprite(data)
    }

    private fun prepareShader(uniformData: ShaderUniformData) {
        spriteShader.bind()
        spriteShader.updateUniforms(uniformData)
    }
    private fun drawBatches(){

        val projectionMatrix: Matrix4f = Matrix4f()
            .identity()
            .setOrtho2D(left, right, bottom, top)

        for(batch in spriteBatches){
            batch.bind()
            prepareShader(ShaderUniformData(
                projectionMatrix = projectionMatrix,
                textureSlots = batch.getTextureSlots()
            ))
            glDrawElements(GL_TRIANGLES, batch.nIndices, GL_UNSIGNED_INT, 0)
            batch.unbind()
        }
    }

    companion object {
        const val DEFAULT_BATCH_SIZE: Int = 10000
        const val DEFAULT_SCREEN_RENDER_MARGINS: Int  = 100
        const val DEFAULT_SPRITE_SIZE: Int  = 16
    }

}