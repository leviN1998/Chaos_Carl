package de.levin.chaos.carl.game.menu;

import de.levin.engine2d.Shader.ModularShader;
import de.levin.engine2d.toolbox.DisplayManager;
import org.jcp.xml.dsig.internal.MacOutputStream;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

/**
 * Created by levin on 08.07.2017.
 */
public class MenuMain implements Runnable{

    private ButtonMaster buttonMaster;
    private ModularShader passThrough;

    @Override
    public void run() {
        DisplayManager.createDisplay();
        DisplayManager.setUpRendering2D();
        this.init();

        while(!Display.isCloseRequested()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glClearColor(1,1f,1,1);
            this.update();
            this.render();
            DisplayManager.updateDisplay();
        }

        this.cleanUp();
        DisplayManager.closeDisplay();
    }

    public void init(){
        buttonMaster = new ButtonMaster();
        passThrough = ModularShader.createPassThrough();
    }

    public void update(){
        buttonMaster.update(new Vector2f(Mouse.getX(), Mouse.getY()));
    }

    public void render(){
        passThrough.enable();

        buttonMaster.draw(passThrough);

        passThrough.disable();
    }

    public void cleanUp(){
        buttonMaster.cleanUp();
        passThrough.cleanUp();
    }

    public static void main(String[] args){
        new MenuMain().run();
    }
}
