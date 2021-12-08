package k3dge.render.model

import k3dge.tools.Log
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.opengl.GL20.*
import java.nio.FloatBuffer
import java.nio.IntBuffer

class ShaderModel(vertexSource: String, fragmentSource: String) {

    private val programId: Int = glCreateProgram()
    private val shaders: MutableList<Int> = mutableListOf()
    private val uniforms: MutableMap<String, Int> = mutableMapOf()

    init {
        addShader(vertexSource, GL_VERTEX_SHADER)
        addShader(fragmentSource, GL_VERTEX_SHADER)
        linkProgram()

        addUniform("in_modelMatrix")
        addUniform("in_viewMatrix")
        addUniform("in_projectionMatrix")
    }

    fun bindAttribute(attribute: Int, variableName: String){
        glBindAttribLocation(programId, attribute, variableName);
    }
    fun addUniform(name: String){
        if(uniforms.containsKey(name)) return
        val location: Int = glGetUniformLocation(programId, name)
        if(location == GL_INVALID_VALUE || location == GL_INVALID_OPERATION || location == GL_INVALID_OPERATION){
            Log.e("Failed to create \"$name\" uniform.");
            return
        }
        uniforms[name] = location
    }
    fun setUniformi(name: String, value: Int){
        uniforms[name]?.let {
            glUniform1i(it, value)
        }
    }
    fun setUniformf(name: String, value: Float){
        uniforms[name]?.let {
            glUniform1f(it, value)
        }
    }
    fun setUniform3f(name: String, value: Vector3f){
        uniforms[name]?.let {
            glUniform3f(it, value.x, value.y, value.z)
        }
    }
    fun setUniform4f(name: String, value: Vector4f){
        uniforms[name]?.let {
            glUniform4f(it, value.x, value.y, value.z, value.w)
        }
    }
    fun setUniformMatrix4f(name: String, value: Matrix4f){
        uniforms[name]?.let {
            val data = FloatBuffer.allocate(4 * 4)
            glUniformMatrix4fv(it, false, value.get(data))
        }
    }
    fun setModelMatrix(value: Matrix4f){
        setUniformMatrix4f(MODEL_MATRIX_UNIFORM, value)
    }
    fun setViewMatrix(value: Matrix4f){
        setUniformMatrix4f(VIEW_MATRIX_UNIFORM, value)
    }
    fun setProjectionMatrix(value: Matrix4f){
        setUniformMatrix4f(PROJECTION_MATRIX_UNIFORM, value)
    }

    private fun addShader(sourceCode: String, type: Int){

        val shader = glCreateShader(type)
        if(shader == 0){
            Log.e("Error creating shader")
            return
        }
        glShaderSource(shader, sourceCode, null);

        val compileSuccess = IntBuffer.allocate(1)
        glCompileShader(shader);
        glGetShaderiv(shader, GL_COMPILE_STATUS, compileSuccess)
        if(compileSuccess.get() != GL_TRUE){
            val message = glGetShaderInfoLog(shader)
            Log.e(message)
            return
        }

        glAttachShader(programId, shader)
        shaders.add(shader)
    }
    private fun linkProgram(){

        val success = IntBuffer.allocate(1)
        glLinkProgram(programId);
        glGetProgramiv(programId, GL_LINK_STATUS, success)
        if(success.get() != GL_TRUE){
            val message = glGetProgramInfoLog(programId)
            Log.e(message)
            return
        }

        glValidateProgram(programId);
        glGetProgramiv(programId, GL_VALIDATE_STATUS, success)
        if(success.get() != GL_TRUE) {
            val message = glGetProgramInfoLog(programId)
            Log.e(message)
            return
        }
    }

    companion object {
        const val MODEL_MATRIX_UNIFORM = "in_modelMatrix"
        const val VIEW_MATRIX_UNIFORM = "in_viewMatrix"
        const val PROJECTION_MATRIX_UNIFORM = "in_projectionMatrix"
    }

}