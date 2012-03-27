package com.game;

import java.awt.event.KeyEvent;

public class EntityPlayer extends Entity {
	int timeInAir;
	int spawnX, spawnY;
	int score;
	int invurn;
	
	public EntityPlayer(int x, int y, int size, String spriteName, Main main) {
		super(x, y, size, spriteName, main);
		spawnX = x;
		spawnY = y;
		timeInAir = 0;
		score = 0;
	}
	
	@Override
	public void kill() {
		if (invurn > 0) return;
		score -= main.t.intNumber("score-per-coin");
		x = spawnX;
		y = spawnY;
		invurn = 20;
	}
	
	@Override
	public void tick() {
		super.tick();
		invurn--;
		if (!onGround()){
			timeInAir++;
		}
		else {
			timeInAir = 0;
		}
		//System.out.println(timeInAir);
		if (Keyboard.KEYBOARD.isKeyDown(KeyEvent.VK_D)){
			moveHorizontally(main.t.intNumber("player-move-speed"));
		}
		if (Keyboard.KEYBOARD.isKeyDown(KeyEvent.VK_A)){
			moveHorizontally(-main.t.intNumber("player-move-speed"));
		}
		if (Keyboard.KEYBOARD.isKeyDown(KeyEvent.VK_W)){
			if (timeInAir < 10){
				moveVertically(main.t.intNumber("gravity") + 2 * main.t.intNumber("player-jump-base"));
			}
			else if (timeInAir < 15){
				moveVertically(main.t.intNumber("gravity") + main.t.intNumber("player-jump-base"));
			}
			else if (timeInAir < 20){
				moveVertically(main.t.intNumber("gravity") + 1);
			}
		}
		if (Keyboard.KEYBOARD.isKeyDown(KeyEvent.VK_Q)){
			System.out.println("got quit");
			main.stopped = true;
			Keyboard.KEYBOARD.reset();
		}
	}
}
