package wps;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class PaintingTest {
	
	//Test 1: BD1, BD2 - this null and dali null → NullPointerException
	@Test(expected = NullPointerException.class)
	public void testCanMatchDali1() {
		Painting picasso = null;
		Painting dali = null;
		picasso.canMatchDali(dali);
	}

	//Test 2: CC1, B2, B4 - this.height = 1, dali.height = 2 → true
	@Test
	public void testCanMatchDali2() {
		Painting picasso = new Painting(1,1);
		Painting dali = new Painting(1,2);
		assertTrue(picasso.canMatchDali(dali));
	}
	
	//Test 3: CC2, B2, B4 - this.height = 2, dali.height = 1 → false
	@Test
	public void testCanMatchDali3() {
		Painting picasso = new Painting(1,2);
		Painting dali = new Painting(1,1);
		assertFalse(picasso.canMatchDali(dali));
	}
	
	//Test 4: BD3, BD4, B1, B3 - this.height = 0, dali.height = 0 → IllegalArgumentException
	@Test(expected = IllegalArgumentException.class)
	public void testCanMatchDali4() {
		Painting picasso = new Painting(1,0);
		Painting dali = new Painting(1,0);
		picasso.canMatchDali(dali);
	}
	
	//Test 1: BD1 - painting null -→  NullPointerException
	@Test(expected = NullPointerException.class)
	public void testValidate1() {
		Painting test = null;
		test.validate();
	}
	
	//Test 2: CC1, B2, B4, DF4 - height = 1, price = 1 → painting
	@Test
	public void testValidate2() {
		Painting test = new Painting(1,1);
		assertEquals(test.validate(), test);
	}
	
	//Test 3: CC2, BD2, BD3, B1, B3, DF3 - height = 0, price = 0 → IllegalArgumentException
	@Test(expected = IllegalArgumentException.class)
	public void testValidate3() {
		Painting test = new Painting(0,0);
		test.validate();
	}
	
	//Test 4: CC2, BD3, B2, B3, DF2 - height = 1, price = 0 → IllegalArgumentException
	@Test(expected = IllegalArgumentException.class)
	public void testValidate4() {
		Painting test = new Painting(0,1);
		test.validate();
	}
	
	//Test 5: CC2, BD2, B1, B4, DF1 - height = 0, price = 1 → IllegalArgumentException
	@Test(expected = IllegalArgumentException.class)
	public void testValidate5() {
		Painting test = new Painting(1,0);
		test.validate();
	}
		
	
	
	
	
	

}
