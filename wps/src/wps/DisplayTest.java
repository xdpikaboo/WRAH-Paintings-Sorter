package wps;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;
import org.junit.Before;

public class DisplayTest {

	private Display test;

	@Before
	public void setUp() throws Exception {
		/* Set up the object to be tested */
		test = new Display(10);
		test.getPicassos().add(new Painting(1, 2));
		test.getPicassos().add(new Painting(1, 2));
		test.getPicassos().add(new Painting(2, 3));
		test.getPicassos().add(new Painting(3, 2));
		test.getPicassos().add(new Painting(3, 4));
		test.getPicassos().add(new Painting(3, 5));
		test.getPicassos().add(new Painting(4, 1));
		test.getPicassos().add(new Painting(4, 3));
		test.getPicassos().add(new Painting(4, 4));
		test.getPicassos().add(new Painting(5, 2));
		test.getDalis().add(new Painting(1, 3));
		test.getDalis().add(new Painting(1, 3));
		test.getDalis().add(new Painting(2, 1));
		test.getDalis().add(new Painting(2, 3));
		test.getDalis().add(new Painting(2, 4));
		test.getDalis().add(new Painting(3, 2));
		test.getDalis().add(new Painting(3, 5));
		test.getDalis().add(new Painting(4, 3));
		test.getDalis().add(new Painting(5, 1));
		test.getDalis().add(new Painting(5, 4));
	}

	// Test 1: CC1, B2, B4, DF2 - i = 0, j = 0 → checkMemo(i, j)
	@Test
	public void testSort1() {
		assertEquals(test.sort(0, 0), test.checkMemo(0, 0));
	}

	// Test 2: CC2, BD1, BD2, B1, B3 - i = -1, j = -1 → -1
	@Test
	public void testSort2() {
		assertEquals(test.sort(-1, -1), new Integer(-1));
	}

	// Test 1: CC1, B2, B4 - i = 0, j = 0, memo[i][j] not null → memo[i][j]
	@Test
	public void testCheckMemo1() {
		assertEquals(test.checkMemo(0, 0), test.getMemo()[0][0]);
	}

	// Test 2: CC2, B2, B4 - i = 0, j = 0, memo[i][j] null → solve(i, j)
	@Test
	public void testCheckMemo2() {
		test.getMemo()[0][0] = null;
		assertEquals(test.checkMemo(0, 0), test.solve(0, 0));
	}

	// Test 3: B1, B3 - i = n + 1, j = n + 1 → ArrayIndexOutOfBoundsException
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testCheckMemo3() {
		test.checkMemo(test.getMemo().length + 1, test.getMemo().length + 1);
	}

	// Test 1: CC1, CC3, BC1, DF1 - i = 0, j = 0,
	// picassos.get(j).canMatchDali(dalis.get(i)) = True → 1
	@Test
	public void testSolve1() {
		test.getPicassos().add(0, new Painting(1, 1));
		test.getDalis().add(0, new Painting(1, 2));
		assertEquals(test.solve(0, 0), new Integer(1));
	}

	// Test 2: CC1, CC4, DF2 - i = 0, j = 0,
	// picassos.get(j).canMatchDali(dalis.get(i)) = False → 0
	@Test
	public void testSolve2() {
		test.getPicassos().add(0, new Painting(1, 1));
		test.getDalis().add(0, new Painting(1, 1));
		assertEquals(test.solve(0, 0), new Integer(0));
	}

	// Test 3: CC2, CC3, DF3 - i = 2, j = 1,
	// picassos.get(j).canMatchDali(dalis.get(i)) = True → sort(i - 1,j - 1) + 1
	@Test
	public void testSolve3() {
		test.getPicassos().add(1, new Painting(1, 1));
		test.getDalis().add(2, new Painting(1, 2));
		assertEquals(test.solve(2, 1), new Integer(test.sort(1, 0) + 1));
	}

	// Test 4: CC2, CC4, BC2, DF4 - i = 1, j = 2,
	// picassos.get(j).canMatchDali(dalis.get(i)) = False → Math.max(sort(i - 1,
	// j),sort(i, j - 1)
	@Test
	public void testSolve4() {
		test.getPicassos().add(1, new Painting(1, 1));
		test.getDalis().add(0, new Painting(1, 1));
		Integer max = Math.max(test.sort(0, 2), test.sort(1, 1));
		assertEquals(test.solve(1, 2), max);
	}

	// Test 1: CC1, CC3, BC1, B2, B4, B6, B8 - i = 0, j = 0,
	// picassos.get(j).canMatchDali(dalis.get(i)) = True → trace(i - 1, j -
	// 1);TraceP = {picassos.get(j)};TraceD = {dalis.get(i)};
	@Test
	public void testTrace1() {
		test.getPicassos().add(0, new Painting(1, 1));
		test.getDalis().add(0, new Painting(1, 2));
		test.sort(0, 0);
		test.trace(0, 0);
		assertEquals(test.getTraceP().get(0), test.getPicassos().get(0));
	}

