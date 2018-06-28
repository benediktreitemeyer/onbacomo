package controller.toolbar.initialize;

public class ToolbarElementCreator {
	public static void createElement(String shape){

      switch (shape){
          case "Rectangle":
              System.out.println("Rectangle");
              break;
          case "Circle":
              System.out.println("Circle");
              break;
          case "Arrow":
              System.out.println("Arrow");
              break;
          case "Image":
              System.out.println("Image");
              break;
      }
    }
}
