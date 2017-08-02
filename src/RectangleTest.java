import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RectangleTest {	
	Rectangle rectangle = new Rectangle(5, 6);

	@Test
	public void testGetArea() {
		assertEquals(rectangle.getArea(), 30);
	}

	@Test
	public void testGetPerimeter() {
		assertEquals(rectangle.getPerimeter(), 22);
	}

	@Test
	public void testLength() {
		assertEquals(rectangle.length, 5);
	}

	@Test
	public void testWidth() {
		assertEquals(rectangle.width, 6);
	}
}