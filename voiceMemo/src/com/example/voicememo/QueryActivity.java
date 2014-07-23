package com.example.voicememo;

import java.io.IOException;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import cn.qihoo.calendar.utils.AudioAdapter;
import cn.qihoo.calender.sqlite.AudioHelper;

public class QueryActivity extends ListActivity {

	private static final String LOG_TAG = "QueryActivity";
	public static String mFileName = null;
	private Button mPlayButton = null;
	private static MediaPlayer mPlayer = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(LOG_TAG, "faill");
		setContentView(R.layout.detail);

		Log.e(LOG_TAG, "faill");

		final AudioHelper helpter = new AudioHelper(this);
		Cursor cur = helpter.query4Month("2014-07");

		AudioAdapter adapter = new AudioAdapter(this, cur);

		// String[] from = { "_id", "date", "url", "remind" };
		// int[] to = { R.id.text0, R.id.text1, R.id.text2, R.id.text3 };
		//
		// SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
		// R.layout.row, c, from, to);

		ListView listView = getListView();
		listView.setAdapter(adapter);

		Button button = (Button) findViewById(R.id.Button_play);
		mPlayButton = button;

		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			// 点击播放
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final int temp = (int) arg3;
				Cursor cur = helpter.query4Id(temp);
				int urlcolum = cur.getColumnIndex("url");
				String url = cur.getString(urlcolum);
				mFileName = url;

			}
			// // 点击删除
			// @Override
			// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			// long arg3) {
			// final long temp = arg3;
			// builder.setMessage("真的要删除该记录吗？").setPositiveButton("是",
			// new DialogInterface.OnClickListener() {
			// public void onClick(DialogInterface dialog,
			// int which) {
			// helpter.del((int)temp);
			// Cursor c = helpter.query();
			// String[] from = { "_id", "date", "url", "remind" };
			// int[] to = { R.id.text0, R.id.text1, R.id.text2, R.id.text3 };
			// SimpleCursorAdapter adapter = new
			// SimpleCursorAdapter(getApplicationContext(),
			// R.layout.row, c, from, to);
			// ListView listView = getListView();
			// listView.setAdapter(adapter);
			// }
			// }).setNegativeButton("否",
			// new DialogInterface.OnClickListener() {
			// public void onClick(DialogInterface dialog,
			// int which) {
			//
			// }
			// });
			// AlertDialog ad = builder.create();
			// ad.show();
			// }
		});
		helpter.close();
	}

	class PlayButton extends Button {
		boolean mStartPlaying = true;
		OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {
				onPlay(mStartPlaying);
				if (mStartPlaying) {
					setText("停止");
				} else {
					setText("播放");
				}
				mStartPlaying = !mStartPlaying;
			}
		};

		public PlayButton(Context ctx) {
			super(ctx);
			setText("播放");
			setOnClickListener(clicker);
		}
	}

	public static void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
	}

	private static void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(mFileName);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}

	public static void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}

	@Override
	public void onPause() {
		super.onPause();

		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}

}