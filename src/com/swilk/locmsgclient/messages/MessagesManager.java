package com.swilk.locmsgclient.messages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.swilk.locmsgclient.MainActivity;

import android.content.Context;

public class MessagesManager implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static MessagesManager instance;
	
	private HashMap<Integer,Message> messagesMap;
	
	private final String messageFile = "message_map.dat";
	
	private Context ctx;
	
	private MessagesManager(Context ctx)
	{
		//Load the file.
		this.ctx = ctx;
		HashMap<Integer,Message> loadedData = this.load();
		if(loadedData == null) 
		{
			messagesMap = new HashMap<Integer, Message>();
		}
		else messagesMap = loadedData;
		instance = this;
		this.save();
	}
	
	public static MessagesManager getInstance()
	{
		instance = new MessagesManager(MainActivity.activityContext);
		return instance;
	}
	
	public synchronized void putMessages(ArrayList<Message> messages)
	{
		for(Message m : messages)
		{
			//Get the id
			int id = m.getId();
			//check if it exists
			if(messagesMap.containsKey(id))
			{
				System.out.println("Skipping message, already exists: " + m);
				continue;
			}
			//Put the message, ID into the hashmap if it doesnt exist
			messagesMap.put(id, m);
		}
		//Save whenever I add more messages
		this.save();
	}
	
	public synchronized ArrayList<Message> getAllMessages()
	{
		return new ArrayList<Message>(this.messagesMap.values());
	}
	
	private HashMap<Integer, Message> load()
	{
		File file = new File(this.ctx.getFilesDir(), messageFile);
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			ObjectInputStream objectReader = new ObjectInputStream(fis);
			Object mapData = objectReader.readObject();
			return (HashMap<Integer, Message>)mapData;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//No saved data
			return new HashMap<Integer, Message>(); 
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
			
	
	private void save()
	{
		File file = new File(this.ctx.getFilesDir(), messageFile);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream objectWriter = new ObjectOutputStream(fos);
			objectWriter.writeObject(this.messagesMap);
			objectWriter.flush();
			objectWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class MessageWorker implements Runnable 
	{
		
		String serverResponse;
		
		public MessageWorker(String serverResponse)
		{
			this.serverResponse = serverResponse;
		}

		@Override
		public void run() {
			//Decode server response
			System.out.println("Hey! Its runnable!");
			try {
				ArrayList<Message> messages = Message.decodeMessage(serverResponse);
				MessagesManager messagesManager = MessagesManager.getInstance();
				messagesManager.putMessages(messages);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}
