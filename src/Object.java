/*
MIT License

Copyright (c) 2018 Alex Cadigan, Jacob Naranjo, Tanush Samson

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
import java.sql.Timestamp;
import java.util.ArrayList;
/* Creates an object that acts as a process in the mutual exclusion demonstration */
public class Object {
	private String shape;
	private boolean inCS = false;
	private boolean sentCSRequest = false;
	private boolean needToSendAck = false;
	private ArrayList<Object> sendAckTo = new ArrayList<Object>();
	private int numberOfAcks = 0;
	private ArrayList<ArrayList<String>> messageQueue = new ArrayList<ArrayList<String>>();
	private MessageQueue allMessages;
	/* Initializes instance variables */
	public Object(String shape, MessageQueue messageQueue) {
		this.shape = shape;
		this.allMessages = messageQueue;
	}
	/* Gets the shape of this object */
	public String getShape() {
		return this.shape;
	}
	/* Returns true if the process is in the CS, false otherwise */
	public boolean inCS() {
		return this.inCS;
	}
	/* Modifies if the process is in the CS */
	public void setInCS(boolean value) {
		this.inCS = value;
	}
	/* Modifies if the process has sent a CS request message yet */
	public void setSentCSRequest(boolean value) {
		this.sentCSRequest = value;
	}
	/* Sets the number of acks this process has received */
	public void setNumberOfAcks(int value) {
		this.numberOfAcks = value;
	}
	/* Determines if the object can enter the critical section */
	public void attemptEnterCriticalSection(Object [] processes) {
		if (!this.sentCSRequest) {
			this.sendCSRequest(processes, new Timestamp(System.currentTimeMillis()));
			this.sentCSRequest = true;
		}
	}
	/* Requests to enter the CS */
	private void sendCSRequest(Object [] processes, Timestamp timestamp) {
		for (int index = 0; index < processes.length; index ++) {
			processes[index].receiveCSRequest(this, timestamp.toString(), "requesting access to CS");
			this.allMessages.logMessage(processes[index].getShape(), this.shape, timestamp.toString(), "requesting access to CS");
		}
	}
	/* Receives a request to enter the CS */
	public void receiveCSRequest(Object process, String timestamp, String message) {
		ArrayList<String> newMessage = new ArrayList<String>();
		newMessage.add(process.getShape());
		newMessage.add(timestamp);
		newMessage.add(message);
		this.messageQueue.add(newMessage);
		if (!process.getShape().equals(this.shape)) {
			if (this.inCS) {
				this.needToSendAck = true;
				this.sendAckTo.add(process);
			}
			else {
				this.sendAck(process, new Timestamp(System.currentTimeMillis()));
			}
		}
	}
	/* Sends an acknowledgement request */
	private void sendAck(Object process, Timestamp timestamp) {
		process.receiveAck();
		this.allMessages.logMessage(process.getShape(), this.shape, timestamp.toString(), "acknowledging request to access CS");
	}
	/* Receives an acknowledgement */
	public void receiveAck() {
		this.numberOfAcks ++;
	}
	/* Returns true if this process may enter the CS, false otherwise */
	public boolean enterCS(Object [] processes) {
		for (int index = 0; index < processes.length; index ++) {
			if (processes[index].inCS()) {
				return false;
			}
		}
		// Loop through messagequeue and see if the lowest timestamp value is it's request to enter CS
		ArrayList<String> oldestMessage = this.messageQueue.get(0);
		for (int index = 1; index < this.messageQueue.size(); index ++) {
			if (oldestMessage.get(1).compareTo(this.messageQueue.get(index).get(1)) > 0) {
				oldestMessage = this.messageQueue.get(index);
			}
		}
		if (oldestMessage.get(0).equals(this.shape) && this.numberOfAcks == 3) {
			return true;
		}
		else {
			return false;
		}
	}
	/* Sends messages that this process is out of CS */
	public void leaveCS(Object [] processes) {
		this.inCS = false;
		// Deletes request from local queue
		for (int index = 0; index < this.messageQueue.size(); index ++) {
			if (this.messageQueue.get(index).get(0).equals(this.shape)) {
				this.messageQueue.remove(index);
			}
		}
		// Checks if this process needs to send an ack message
		if (this.needToSendAck) {
			for (int index = 0; index < this.sendAckTo.size(); index ++) {
				this.sendAck(this.sendAckTo.get(index), new Timestamp(System.currentTimeMillis()));
			}
		}
		this.needToSendAck = false;
		this.sendAckTo = new ArrayList<Object>();
		this.sendRelease(processes, new Timestamp(System.currentTimeMillis()));
	}
	/* Sends a release message to other processes */
	private void sendRelease(Object [] processes, Timestamp timestamp) {
		for (int index = 0; index < processes.length; index ++) {
			processes[index].receiveRelease(this.shape);
			this.allMessages.logMessage(processes[index].getShape(), this.shape, timestamp.toString(), "leaving CS");
		}
	}
	/* Receives a release message */
	public void receiveRelease(String shape) {
		for (int index = 0; index < this.messageQueue.size(); index ++) {
			if (this.messageQueue.get(index).get(0).equals(shape)) {
				this.messageQueue.remove(index);
			}
		}
	}
}	
