#version 330 core

in vec2 in_position;

uniform mat4 in_projectionMatrix;

out vec2 textureCoords;

void main(){
    gl_Position = in_projectionMatrix * vec4(in_position, 0.0, 1.0);
    textureCoords = vec2((in_position.x + 1.0) / 2.0, 1 - (in_position.y + 1.0) / 2.0);
};