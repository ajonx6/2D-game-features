package com.curaxu.game.entity.components;

import com.curaxu.game.Game;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.util.Timer;

public class RandomWalkComponent extends Component {
    private MoveComponent move;
    private Timer timer;

    public RandomWalkComponent(Entity entity) {
        super(entity, "SpriteList", "Move");
        this.move = (MoveComponent) entity.getComponent("Move");
        this.timer = new Timer(0, false);
    }

    public double generateTime() {
        return 0.2 + Game.getInstance().getRandom().nextDouble();
    }

    public void tick(double delta) {
        if (timer.tick(delta)) {
            timer.setTimeLength(generateTime());
            move.setDx(Game.getInstance().getRandom().nextDouble() * 2 - 1);
            move.setDy(Game.getInstance().getRandom().nextDouble() * 2 - 1);
        }
    }

    public void render(Screen screen) {}

    public String getName() {
        return "RandomWalk";
    }
}
