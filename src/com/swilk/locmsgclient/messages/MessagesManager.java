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

import org.json.JSONObject;

import android.content.Context;

public class MessagesManager implements Serializable {
	
	private static MessagesManager instance;
	
	private HashMap<Integer,Message> messagesMap;
	
	private final String messageFile = "messages.dat";
	
	private Context ctx;
	
	private MessagesManager(Context ctx)
	{
		//Load the file.
		this.messagesMap = this.load();
		instance = this;
	}
	
	public MessagesManager getInstance(Context ctx)
	{
		instance = new MessagesManager(ctx);
		return instance;
	}
	
	public void putMessages(ArrayList<Message> messages)
	{
		for(Message m : messages)
		{
			//Get the id
			int id = m.getId();
			//Put the message, ID into the hashmap
			messagesMap.put(id, m);
		}
		//Save whenever I add more messages
		this.save();
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
				e.printStackTrace();
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
}
