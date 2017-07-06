package de.levin.chaos.carl.game.background;

import de.levin.engine2d.Shader.ModularShader;
import de.levin.engine2d.model.TexturedVAO;
import org.lwjgl.util.vector.Vector2f;

/**
 * Created by Levin on 29.06.2017.
 */
public class Background {

    private TextureObject[] objects;
    private String path = "res/bg.jpeg";
    private Vector2f scale;
    private float offset;


    private float speed;

    public Background(String file, int objCount, Vector2f scale, float offset){
        this.path = file;
        this.scale = scale;
        this.offset = offset;
        objects = new TextureObject[objCount];
        init();
    }


    private void init(){
        for (int i = 0; i<objects.length;i++){
            if(i == 0){
                objects[i] = new TextureObject(path, new Vector2f(-9, 0), scale, 0);
            }else {
                Vector2f pos = new Vector2f(objects[i-1].getPosition().x + objects[i-1].getWidth() - offset, objects[i-1].getPosition().y);
                objects[i] = new TextureObject(path, pos, scale, 0);
            }
        }

        speed = 0.04f;
    }

    public void update(float delta){
        speed = 0.03f * delta;
        for (int i = 0;i<objects.length;i++) {
            moveObject(objects[i],i);
        }
    }

    public void increaseSpeed(float inc){
        this.speed += inc;
    }

    public void moveObject(TextureObject o, int index){
        o.move(-speed,0);
        if(o.getPosition().x < -10-(o.getWidth()/2)){
            Vector2f last = getLastObject().getPosition();
            float width = getLastObject().getWidth();
            o.setPosition(new Vector2f(last.x + width - offset, last.y));
            if(index == 0){
                o.move(-speed,0);
            }
        }
    }

    public TextureObject getLastObject(){
        float[] positions = new float[objects.length];
        for (int i = 0; i<objects.length;i++){
            positions[i] = objects[i].getPosition().x;
        }
        float highest = positions[0];
        int counter = 0;
        for (int i = 0;i<positions.length;i++){
            if(positions[i] >= highest){
                highest = positions[i];
                counter = i;
            }
        }
        return objects[counter];
    }

    public void draw(ModularShader activePassThrough){
        for (int i = 0;i<objects.length;i++){
            objects[i].draw(activePassThrough);
        }
    }

    public void cleanUp(){
        for (int i = 0;i<objects.length;i++){
            objects[i].cleanUp();
        }
    }
}
