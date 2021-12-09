package k3dge.tools

import org.lwjgl.BufferUtils
import org.lwjgl.stb.STBImage
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
    data class MeshData(
        val vertices: List<Float>,
        val textureCoordinates: List<Float>,
        val normals: List<Float>,
        val indices: List<Int>,
    )

    companion object {

        fun loadShaderSourceFromFile(fileName: String): String? {
            return try {
                object {}::class.java.getResource(fileName).readText()
            }
            catch (e: Exception) {
                Log.e("Error loading shader resource $fileName. ${e.message}" )
                null
            }
        }

        fun loadTextureFromFile(fileName: String): TextureData? {
            return try {
                val width: IntBuffer = BufferUtils.createIntBuffer(1)
                val height: IntBuffer = BufferUtils.createIntBuffer(1)
                val components: IntBuffer = BufferUtils.createIntBuffer(1)
                var resourcePath = object {}::class.java.getResource(fileName).path

                //Why do I need this hack?
                //resourcePath = resourcePath.drop(1)
                val data: ByteBuffer? = stbi_load(resourcePath, width, height, components, 4)
                TextureData(width.get(), height.get(), components.get(), data!!)
            } catch (e: Exception) {
                Log.e("Error loading texture resource $fileName. ${e.message}. ${stbi_failure_reason()}" )
                null
            }
        }
    }
}