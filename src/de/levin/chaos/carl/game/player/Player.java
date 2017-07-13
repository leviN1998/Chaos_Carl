package de.levin.chaos.carl.game.player;

import de.levin.engine2d.Shader.ModularShader;
import de.levin.engine2d.hitbox.SquareHitbox;
import de.levin.engine2d.toolbox.DisplayManager;
import de.levin.engine2d.toolbox.MatrixMaths;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

/**
 * Created by Levin on 03.07.2017.
 */
public class Player {

    private Vector2f position;
    private Vector2f startPosition;
    private Vector2f scale;
    private float rotation;

    private Animation walk;
    private Animation jump1;
    private Animation jump2;

    private Jump jumpCalc;
    private float jumpOffset = 0.45f;

    private SquareHitbox hitbox;



    public Player(Vector2f position, Vector2f scale, float rotation){
        this.position = position;
        this.startPosition = new Vector2f(position.x, position.y);
        this.scale = scale;
        this.rotation = rotation;
        this.rotation += 180;
        String[] walks = new String[8];
        for (int i = 1;i<9;i++){
            walks[i-1] = i+".png";
        }

        String[] jumps1 = {
                "1.png",
                "2.png",
                "3.png",
        };
        String[] jumps2 = {
                "3.png",
                "4.png",
                "5.png"
        };
        this.jump1 = new Animation("res/jump/", jumps1, 20);
        this.jump2 = new Animation("res/jump/", jumps2, 20);

        jumpCalc = new Jump(0.004f, new Vector2f(0.5f,0.17f), 50);

        this.walk = new Animation("res/walk/", walks, 10);
        this.walk.start();

    }

    private int jumpBlock = 0;
    public void update(){
        if (!jumpCalc.isActive()){
            jumpBlock++;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)&&jumpBlock>10){
            jumpBlock = 0;
            if (!jumpCalc.isActive()){
                walk.stop();
                jumpCalc.startJump(new Vector2f(position.getX(), position.getY()));
                jump1.startCycle();
                jump1.start();
                this.position.y += jumpOffset;
            }
        }
        if (jumpCalc.isChangedState()){
            jumpCalc.setChangedState(false);
            jump1.stop();
            jump2.startCycle();
            jump2.start();
        }
        if (jumpCalc.isHasStopped()){
            jumpCalc.setHasStopped(false);
            jump2.stop();
            walk.startCycle();
            walk.start();
        }
        walk.update();
        jumpCalc.update();

        jump1.update();
        jump2.update();
        if (jumpCalc.isActive()){
            this.position = jumpCalc.getPosition();
        }else {
            position.x = startPosition.getX();
            position.y = startPosition.getY();
        }

        hitbox = new SquareHitbox(position, walk.getDimension(scale).x, walk.getDimension(scale).y);
    }

    public void draw(ModularShader activeShader){
        walk.setPr_Matrix(DisplayManager.getPr_matrix());
        walk.setTransformationMatrix(MatrixMaths.createTransformationMatrix(position, scale, rotation));

        jump1.setPr_Matrix(DisplayManager.getPr_matrix());
        jump1.setTransformationMatrix(MatrixMaths.createTransformationMatrix(position, scale, rotation));

        jump2.setPr_Matrix(DisplayManager.getPr_matrix());
        jump2.setTransformationMatrix(MatrixMaths.createTransformationMatrix(position, scale, rotation));

        if (walk.isRunning())walk.draw(activeShader);
        if (jump1.isRunning()) jump1.draw(activeShader);
        if (jump2.isRunning()) jump2.draw(activeShader);
    }

    public SquareHitbox getHitbox(){
        return hitbox;
    }


}
