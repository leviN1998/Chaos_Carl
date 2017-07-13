package de.levin.chaos.carl.game.obstacles;

import de.levin.chaos.carl.game.background.TextureObject;
import de.levin.engine2d.Shader.ModularShader;
import de.levin.engine2d.hitbox.SquareHitbox;
import org.lwjgl.util.vector.Vector2f;

/**
 * Created by levin on 13.07.2017.
 */
public class Entity {

    private TextureObject object;
    private SquareHitbox hitbox;

    public Entity(Vector2f position,Vector2f scale, String path){
        object = new TextureObject(path, position, scale, 0);
    }

    public void update(float dx){
        object.move(dx, 0);
        hitbox = new SquareHitbox(object.getPosition(), object.getWidth(), object.getHeight());
    }

    public void draw(ModularShader passThrough){
        object.draw(passThrough);
    }

    public SquareHitbox getHitbox(){
        return hitbox;
    }

    public void setPosition(Vector2f position){
        object.setPosition(position);
    }

}
