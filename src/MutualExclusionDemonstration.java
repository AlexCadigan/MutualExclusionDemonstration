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
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
/* Runs a simulation that demonstrates mutual exclusion in distributed systems */
public class MutualExclusionDemonstration extends Application {
	/* Begins the simulation */
	public static void main(String [] args) {
		Application.launch(args);
	}
	/* Creates a GUI window for users to enter simulation settings */
	@Override
	public void start(Stage stage) {
		stage.setTitle("Mutual Exclusion Demonstration");
		// Object speed labels
		HBox label0 = new HBox(10);
		label0.setAlignment(Pos.CENTER);
		label0.getChildren().addAll(new Label("Note: objects move faster with lower speeds!"));
		TextField txtCircle = new TextField("10");
		HBox label1 = new HBox(10);
		label1.setAlignment(Pos.CENTER);
		label1.getChildren().addAll(new Label("Circle Speed:"), txtCircle);
		TextField txtSquare = new TextField("10");
		HBox label2 = new HBox(10);
		label2.setAlignment(Pos.CENTER);
		label2.getChildren().addAll(new Label("Square Speed:"), txtSquare);
		TextField txtTriangle = new TextField("10");
		HBox label3 = new HBox(10);
		label3.setAlignment(Pos.CENTER);
		label3.getChildren().addAll(new Label("Triangle Speed:"), txtTriangle);
		TextField txtRhombus = new TextField("10");
		HBox label4 = new HBox(10);
		label4.setAlignment(Pos.CENTER);
		label4.getChildren().addAll(new Label("Rhombus Speed:"), txtRhombus);
		// Start button
		Button btnStart = new Button("Start Simulation");
		btnStart.setOnAction(new Simulation(new TextField [] {txtCircle, txtSquare, txtTriangle, txtRhombus}));
		HBox button = new HBox(10);
		button.setAlignment(Pos.CENTER);
		button.getChildren().addAll(btnStart);
		// Displays the GUI
		VBox vbox = new VBox(40);
		vbox.setPadding(new Insets(30, 30, 30, 30));
		vbox.getChildren().addAll(label0, label1, label2, label3, label4, button);
		stage.setScene(new Scene(vbox, 450, 425));
		stage.show();
	}
}
