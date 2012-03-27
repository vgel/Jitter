package com.game;

import java.util.*;

public class Tweakables {
	HashMap<String, Number> tweakables;
	Random rand;
	
	public Tweakables() {
		tweakables = new HashMap<>();
		rand = new Random();
	}
	
	public void add(String s, Number n){
		tweakables.put(s, n);
	}
	
	public Tweakables mutate(){
		Tweakables t = new Tweakables();
		for (String s : tweakables.keySet()){
			if (tweakables.get(s) instanceof Integer){
				t.add(s, mutate(tweakables.get(s).intValue()));
			}
			else if (tweakables.get(s) instanceof Double){
				t.add(s, mutate(tweakables.get(s).doubleValue()));
			}
			else {
				throw new RuntimeException(s + " " + tweakables.get(s));
			}
		}
		return t;
	}
	
	private int mutate(int i){
		//no change
		//up
		//down
		int chance = rand.nextInt(5);
		if (chance <= 1){
			return i + rand.nextInt(4);
		}
		else if (chance <= 3){
			return i - rand.nextInt(4);
		}
		return i;
	}
	
	private double mutate(double d){
		//no change
		//up
		//down
		int chance = rand.nextInt(5);
		if (chance <= 1){
			return d + rand.nextDouble() * 4;
		}
		else if (chance <= 3){
			return d - rand.nextDouble() * 4;
		}
		return d;
	}
	
	public int intNumber(String s){
		try {
			return tweakables.get(s).intValue();
		} catch (NullPointerException e){
			throw e;
		}
	}
	
	public double dblNumber(String s){
		try {
			return tweakables.get(s).doubleValue();
		} catch (NullPointerException e){
			throw e;
		}
	}

	@Override
	public String toString() {
		return "Tweakables [tweakables=" + tweakables + ", rand=" + rand + "]";
	}
}
