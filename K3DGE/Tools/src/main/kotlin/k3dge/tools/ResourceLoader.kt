package k3dge.tools

import org.lwjgl.BufferUtils
import org.lwjgl.stb.STBImage.stbi_load
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
        val vertices: MutableList<Float> = mutableListOf(),
        val textureCoordinates: MutableList<Float> = mutableListOf(),
        val normals: MutableList<Float> = mutableListOf(),
        val indices: MutableList<Int> = mutableListOf()
    )

    companion object {
        private fun getResourceURLFromFile(fileName: String): String? {
            object{}::class.java.getResource(fileName)?.let { url->
                var resourcePath = url.path
                //Why do I need this hack in windows?
                //resourcePath = resourcePath.drop(1)
                return resourcePath
            }
            Log.e("Error loading resource $fileName.")
            return null
        }
        fun loadShaderSourceFromFile(fileName: String): String? {
            getResourceURLFromFile(fileName)?.let { resourcePath->
                val url = object{}::class.java.getResource(fileName)
                return url.readText()
            }
            return null
        }
        fun loadTextureFromFile(fileName: String): TextureData? {
            getResourceURLFromFile(fileName)?.let { resourcePath->
                val width: IntBuffer = BufferUtils.createIntBuffer(1)
                val height: IntBuffer = BufferUtils.createIntBuffer(1)
                val components: IntBuffer = BufferUtils.createIntBuffer(1)
                val data: ByteBuffer? = stbi_load(resourcePath, width, height, components, 4)
                return TextureData(width.get(), height.get(), components.get(), data!!)
            }
            return null
        }
        fun loadMeshFromFile(fileName: String): MeshData? {
            getResourceURLFromFile(fileName)?.let { resourcePath->
                SimpleObj.fromFile(resourcePath)?.let { parsedObj->
                    val meshData = MeshData()
                    var faceIndex = 0
                    parsedObj.faces.forEach { face->
                        val vertex = parsedObj.positions[face.v - 1]
                        meshData.vertices.add(vertex.x)
                        meshData.vertices.add(vertex.y)
                        meshData.vertices.add(vertex.z)
                        face.vn?.let { vn->
                            val normal = parsedObj.normals[vn - 1]
                            meshData.normals.add(normal.x)
                            meshData.normals.add(normal.y)
                            meshData.normals.add(normal.z)
                        }
                        face.vt?.let { vt->
                            val textCoord = parsedObj.textCoords[vt - 1]
                            meshData.textureCoordinates.add(textCoord.u)
                            meshData.textureCoordinates.add(textCoord.v)
                        }
                        meshData.indices.add(faceIndex++)
                    }
                    return meshData
                }
            }
            return null
        }
    }
}