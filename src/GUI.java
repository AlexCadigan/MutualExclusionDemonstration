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
import javafx.scene.shape.Circle;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javafx.util.Duration;

/*
* Contains code to construct a GUI that allows users to initiate the simulation
*/
public class GUI extends Application
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

			startSimulation();
		}

		/*
		* Begins the simulation
		*/
		private void startSimulation()
		{
			Stage animationStage = new Stage();
			animationStage.initModality(Modality.APPLICATION_MODAL);
			animationStage.setTitle("Mutual Exclusion Simulation");
			animationStage.setResizable(false);

			Group objects = new Group();
			Circle circle = new Circle(10, Color.RED);
			objects.getChildren().add(circle);

			Path path = new Path();
			path.getElements().addAll(new MoveTo(50, 50), new HLineTo(350));
			path.setFill(null);
			path.setStroke(Color.BLACK);
			path.setStrokeWidth(2);

			objects.getChildren().add(path);

			Scene animationScene = new Scene(objects, 400, 100);
			animationStage.setScene(animationScene);
			animationStage.show();

			PathTransition pathTrack = new PathTransition(Duration.millis(4000), path, circle);
			pathTrack.setCycleCount(Animation.INDEFINITE);
			pathTrack.setAutoReverse(true);
			pathTrack.play();
		}
	}
}
