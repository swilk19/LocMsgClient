package com.swilk.locmsgclient.messages;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@TargetApi(19)
public class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private double lat, lng;
	
	private String created_at, updated_at, msg;
	
	public static ArrayList<Message> decodeMessage(String jsonData) throws JSONException
	{
		//Convert to JSON
		//JSONObject convertedJSON = new JSONObject(jsonData);
		
		//Convert to JSONArray
		JSONArray messagesArray = new JSONArray(jsonData);
		
		//Iterate over the objects, create new messages
		ArrayList<Message> newMessages = new ArrayList<Message>();
		for(int i = 0; i<messagesArray.length(); i++)
		{
			JSONObject curMessage = messagesArray.getJSONObject(i);
			int id = curMessage.getInt("id");
			double lat = curMessage.getDouble("lat");
			double lng = curMessage.getDouble("long");
			String created_at = curMessage.getString("created_at");
			String updated_at = curMessage.getString("updated_at");
			String msg = curMessage.getString("msg");
			Message newMessage = new Message(id, lat, lng, created_at, updated_at, msg);
			System.out.println(newMessage);
			newMessages.add(newMessage);
		}
		return newMessages;
		
	}

	private Message(int id, double lat, double lng, String created_at, String updated_at, String msg)
	{
		this.id = id;
		this.lat = lat;
		this.lng = lng;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.msg = msg;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String toString()
	{
		return "MESSAGE: " + this.msg + " && LAT = " + this.lat + " && LONG = " + this.lng;
	}
	

	
}
