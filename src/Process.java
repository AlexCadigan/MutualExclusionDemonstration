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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
/* Simulates a process in a distributed system */
public class Process {
	private String shape;
	private boolean inCS = false;
	private int trackNumber = 0;
	private int numberOfAcks = 0;
	private ArrayList<Process> sendAckTo = new ArrayList<Process>();
	private PathTransition [] track;
	private Process [] processes;
	private ArrayList<ArrayList<String>> messageQueue = new ArrayList<ArrayList<String>>();
	/* Initialize instance variables */
	public Process(String shape, PathTransition [] track) {
		this.shape = shape;
		this.track = track;
	}
	/* Stores the other processes so they can communicate together */
	public void storeProcesses(Process [] processes) {
		this.processes = processes;
	}
	/* Returns the shape of this process */
	public String getShape() {
		return this.shape;
	}
	/* Begins playing the animation */
	public void start() {
		this.track[0].setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				requestCS();
				if (canEnterCS()) {
					enterCS();
					track[1].play();
				}
			}
		});
		this.track[1].setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				leaveCS();
				trackNumber = 2;
				track[2].play();
			}
		});
		this.track[2].setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				requestCS();
				if (canEnterCS()) {
					enterCS();
					track[3].play();
				}
			}
		});
		this.track[3].setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				leaveCS();
				trackNumber = 0;
				track[0].play();
			}
		});
		this.track[0].play();
	}
	/* Requests access to the critical section */
	private void requestCS() {
		for (int index = 0; index < this.processes.length; index ++) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			this.logMessage(processes[index].getShape(), this.shape, timestamp.toString(), "requesting access to CS");
			processes[index].receiveCSRequest(this, timestamp.toString(), "ReqCS");
		}
	}
	/* Receives a request to enter the critical section */
	public void receiveCSRequest(Process sender, String timestamp, String message) {
		// Enters the message in queue
		ArrayList<String> newMessage = new ArrayList<String>();
		newMessage.add(sender.getShape());
		newMessage.add(timestamp);
		newMessage.add(message);
		this.messageQueue.add(newMessage);
		if (!sender.getShape().equals(this.shape)) {
			if (this.inCS) {
				this.sendAckTo.add(sender);
			}
			else {
				this.sendAck(sender);
			}
		}
	}
	/* Sends an acknowledgement message */
	private void sendAck(Process receiver) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.logMessage(receiver.getShape(), this.shape, timestamp.toString(), "acknowledging CS request");
		receiver.receiveAck();
	}
	/* Receives an acknowledgement message */
	public void receiveAck() {
		this.numberOfAcks ++;
	}
	/* Checks if this process is able to enter the CS */
	private boolean canEnterCS() {
		ArrayList<String> oldestMessage = this.getOldestMessage();
		if (oldestMessage != null) {
			if (oldestMessage.get(0).equals(this.shape) && this.numberOfAcks == 3) {
				return true;
			}
		}
		return false;
	}
	/* Gets the oldest message in the message queue */
	private ArrayList<String> getOldestMessage() {
		if (this.messageQueue.size() == 0) {
			return null;
		}
		else {
			ArrayList<String> oldest = this.messageQueue.get(0);
			for (int index = 1; index < this.messageQueue.size(); index ++) {
				if (oldest.get(1).compareTo(this.messageQueue.get(index).get(1)) > 0) {
					oldest = this.messageQueue.get(index);
				}
			}
			return oldest;
		}
	}
	/* Modifies instance variables to reflect that this process is in the critical section */
	private void enterCS() {
		this.inCS = true;
		this.numberOfAcks = 0;
	}
	/* Sends messages that this process has left the critical section */
	private void leaveCS() {
		this.inCS = false;
		// Sends acknowledgement messages
		for (int index = 0; index < this.sendAckTo.size(); index ++) {
			this.sendAck(this.sendAckTo.get(index));
		}		
		this.sendAckTo = new ArrayList<Process>();
		// Sends release message
		this.sendRelease();
	}
	/* Sends a release message to the other processes */
	private void sendRelease() {
		for (int index = 0; index < this.processes.length; index ++) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			this.logMessage(this.processes[index].getShape(), this.shape, timestamp.toString(), "leaving CS");
			this.processes[index].receiveRelease(this.shape);
		}
	}
	/* Receives a release message */
	public void receiveRelease(String shape) {
		for (int index = 0; index < this.messageQueue.size(); index ++) {
			if (this.messageQueue.get(index).get(0).equals(shape)) {
				this.messageQueue.remove(index);
			}
		}
		// Checks if process can now enter CS
		if (this.trackNumber == 0) {
			if (this.canEnterCS()) {
				this.enterCS();
				this.track[1].play();
			}
		}
		else {
			if (this.canEnterCS()) {
				this.enterCS();
				this.track[3].play();
			}
		}
	}
	/* Logs a message to a file */
	private void logMessage(String receiver, String sender, String timestamp, String message) {
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("../logs/Log.txt", true)))) {
			if (sender.equals("triangle")) {
				writer.println("Receiver: " + receiver + "\tSender: " + sender + "\tTimestamp: " + timestamp + "\tMessage: " + message);
			}
			else {
				writer.println("Receiver: " + receiver + "\tSender: " + sender + "\t\tTimestamp: " + timestamp + "\tMessage: " + message);
			}
		    writer.close();
		} 
		catch (Exception exception) {
			System.out.println("\nError logging message to file!\n");
		}
	}
}
