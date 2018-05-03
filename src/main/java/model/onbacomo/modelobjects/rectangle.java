package model.onbacomo.modelobjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class rectangle {
	public Rectangle createRectangle() {
	Rectangle rec = new Rectangle();
	rec.setX(50);
	rec.setY(50);
	rec.setWidth(75);
	rec.setHeight(37.5);
	rec.setFill(Color.BLUE);
	rec.setArcWidth(10);
	rec.setArcHeight(10);
	rec.setStroke(Color.BLACK);
	rec.setId("Rectangle");
	return rec;
	}
}
