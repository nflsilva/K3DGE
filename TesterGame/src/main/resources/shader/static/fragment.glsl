#version 330 core

in vec2 texCoord;
in vec3 normal;
in vec3 lightVector;

uniform sampler2D texture0;
uniform vec4 in_lightColor;

out vec4 out_color;

vec4 processLight(vec3 normal, vec3 lightVector, vec4 lightColor){
    float diffuseIntensity = max(dot(normal, lightVector), 0.0);
	return diffuseIntensity * lightColor;
}

void main()
{
    //out_color = vec4(1.0, 0.5, 0.5, 1.0);
    out_color = texture(texture0, texCoord);

    //vec4 diffure = processLight(normal, lightVector, in_lightColor);
    //out_color = diffure * texture(texture0, texCoord);
};