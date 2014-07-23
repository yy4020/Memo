package cn.qihoo.calendar.audio;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import android.widget.Button;

public class PlayAudio extends Activity {

	private static final String LOG_TAG = "PlayAudio";
	private static String mFileName = null;
	private PlayButton mPlayButton = null;
	private MediaPlayer mPlayer = null;

	public PlayAudio() {
		String sdcardDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		String fileDir = sdcardDir + "/voiceMemo/recordFile";
		mFileName = fileDir + "/" + System.currentTimeMillis() + ".amr";
	}

	private void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
	}

	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(mFileName);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}

	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}

	class PlayButton extends Button {
		boolean mStartPlaying = true;
		OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {
				onPlay(mStartPlaying);
				if (mStartPlaying) {
					setText("Stop playing");
				} else {
					setText("Start playing");
				}
				mStartPlaying = !mStartPlaying;
			}
		};

		public PlayButton(Context ctx) {
			super(ctx);
			setText("Start playing");
			setOnClickListener(clicker);
		}
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
