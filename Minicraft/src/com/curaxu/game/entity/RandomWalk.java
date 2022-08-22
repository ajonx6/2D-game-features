package com.curaxu.game.entity;

import com.curaxu.game.Game;
import com.curaxu.game.KeyInput;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.util.Timer;

import java.awt.event.KeyEvent;

public class RandomWalk extends Component {
    private CanMove move;
    private Timer timer;

    public RandomWalk(Entity entity) {
        super(entity, "SpriteList", "CanMove");
        this.move = (CanMove) entity.getComponent("CanMove");
        this.timer = new Timer(0, false);
    }

    public double generateTime() {
        return 0.2 + Game.getInstance().getRandom().nextDouble();
    }

    public void tick(double delta) {
        if (timer.tick(delta)) {
            timer.setTime(generateTime());
            move.setDx(Game.getInstance().getRandom().nextDouble() * 2 - 1);
            move.setDy(Game.getInstance().getRandom().nextDouble() * 2 - 1);
        }
    }

    public void render(Screen screen) {}

    public String getName() {
        return "PlayerControl";
    }
}
