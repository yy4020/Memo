package com.example.voicememo;

import cn.qihoo.calendar.audio.Record;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class recordActivity extends Activity {

	private boolean isBegan=true;
	private String LOG_TAG="recordActivity";
	Button recording;
	Button clock;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_activity);
		
		ImageView loading=(ImageView)findViewById(R.id.loading);
		Animation rorate=AnimationUtils.loadAnimation(this, R.anim.rorate); 
		loading.startAnimation(rorate);
		

		recording = (Button) findViewById(R.id.record_voice);
		recording.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				// 本地录音1
				int LOCAL_RECORD = 1;
				int CALL_RECORD = 0;
//
//				if (v.getId() == R.id.LocRecord1) {
//					Button LocRecord1 = (Button) findViewById(R.id.LocRecord1);
					Record localRecord = Record.getsInstance();
					if (isBegan) {
						Log.d(LOG_TAG,"录音...");
						Toast.makeText(recordActivity.this, "开始录音", Toast.LENGTH_SHORT).show();
						localRecord.beginMediaRecording(LOCAL_RECORD);
						localRecord.insertDB(getApplicationContext());
						recording.setText("停止");
//						btnClickable(false);
//						record.setEnabled(true);
						isBegan = false;
					} else {
						localRecord.endMediaRecording();
						recording.setText("录音");
////						btnClickable(true);
						isBegan = true;
						Log.d(LOG_TAG,"录音完成,录音文件大小为：" + localRecord.fileLen());
						Toast.makeText(recordActivity.this, "录音完成", Toast.LENGTH_SHORT).show();
					}
				
			}
		});

		Button cancle = (Button) findViewById(R.id.cancle);
		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		clock=(Button) findViewById(R.id.set_clock);
		clock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

}
