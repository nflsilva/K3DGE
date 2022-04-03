#version 330 core

in vec2 textureCoords;
in float textureIndex;

uniform sampler2D in_samplers[100];

out vec4 out_color;

void main()
{
    /*
    vec4 color = vec4(1.0F);
    if(textureIndex == 1.0F){
        color = vec4(1.0F, 0.0F, 0.0F, 1.0F);
    }
    else if (textureIndex == 2.0F) {
        color = vec4(0.0F, 1.0F, 0.0F, 1.0F);
    }
    else if (textureIndex == 3.0F) {
        color = vec4(0.0F, 0.0F, 1.0F, 1.0F);
    }
    */

    out_color = texture(in_samplers[int(textureIndex)], textureCoords);
};