package wps;

import java.util.ArrayList;

public class Display {

	private Painting[] picassos;
	
	private Painting[] dalis;
	
	private Integer[][] memo;
	
	private ArrayList<Painting> TraceP;
	
	private ArrayList<Painting> TraceD;
	
	
	public Display(int n) {
		picassos = new Painting[n];
		dalis = new Painting[n];
			
		TraceP = new ArrayList<Painting>();
		TraceD = new ArrayList<Painting>();
		generate(n);
	}
	
	public static void main(String[] args) {
		Display test = new Display(10);
		test.sort(9, 9);
		test.trace(9,9);
		test.toSimpleString();

	}
	
	public Integer sort(int i,int j) {
		if (i >= 0 && j >= 0) {
			if (memo[i][j] != null) {
				return memo[i][j];
			} else if (i == 0 && j == 0) {
				if (picassos[j].canMatchDali(dalis[i])) {
					memo[i][j] = 1;
					return 1;
				} else {
					memo[i][j] = 0;
					return 0;
				}
			} else if (picassos[j].canMatchDali(dalis[i])) {
				memo[i][j] = sort(i - 1, j - 1) + 1;
				return memo[i][j];
			} else {
				memo[i][j] = Math.max(sort(i - 1, j),sort(i, j - 1));
				return memo[i][j];
			}
		}
		return -1;
	}
	
	public void trace(int i,int j) {
		if(i >= 0 && j >= 0) {
			if (picassos[j].canMatchDali(dalis[i])) {
				TraceP.add(picassos[j]);
				TraceD.add(dalis[i]);
				trace(i - 1, j - 1);
			} else {
				if (sort(i - 1, j) > sort(i, j - 1)) {
					trace(i - 1, j);
				} else {
					trace(i, j - 1);
				}
			}
		}
	}
	
	
	
	public void generate(int n) {
		picassos[0] = new Painting(1, 2);
		picassos[1] = new Painting(2, 1);
		picassos[2] = new Painting(2, 3);
		picassos[3] = new Painting(3, 2);
		picassos[4] = new Painting(3, 4);
		picassos[5] = new Painting(3, 5);
		picassos[6] = new Painting(4, 1);
		picassos[7] = new Painting(4, 3);
		picassos[8] = new Painting(4, 4);
		picassos[9] = new Painting(5, 2);
		dalis[0] = new Painting(1, 3);
		dalis[1] = new Painting(1, 3);
		dalis[2] = new Painting(2, 1);
		dalis[3] = new Painting(2, 3);
		dalis[4] = new Painting(2, 4);
		dalis[5] = new Painting(3, 2);
		dalis[6] = new Painting(3, 5);
		dalis[7] = new Painting(4, 3);
		dalis[8] = new Painting(5, 1);
		dalis[9] = new Painting(5, 4);
	}
	
	public void toSimpleString() {
		for(int i = 0; i < memo.length; i++)
		{
		    for(int j = 0; j < memo.length; j++)
		    {
		        System.out.print("| " + memo[i][j] + " |");
		    }
		    System.out.println();
		}
		System.out.println();
		TraceP.forEach((p) -> System.out.print(p.toString()));
		System.out.println();
		TraceD.forEach((d) -> System.out.print(d.toString()));
		
		
	}
	
}
