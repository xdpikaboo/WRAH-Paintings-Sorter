package wps;

import java.util.*;

public class Display {

	//assuming that picassos and dalis are sorted by price, then height
	private List<Painting> picassos;
	private List<Painting> dalis;
	
	//memory array to store max number of pairs
	private Integer[][] memo;
	
	//arrays used to store max matching pairs from the input arrays
	private List<Painting> TraceP;
	private List<Painting> TraceD;
	
	public static void main(String[] args) {
		Display test = new Display(10);
		test.generate(0);
		test.sort(9,9);
		test.trace(9,9);
		test.reverse();
		test.toSimpleString();

	}
	
	public void generate(int n) {
		picassos.add(new Painting(1, 2));
		picassos.add(new Painting(1, 2));
		picassos.add(new Painting(2, 3));
		picassos.add(new Painting(3, 2));
		picassos.add(new Painting(3, 4));
		picassos.add(new Painting(3, 5));
		picassos.add(new Painting(4, 1));
		picassos.add(new Painting(4, 3));
		picassos.add(new Painting(4, 4));
		picassos.add(new Painting(5, 2));
		dalis.add(new Painting(1, 3));
		dalis.add(new Painting(1, 3));
		dalis.add(new Painting(2, 1));
		dalis.add(new Painting(2, 3));
		dalis.add(new Painting(2, 4));
		dalis.add(new Painting(3, 2));
		dalis.add(new Painting(3, 5));
		dalis.add(new Painting(4, 3));
		dalis.add(new Painting(5, 1));
		dalis.add(new Painting(5, 4));
	}
	
	public Display(int n) {
		picassos = new ArrayList<Painting>();
		dalis = new ArrayList<Painting>();
		memo = new Integer[n][n];
		TraceP = new ArrayList<Painting>();
		TraceD = new ArrayList<Painting>();
	}
	
	//takes in i for dalis and j for picassos which is the index of the last item of the arrays that will be sorted
	//runtime Complexity is O(n^2) since we are recursing on both i and j. 
	//returns the maximum number of pairs that is sorted within picassos[j] and dalis[i]
	/*
	 * Integer sort(int i,int j) {
		if( i >= 0 && j >= 0) {
			- if memo[i][j] is not null, return memo[i][j]
			- else if i = 0 && j = 0 {
				- if picassos[j] can match dalis[i], memo[i][j] <- 1 (save info into memo), return 1
				- else, memo[i][j] <- 0, return 0
			- else  if picassos[j] can match dalis[i], memo[i][j] <- sort(i - 1, j - 1) + 1, return memo[i][j]
			- else  memo[i][j] <- max(sort(i - 1, j),sort(i, j - 1)), return memo[i][j]
		else return -1

	 */
	public Integer sort(int i,int j) {
		if (i >= 0 && j >= 0) {
			return checkMemo(i, j);
		}
		return -1;
	}
	
	public Integer checkMemo(int i,int j) {
		if (memo[i][j] != null) {
			return memo[i][j];
		} else {
			return solve(i, j);
		}
	}
	
	public Integer solve(int i,int j) {
		if (i == 0 && j == 0) {
			if (picassos.get(j).canMatchDali(dalis.get(i))) {
				memo[i][j] = 1;
				return 1;
			} else {
				memo[i][j] = 0;
				return 0;
			}
		} else if (picassos.get(j).canMatchDali(dalis.get(i))) {
			memo[i][j] =  sort(i - 1, j - 1) + 1;
			return memo[i][j];
		} else {
			memo[i][j] = Math.max(sort(i - 1, j),sort(i, j - 1));
			return memo[i][j];
		}
	}
	
	//takes in i for dalis and j for picassos which is the index of the highest max number of pairs from memo after sort()
	//runtime Complexity is O(n^2) since we are iterating memo on both i and j.
	//trace maximum matching pairs from memo after sort()
	/*
	 * void  trace(int i, int j) {
		if (i >= 0 and j >= 0) {
			- if picassos[j] can match dalis[i],add picassos[j] to traceP and dalis[i] to traceD, trace(i - 1, j - 1)
			- else 
				- if (memo(i - 1, j) > memo(i, j - 1)), trace(i - 1, j);
				- else, trace(i, j - 1);
		Reverse TraceD and TraceP to get the right order.

	 */
	public void trace(int i,int j) {
		if(i >= 0 && j >= 0) {
			if (picassos.get(j).canMatchDali(dalis.get(i))) {
				TraceP.add(picassos.get(j));
				TraceD.add(dalis.get(i));
				trace(i - 1, j - 1);
			} else {
				if (memo[i - 1][j] > memo[i][j - 1]) {
					trace(i - 1, j);
				} else {
					trace(i, j - 1);
				}
			}
		}
	}
	
	//error handling
	public final Display validate() {
		TraceP.forEach((p) -> Painting.validate(p));
		TraceD.forEach((d) -> Painting.validate(d));	
		return this;
	}
		
	public static final Display validate(Display display) {
		Objects.requireNonNull(display);
		return display.validate();
	}
		
	//reverse for the correct anwser
	public void reverse() {
		Collections.reverse(TraceP);
		Collections.reverse(TraceD);
	}
	
	public void toSimpleString() {
		for(int i = 0; i < memo.length; i++)
		{
		    for(int j = 0; j < memo[0].length; j++)
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
	
	//getters and setters used for testing
	List<Painting> getPicassos() {
		return this.picassos;
	}
	
	List<Painting> getDalis() {
		return this.dalis;
	}
	
	Integer[][] getMemo() {
		return memo;
	}
	
	List<Painting> getTraceP() {
		return TraceP;
	}
	
	void setTraceP(List<Painting> TraceP) {
		this.TraceP = TraceP;
	}
	
	List<Painting> getTraceD() {
		return TraceD;
	}
	
	void setTraceD(List<Painting> TraceD) {
		this.TraceD = TraceD;
	}
	
}

