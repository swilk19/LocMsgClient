package com.swilk.locmsgclient;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.swilk.locmsgclient.messages.MessagesManager;
import com.swilk.locmsgclient.messages.Message;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        
        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        Intent i = getIntent();
        LatLng myLocation = new LatLng(i.getDoubleExtra("lat", 0), i.getDoubleExtra("long", 0));
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 13));
        
        MessagesManager messageManager = MessagesManager.getInstance();
        placeAllMessages(map, messageManager);
        
    }
    
    private void placeMarker(GoogleMap map, Message message)
    {
    	map.addMarker(new MarkerOptions()
    		.title("New message!")
    		.snippet(message.getMsg())
    		.position(new LatLng(message.getLat(), message.getLng())));
    }
    
    private void placeAllMessages(GoogleMap map, MessagesManager messagesManager)
    {
    	ArrayList<Message> messages = messagesManager.getAllMessages();
    	for(Message m : messages)
    	{
    		placeMarker(map, m);
    	}
    }
}
