#version 330 core

in vec2 textureCoords;
in vec3 normal;
in vec3 lightVector;
in vec4 shadowCoords;

uniform sampler2D texture0;
uniform sampler2D depthMap;
uniform vec4 in_lightColor;
uniform float in_ambientCoefficient;
uniform bool in_shadowsEnabled;

out vec4 out_color;

vec4 processLight(float dotNormalLight){
    float diffuseComponent = max(dotNormalLight, 0.0);
	return diffuseComponent * in_lightColor;
}
float processShadow(float dotNormalLight){

    vec3 fragPosLightSpace = shadowCoords.xyz * 0.5 + 0.5;
    float fragDepth = min(fragPosLightSpace.z, 1.0);

    float bias = max(0.05 * (1.0 - dotNormalLight), 0.005);

    float shadow = 0.0;
    vec2 texelSize = 2.0 / textureSize(depthMap, 0);
    for(int x = -2; x <= 2; ++x)
    {
        for(int y = -2; y <= 2; ++y)
        {
            float lightDepth = texture(depthMap, fragPosLightSpace.xy + vec2(x, y) * texelSize).r;
            shadow += (lightDepth + bias) < fragDepth ? 0.0 : 1.0;
        }
    }
    return shadow /= 18.0;
}

void main()
{
    float dotNormalLight = dot(normalize(normal), normalize(-lightVector));

    vec4 diffuseComponent = processLight(dotNormalLight);
    float shadowCoefficient = 1.0f;
    if(in_shadowsEnabled) {
        shadowCoefficient = processShadow(dotNormalLight);
    }

    vec4 materialColor = texture(texture0, textureCoords);
    vec4 lightColor = in_lightColor;

    vec4 ambientColor = materialColor * in_ambientCoefficient;
    vec4 diffuseColor = diffuseComponent * materialColor;

    out_color = mix(diffuseColor * shadowCoefficient, ambientColor, in_ambientCoefficient);
    //out_color = vec4(shadowCoefficient);
};