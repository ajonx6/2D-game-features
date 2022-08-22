package com.curaxu.game.entity;

import com.curaxu.game.graphics.Screen;

import java.util.ArrayList;
import java.util.List;

public class InputListener extends Component {
    private List<Input> inputs = new ArrayList<>();

    public InputListener(Entity entity) {
        super(entity);
    }

    public void tick(double delta) {}

    public void render(Screen screen) {}

    public void addInput(Input i) {
        inputs.add(i);
        i.bind();
    }

    public void activateAll() {
        for (Input i : inputs) {
            i.enable();
        }
    }

    public void deactivateAll() {
        for (Input i : inputs) {
            i.disable();
        }
    }

    public String getName() {
        return "InputListener";
    }
}
