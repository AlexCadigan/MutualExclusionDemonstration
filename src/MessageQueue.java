import java.sql.Timestamp;

/* Creates an object to store messages that are passed between shapes */
public class MessageQueue {
	private String shape;

	/* Constructs an instance of this class */
	public MessageQueue(String shape) {
		this.shape = shape;
	}

	/* Gets the shape that belongs to this message queue */
	public String getShape() {
		return this.shape;
	}

	/* Sends a message */
	public void sendMessage(MessageQueue queue, Timestamp timeStamp, int message) {
		
	}
}
