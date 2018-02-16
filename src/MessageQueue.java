import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;

/* Creates an object to store messages that are passed between shapes */
public class MessageQueue {
	private String shape;
	private ArrayList<ArrayList<String>> queue = new ArrayList<ArrayList<String>>();

	/* Constructs an instance of this class */
	public MessageQueue(String shape) {
		this.shape = shape;
	}

	/* Gets the shape that belongs to this message queue */
	public String getShape() {
		return this.shape;
	}

	/* Gets the message queue */
	public ArrayList<ArrayList<String>> getQueue() {
		return this.queue;
	}

	/* Sends a message */
	public void sendMessage(PrintWriter writer, MessageQueue sender, Timestamp timeStamp, int messageNumber) {
		String message;
		if (messageNumber == 0) {
			message = "Requesting access to critical section";
		}
		else {
			message = "";
		}
		if (sender == this) {
			ArrayList<String> tempList = new ArrayList<String>();
			tempList.add(sender.getShape());
			tempList.add(timeStamp.toString());
			tempList.add(message);
			queue.add(tempList);
			sender.print(writer, sender.getShape(), timeStamp.toString(), message);
		}
		else {
			sender.receiveMessage(writer, sender, timeStamp.toString(), messageNumber);
		}
	}

	/* Receives a message */
	public void receiveMessage(PrintWriter writer, MessageQueue receiver, String timeStamp, int messageNumber) {
		String message;
		if (messageNumber == 0) {
			message = "Receiving request to access critical section";
		}
		else {
			message = "";
		}
		ArrayList<ArrayList<String>> queue = receiver.getQueue();
		ArrayList<String> tempList = new ArrayList<String>();
		tempList.add(receiver.getShape());
		tempList.add(timeStamp);
		tempList.add(message);
		queue.add(tempList);
	}

	/* Logs the message in a file */
	public void print(PrintWriter writer, String shape, String timeStamp, String message) {
		System.out.println("Here");
		writer.println("TEST");
		
		// writer.close();
	}

	/* Prints the messages */
	public void printMessages() {
		for (ArrayList<String> arrayList:this.queue) {
			System.out.println(arrayList.get(0) + "\t" + arrayList.get(1) + "\t" + arrayList.get(2));
		}
	}
}
