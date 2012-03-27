package com.game;

import java.util.*;
import java.util.Timer;
import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

public class Render extends JPanel {
	Map<String, Image> sprites;
	public static final int TOP_OFFSET = 27;

	public Render() {
		sprites = new HashMap<>();
		registerSprite("block.png");
		registerSprite("player.png");
		registerSprite("enemy.png");
		registerSprite("coin.png");
	}

	public void registerSprite(String filename) {
		ImageIcon ic = new ImageIcon(filename);
		sprites.put(filename, ic.getImage());
	}

	public void render(BufferStrategy strategy, int w, int h, Main main) {
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, w, h);
		g.setColor(Color.YELLOW);
		g.drawString("Score: " + main.player.score, 10, TOP_OFFSET + 10);
		//System.out.println(g);
		Image block = sprites.get("block.png");
		for (Block b : Block.levelBlocks) {
			g.drawImage(block, b.x * Block.BLOCK_SIZE, b.y * Block.BLOCK_SIZE + TOP_OFFSET, null);
		}
		synchronized (main.entities) {
			for (Entity e : main.entities) {
				Image i = sprites.get(e.spriteName);
				if (i == null) throw new NullPointerException("Could not find sprite " + e.spriteName);
				g.drawImage(i, e.x, e.y + TOP_OFFSET, e.size + 1, e.size + 1, null);
				/*g.setColor(Color.GREEN);
				int bx = ((e.x + e.size) / Block.BLOCK_SIZE);
				int by = (e.y / Block.BLOCK_SIZE) + 1;
				if (Block.levelBlocks.contains(new Block(bx, by))){
					g.fillRect(bx * Block.BLOCK_SIZE, by * Block.BLOCK_SIZE + TOP_OFFSET, 16, 16);
				}*/
			}
		}
		g.dispose();
		strategy.show();
	}
}
