import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
* This class controls the GUI
*/
public class GUI
{
	// Constructors:

	/**
	* GUI constructor
	*/
	public GUI()
	{
		// Initializes GUI window
		JFrame GUIFrame = new JFrame();
		GUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUIFrame.setTitle("Mutual Exclusion Demonstration");
		GUIFrame.setSize(500, 400);
		GUIFrame.setLocationRelativeTo(null);

		// The speed of each ball
		JPanel ballSpeeds = new JPanel();
		ballSpeeds.setLayout(new BoxLayout(ballSpeeds, BoxLayout.Y_AXIS));
		JPanel ball1Speed = new JPanel();
		ball1Speed.add(new JLabel("Ball 1 Speed:"));
		JTextField txtBall1Speed = new JTextField("10", 5);
		ball1Speed.add(txtBall1Speed);
		ballSpeeds.add(ball1Speed);
		JPanel ball2Speed = new JPanel();
		ball2Speed.add(new JLabel("Ball 2 Speed:"));
		JTextField txtBall2Speed = new JTextField("10", 5);
		ball2Speed.add(txtBall2Speed);
		ballSpeeds.add(ball2Speed);
		JPanel ball3Speed = new JPanel();
		ball3Speed.add(new JLabel("Ball 3 Speed:"));
		JTextField txtBall3Speed = new JTextField("10", 5);
		ball3Speed.add(txtBall3Speed);
		ballSpeeds.add(ball3Speed);
		JPanel ball4Speed = new JPanel();
		ball4Speed.add(new JLabel("Ball 4 Speed:"));
		JTextField txtBall4Speed = new JTextField("10", 5);
		ball4Speed.add(txtBall4Speed);
		ballSpeeds.add(ball4Speed);

		// Start simulation button
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				collectUserInput(GUIFrame, new JTextField [] {txtBall1Speed, txtBall2Speed, txtBall3Speed, txtBall4Speed});
			}
		});

		GUIFrame.add(ballSpeeds);
		GUIFrame.add(btnStart, BorderLayout.SOUTH);
		GUIFrame.setVisible(true);
	}

	// Methods:

	/**
	* Gets the input entered by the user
	*/
	private void collectUserInput(JFrame GUI, JTextField [] ballSpeedHolders)
	{
		// Makes sure user input is valid
		try
		{
			double [] ballSpeeds = new double [4];

			for (int index = 0; index < ballSpeedHolders.length; index ++)
			{
				// Checks if input is negative
				if (Double.parseDouble(ballSpeedHolders[index].getText()) < 0)
				{
					ballSpeeds[index] = 1;
				}
				// Checks if input is greater than 100 (the cap)
				else if (Double.parseDouble(ballSpeedHolders[index].getText()) > 100)
				{
					ballSpeeds[index] = 100;
				}
				else
				{
					ballSpeeds[index] = Double.parseDouble(ballSpeedHolders[index].getText());
				}
			}
		}
		catch (NumberFormatException exception)
		{
			JOptionPane.showMessageDialog(null, "Error!  Please enter only numbers for the ball speeds.", "Error Message", 0);
			new GUI();
			GUI.dispose();
		}
	}
}
