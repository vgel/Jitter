package com.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class EvolveGame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int targetScore = 9;
		final int childrenPerParent = 2; //more = more variation, but more levels to play
		int highestScore = 0;
		Tweakables t = new Tweakables();
		t.add("gravity", -3);
		t.add("score-per-coin", 1);
		t.add("player-move-speed", 1);
		t.add("player-jump-base", 8);
		t.add("enemy-move-speed", 3);
		t.add("number-enemies", 7);
		t.add("number-coins", 10);
		while (highestScore < targetScore){
			Tweakables bestOfRun = t;
			int bestScoreOfRun = 0;
			for (int i = 0; i < childrenPerParent; i++){
				Tweakables t1 = t.mutate();
				System.out.println(t1);
				Main m = new Main(t1);
				m.start();
				int score = showScoringPane();
				if (score > highestScore){
					bestScoreOfRun = score;
					bestOfRun = t1;
				}
			}
			if (bestScoreOfRun > highestScore){
				t = bestOfRun;
				highestScore = bestScoreOfRun;
			}
		}
		System.out.println(highestScore);
	}
	
	public static int showScoringPane(){
		JPanel p = new JPanel();
		ButtonGroup group = new ButtonGroup();
		final int[] sel = new int[1];
		sel[0] = -1;
		for (int i = 1; i <= 10; i++){
			JRadioButton b = new JRadioButton("" + i);
			b.setActionCommand("" + i);
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sel[0] = Integer.parseInt(e.getActionCommand());
				}
			});
			group.add(b);
			p.add(b);
		}
		JFrame popup = new JFrame("What did you think of the level?");
		popup.setSize(300, 100);
		popup.setLocation(50, 50);
		popup.setContentPane(p);
		popup.setVisible(true);
		while (sel[0] < 0){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e){}
		}
		popup.setVisible(false);
		popup.dispose();
		return sel[0];
	}

}
