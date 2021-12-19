#version 330 core

in vec2 textureCoords;

uniform sampler2D texture0;

out vec4 out_color;

void main()
{
    out_color = texture(texture0, textureCoords);
};