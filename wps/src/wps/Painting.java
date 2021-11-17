package wps;

import java.util.Objects;

public record Painting(int price, int height) {
	
	//return true if the dali can match this picasso
	public boolean canMatchDali(Painting dali) {
		return validate(this).height < validate(dali).height;
	}
	
	
	public Integer getHeight() {
		return height;
	}
	
	public Integer getPrice() {
		return price;
	}
	
	//error handling
	public final Painting validate() {
		if (price <= 0 || height <= 0) {
			throw new IllegalArgumentException("price or/and height is invalid");
		}
		return this;
	}
	
	public static final Painting validate(Painting painting) {
		Objects.requireNonNull(painting);
		return painting.validate();
	}

	public String toString() {
		return "($" + price + ", " + height + "cm)";
	}
	
}
