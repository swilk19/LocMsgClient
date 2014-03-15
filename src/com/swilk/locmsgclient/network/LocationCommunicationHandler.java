package com.swilk.locmsgclient.network;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.swilk.locmsgclient.network.AbstractCommunicationHandler.Actions;
import com.swilk.locmsgclient.network.AbstractCommunicationHandler.SendMessage;

public class LocationCommunicationHandler extends AbstractCommunicationHandler
{
	public LocationClient locationClient;
	
	GooglePlayServicesClient.ConnectionCallbacks connCallback;
	
	GooglePlayServicesClient.OnConnectionFailedListener connFailListener;
	
	private String radius;
	
	public LocationCommunicationHandler(Context ctx, GooglePlayServicesClient.ConnectionCallbacks connCallback,
			GooglePlayServicesClient.OnConnectionFailedListener connFailListener,  String host)
	{
		super(host);
		this.connCallback = connCallback;
		this.connFailListener = connFailListener;
		this.locationClient = new LocationClient(ctx, connCallback, connFailListener);
		locationClient.connect();
		this.radius = "10";
	}
	
	public void setRadius(String newRadius)
	{
		this.radius = newRadius;
	}
	
	public void postLocation(Map<String, String> additions)
	{
		this.host = "http://ec2-54-186-48-0.us-west-2.compute.amazonaws.com/messages/mobile";
		Location lastLoc = locationClient.getLastLocation();
		HashMap<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("lat", "" + lastLoc.getLatitude());
		jsonMap.put("long", "" + lastLoc.getLongitude());
		jsonMap.putAll(additions);
		this.postJSON(jsonMap);
	}
	
	public void getMessages()
	{
		this.host = "http://ec2-54-186-48-0.us-west-2.compute.amazonaws.com/messages/find";
		HashMap<String, String> getParams = new HashMap<String, String>();
		Location lastLoc = locationClient.getLastLocation();
		getParams.put("r",radius);
		getParams.put("lat", "" + lastLoc.getLatitude());
		getParams.put("long", "" + lastLoc.getLongitude());
		this.sendGet(getParams);
	}
	

}
