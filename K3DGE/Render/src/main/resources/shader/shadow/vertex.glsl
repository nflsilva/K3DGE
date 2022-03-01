#version 330 core

in vec3 in_position;

uniform mat4 in_mvpMatrix;

void main(){
    gl_Position = in_mvpMatrix * vec4(in_position, 1.0);
};