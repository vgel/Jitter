package com.game;

import java.awt.event.*;
import java.util.*;

public class Keyboard {
	public static final Keyboard KEYBOARD;
	HashMap<Integer, KeyState> keys;
	KeyAdapter listener;
	
	static {
		KEYBOARD = new Keyboard();
	}
	
	public Keyboard() {
		keys = new HashMap<>();
		listener = new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				synchronized (keys) {
					keys.put(e.getKeyCode(), KeyState.DOWN);
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				synchronized (keys) {
					keys.put(e.getKeyCode(), KeyState.PRESSED);
				}
			}
		};
	}
	
	public boolean isKeyDown(int key){
		KeyState k = keyState(key);
		return k == KeyState.DOWN || k == KeyState.PRESSED;
	}
	
	public boolean isKeyCurrentlyDown(int key){
		KeyState k = keyState(key);
		return k == KeyState.DOWN;
	}
	
	public KeyState keyState(int key){
		synchronized (keys) {
			KeyState k = keys.get(key);
			if (k == KeyState.PRESSED){
				keys.put(key, KeyState.UP);
			}
			return k;
		}
	}
	
	public void reset(){
		keys.clear();
	}
	
	/**
	 * Key states.
	 * Up & down: self explanatory. Current state
	 * Pressed: if the key has been pressed then released **since last being polled**. 
	 * That means the key is actually up but has been down at some point without the game knowing (due to being in-between ticks)
	 */
	public static enum KeyState {UP, DOWN, PRESSED};
}
