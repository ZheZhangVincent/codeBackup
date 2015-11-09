
public class Bottle {
	private Beer beer;
	private double volume;
	private double alcoholContent;
	private Colour colour;
	private double price;
	private int quantity;
	
	public Bottle(Beer beer, double volume, Colour colour, double price, int quantity) {
		this.beer = beer;
		this.volume = volume;
		this.alcoholContent = volume * beer.getStrength() / 100;
		this.colour = colour;
		this.price = price;
		this.quantity = quantity;
	}
	
	public Beer getBeer() {
		return beer;
	}

	public void setBeer(Beer beer) {
		this.beer = beer;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getAlcoholContent() {
		return alcoholContent;
	}

	public void setAlcoholContent(double alcoholContent) {
		this.alcoholContent = alcoholContent;
	}
	
	public enum Colour {
		
		BLUE(1), BLACK(2), GREEN(3), WHITE(4), CLEAN(5), BROWN(6);
		
		private int Num;
		
		private Colour (int Num) {
			this.Num = Num;
		}
		
		public String toString() {
			return String.valueOf(this.Num);
		}
	}

	public Colour getColour() {
		return colour;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
