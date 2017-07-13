package de.levin.chaos.carl.game.menu;

import de.levin.chaos.carl.game.background.TextureObject;
import de.levin.chaos.carl.game.player.Animation;
import de.levin.engine2d.Shader.ModularShader;
import de.levin.engine2d.button.ButtonTemplate;
import de.levin.engine2d.toolbox.DisplayManager;
import de.levin.engine2d.toolbox.MatrixMaths;
import org.lwjgl.util.vector.Vector2f;

/**
 * Created by levin on 08.07.2017.
 */
public class Button extends ButtonTemplate{

    private TextureObject standing;
    private Animation clicked;
    private Vector2f scaleBuffer;
    private String standingPath;

    public Button(Vector2f position, Vector2f scale, String standingPath, Animation animation){
        super(position, scale, 0, animation.getDimension(scale).x, new Vector2f(scale).y);
        this.scaleBuffer = new Vector2f(scale.x, scale.y);
        this.standingPath = standingPath;
        this.standing = new TextureObject(standingPath, position, scale, rotation);
        clicked = animation;
        rotation += 180;
    }


    @Override
    protected void init() {

    }

    @Override
    protected void onHover() {
        this.scale.x += 0.2f;
        this.scale.y += 0.2f;
    }

    @Override
    protected void redoHover() {
        this.scale.x = scaleBuffer.x;
        this.scale.y = scaleBuffer.y;
    }

    @Override
    protected void onPress() {
        this.scale.x += 0.2f;
        this.scale.y += 0.2f;
    }

    @Override
    protected void onRelease() {
        clicked.start();
    }

    @Override
    public void update(Vector2f displayCoords){
        super.update(displayCoords);
        if (clicked.isRunning()){
            clicked.update();
            if (clicked.isFirstCycle()){
                clicked.stop();
                //clicked.startCycle();
                start = true;
            }
        }
    }

    public boolean start = false;

    public void draw(ModularShader passThrough){
        if (clicked.isRunning()){
            clicked.setPr_Matrix(DisplayManager.getPr_matrix());
            clicked.setTransformationMatrix(MatrixMaths.createTransformationMatrix(position, scale, rotation));
            clicked.draw(passThrough);
        }else {
            standing.draw(passThrough);
        }
    }

    public void cleanUp(){
        standing.cleanUp();
        clicked.cleanUp();
    }
}
