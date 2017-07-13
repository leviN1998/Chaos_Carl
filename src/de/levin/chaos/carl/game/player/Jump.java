package de.levin.chaos.carl.game.player;

import org.lwjgl.util.vector.Vector2f;

/**
 * Created by levin on 11.07.2017.
 */
public class Jump {

    private boolean active;
    private Vector2f position;
    private Vector2f velocity;
    private Vector2f startVelocity;
    private float gravity;
    private int counter;
    private int halfCounterMax;
    private boolean jumpState; //true = first

    private boolean hasStopped;
    private boolean changedState;
    private float startY;

    public Jump(float gravity, Vector2f startVelocity, int halfCounterMax){
        this.gravity = gravity;
        velocity = startVelocity;
        this.startVelocity = new Vector2f(velocity.x,velocity.y);
        this.halfCounterMax = halfCounterMax;
        this.changedState = false;
    }

    public void startJump(Vector2f position){
        this.position = position;
        this.active = true;
        this.velocity = new Vector2f(startVelocity.x,startVelocity.y);
        this.counter = 0;
        this.jumpState = true;
        this.startY = position.getY();
    }

    public void update(){
        if (active) {
            if (jumpState) {
                this.position.y += velocity.y;
                if (velocity.y <= 0) velocity.y = 0;
                this.velocity.y -= gravity;
                counter++;
            }else {
                if (this.position.getY() <= startY+velocity.y){
                    velocity.y = 0;
                    position.setY(startY);
                }
                this.position.y -= velocity.y;
                this.velocity.y += gravity;
                counter++;
            }
            if (counter == halfCounterMax){
                if (jumpState){
                    counter = 0;
                    jumpState = false;
                    changedState = true;
                }else {
                    counter = 0;
                    active = false;
                    hasStopped = true;
                }
            }
        }
    }

    public Vector2f getPosition(){
        return position;
    }

    public boolean isActive(){
        return active;
    }

    public boolean isHasStopped(){
        return hasStopped;
    }

    public void setHasStopped(boolean hasStopped){
        this.hasStopped = hasStopped;
    }

    public boolean getState(){
        return jumpState;
    }

    public boolean isChangedState() {
        return changedState;
    }

    public void setChangedState(boolean changedState) {
        this.changedState = changedState;
    }
}
