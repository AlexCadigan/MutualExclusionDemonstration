/*
MIT License

Copyright (c) 2018 Alex Cadigan, Jacob Naranjo, Tanush Samson, Lionel Niyongabire

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
import java.io.PrintWriter;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
/* Begins the simulation */
public class Simulation implements EventHandler<ActionEvent> {
	private TextField [] textFields;
	private double [] objectSpeeds = new double [4];
	/* Initializes instance variables */
	public Simulation(TextField [] textFields) {
		this.textFields = textFields;
	}
	/* Collects the speeds entered by the user */
	@Override
	public void handle(ActionEvent event) {
		for (int index = 0; index < this.textFields.length; index ++) {
			try {
				this.objectSpeeds[index] = Double.parseDouble(this.textFields[index].getText());
				if (this.objectSpeeds[index] < 0) {
					this.objectSpeeds[index] = 1;
				}
			}
			catch (NumberFormatException exception) {
				objectSpeeds[index] = 10;
			}
		}
		this.buildSimulation();
	}
	/* Creates the simulation GUI */
	private void buildSimulation() {
		// Creates the GUI window
		Stage animationStage = new Stage();
		animationStage.initModality(Modality.APPLICATION_MODAL);
		animationStage.setTitle("Mutual Exclusion Simulation");
		animationStage.setResizable(false);
		// Creates the moving objects (circle, square, triangle, rhombus)
		Group objects = new Group();
		Circle circle = new Circle(10, Color.RED);
		Rectangle square = new Rectangle(15, 15, Color.BLUE);
		Polygon triangle = new Polygon();
		triangle.setFill(Color.YELLOW);
    	triangle.getPoints().addAll(new Double[] { 0.0, 0.0, 20.0, 10.0, 10.0, 20.0 });
    	Polygon rhombus = new Polygon();
    	rhombus.setFill(Color.GREEN);
    	rhombus.getPoints().addAll(new Double[] { 0.0, 0.0, -10.0, 10.0, 0.0, 20.0, 10.0, 10.0 });
		objects.getChildren().add(circle);
		objects.getChildren().add(square);
		objects.getChildren().add(triangle);
		objects.getChildren().add(rhombus);
		// Left lower triangle
		MoveTo lLTMoveTo = new MoveTo();
		lLTMoveTo.setX(250.0f);
		lLTMoveTo.setY(250.0f);
		LineTo lLTLowerLine = new LineTo();
		lLTLowerLine.setX(100.0f);
		lLTLowerLine.setY(400.0f);
		LineTo lLTVLine = new LineTo();
		lLTVLine.setX(100.0f);
		lLTVLine.setY(100.0f);
		LineTo lLTUpperLine = new LineTo();
		lLTUpperLine.setX(250.0f);
		lLTUpperLine.setY(250.0f);
		Path lLTriangle = new Path();
		lLTriangle.setStroke(Color.BLACK);
		lLTriangle.getElements().add(lLTMoveTo);
		lLTriangle.getElements().add(lLTLowerLine);
		lLTriangle.getElements().add(lLTVLine);
		lLTriangle.getElements().add(lLTUpperLine);
		objects.getChildren().add(lLTriangle);
		// Left upper triangle
		MoveTo lUTMoveTo = new MoveTo();
		lUTMoveTo.setX(250.0f);
		lUTMoveTo.setY(250.0f);
		LineTo lUTUpperLine = new LineTo();
		lUTUpperLine.setX(100.0f);
		lUTUpperLine.setY(100.0f);
		LineTo lUTVLine = new LineTo();
		lUTVLine.setX(100.0f);
		lUTVLine.setY(400.0f);
		LineTo lUTLowerLine = new LineTo();
		lUTLowerLine.setX(250.0f);
		lUTLowerLine.setY(250.0f);
		Path lUTriangle = new Path();
		lUTriangle.setStroke(Color.BLACK);
		lUTriangle.getElements().add(lUTMoveTo);
		lUTriangle.getElements().add(lUTUpperLine);
		lUTriangle.getElements().add(lUTVLine);
		lUTriangle.getElements().add(lUTLowerLine);
		objects.getChildren().add(lUTriangle);
		// Right lower triangle
		MoveTo rLTMoveTo = new MoveTo();
		rLTMoveTo.setX(800.0f);
		rLTMoveTo.setY(250.0f);
		LineTo rLTLowerLine = new LineTo();
		rLTLowerLine.setX(950.0f);
		rLTLowerLine.setY(400.0f);
		LineTo rLTVLine = new LineTo();
		rLTVLine.setX(950.0f);
		rLTVLine.setY(100.0f);
		LineTo rLTUpperLine = new LineTo();
		rLTUpperLine.setX(800.0f);
		rLTUpperLine.setY(250.0f);
		Path rLTriangle = new Path();
		rLTriangle.setStroke(Color.BLACK);
		rLTriangle.getElements().add(rLTMoveTo);
		rLTriangle.getElements().add(rLTLowerLine);
		rLTriangle.getElements().add(rLTVLine);
		rLTriangle.getElements().add(rLTUpperLine);
		objects.getChildren().add(rLTriangle);
		// Right upper triangle
		MoveTo rUTMoveTo = new MoveTo();
		rUTMoveTo.setX(800.0f);
		rUTMoveTo.setY(250.0f);
		LineTo rUTUpperLine = new LineTo();
		rUTUpperLine.setX(950.0f);
		rUTUpperLine.setY(100.0f);
		LineTo rUTVLine = new LineTo();
		rUTVLine.setX(950.0f);
		rUTVLine.setY(400.0f);
		LineTo rUTLowerLine = new LineTo();
		rUTLowerLine.setX(800.0f);
		rUTLowerLine.setY(250.0f);
		Path rUTriangle = new Path();
		rUTriangle.setStroke(Color.BLACK);
		rUTriangle.getElements().add(rUTMoveTo);
		rUTriangle.getElements().add(rUTUpperLine);
		rUTriangle.getElements().add(rUTVLine);
		rUTriangle.getElements().add(rUTLowerLine);
		objects.getChildren().add(rUTriangle);
		// Left critical section
		MoveTo lCSMoveTo = new MoveTo();
		lCSMoveTo.setX(250.0f);
		lCSMoveTo.setY(250.0f);
		LineTo lCSLine = new LineTo();
		lCSLine.setX(800.0f);
		lCSLine.setY(250.0f);
		Path lCSPath = new Path();
		lCSPath.setStroke(Color.RED);
		lCSPath.getElements().add(lCSMoveTo);
		lCSPath.getElements().add(lCSLine);
		objects.getChildren().add(lCSPath);
		// Right critical section
		MoveTo rCSMoveTo = new MoveTo();
		rCSMoveTo.setX(800.0f);
		rCSMoveTo.setY(250.0f);
		LineTo rCSLine = new LineTo();
		rCSLine.setX(250.0f);
		rCSLine.setY(250.0f);
		Path rCSPath = new Path();
		rCSPath.setStroke(Color.RED);
		rCSPath.getElements().add(rCSMoveTo);
		rCSPath.getElements().add(rCSLine);
		objects.getChildren().add(rCSPath);
		// Displays the path and objects
		Scene animationScene = new Scene(objects, 1050, 500);
		animationStage.setScene(animationScene);   
		animationStage.show();	
		// Animation tracks
		PathTransition [] cTrack = new PathTransition [] { new PathTransition(Duration.millis(objectSpeeds[0] * 1000), lLTriangle, circle), new PathTransition(Duration.millis(objectSpeeds[0] * 1000), lCSPath, circle), new PathTransition(Duration.millis(objectSpeeds[0] * 1000), rUTriangle, circle), new PathTransition(Duration.millis(objectSpeeds[0] * 1000), rCSPath, circle) };
		PathTransition [] sTrack = new PathTransition [] { new PathTransition(Duration.millis(objectSpeeds[1] * 1000), lUTriangle, square), new PathTransition(Duration.millis(objectSpeeds[1] * 1000), lCSPath, square), new PathTransition(Duration.millis(objectSpeeds[1] * 1000), rLTriangle, square), new PathTransition(Duration.millis(objectSpeeds[1] * 1000), rCSPath, square) };
		PathTransition [] tTrack = new PathTransition [] { new PathTransition(Duration.millis(objectSpeeds[2] * 1000), rLTriangle, triangle), new PathTransition(Duration.millis(objectSpeeds[2] * 1000), rCSPath, triangle), new PathTransition(Duration.millis(objectSpeeds[2] * 1000), lUTriangle, triangle), new PathTransition(Duration.millis(objectSpeeds[2] * 1000), lCSPath, triangle) };
		PathTransition [] rTrack = new PathTransition [] { new PathTransition(Duration.millis(objectSpeeds[3] * 1000), rUTriangle, rhombus), new PathTransition(Duration.millis(objectSpeeds[3] * 1000), rCSPath, rhombus), new PathTransition(Duration.millis(objectSpeeds[3] * 1000), lLTriangle, rhombus), new PathTransition(Duration.millis(objectSpeeds[3] * 1000), lCSPath, rhombus) };
		// Clears contents of log
		try (PrintWriter writer = new PrintWriter("../logs/Log.txt")) {
			writer.close();
		}
		catch (Exception exception) {
			System.out.println("\nError clearing message log!\n");
		}
		// Processes
		Process [] processes = new Process [] { new Process("circle", cTrack),  new Process("square", sTrack), new Process("triangle", tTrack), new Process("rhombus", rTrack) };
		// Starts processes running
		for (int index = 0; index < processes.length; index ++) {
			processes[index].storeProcesses(processes);
			processes[index].start();
		}
	}
}
