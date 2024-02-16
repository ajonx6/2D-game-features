package com.curaxu.game.items;

import com.curaxu.game.entity.Entity;
import com.curaxu.game.level.Level;

public interface ItemAction {
	void onLeftClick(Entity user, Level level);
	
	void onRightClick(Entity user, Level level);
}