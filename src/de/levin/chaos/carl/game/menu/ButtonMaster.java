package de.levin.chaos.carl.game.menu;

import de.levin.chaos.carl.game.player.Animation;
import de.levin.engine2d.Shader.ModularShader;
import de.levin.engine2d.toolbox.utils.ButtonMaths;
import org.lwjgl.util.vector.Vector2f;

/**
 * Created by levin on 08.07.2017.
 */
public class ButtonMaster {

    private String[] playNames = {
            "01.png",
            "02.png",
            "03.png",
            "04.png",
            "05.png",
            "06.png",
            "07.png",
            "08.png",
            "09.png",
            "10.png",
            "11.png",
            "12.png",
            "13.png",
            "14.png",
            "15.png",
    };

    private String playString = "res/menu/play/";

    private Button play;

    public ButtonMaster(){
        Animation a = new Animation(playString, playNames, 10);
        play = new Button(new Vector2f(0,0),new Vector2f(3,3), playString+"15.png", a);
    }

    public void update(Vector2f displayCoords){
        play.update(displayCoords);
    }

    public void draw(ModularShader passThrough){
        play.draw(passThrough);
    }

    public void cleanUp(){
        play.cleanUp();
    }

}
