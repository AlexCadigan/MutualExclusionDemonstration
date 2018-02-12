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

		/*
		* Begins the simulation
		*/
		private void startSimulation(double [] objectSpeeds)
		{
			// Creates the GUI window
			Stage animationStage = new Stage();
			animationStage.initModality(Modality.APPLICATION_MODAL);
			animationStage.setTitle("Mutual Exclusion Simulation");
			animationStage.setResizable(false);
			// Creates the moving objects (circle, square, triangle, rhombus)
			Group objects = new Group();
			Circle circle = new Circle(10, Color.RED);
			Rectangle square = new Rectangle(15, 15, Color.BLUE);
			objects.getChildren().add(circle);
			objects.getChildren().add(square);
			// Path for circle
			Path circlePath = new Path();
			MoveTo circleMoveTo = new MoveTo();
			circleMoveTo.setX(100.0f);
			circleMoveTo.setY(400.0f);
			LineTo circleLeftVLine = new LineTo();
			circleLeftVLine.setX(100.0f);
			circleLeftVLine.setY(100.0f);
			LineTo circleLeftUpperLine = new LineTo();
			circleLeftUpperLine.setX(250.0f);
			circleLeftUpperLine.setY(250.0f);
			LineTo circleLeftCriticalSection = new LineTo();
			circleLeftCriticalSection.setX(650.0f);
			circleLeftCriticalSection.setY(250.0f);
			LineTo circleRightUpperLine = new LineTo();
			circleRightUpperLine.setX(800.0f);
			circleRightUpperLine.setY(100.0f);
			LineTo circleRightVLine = new LineTo();
			circleRightVLine.setX(800.0f);
			circleRightVLine.setY(400.0f);
			LineTo circleRightLowerLine = new LineTo();
			circleRightLowerLine.setX(650.0f);
			circleRightLowerLine.setY(250.0f);
			LineTo circleRightCriticalSection = new LineTo();
			circleRightCriticalSection.setX(250.0f);
			circleRightCriticalSection.setY(250.0f);
			LineTo circleLeftLowerLine = new LineTo();
			circleLeftLowerLine.setX(100.0f);
			circleLeftLowerLine.setY(400.0f);
			circlePath.getElements().add(circleMoveTo);
			circlePath.getElements().add(circleLeftVLine);
			circlePath.getElements().add(circleLeftUpperLine);
			circlePath.getElements().add(circleLeftCriticalSection);
			circlePath.getElements().add(circleRightUpperLine);
			circlePath.getElements().add(circleRightVLine);
			circlePath.getElements().add(circleRightLowerLine);
			circlePath.getElements().add(circleRightCriticalSection);
			circlePath.getElements().add(circleLeftLowerLine);
			objects.getChildren().add(circlePath);

			// Path for square
			Path squarePath = new Path();
			MoveTo squareMoveTo = new MoveTo();
			squareMoveTo.setX(100.0f);
			squareMoveTo.setY(100.0f);
			LineTo squareLeftUpperLine = new LineTo();
			squareLeftUpperLine.setX(250.0f);
			squareLeftUpperLine.setY(250.0f);
			LineTo squareLeftCriticalSection = new LineTo();
			squareLeftCriticalSection.setX(650.0f);
			squareLeftCriticalSection.setY(250.0f);
			LineTo squareRightUpperLine = new LineTo();
			squareRightUpperLine.setX(800.0f);
			squareRightUpperLine.setY(100.0f);
			LineTo squareRightVLine = new LineTo();
			squareRightVLine.setX(800.0f);
			squareRightVLine.setY(400.0f);
			squarePath.getElements().add(squareMoveTo);
			squarePath.getElements().add(squareLeftUpperLine);
			squarePath.getElements().add(squareLeftCriticalSection);
			squarePath.getElements().add(squareRightUpperLine);
			squarePath.getElements().add(squareRightVLine);
			objects.getChildren().add(squarePath);

			// Displays the path and objects
			Scene animationScene = new Scene(objects, 1000, 500);
			animationStage.setScene(animationScene);
			animationStage.show();
			// Starts the animations
			PathTransition circleTrack = new PathTransition(Duration.millis(objectSpeeds[0] * 1000), circlePath, circle);
			circleTrack.setCycleCount(Animation.INDEFINITE);
			circleTrack.play();
			PathTransition squareTrack = new PathTransition(Duration.millis(objectSpeeds[2] * 1000), squarePath, square);
			squareTrack.setCycleCount(Animation.INDEFINITE);
			squareTrack.play();
		}
	}
}
