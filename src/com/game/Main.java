package com.game;

import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.*;

import javax.swing.*;

public class Main {
	public static long MAX_FPS = 60;
	long fps;
	long timeOff;
	boolean paused, stopped = false;
	JFrame window;
	Render render;
	BufferStrategy strategy;
	EntityPlayer player;
	public List<Entity> entities = new CopyOnWriteArrayList<>();
	public Tweakables t;
	
	public Main(Tweakables t) {
		this.t = t;
		render = new Render();
		window = new JFrame("Jitter Demonstration");
		window.setSize(32 * Block.BLOCK_SIZE, 21 * Block.BLOCK_SIZE);
		window.setLocation(500, 500);
		window.setContentPane(render);
		window.addKeyListener(Keyboard.KEYBOARD.listener);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.createBufferStrategy(2);
		strategy = window.getBufferStrategy();
	}
	
	public void start(){
		stopped = false;
		gameLoop();
		window.dispose();
	}

	public void gameLoop() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e){}
		Block.buildLevel("                                ." + //Do not put anything here for now, top row is glitchy
						 "                        #  ###  ." +
						 "  #############          ##     ." +
						 "                                ." +
						 "                                ." +
						 "                    ##          ." +
						 "                    #           ." +
						 "          ###      #            ." +
						 "           #      #             ." +
						 "                 #              ." +
						 "###            ##               ." +
						 "#####                           ." +
						 "####### ##################      ." +
						 "#   ###########   ###########   ." +
						 "#    ########         #######   ." +
						 "#############          ######   ." +
						 "####################    #####   ." +
						 "#####################    ####   ." +
						 "######################          ." +
						 "#######################         ." +
						 "#############################   .", this);
		player = new EntityPlayer(2 * Block.BLOCK_SIZE, 1 * Block.BLOCK_SIZE, 14, "player.png", this);
		while (!stopped) {
			timeOff = System.currentTimeMillis();
			if (!paused) tick();
			render.render(strategy, window.getWidth(), window.getHeight(), this);
			try {
				long l = 1000 / MAX_FPS - timeOff();
				if (l > 0)
					Thread.sleep(l);
				if (timeOff() > 0)
					fps = 1000 / timeOff();
				else
					fps = 0;
			} catch (InterruptedException e){}
		}
	}
	
	private long timeOff(){
		return System.currentTimeMillis() - timeOff;
	}
	
	public void tick(){
		synchronized (entities){
			for (Entity e : entities){
				e.tick();
			}
		}
	}
}
