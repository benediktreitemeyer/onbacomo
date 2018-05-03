package model.onbacomo.modelobjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class circle {
	
	public Circle createCircle() {
		Circle cir = new Circle();
		cir.setFill(Color.BLUE);
		cir.setCenterX(25.0);
		cir.setCenterY(25.0);
		cir.setRadius(25.0);
		cir.setStroke(Color.BLACK);
		cir.setStrokeWidth(1.0);
		cir.setId("Circle");
		return cir;
	}
}
