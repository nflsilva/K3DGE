package k3dge.render.renderer2d.model

import k3dge.render.common.dto.TransformData
import k3dge.render.common.model.Texture
import k3dge.render.renderer2d.dto.Sprite
import org.joml.Vector2f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30.*
import java.nio.FloatBuffer
import java.nio.IntBuffer

class SpriteBatch(private val maxQuads: Int,
                  private val maxTextures: Int) {

    var nIndices: Int = 0
    var nQuads: Int = 0
    private val vao: Int = glGenVertexArrays()
    private val positionsVbo: Int
    private val sizesVbo: Int
    private val translationsVbo: Int
    private val rotationsVbo: Int
    private val scalesVbo: Int
    private val textureCoordinatesVbo: Int
    private val indexesVbo: Int

    private val positions: FloatBuffer
    private val sizes: FloatBuffer
    private val translations: FloatBuffer
    private val rotations: FloatBuffer
    private val scales: FloatBuffer
    private val textureCoordinates: FloatBuffer
    private val indices: IntBuffer

    private val textureIndicesVbo: Int
    private val textureIndices: FloatBuffer
    private val textures: MutableList<Texture> = mutableListOf()

    init {
        glBindVertexArray(vao)

        positionsVbo = glGenBuffers()
        positions = BufferUtils.createFloatBuffer(maxQuads * 4 * 2)
        glBindBuffer(GL_ARRAY_BUFFER, positionsVbo)
        glBufferData(GL_ARRAY_BUFFER, positions, GL_DYNAMIC_DRAW)
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0)

        sizesVbo = glGenBuffers()
        sizes = BufferUtils.createFloatBuffer(maxQuads * 4 * 1)
        glBindBuffer(GL_ARRAY_BUFFER, sizesVbo)
        glBufferData(GL_ARRAY_BUFFER, sizes, GL_DYNAMIC_DRAW)
        glVertexAttribPointer(1, 1, GL_FLOAT, false, 0, 0)

        translationsVbo = glGenBuffers()
        translations = BufferUtils.createFloatBuffer(maxQuads * 4 * 2)
        glBindBuffer(GL_ARRAY_BUFFER, translationsVbo)
        glBufferData(GL_ARRAY_BUFFER, translations, GL_DYNAMIC_DRAW)
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0)

        rotationsVbo = glGenBuffers()
        rotations = BufferUtils.createFloatBuffer(maxQuads * 4 * 1)
        glBindBuffer(GL_ARRAY_BUFFER, rotationsVbo)
        glBufferData(GL_ARRAY_BUFFER, rotations, GL_DYNAMIC_DRAW)
        glVertexAttribPointer(3, 1, GL_FLOAT, false, 0, 0)

        scalesVbo = glGenBuffers()
        scales = BufferUtils.createFloatBuffer(maxQuads * 4 * 2)
        glBindBuffer(GL_ARRAY_BUFFER, scalesVbo)
        glBufferData(GL_ARRAY_BUFFER, scales, GL_DYNAMIC_DRAW)
        glVertexAttribPointer(4, 2, GL_FLOAT, false, 0, 0)

        textureCoordinatesVbo = glGenBuffers()
        textureCoordinates = BufferUtils.createFloatBuffer(maxQuads * 4 * 2)
        glBindBuffer(GL_ARRAY_BUFFER, textureCoordinatesVbo)
        glBufferData(GL_ARRAY_BUFFER, textureCoordinates, GL_DYNAMIC_DRAW)
        glVertexAttribPointer(5, 2, GL_FLOAT, false, 0, 0)

        textureIndicesVbo = glGenBuffers()
        textureIndices = BufferUtils.createFloatBuffer(maxQuads * 4 * 1)
        glBindBuffer(GL_ARRAY_BUFFER, textureIndicesVbo)
        glBufferData(GL_ARRAY_BUFFER, textureIndices, GL_DYNAMIC_DRAW)
        glVertexAttribPointer(6, 1, GL_FLOAT, false, 0, 0)

        indexesVbo = glGenBuffers()
        indices = BufferUtils.createIntBuffer(maxQuads * 3 * 2)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexesVbo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_DYNAMIC_DRAW)

        glBindBuffer(GL_ARRAY_BUFFER, 0)

    }
    fun cleanUp(){
        glBindVertexArray(0)
        glDeleteVertexArrays(vao)
        glDeleteBuffers(arrayOf(positionsVbo, translationsVbo, rotationsVbo, scalesVbo, textureCoordinatesVbo, textureIndicesVbo, indexesVbo).toIntArray())
    }

    fun bind(){

        glBindBuffer(GL_ARRAY_BUFFER, positionsVbo)
        glBufferSubData(GL_ARRAY_BUFFER, 0, positions.flip())

        glBindBuffer(GL_ARRAY_BUFFER, sizesVbo)
        glBufferSubData(GL_ARRAY_BUFFER, 0, sizes.flip())

        glBindBuffer(GL_ARRAY_BUFFER, translationsVbo)
        glBufferSubData(GL_ARRAY_BUFFER, 0, translations.flip())

        glBindBuffer(GL_ARRAY_BUFFER, rotationsVbo)
        glBufferSubData(GL_ARRAY_BUFFER, 0, rotations.flip())

        glBindBuffer(GL_ARRAY_BUFFER, scalesVbo)
        glBufferSubData(GL_ARRAY_BUFFER, 0, scales.flip())

        glBindBuffer(GL_ARRAY_BUFFER, textureCoordinatesVbo)
        glBufferSubData(GL_ARRAY_BUFFER, 0, textureCoordinates.flip())

        glBindBuffer(GL_ARRAY_BUFFER, textureIndicesVbo)
        glBufferSubData(GL_ARRAY_BUFFER, 0, textureIndices.flip())

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexesVbo)
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, indices.flip())

        glBindVertexArray(vao)
        for(i in 0 until 6) {
            glEnableVertexAttribArray(i)
        }

        for(i in 0 until textures.size) {
            textures[i].bind(i)
        }

    }
    fun unbind(){
        for (i in 0 until 6) {
            glDisableVertexAttribArray(i)
        }
        glBindVertexArray(0)
    }

    fun addSprite(sprite: Sprite, transform: TransformData){

        //TODO: Create exception for this
        if(nQuads >= maxQuads || textures.size >= maxTextures) { return }

        val size = sprite.spriteSize.value.toFloat()
        val tl = Vector2f(0f, size)
        val bl = Vector2f(0f, 0f)
        val br = Vector2f(size, 0f)
        val tr = Vector2f(size, size)

        positions
            .put(tl.x).put(tl.y)
            .put(bl.x).put(bl.y)
            .put(br.x).put(br.y)
            .put(tr.x).put(tr.y)

        sizes
            .put(size)
            .put(size)
            .put(size)
            .put(size)

        translations
            .put(transform.position.x).put(transform.position.y)
            .put(transform.position.x).put(transform.position.y)
            .put(transform.position.x).put(transform.position.y)
            .put(transform.position.x).put(transform.position.y)

        rotations
            .put(transform.rotation.z)
            .put(transform.rotation.z)
            .put(transform.rotation.z)
            .put(transform.rotation.z)

        scales
            .put(transform.scale.x).put(transform.scale.y)
            .put(transform.scale.x).put(transform.scale.y)
            .put(transform.scale.x).put(transform.scale.y)
            .put(transform.scale.x).put(transform.scale.y)

        textureCoordinates
            .put(sprite.startTextureCoordinates.x).put(sprite.startTextureCoordinates.y)    // TL
            .put(sprite.startTextureCoordinates.x).put(sprite.endTextureCoordinates.y)      // BL
            .put(sprite.endTextureCoordinates.x).put(sprite.endTextureCoordinates.y)        // BR
            .put(sprite.endTextureCoordinates.x).put(sprite.startTextureCoordinates.y)      // TR

        var textureIndex = textures.indexOf(sprite.texture).toFloat()
        if(textureIndex < 0){
            textures.add(sprite.texture)
            textureIndex = textures.size.toFloat()
        }
        textureIndices
            .put(textureIndex)
            .put(textureIndex)
            .put(textureIndex)
            .put(textureIndex)

        val indexOffset = nQuads * 4
        indices
            .put(0 + indexOffset)
            .put(1 + indexOffset)
            .put(2 + indexOffset)

            .put(2 + indexOffset)
            .put(3 + indexOffset)
            .put(0 + indexOffset)

        nQuads += 1
        nIndices += 6
    }
    fun clear() {
        positions.clear()
        sizes.clear()
        translations.clear()
        rotations.clear()
        scales.clear()
        textureCoordinates.clear()
        indices.clear()
        textureIndices.clear()
        textures.clear()
        nIndices = 0
        nQuads = 0
    }
    fun isSpriteFull(): Boolean {
        return nQuads == maxQuads
    }
    fun isTextureFull(): Boolean {
        return textures.size == maxTextures
    }
    fun hasTexture(texture: Texture): Boolean {
        return textures.contains(texture)
    }
    fun getTextureSlots(): Int {
        return textures.size
    }

}