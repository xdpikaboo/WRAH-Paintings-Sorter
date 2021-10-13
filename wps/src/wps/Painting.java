package wps;

import java.util.Objects;

import gis.Coordinate;

public class Painting {
	
	private double height;
	
	private double price;
	
	public Painting(int price, int height) {
		this.height = height;
		this.price = price;
	}
	
	public boolean canMatchDali(Painting dali) {
		return this.validate().height < dali.validate().height;
	}
	
	
	public double getHeight() {
		return height;
	}
	
	public double getPrice() {
		return price;
	}
	
	public Painting validate() {
		if (price <= 0 || height <= 0) {
			throw new NullPointerException("price or/and height is invalid");
		}
		return this;
	}
	
	public Painting validate(Painting painting) {
		Objects.requireNonNull(painting);
		return painting.validate();
	}

	public String toString() {
		return "($" + price + ", " + height + "cm)";
	}
}
