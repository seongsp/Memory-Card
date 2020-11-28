
//Name: Seong Su Park and Salil Lakhani
//Date: 3/23/2018
//Per: 1
//Computer Science Data Structures

import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import javax.swing.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.*;
import java.awt.event.ActionListener;

public class MemoryCard {

	private JFrame frame;
	private JMenuBar toolBar;
	private JButton[][] buttonGrid;
	private Map<String, ArrayList<Integer>> overMap;

	private Timer timer;
	private int time;
	private TimerTask task;

	private JLabel countLabel;
	private int pairCount;
	private int currentMoveCount;
	private String cardOne;
	private String cardTwo;

	public MemoryCard() {
		// 4 x 4 Frame
		frame = new JFrame("Memory Card");
		frame.setResizable(false);
		frame.setSize(660, 960);
		frame.setLayout(new GridLayout(4, 4));

		// 4 x 4 Grid of Card Buttons and ToolBar
		setUpButtons();

		// Timer
		time = 1;
		setUpTimer();

		frame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void setUpTimer() {
		class TimerListener implements ActionListener {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				time--;
				if (time == 0) {
					timer.stop();
					if (cardOne.equals(cardTwo)) {

						pairCount++;
						currentMoveCount = 0;
						countLabel.setText("   Pairs: " + pairCount);
						frame.repaint();
						for (Integer coord : overMap.get(cardOne)) {
							buttonGrid[coord % 4][coord / 4].setIcon(new ImageIcon("src/Images/done.png"));
							// buttonMode(buttonGrid[coord % 4][coord / 4], false);
						}
						cardOne = "";
						cardTwo = "";

					} else {

						currentMoveCount = 0;
						for (Integer cox : overMap.get(cardOne)) {
							buttonGrid[cox % 4][cox / 4].setIcon(new ImageIcon("src/Images/bamboo.jpg"));
						}
						for (Integer lox : overMap.get(cardTwo)) {
							buttonGrid[lox % 4][lox / 4].setIcon(new ImageIcon("src/Images/bamboo.jpg"));
						}
						cardOne = "";
						cardTwo = "";
					}
					time = 1;
				}
			}
		}
		ActionListener listener = new TimerListener();
		timer = new Timer(1000, listener);
	}

	public void setUpButtons() {
		frame.getContentPane().removeAll();

		// Tool Bar
		toolBar = new JMenuBar();
		JButton resetButton = new JButton("Reset");

		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				setUpButtons();
				frame.repaint();
			}
		});

		// Moves
		pairCount = 0;
		currentMoveCount = 0;
		cardOne = "";
		cardTwo = "";

		countLabel = new JLabel("   Pairs: " + pairCount);
		toolBar.add(resetButton);
		toolBar.add(countLabel);
		frame.setJMenuBar(toolBar);
		overMap = new TreeMap<String, ArrayList<Integer>>();

		// Randomizes Cards
		ArrayList<Integer> randList = new ArrayList<Integer>();
		for (int n = 0; n < 16; n++) {
			randList.add(n);
		}
		Collections.shuffle(randList);

		ArrayList<Integer> ana = new ArrayList<Integer>();
		ana.add(randList.get(0));
		ana.add(randList.get(1));
		overMap.put("ana", ana);

		ArrayList<Integer> bast = new ArrayList<Integer>();
		bast.add(randList.get(2));
		bast.add(randList.get(3));
		overMap.put("bastion", bast);

		ArrayList<Integer> dv = new ArrayList<Integer>();
		dv.add(randList.get(4));
		dv.add(randList.get(5));
		overMap.put("dva", dv);

		ArrayList<Integer> ge = new ArrayList<Integer>();
		ge.add(randList.get(6));
		ge.add(randList.get(7));
		overMap.put("genji", ge);

		ArrayList<Integer> ha = new ArrayList<Integer>();
		ha.add(randList.get(8));
		ha.add(randList.get(9));
		overMap.put("hanzo", ha);

		ArrayList<Integer> ju = new ArrayList<Integer>();
		ju.add(randList.get(10));
		ju.add(randList.get(11));
		overMap.put("junkrat", ju);

		ArrayList<Integer> u = new ArrayList<Integer>();
		u.add(randList.get(12));
		u.add(randList.get(13));
		overMap.put("lucio", u);

		ArrayList<Integer> r = new ArrayList<Integer>();
		r.add(randList.get(14));
		r.add(randList.get(15));
		overMap.put("reinhardt", r);

		buttonGrid = new JButton[4][4];
		for (int k = 0; k < 4; k++) {
			for (int e = 0; e < 4; e++) {
				buttonGrid[k][e] = new JButton();
				frame.add(buttonGrid[k][e]);
			}
		}

		for (Map.Entry<String, ArrayList<Integer>> entry : overMap.entrySet()) {
			for (Integer i : entry.getValue()) {
				// String s = "Images/" + entry.getKey() + ".png";

				ImageIcon icon = new ImageIcon("src/Images/bamboo.jpg");

				buttonGrid[i % 4][i / 4].setIcon(icon);
				buttonGrid[i % 4][i / 4].setText(entry.getKey());

				buttonGrid[i % 4][i / 4].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						if (!((ImageIcon) ((JButton) event.getSource()).getIcon()).getDescription()
								.equals("src/Images/bamboo.jpg")) {
							return;
						}

						if (currentMoveCount == 0) {
							cardOne = ((JButton) event.getSource()).getText();
							((JButton) event.getSource()).setIcon(new ImageIcon("src/Images/" + cardOne + ".png"));
							currentMoveCount++;
						} else if (currentMoveCount == 1) {
							currentMoveCount++;
							cardTwo = ((JButton) event.getSource()).getText();
							((JButton) event.getSource()).setIcon(new ImageIcon("src/Images/" + cardTwo + ".png"));
							frame.repaint();

							timer.start();

						}
						frame.repaint();
					}
				});
			}
		}

		frame.repaint();
	}

	public static void main(String[] args) {
		MemoryCard memory = new MemoryCard();
	}
}
