public class Rectangle {
	public int length;
	public int width;

	public Rectangle(int length, int width) {
		this.length = length;
		this.width = width;
	}

	public int getArea() {
		return length * width;
	}

	pubic int getPerimeter() {
		return 2 * (length + width);
	}
}