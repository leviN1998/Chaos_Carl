package de.levin.chaos.carl.game.obj;

import de.levin.engine2d.model.VaoTemplate;

/**
 * Created by Levin on 29.06.2017.
 */
public class Vao extends VaoTemplate{
    public Vao(float[] positions, float[] textureCoordinates, int[] indices) {
        this.bindIndicesBuffer(indices);
        this.addAttrib(0, 2, positions);
        this.addAttrib(1, 2, textureCoordinates);
        this.vertexCount = indices.length;
    }

    public void draw(){
        super.draw();
    }
}
