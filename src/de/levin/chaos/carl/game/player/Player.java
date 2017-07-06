package de.levin.chaos.carl.game.player;

import de.levin.engine2d.Shader.ModularShader;
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
    private Animation jump;

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

        String[] jumps = new String[5];
        for (int i = 1; i<6;i++){
            jumps[i-1] = i+".png";
        }
        this.jump = new Animation("res/jump/", jumps, 20);

        this.walk = new Animation("res/walk/", walks, 10);
        this.walk.start();
    }

    public void update(){
        if (jump.isFirstCycle()){
            jump.stop();
            walk.start();
            position = new Vector2f(startPosition.x, startPosition.y);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            jumpCounter = 0;
            jump.startCycle();
            walk.stop();
            jump.start();
        }
        if (jump.isRunning()){
            updateJump();
        }
        walk.update();
        jump.update();
    }

    private int jumpCounter;
    public void updateJump(){
        jumpCounter ++;
        int jLength = jump.getLength() * jump.getFramesToChange();
        if (jumpCounter < jLength / 2){
            position.y += 0.05f;
        }
        if (jumpCounter > jLength / 2){
            position.y -= 0.05f;
        }
    }

    public void draw(ModularShader activeShader){
        walk.setPr_Matrix(DisplayManager.getPr_matrix());
        walk.setTransformationMatrix(MatrixMaths.createTransformationMatrix(position, scale, rotation));

        jump.setPr_Matrix(DisplayManager.getPr_matrix());
        jump.setTransformationMatrix(MatrixMaths.createTransformationMatrix(position,scale,rotation));

        if (walk.isRunning()) walk.draw(activeShader);
        if (jump.isRunning()) jump.draw(activeShader);
    }


}
