package k3dge.render.renderer2d.model

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
    private val texCoordinatesVbo: Int
    private val indexesVbo: Int

    private val positions: FloatBuffer
    private val textureCoordinates: FloatBuffer
    private val indices: IntBuffer

    private val textureIndicesVbo: Int
    private val textureIndices: FloatBuffer
    private val textures: MutableList<Int> = mutableListOf()

    init {
        glBindVertexArray(vao)

        positionsVbo = glGenBuffers()
        positions = BufferUtils.createFloatBuffer(maxQuads * 4 * 2)
        glBindBuffer(GL_ARRAY_BUFFER, positionsVbo)
        glBufferData(GL_ARRAY_BUFFER, positions, GL_DYNAMIC_DRAW)
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0)

        texCoordinatesVbo = glGenBuffers()
        textureCoordinates = BufferUtils.createFloatBuffer(maxQuads * 4 * 2)
        glBindBuffer(GL_ARRAY_BUFFER, texCoordinatesVbo)
        glBufferData(GL_ARRAY_BUFFER, textureCoordinates, GL_DYNAMIC_DRAW)
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0)

        textureIndicesVbo = glGenBuffers()
        textureIndices = BufferUtils.createFloatBuffer(maxQuads * 4)
        glBindBuffer(GL_ARRAY_BUFFER, textureIndicesVbo)
        glBufferData(GL_ARRAY_BUFFER, textureIndices, GL_DYNAMIC_DRAW)
        glVertexAttribPointer(2, 1, GL_FLOAT, false, 0, 0)

        indexesVbo = glGenBuffers()
        indices = BufferUtils.createIntBuffer(maxQuads * 3 * 2)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexesVbo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_DYNAMIC_DRAW)

        glBindBuffer(GL_ARRAY_BUFFER, 0)

    }
    fun cleanUp(){
        glBindVertexArray(0)
        glDeleteVertexArrays(vao)
        glDeleteBuffers(arrayOf(positionsVbo, texCoordinatesVbo, textureIndicesVbo, indexesVbo).toIntArray())
    }

    fun bind(){

        glBindBuffer(GL_ARRAY_BUFFER, positionsVbo)
        glBufferSubData(GL_ARRAY_BUFFER, 0, positions.flip())

        glBindBuffer(GL_ARRAY_BUFFER, texCoordinatesVbo)
        glBufferSubData(GL_ARRAY_BUFFER, 0, textureCoordinates.flip())

        glBindBuffer(GL_ARRAY_BUFFER, textureIndicesVbo)
        glBufferSubData(GL_ARRAY_BUFFER, 0, textureIndices.flip())

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexesVbo)
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, indices.flip())

        glBindVertexArray(vao)
        for (i in 0 until 3) {
            glEnableVertexAttribArray(i)
        }

        for(i in 0 until textures.size){
            glActiveTexture(GL_TEXTURE0 + i)
            glBindTexture(GL_TEXTURE_2D, textures[i])
        }

    }
    fun unbind(){
        for (i in 0 until 3) {
            glDisableVertexAttribArray(i)
        }
        glBindVertexArray(0)
    }

    fun addSprite(sprite: Sprite){

        //TODO: Create exception for this
        if(nQuads >= maxQuads || textures.size >= maxTextures) { return }

        val x = 0//data.position.x
        val y = 0//data.position.y

        val tl = Vector2f(0.0f + x, sprite.spriteSize.value + y.toFloat())
        val bl = Vector2f(0.0f + x, 0.0f + y)
        val br = Vector2f(sprite.spriteSize.value + x.toFloat(), 0.0f + y)
        val tr = Vector2f(sprite.spriteSize.value + x.toFloat(), sprite.spriteSize.value + y.toFloat())

        positions
            .put(tl.x).put(tl.y)
            .put(bl.x).put(bl.y)
            .put(br.x).put(br.y)
            .put(tr.x).put(tr.y)

        textureCoordinates
            .put(sprite.startTextureCoordinates.x).put(sprite.startTextureCoordinates.y)    // TL
            .put(sprite.startTextureCoordinates.x).put(sprite.endTextureCoordinates.y)      // BL
            .put(sprite.endTextureCoordinates.x).put(sprite.endTextureCoordinates.y)        // BR
            .put(sprite.endTextureCoordinates.x).put(sprite.startTextureCoordinates.y)      // TR

        var textureId = textures.find { id -> sprite.textureId == id }
        if(textureId == null){
            textures.add(sprite.textureId)
            textureId = textures.size
        }

        textureIndices
            .put(textureId*1F).put(textureId*1F).put(textureId*1F).put(textureId*1F)

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
    fun hasTexture(texture: Int): Boolean {
        return textures.contains(texture)
    }
    fun getTextureSlots(): Int {
        return textures.size
    }
}