package com.example.voicememo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class recordActivity extends Activity {

	private boolean isBegan=true;
	private String LOG_TAG="recordActivity";
	Button record;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_activity);
		record = (Button) findViewById(R.id.record_voice);
		record.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent intent = new
				// Intent(recordActivity.this,AudioRecord.class);
				// startActivity(intent);
				// 本地录音1
				int LOCAL_RECORD = 1;
				int CALL_RECORD = 0;

//				if (v.getId() == R.id.LocRecord1) {
//					Button LocRecord1 = (Button) findViewById(R.id.LocRecord1);
					Record localRecord = Record.getsInstance();
					if (isBegan) {
						Log.d(LOG_TAG,"录音...");
						localRecord.beginMediaRecording(CALL_RECORD);
						record.setText("停止");
//						btnClickable(false);
//						record.setEnabled(true);
						isBegan = false;
					} else {
						localRecord.endMediaRecording();
						record.setText("录音");
//						btnClickable(true);
						isBegan = true;
						Log.d(LOG_TAG,"录音完成,录音文件大小为：" + localRecord.fileLen());
					}

//				}
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

		Button clock = (Button) findViewById(R.id.set_clock);
		clock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

}
