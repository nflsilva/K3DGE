package k3dge.render.renderer2d

import k3dge.configuration.EngineConfiguration
import k3dge.render.renderer2d.dto.SpriteBatchRenderData
import k3dge.render.renderer2d.dto.SpriteRenderData
import k3dge.render.renderer2d.shader.SpriteShader
import k3dge.render.renderer3d.dto.ShaderUniformData
import k3dge.tools.Log
import org.joml.Matrix4f
import org.lwjgl.opengl.GL30.*

class Renderer2D(private val configuration: EngineConfiguration) {

    private var currentBatchIndex: Int = 0
    private lateinit var spriteBatches: MutableList<SpriteBatchRenderData>
    private lateinit var spriteShader: SpriteShader

    private var projectionMatrix: Matrix4f = Matrix4f()
        .setOrtho2D(
            0.0F,
            configuration.resolutionWidth * 1.0F,
            0.0F,
            configuration.resolutionHeight * 1.0F)

    private var viewMatrix: Matrix4f = Matrix4f().identity()

    fun onStart() {
        spriteBatches = mutableListOf(SpriteBatchRenderData(DEFAULT_BATCH_SIZE))
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
        currentBatchIndex = 0
    }

    fun renderSprite(data: SpriteRenderData) {

        if(
            data.position.x >= -10F &&
            data.position.x <= configuration.resolutionWidth + 10F &&
            data.position.y >= -10F &&
            data.position.y <= configuration.resolutionHeight + 10F) {

            if(spriteBatches[currentBatchIndex].isFull()) {
                currentBatchIndex += 1
                if(currentBatchIndex == spriteBatches.size) {
                    val currentBatch = SpriteBatchRenderData(DEFAULT_BATCH_SIZE)
                    spriteBatches.add(currentBatch)
                }
            }
            spriteBatches[currentBatchIndex].appendSpriteData(data)

        }


    }

    private fun prepareShader(uniformData: ShaderUniformData) {
        spriteShader.bind()
        spriteShader.updateUniforms(uniformData)
    }
    private fun drawBatches(){
        for(batch in spriteBatches){
            batch.bind()
            prepareShader(ShaderUniformData(
                projectionMatrix = projectionMatrix
            ))

            glDrawElements(GL_TRIANGLES, batch.nIndices, GL_UNSIGNED_INT, 0)
            batch.unbind()
        }
    }

    companion object {
        const val DEFAULT_BATCH_SIZE = 10000
    }

}