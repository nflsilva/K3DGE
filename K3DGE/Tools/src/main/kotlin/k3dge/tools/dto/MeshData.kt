package k3dge.tools.dto

data class MeshData(val vertices: MutableList<Float> = mutableListOf(),
                    val textureCoordinates: MutableList<Float> = mutableListOf(),
                    val normals: MutableList<Float> = mutableListOf(),
                    val indices: MutableList<Int> = mutableListOf())