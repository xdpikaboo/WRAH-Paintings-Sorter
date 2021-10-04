package wps;

import java.util.ArrayList;

public class Display {

	private Painting[][] display;
	
	public Display(int n) {
		display = new Painting[2][n];
	}
	
	public ArrayList<ArrayList<Painting>> sort() {
		ArrayList<ArrayList<Painting>> list = new ArrayList<ArrayList<Painting>>(2);
		for (int i = 0; i < display.length; i++) {
			if (display[0][i].getHeight() < display[1][i].getHeight()) {
				list.get(0).add(display[0][i]);
				list.get(1).add(display[1][i]);
			}
		}
		return null;
	}

}
