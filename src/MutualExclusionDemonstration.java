import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.VLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javafx.util.Duration;

/*
* Contains code to construct a GUI that allows users to initiate the simulation
*/
public class MutualExclusionDemonstration extends Application
{
	/*
	* Main method that begins the creation of the GUI
	*/
	public static void main(String [] args)
	{
		Application.launch(args);
	}

	/*
	* Creates GUI page for users to enter ball speeds
	*/
	@Override
	public void start(Stage stage) 
	{
		stage.setTitle("Mutual Exclusion Demonstration");

		// Object speed labels
		Label lblCircle = new Label("Circle Speed:");
		TextField txtCircle = new TextField("10");
		HBox label1 = new HBox(10);
		label1.setAlignment(Pos.CENTER);
		label1.getChildren().addAll(lblCircle, txtCircle);

		Label lblTriangle = new Label("Triangle Speed:");
		TextField txtTriangle = new TextField("10");
		HBox label2 = new HBox(10);
		label2.setAlignment(Pos.CENTER);
		label2.getChildren().addAll(lblTriangle, txtTriangle);

		Label lblSquare = new Label("Square Speed:");
		TextField txtSquare = new TextField("10");
		HBox label3 = new HBox(10);
		label3.setAlignment(Pos.CENTER);
		label3.getChildren().addAll(lblSquare, txtSquare);

		Label lblRhombus = new Label("Rhombus Speed:");
		TextField txtRhombus = new TextField("10");
		HBox label4 = new HBox(10);
		label4.setAlignment(Pos.CENTER);
		label4.getChildren().addAll(lblRhombus, txtRhombus);

		// Start button
		Button btnStart = new Button("Start Simulation");
		btnStart.setOnAction(new startButtonListener(stage, new TextField [] {txtCircle, txtTriangle, txtSquare, txtRhombus}));
		HBox button = new HBox(10);
		button.setAlignment(Pos.CENTER);
		button.getChildren().addAll(btnStart);

		// Displays the GUI
		VBox vbox = new VBox(40);
		vbox.setPadding(new Insets(30, 30, 30, 30));
		vbox.getChildren().addAll(label1, label2, label3, label4, button);

		Scene scene = new Scene(vbox, 450, 375);
		stage.setScene(scene);
		stage.show();
	}

	/*
	* Called on start button press
	*/
	private class startButtonListener implements EventHandler <ActionEvent> 
	{
		private TextField [] textFields;
		private Stage stage;

		/*
		* Class constructor
		*/
		private startButtonListener(Stage stage, TextField [] textFields)
		{
			this.textFields = textFields;
			this.stage = stage;
		}

		/*
		* Collects speeds of the objects
		*/
		@Override
		public void handle(ActionEvent event) 
		{
			double [] objectSpeeds = new double [4];

			for (int index = 0; index < this.textFields.length; index ++)
			{
				try 
				{
					objectSpeeds[index] = Double.parseDouble(this.textFields[index].getText());

					// If speed is negative
					if (objectSpeeds[index] < 0)
					{
						objectSpeeds[index] = 1;
					}
				}
				catch (NumberFormatException exception)
				{
					objectSpeeds[index] = 10;
				}
			}

			startSimulation(objectSpeeds);
		}

