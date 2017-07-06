package de.levin.chaos.carl.game;

import de.levin.chaos.carl.game.background.Background;
import de.levin.chaos.carl.game.background.TextureObject;
import de.levin.chaos.carl.game.player.Player;
import de.levin.engine2d.Shader.ModularShader;
import de.levin.engine2d.toolbox.DisplayManager;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

/**
 * Created by Levin on 29.06.2017.
 */
public class Main implements Runnable{

    //Utils
    Background background;
    ModularShader passThrough;
    Player player;


    @Override
    public void run(){
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
        background = new Background("res/background/collegeblockteil.jpg", 8, new Vector2f(11.3f, 11.3f), 0);
        passThrough = ModularShader.createPassThrough();
        player = new Player(new Vector2f(-6, -2.71f), new Vector2f(3,3), 0);

    }

    private void update(){
        background.update(1);
        player.update();
    }

    private void render(){
        passThrough.enable();

        background.draw(passThrough);
        player.draw(passThrough);

        passThrough.disable();
    }

    private void cleanUp(){
        passThrough.cleanUp();
        background.cleanUp();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
