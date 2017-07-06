package de.levin.chaos.carl.game.background;

import de.levin.chaos.carl.game.obj.Vao;
import de.levin.engine2d.Shader.ModularShader;
import de.levin.engine2d.toolbox.Data;
import de.levin.engine2d.toolbox.DisplayManager;
import de.levin.engine2d.toolbox.MatrixMaths;
import de.levin.engine2d.toolbox.Texture;
import de.levin.engine2d.toolbox.utils.TextureUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

/**
 * Created by Levin on 29.06.2017.
 */
public class TextureObject {
    private Vector2f position;
    private Vector2f scale;
    private float rotation;
    private Texture texture;
    private Vao vao;
    private float width,height;

    public TextureObject(String texturePath, Vector2f position, Vector2f scale, float rotation) {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
        this.texture = new Texture(texturePath);
        float[] positions = TextureUtils.calculateVertices(this.texture.getWidth(), this.texture.getHeight(), 1);
        this.vao = new Vao(positions, Data.textureCoords, Data.indices);
        this.width = TextureUtils.getDimension(texture, scale).x;
        this.height = TextureUtils.getDimension(texture, scale).y;
        rotate(180);
    }

    public void draw(ModularShader activeShader) {
        this.vao.bind();
        this.texture.bind();
        Matrix4f m = MatrixMaths.createTransformationMatrix(this.position, this.scale, this.rotation);
        activeShader.setUniformMat4("transformation", m);
        activeShader.setUniformMat4("pr_matrix", DisplayManager.getPr_matrix());
        this.vao.draw();
        this.texture.unbind();
    }

    public void unbind() {
        this.vao.unbind();
    }

    public void cleanUp() {
        this.vao.cleanUp();
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public float getRotation() {
        return rotation;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vao getVao() {
        return vao;
    }

    public void move(float dx, float dy){
        this.position.x += dx;
        this.position.y += dy;
    }

    public void rotate(float rotation){
        this.rotation = rotation;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
