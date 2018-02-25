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
import java.util.ArrayList;
/* Stores all of the messages from the simulation */
public class MessageQueue {
	private ArrayList<ArrayList<String>> queue = new ArrayList<ArrayList<String>>();
	/* Gets the message queue */
	public ArrayList<ArrayList<String>> getQueue() {
		return this.queue;
	}
	/* Logs the given message in the queue */
	public void logMessage(String receiver, String sender, String timestamp, String message) {
		ArrayList<String> newMessage = new ArrayList<String>();
		newMessage.add(receiver);
		newMessage.add(sender);
		newMessage.add(timestamp);
		newMessage.add(message);
		this.queue.add(newMessage);
	}
}
