package disused;

import com.curaxu.game.KeyInput;
import com.curaxu.game.Vector;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.entity.components.CanMoveComponent;
import com.curaxu.game.entity.components.Component;
import com.curaxu.game.graphics.Screen;

import java.awt.event.KeyEvent;

public class PlayerControlComponent extends Component {
    private CanMoveComponent move;
    private Vector velocity = new Vector();

    public PlayerControlComponent(Entity entity) {
        super(entity, "SpriteList", "CanMove");
        this.move = (CanMoveComponent) entity.getComponent("CanMove");
    }

    public void move(double delta) {
        double movespeed = move.getCurrentSpeed();
        velocity = velocity.mul(movespeed);
        // Tile next = Game.getInstance().getLevel().getTileAtWorldPos(entity.worldPos.add(velocity));
        // if (next != null && next.getOntop() != null) {
        //     AABBBox a = (AABBBox) next.getOntop().getComponent("AABBBox");
        //     AABBBox b = (AABBBox) entity.getComponent("AABBBox");
        //     if (a != null && b != null && Collisions.collisionWithBox(a, b)) return;
        // }
        entity.worldPos = entity.worldPos.add(velocity.mul(delta));
    }

    public void tick(double delta) {
        // velocity.setX(0);
        // velocity.setY(0);
        // if (KeyInput.isDown(KeyEvent.VK_W)) {
        //     velocity.setY(-1);
        // } else if (KeyInput.isDown(KeyEvent.VK_S)) {
        //     velocity.setY(1);
        // }
        // if (KeyInput.isDown(KeyEvent.VK_A)) {
        //     velocity.setX(-1);
        // }
        // if (KeyInput.isDown(KeyEvent.VK_D)) {
        //     velocity.setX(1);
        // }
        //
        // if (!velocity.isZero()) move(delta);
    }

    public void render(Screen screen) {}

    public String getName() {
        return "PlayerControl";
    }
}
