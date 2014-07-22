package com.example.voicememo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartupReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//启动一个Service
		Intent serviceIntent = new Intent(context,FloatingServicce.class);  
		context.startService(serviceIntent);
	}

}
