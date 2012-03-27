package com.game;

import java.util.*;

public class Block {
	public static final int BLOCK_SIZE = 16;
	public static List<Block> levelBlocks = new ArrayList<>();
	int x, y;
	int coordX, coordY;
	
	public static void buildLevel(String blocks, Main main){
		int y = 0;
		int x = 0;
		for (int i = 0; i < blocks.length(); i++){
			char c = blocks.charAt(i);
			switch(c){
			case '#':
				levelBlocks.add(new Block(x, y));
				x++;
				break;
			case '.':
				y++;
				x = 0;
				break;
			case ' ':
				x++;
				break;
			case 'x':
				new EntityEnemy(x * BLOCK_SIZE, y * BLOCK_SIZE, 14, "enemy.png", main);
				x++;
				break;
			case 'o':
				new EntityCoin(x * BLOCK_SIZE, y * BLOCK_SIZE, 16, main);
				x++;
				break;
			}
		}
		spawn(main);
		//System.out.println(levelBlocks);
	}
	
	public static void spawn(Main main){
		Random rand = new Random();
		int numEnemies = main.t.intNumber("number-enemies");
		int numCoins = main.t.intNumber("number-coins");
		List<Block> blocks = new ArrayList<>();
		for (Block b : levelBlocks){
			if (!blockExists(b.x, b.y - 1)){
				blocks.add(b);
			}
		}
		for (int i = 0; i < numEnemies; i++){
			Block b = blocks.get(rand.nextInt(blocks.size()));
			new EntityEnemy(b.coordX, b.coordY - Block.BLOCK_SIZE, 14, "enemy.png", main);
		}
		for (int i = 0; i < numCoins; i++){
			Block b = blocks.get(rand.nextInt(blocks.size()));
			new EntityCoin(b.coordX, b.coordY - Block.BLOCK_SIZE, 14, main);
		}
	}
	
	public static boolean blockExists(int x, int y) {
		for (Block b : levelBlocks){
			if (b.x == x && b.y == y) return true;
		}
		return false;
	}
	
	public static int maxY(){
		int y = 0;
		for (Block b : levelBlocks){
			if (b.y > y) y = b.y;
		}
		return y;
	}
	
	public Block(int x, int y) {
		this.x = x;
		this.y = y;
		this.coordX = x * BLOCK_SIZE;
		this.coordY = y * BLOCK_SIZE;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Block)) return false;
		Block b = (Block)obj;
		return b.x == x && b.y == y;
	}

	@Override
	public String toString() {
		return "Block [x=" + x + ", y=" + y + ", coordX=" + coordX + ", coordY=" + coordY + "]";
	}
}
