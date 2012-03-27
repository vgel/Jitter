package com.game;

public class EntityCoin extends Entity {

	public EntityCoin(int x, int y, int size, Main main) {
		super(x, y, size, "coin.png", main);
	}
	
	@Override
	public void tick() {
		super.tick();
		synchronized (main.entities) {
			for (Entity e : main.entities){
				if (e instanceof EntityPlayer){
					if (pointInside(e.x, e.y) || pointInside(e.x + e.size, e.y) || pointInside(e.x, e.y + e.size) || pointInside(e.x + e.size, e.y + e.size)){
						((EntityPlayer)e).score += main.t.intNumber("score-per-coin");
						kill();
						break;
					}
				}
			}
		}
	}

}
