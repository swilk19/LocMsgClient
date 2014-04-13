package com.swilk.locmsgclient;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.swilk.locmsgclient.network.LocationCommunicationHandler;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {
	
	public static final String MESSAGE_SERVER = "http://162.243.90.103/messages/mobile";
	
	public static Context activityContext;
	
	LocationCommunicationHandler locationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activityContext = this.getApplicationContext();
		setContentView(R.layout.activity_main);
		Button updateLocButton = (Button)findViewById(R.id.button1);
		updateLocButton.setOnClickListener(new updateLocButtonHandler());
		Button sendMessageButton = (Button)findViewById(R.id.button2);
		sendMessageButton.setOnClickListener(new sendMessageButtonHandler());
		Button getMessagesButton = (Button)findViewById(R.id.button3);
		getMessagesButton.setOnClickListener(new getMessagesButtonHandler());
		this.locationManager = new LocationCommunicationHandler(this, this, this, MESSAGE_SERVER);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
    public void onConnected(Bundle dataBundle) {
        // Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();

    }

	public void onDisconnected() {
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }
	
	@Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        this.locationManager.locationClient.connect();
    }
	
	protected void onStop() {
        // Disconnecting the client invalidates it.
        this.locationManager.locationClient.disconnect();
        super.onStop();
    }

	private class updateLocButtonHandler implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
			TextView locLabel = (TextView)findViewById(R.id.textView1);
			Location curLoc = locationManager.locationClient.getLastLocation();
			String lat = "" + curLoc.getLatitude();
			String longitude = "" + curLoc.getLongitude();
			locLabel.setText("Lat: " + lat + "  Long: " + longitude);
				
			
		}
		
	}
	private class sendMessageButtonHandler implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
			EditText message = (EditText)findViewById(R.id.editText1);
			String messageText = message.getText().toString();
			HashMap<String, String> messageMap = new HashMap<String,String>();
			messageMap.put("msg", messageText);
			locationManager.postLocation(messageMap);
			
				
			
		}
		
	}
	
	private class getMessagesButtonHandler implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
			locationManager.getMessages();
			
				
			
		}
		
	}
	
	

}
