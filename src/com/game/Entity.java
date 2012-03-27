package com.game;

public class Entity {
	int x, y;
	int size;
	String spriteName;
	Main main;
	
	public Entity(int x, int y, int size, String spriteName, Main m) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.spriteName = spriteName;
		this.main = m;
		synchronized (main.entities) {
			main.entities.add(this);
		}
	}
	
	public void tick(){
		moveVertically(main.t.intNumber("gravity"));
		if (y > Block.maxY() * Block.BLOCK_SIZE){
			kill();
		}
	}
	
	public void kill(){
		synchronized (main.entities) {
			main.entities.remove(this);
		}
	}
	
	public void moveVertically(int dist){
		if (dist == 0) return;
		boolean up = true;
		if (dist < 0){
			up = false;
			dist = -dist;
		}
		for (int i = 0; i < dist; i++){
			int prevY = y;
			if (up) y--;
			else y++;
			if (collidingWithAny()){
				y = prevY;
				return;
			}
		}
	}
	
	public boolean moveHorizontally(int dist){
		if (dist == 0) return true;
		boolean right = true;
		if (dist < 0){
			right = false;
			dist = -dist;
		}
		for (int i = 0; i < dist; i++){
			int prevX = x;
			if (right) x++;
			else x--;
			if (collidingWithAny()){
				x = prevX;
				return false;
			}
		}
		return true;
	}
	
	protected boolean collidingWithAny(){
		for (Block b : Block.levelBlocks){
			if (collidingWith(b)){
				return true;
			}
		}
		return false;
	}
	
	protected boolean collidingWithHoriz(){
		for (Block b : Block.levelBlocks){
			int i = b.coordY + Block.BLOCK_SIZE;
			if (b.coordY >= y + size && b.coordY <= y && i >= y + size && i <= y && collidingWith(b)){
				return true;
			}
		}
		return false;
	}
	
	protected boolean collidingWith(Block b){
		//System.out.println(this + ", " + b);
		if (xCB(x, b) || xCB(x + size, b)){
			if (yCB(y, b) || yCB(y + size, b)){
				return true;
			}
		}
		return false;
	}
	
	public boolean pointInside(int px, int py){
		if (px >= x && px <= x + size){
			if (py >= y && py <= y + size){
				return true;
			}
		}
		return false;
	}
	
	public boolean onGround(){
		for (Block b: Block.levelBlocks){
			if (xCB(x, b) || xCB(x + size, b)){
				if (Math.abs((y + size) - b.coordY) <= 1){
					return true;
				}
			}
		}
		return false;
	}
	
	protected boolean xCB(int x, Block b){
		return (x >= b.coordX && x <= b.coordX + Block.BLOCK_SIZE) || (b.coordX >= x && b.coordX + Block.BLOCK_SIZE <= x);
	}
	
	protected boolean yCB(int y, Block b){
		return (y >= b.coordY && y <= b.coordY + Block.BLOCK_SIZE) || (b.coordY >= y && b.coordY + Block.BLOCK_SIZE <= y);
	}

	@Override
	public String toString() {
		return "Entity [x=" + x + ", y=" + y + ", size=" + size + ", spriteName=" + spriteName + "]";
	}
}