		/* Begins the simulation */
		private void startSimulation(double [] objectSpeeds) {
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

			// Left triangle path for circle
			MoveTo cLTMoveTo = new MoveTo();
			cLTMoveTo.setX(250.0f);
			cLTMoveTo.setY(250.0f);
			LineTo cLLowerLine = new LineTo();
			cLLowerLine.setX(100.0f);
			cLLowerLine.setY(400.0f);
			LineTo cLVLine = new LineTo();
			cLVLine.setX(100.0f);
			cLVLine.setY(100.0f);
			LineTo cLUpperLine = new LineTo();
			cLUpperLine.setX(250.0f);
			cLUpperLine.setY(250.0f);
			Path cLTriangle = new Path();
			cLTriangle.setStroke(Color.BLACK);
			cLTriangle.getElements().add(cLTMoveTo);
			cLTriangle.getElements().add(cLLowerLine);
			cLTriangle.getElements().add(cLVLine);
			cLTriangle.getElements().add(cLUpperLine);
			objects.getChildren().add(cLTriangle);

			// Left triangle path for square
			MoveTo sLTMoveTo = new MoveTo();
			sLTMoveTo.setX(250.0f);
			sLTMoveTo.setY(250.0f);
			LineTo sLUpperLine = new LineTo();
			sLUpperLine.setX(100.0f);
			sLUpperLine.setY(100.0f);
			LineTo sLVLine = new LineTo();
			sLVLine.setX(100.0f);
			sLVLine.setY(400.0f);
			LineTo sLLowerLine = new LineTo();
			sLLowerLine.setX(250.0f);
			sLLowerLine.setY(250.0f);
			Path sLTriangle = new Path();
			sLTriangle.setStroke(Color.BLACK);
			sLTriangle.getElements().add(sLTMoveTo);
			sLTriangle.getElements().add(sLUpperLine);
			sLTriangle.getElements().add(sLVLine);
			sLTriangle.getElements().add(sLLowerLine);
			objects.getChildren().add(sLTriangle);

			// Right triangle path for triangle
			MoveTo tRTMoveTo = new MoveTo();
			tRTMoveTo.setX(800.0f);
			tRTMoveTo.setY(250.0f);
			LineTo tRUpperLine = new LineTo();
			tRUpperLine.setX(950.0f);
			tRUpperLine.setY(100.0f);
			LineTo tRVLine = new LineTo();
			tRVLine.setX(950.0f);
			tRVLine.setY(400.0f);
			LineTo tRLowerLine = new LineTo();
			tRLowerLine.setX(800.0f);
			tRLowerLine.setY(250.0f);
			Path tRTriangle = new Path();
			tRTriangle.setStroke(Color.BLACK);
			tRTriangle.getElements().add(tRTMoveTo);
			tRTriangle.getElements().add(tRUpperLine);
			tRTriangle.getElements().add(tRVLine);
			tRTriangle.getElements().add(tRLowerLine);
			objects.getChildren().add(tRTriangle);

			// Right triangle path for rhombus
			MoveTo rRTMoveTo = new MoveTo();
			rRTMoveTo.setX(800.0f);
			rRTMoveTo.setY(250.0f);
			LineTo rRLowerLine = new LineTo();
			rRLowerLine.setX(950.0f);
			rRLowerLine.setY(400.0f);
			LineTo rRVLine = new LineTo();
			rRVLine.setX(950.0f);
			rRVLine.setY(100.0f);
			LineTo rRUpperLine = new LineTo();
			rRUpperLine.setX(800.0f);
			rRUpperLine.setY(250.0f);
			Path rRTriangle = new Path();
			rRTriangle.setStroke(Color.BLACK);
			rRTriangle.getElements().add(rRTMoveTo);
			rRTriangle.getElements().add(rRLowerLine);
			rRTriangle.getElements().add(rRVLine);
			rRTriangle.getElements().add(rRUpperLine);
			objects.getChildren().add(rRTriangle);

			// Displays the path and objects
			Scene animationScene = new Scene(objects, 1050, 500);
			animationStage.setScene(animationScene);
			animationStage.show();

			// Creates message queue objects
			MessageQueue cQueue = new MessageQueue("circle");
			MessageQueue tQueue = new MessageQueue("triangle");
			MessageQueue sQueue = new MessageQueue("square");
			MessageQueue rQueue = new MessageQueue("rhombus");

			try (PrintWriter writer = new PrintWriter(new FileWriter("../logs/Log.txt"))) { 
				// Starts the animations
				PathTransition cLTTrack = new PathTransition(Duration.millis(objectSpeeds[0] * 1000), cLTriangle, circle);
				cLTTrack.setOnFinished(new EventHandler <ActionEvent> () {
					@Override
					public void handle (ActionEvent event) {
						requestCriticalSection(writer, cQueue, tQueue, sQueue, rQueue);
					}
				});
				cLTTrack.play();
				PathTransition tRTTrack = new PathTransition(Duration.millis(objectSpeeds[1] * 1000), tRTriangle, triangle);
				tRTTrack.setOnFinished(new EventHandler <ActionEvent> () {
					@Override
					public void handle (ActionEvent event) {
						requestCriticalSection(writer, tQueue, cQueue, sQueue, rQueue);
					}
				});
				tRTTrack.play();
				PathTransition sLTTrack = new PathTransition(Duration.millis(objectSpeeds[2] * 1000), sLTriangle, square);
				sLTTrack.setOnFinished(new EventHandler <ActionEvent> () {
					@Override
					public void handle (ActionEvent event) {
						requestCriticalSection(writer, sQueue, cQueue, tQueue, rQueue);
					}
				});
				sLTTrack.play();
				PathTransition rRTTrack = new PathTransition(Duration.millis(objectSpeeds[3] * 1000), rRTriangle, rhombus);
				rRTTrack.setOnFinished(new EventHandler <ActionEvent> () {
					@Override
					public void handle (ActionEvent event) {
						requestCriticalSection(writer, rQueue, cQueue, tQueue, sQueue);
					}
				});
				rRTTrack.play();
			}
			catch (IOException exception) {
				System.out.println("No file found");
			}
		}

		/* Requests access to the critical section */
		public void requestCriticalSection(PrintWriter writer, MessageQueue sender, MessageQueue receiver1, MessageQueue receiver2, MessageQueue receiver3) {
			sender.sendMessage(writer, sender, new Timestamp(System.currentTimeMillis()), 0);
			sender.sendMessage(writer, receiver1, new Timestamp(System.currentTimeMillis()), 0);
			sender.sendMessage(writer, receiver2, new Timestamp(System.currentTimeMillis()), 0);
			sender.sendMessage(writer, receiver3, new Timestamp(System.currentTimeMillis()), 0);
		}
	}
}