	// Test 2: CC2, B1, B3 - i = -1, j = -1, → nothing happens
	@Test
	public void testTrace2() {
		List<Painting> oldP = test.getTraceP();
		List<Painting> oldD = test.getTraceD();
		test.trace(-1, -1);
		for (int i = 0; i < test.getTraceP().size(); i++) {
			assertEquals(oldP.get(i), test.getTraceP().get(i));
			assertEquals(oldD.get(i), test.getTraceD().get(i));
		}
	}

	// Test 3: B5, B7 - i = n + 1, j = n + 1, → ndexOutOfBoundsException
	@Test(expected = IndexOutOfBoundsException.class)
	public void testTrace3() {
		test.trace(test.getMemo().length + 1, test.getMemo().length + 1);
	}

	// Test 4: CC1, CC4, BC2, B2, B4, B6, B8 - i = 9 && j = 9 and
	// picassos.get(j).canMatchDali(dalis.get(i)) = False and memo[i - 1][j] >
	// memo[i][j - 1] → trace(i - 1, j);
	@Test
	public void testTrace4() {
		test.getPicassos().add(9, new Painting(1, 1));
		test.getDalis().add(9, new Painting(1, 1));
		test.sort(9, 9);
		test.trace(9, 9);
		assertEquals(test.getMemo()[9][9], test.getMemo()[8][9]);

	}

	// Test 5: CC1, CC4, BC3, B2, B4, B6, B8 - i= 1 && j = 1 and
	// picassos.get(j).canMatchDali(dalis.get(i)) = False and memo[i - 1][j] <=
	// memo[i][j - 1] → trace(i, j - 1);
	@Test
	public void testTrace5() {
		test.sort(9, 8);
		test.trace(9, 8);
		assertEquals(test.getMemo()[9][8], test.getMemo()[9][7]);

	}

	// Test 1: CC1, BD1 - display is null → NullPointerException
	@Test(expected = NullPointerException.class)
	public void testValidate1() {
		Display test2 = null;
		Display.validate(test2);

	}

	// Test 2: CC2, BC1, BD2, BD3 - display is not null, TraceP null, TraceD null→
	// NullPointerException
	@Test(expected = NullPointerException.class)
	public void testValidate2() {
		Display test2 = new Display(1);
		test2.setTraceP(null);
		test2.setTraceD(null);
		Display.validate(test2);

	}

	// Test 3: CC2, BC2 - display is not null, TraceP not null, TraceD not null→
	// display
	@Test
	public void testValidate3() {
		Display.validate(test);

	}

//good data (legacy)
//	@Test
//	public void testSort() {
//		Integer[][] memo = test.getMemo();
//		assertEquals(memo[0][0], new Integer(1));
//		assertEquals(memo[1][1], new Integer(2));
//		assertEquals(memo[3][3], new Integer(3));
//		assertEquals(memo[6][4], new Integer(4));
//		assertEquals(memo[8][8], new Integer(5));
//		assertEquals(memo[9][9], new Integer(6));
//	}

//	@Test
//	public void testTrace() {
//		List<Painting> traceP = test.getTraceP();
//		List<Painting> traceD = test.getTraceD();
//		assertEquals(traceP.get(0).getPrice(), new Integer(1));
//		assertEquals(traceP.get(0).getHeight(), new Integer(2));
//		assertEquals(traceD.get(0).getPrice(), new Integer(1));
//		assertEquals(traceD.get(0).getHeight(), new Integer(3));
//		assertEquals(traceP.get(1).getPrice(), new Integer(1));
//		assertEquals(traceP.get(1).getHeight(), new Integer(2));
//		assertEquals(traceD.get(1).getPrice(), new Integer(1));
//		assertEquals(traceD.get(1).getHeight(), new Integer(3));
//		assertEquals(traceP.get(2).getPrice(), new Integer(3));
//		assertEquals(traceP.get(2).getHeight(), new Integer(2));
//		assertEquals(traceD.get(2).getPrice(), new Integer(2));
//		assertEquals(traceD.get(2).getHeight(), new Integer(4));
//		assertEquals(traceP.get(3).getPrice(), new Integer(3));
//		assertEquals(traceP.get(3).getHeight(), new Integer(4));
//		assertEquals(traceD.get(3).getPrice(), new Integer(3));
//		assertEquals(traceD.get(3).getHeight(), new Integer(5));
//		assertEquals(traceP.get(4).getPrice(), new Integer(4));
//		assertEquals(traceP.get(4).getHeight(), new Integer(1));
//		assertEquals(traceD.get(4).getPrice(), new Integer(4));
//		assertEquals(traceD.get(4).getHeight(), new Integer(3));
//		assertEquals(traceP.get(5).getPrice(), new Integer(5));
//		assertEquals(traceP.get(5).getHeight(), new Integer(2));
//		assertEquals(traceD.get(5).getPrice(), new Integer(5));
//		assertEquals(traceD.get(5).getHeight(), new Integer(4));
//
//	}

}
