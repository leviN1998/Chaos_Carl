package de.levin.chaos.carl.game.player;

import de.levin.chaos.carl.game.background.TextureObject;
import de.levin.chaos.carl.game.obj.Vao;
import de.levin.engine2d.Shader.ModularShader;
import de.levin.engine2d.toolbox.Data;
import de.levin.engine2d.toolbox.Texture;
import de.levin.engine2d.toolbox.utils.TextureUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

/**
 * Created by Levin on 30.06.2017.
 */
public class Animation {

    private Texture[] parts;
    private Vao vao;
    private int framesToChange;
    private Matrix4f pr_Matrix;
    private Matrix4f transformationMatrix;
    private boolean running;
    private boolean firstCycle;

    public Animation(String path, String[] names,  int framesToChange){
        running = false;
        parts = new Texture[names.length];
        for (int i = 0;i<parts.length;i++){
            parts[i] = new Texture(path+names[i]);
        }
        vao = new Vao(TextureUtils.calculateVertices(parts[0].getWidth(), parts[0].getHeight(), 1), Data.textureCoords,
                Data.indices);
        this.framesToChange = framesToChange;
        this.firstCycle = false;
    }

    private int i = 0;
    private int currentPart = 0;
    public void update(){
        if (running) {
            if (++i >= framesToChange) {
                i = 0;

                if (++currentPart >= parts.length) {
                    currentPart = 0;
                    firstCycle = true;
                }

            }
        }
    }

    public void start(){
        running = true;
    }

    public void stop(){
        running = false;
    }

    public void startCycle(){
        firstCycle = false;
        currentPart = 0;
    }

    public boolean isFirstCycle(){
        return firstCycle;
    }

    public boolean isRunning(){
        return running;
    }

    public int getLength(){
        return parts.length;
    }

    public int getFramesToChange(){
        return framesToChange;
    }

    public void setCurrentPart(int cp){
        currentPart = cp;
    }

    public void draw(ModularShader activePassThrough){
        vao.bind();
        parts[currentPart].bind();

        activePassThrough.setUniformMat4("transformation", transformationMatrix);
        activePassThrough.setUniformMat4("pr_matrix", pr_Matrix);

        vao.draw();

        vao.unbind();
        parts[currentPart].unbind();
    }

    public void setPr_Matrix(Matrix4f pr_Matrix) {
        this.pr_Matrix = pr_Matrix;
    }

    public void setTransformationMatrix(Matrix4f transformationMatrix) {
        this.transformationMatrix = transformationMatrix;
    }

    public Vector2f getDimension(Vector2f scale){
        return TextureUtils.getDimension(parts[0], scale);
    }

    public void cleanUp(){
        vao.cleanUp();
    }
}
