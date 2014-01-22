package com.swilk.locmsgclient;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

public class LocationCommunicationHandler extends AbstractCommunicationHandler
{
	public LocationClient locationClient;
	
	GooglePlayServicesClient.ConnectionCallbacks connCallback;
	
	GooglePlayServicesClient.OnConnectionFailedListener connFailListener;
	
	public LocationCommunicationHandler(Context ctx, GooglePlayServicesClient.ConnectionCallbacks connCallback,
			GooglePlayServicesClient.OnConnectionFailedListener connFailListener,  String host)
	{
		super(host);
		this.connCallback = connCallback;
		this.connFailListener = connFailListener;
		this.locationClient = new LocationClient(ctx, connCallback, connFailListener);
		locationClient.connect();
	}
	
	public void postLocation(Map<String, String> additions)
	{
		Location lastLoc = locationClient.getLastLocation();
		HashMap<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("lat", "" + lastLoc.getLatitude());
		jsonMap.put("long", "" + lastLoc.getLongitude());
		jsonMap.putAll(additions);
		this.postJSON(jsonMap);
	}

}
