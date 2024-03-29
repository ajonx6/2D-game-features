package com.curaxu.game.entity.components;

import com.curaxu.game.KeyInput;
import com.curaxu.game.Time;
import com.curaxu.game.Vector;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.level.Tile;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

import java.awt.event.KeyEvent;

public class MoveComponent extends Component {
	public static final int IDLE = 0;
	public static final int WALK = 1;
	public static final int SWIM = 2;

	public static final String UP = "up";
	public static final String DOWN = "down";
	public static final String LEFT = "left";
	public static final String RIGHT = "right";

	private boolean playerControls = false;
	private SpriteListComponent sprites;
	private boolean canMoveUp = true, canMoveDown = true, canMoveLeft = true, canMoveRight = true;
	private String dir = RIGHT, prevDir = RIGHT;
	private int currState = IDLE;
	private double moveSpeed;
	private double currentMoveSpeed;
	private Vector direction = new Vector();
	private Vector velocity = new Vector();

	private boolean canSwim = false;
	private double swimSpeed;

	public MoveComponent(Entity entity, double moveSpeed) {
		super(entity, "SpriteList");
		this.moveSpeed = moveSpeed;
		this.currentMoveSpeed = moveSpeed;
		this.sprites = (SpriteListComponent) entity.getComponent("SpriteList");
	}

	private void getPlayerInput() {
		direction = new Vector();
		if (KeyInput.isDown(KeyEvent.VK_W)) direction.setY(-1);
		if (KeyInput.isDown(KeyEvent.VK_S)) direction.setY(direction.getY() + 1);
		if (KeyInput.isDown(KeyEvent.VK_A)) direction.setX(-1);
		if (KeyInput.isDown(KeyEvent.VK_D)) direction.setX(direction.getX() + 1);

		setVelocity();
	}

	public void move(double delta) {
		if (!canMoveUp && direction.getY() < 0) direction.setY(0);
		if (!canMoveDown && direction.getY() > 0) direction.setY(0);
		if (!canMoveLeft && direction.getX() < 0) direction.setY(0);
		if (!canMoveRight && direction.getX() > 0) direction.setY(0);

		entity.worldPos = entity.worldPos.add(velocity);
		if (direction.getX() < 0) dir = LEFT;
		else if (direction.getX() > 0) dir = RIGHT;
		else {
			if (direction.getY() < 0) dir = UP;
			else if (direction.getY() > 0) dir = DOWN;
		}
	}

	public void tick(double delta) {
		if (playerControls) getPlayerInput();

		move(delta);
		canMoveAllDirs();
		int tileId = entity.standing == null ? 0 : entity.standing.getId();

		if (canSwim && tileId == Tile.WATER.getId()) {
			if (currState != SWIM || !dir.equals(prevDir)) {
				sprites.setSprite("swim_" + dir);
				currentMoveSpeed = swimSpeed;
				currState = SWIM;
			}
		} else {
			if (currState != IDLE && direction.isZero() || !dir.equals(prevDir)) {
				sprites.setSprite("idle_" + dir);
				currentMoveSpeed = 0;
				currState = IDLE;
			} else if (currState != WALK && !direction.isZero() || !dir.equals(prevDir)) {
				sprites.setSprite("walk_" + dir);
				currentMoveSpeed = moveSpeed;
				currState = WALK;
			}
		}
		prevDir = dir;
		sprites.getCurrentSprite().tick(delta);
	}

	public MoveComponent setPlayerControls(boolean playerControls) {
		this.playerControls = playerControls;
		return this;
	}

	public MoveComponent canSwim(double swimSpeed) {
		this.canSwim = true;
		this.swimSpeed = swimSpeed;
		return this;
	}

	public void canMoveAllDirs() {
		canMoveUp = canMoveDown = canMoveLeft = canMoveRight = true;
	}

	public void cantMoveAllDirs() {
		canMoveUp = canMoveDown = canMoveLeft = canMoveRight = false;
	}

	public void setMoveDir(int dir, boolean value) {
		if (dir == 0) canMoveUp = value;
		else if (dir == 1) canMoveDown = value;
		else if (dir == 2) canMoveLeft = value;
		else if (dir == 3) canMoveRight = value;
	}

	public void render(Screen screen) {}

	public void setDx(double dx) {
		this.direction = new Vector(dx, direction.getY());
		setVelocity();
	}

	public void setDy(double dy) {
		this.direction = new Vector(direction.getX(), dy);
		setVelocity();
	}

	public double getCurrentMoveSpeed() {
		return currentMoveSpeed;
	}

	public Vector getDirection() {
		return direction;
	}

	public String getDir() {
		return dir;
	}

	public String getPrevDir() {
		return prevDir;
	}

	public int getCurrState() {
		return currState;
	}
	
	public void setState(int state) {
		this.currState = state;
	}

	public boolean canSwim() {
		return canSwim;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity() {
		velocity = direction.normalize().mul(currentMoveSpeed).mul(Time.getFrameTimeInSeconds());
	}

	public void setVelocity(Vector vel) {
		this.velocity = vel;
	}

	public String getName() {
		return "Move";
	}
}
