package com.swilk.locmsgclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public abstract class AbstractCommunicationHandler {
	
	String host;
	
	public AbstractCommunicationHandler(String host) 
	{
		this.host = host;
	}
	
	@SuppressWarnings("unchecked")
	public void postJSON(Map<String, String> toPost) 
	{
		//Convert Map to JSON
		JSONObject jsonData = new JSONObject(toPost);
		String[] params = {this.host, jsonData.toString()};
		ArrayList<String> arguments = new ArrayList<String>(Arrays.asList(params));
		SendPost newPost = (SendPost) new SendPost();
		newPost.execute(arguments);
	}
	
	private class SendPost extends AsyncTask<ArrayList<String>, String, String>
	{

		
		protected String doInBackground(ArrayList<String>... params) {
			//Get Host ^ JSON list
			ArrayList<String> arguments = params[0];
			//Get Host Argument
			String host = arguments.get(0);
			//Create POST
			HttpPost httpPost = new HttpPost(host);
			//Get JSON Argument
			String JSONString = arguments.get(1);
			try {
				JSONObject newJSON = new JSONObject(JSONString);
				System.out.println("JSON: " + newJSON.toString());
				StringEntity entity = new StringEntity(newJSON.toString());
				httpPost.setEntity(entity);
				HttpClient httpClient = new DefaultHttpClient();
				//Post the JSON.
				HttpResponse serverResponse = httpClient.execute(httpPost);
				return serverResponse.toString();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				Log.w("JSONError", "Error Parsing JSON.", e);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Couldn't post for some reason
			return "Error posting to server";
		}
		
	}
	
}
