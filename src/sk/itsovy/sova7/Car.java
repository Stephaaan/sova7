package sk.itsovy.sova7;

public class Car {
	private String brand, color, spz;
	private char fuel;
	private int price;
	
	public Car(String brand, String color, String spz, char fuel, int price) {
		super();
		this.brand = brand;
		this.color = color;
		this.spz = spz;
		this.fuel = fuel;
		this.price = price;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSpz() {
		return spz;
	}

	public void setSpz(String spz) {
		this.spz = spz;
	}

	public char getFuel() {
		return fuel;
	}

	public void setFuel(char fuel) {
		this.fuel = fuel;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
