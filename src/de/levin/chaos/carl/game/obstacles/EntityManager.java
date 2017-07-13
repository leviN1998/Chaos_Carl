package de.levin.chaos.carl.game.obstacles;

import de.levin.chaos.carl.game.Main;
import de.levin.engine2d.Shader.ModularShader;
import de.levin.engine2d.hitbox.Collision;
import de.levin.engine2d.hitbox.SquareHitbox;
import org.lwjgl.util.vector.Vector2f;

/**
 * Created by levin on 13.07.2017.
 */
public class EntityManager {

    private int counter;
    private Entity müll;
    private Entity pfosten;
    private float ystorage;

    public EntityManager(Vector2f playerPos){
        müll = new Entity(new Vector2f(60,playerPos.getY()), new Vector2f(2,2), "res/objects/Mülleimer.png");
        pfosten = new Entity(new Vector2f(25,playerPos.getY()), new Vector2f(2f,2f), "res/objects/Pfoschdn.png");
        ystorage = playerPos.getY();
    }

    public void update(float dx, SquareHitbox player){
        if (counter++ > 1000) {
            counter = 0;
            müll.setPosition(new Vector2f(50, ystorage));
            pfosten.setPosition(new Vector2f(15, ystorage));
        }
        pfosten.update(dx);
        müll.update(dx);
        if (Collision.collideSquares(player, pfosten.getHitbox())) Main.stop();
        if (Collision.collideSquares(player, müll.getHitbox())) Main.stop();
    }

    public void draw(ModularShader activePassThrough){
        müll.draw(activePassThrough);
        pfosten.draw(activePassThrough);
    }

}
