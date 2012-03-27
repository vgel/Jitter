package com.game;

import java.util.*;

public class EntityEnemy extends Entity {
	int dir;
	int timeMoving;
	Random rand;
	int jumping = -1;
	
	public EntityEnemy(int x, int y, int size, String spriteName, Main main) {
		super(x, y, size, spriteName, main);
		rand = new Random();
		int i = main.t.intNumber("enemy-move-speed");
		dir = (rand.nextBoolean() ? -i : i);
	}
	
	@Override
	public void tick() {
		super.tick();
		if ((timeMoving > 10 && rand.nextInt(7) == 2)){
			timeMoving = 0;
			int i = main.t.intNumber("enemy-move-speed");
			dir = (rand.nextBoolean() ? -i : i);
		}
		if (!shouldWalk()) dir = -dir;
		else {
			timeMoving++;
		}
		if (jumping > 0){
			moveVertically(main.t.intNumber("gravity") + 8);
			jumping--;
		}
		boolean b = moveHorizontally(dir);
		if (!b) { //blocked
			jumping = 20;
		}
		for (Entity e : main.entities){
			if (e instanceof EntityPlayer){
				if (pointInside(e.x, e.y) || pointInside(e.x + e.size, e.y) || pointInside(e.x, e.y + e.size) || pointInside(e.x + e.size, e.y + e.size)){
					e.kill();
				}
			}
		}
	}
	
	private boolean shouldWalk(){
		if (dir > 0){
			int bx = ((x + size) / Block.BLOCK_SIZE);
			int by = (y / Block.BLOCK_SIZE) + 1;
			if (!Block.blockExists(bx, by) || !Block.blockExists(bx + 1, by)){ 
				if (getFallDist(bx, by) > 10 || getFallDist(bx + 1, by) > 10) return false;
			}
			return true;
		}
		else if (dir < 0) {
			int bx = (x / Block.BLOCK_SIZE);
			int by = (y / Block.BLOCK_SIZE) + 1;
			if (!Block.blockExists(bx, by) || !Block.blockExists(bx - 1, by)){
				if (getFallDist(bx, by) > 10 || getFallDist(bx - 1, by) > 10) return false;
			}
			return true;
		}
		else {
			return true;
		}
	}
	
	private int getFallDist(int x, int y){
		int fall = 0;
		int maxY = Block.maxY();
		while (!Block.blockExists(x, y) && fall <= maxY){
			fall++;
			y++;
		}
		if (!Block.blockExists(x, y)) fall = Integer.MAX_VALUE;
		//System.out.println(fall);
		return fall;
	}

}
