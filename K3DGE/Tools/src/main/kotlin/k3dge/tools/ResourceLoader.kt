package k3dge.tools

import org.lwjgl.stb.STBImage.*
import java.lang.Exception
import java.nio.ByteBuffer
import java.nio.IntBuffer

class ResourceLoader {

    data class TextureData(
        val width: Int,
        val height: Int,
        val components: Int,
        val data: ByteBuffer
    )

    companion object {
        fun loadTextureFromFile(fileName: String): TextureData? {
            return try {
                val width: IntBuffer = IntBuffer.allocate(1)
                val height: IntBuffer = IntBuffer.allocate(1)
                val components: IntBuffer = IntBuffer.allocate(1)
                val data: ByteBuffer? = stbi_load(fileName, width, height, components, 4)
                TextureData(width.get(), height.get(), components.get(), data!!)
            } catch (e: Exception) {
                Log.e("Error loading texture resource $fileName. ${e.message}" )
                null
            }
        }
    }
}