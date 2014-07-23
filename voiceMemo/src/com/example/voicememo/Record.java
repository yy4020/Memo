package com.example.voicememo;

import java.io.File;
import java.io.FileInputStream;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class Record {

	private static Record sInstance = new Record();

	private AudioRecord mAudioRecord = null;
	private MediaRecorder mMediaRecord = null;
	private int LOCCAL_RECORD = 1;
	private int CALL_RECORD = 0;

	private Record() {
	}

	public static Record getsInstance() {
		return sInstance;
	}

	public void beginAudioRecording(int paramInt) {
		int audioSource = 0;
		int sampleRateInHz = 0;
		int channelConfig = 0;
		int audioFormat = 0;
		int bufferSizeInBytes = 0;

		if (paramInt == LOCCAL_RECORD) {
			audioSource = MediaRecorder.AudioSource.MIC;
		} else if (paramInt == CALL_RECORD) {
			audioSource = MediaRecorder.AudioSource.DEFAULT;
		}
		// 录制频率，单位Hz。 44100Hz是当前唯一能保证在所有设备上工作的采样率
		sampleRateInHz = 44100;
		// 录制通道
		channelConfig = AudioFormat.CHANNEL_IN_STEREO;
		audioFormat = AudioFormat.ENCODING_PCM_16BIT;
		bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,
				channelConfig, audioFormat);

		try {
			AudioRecord localAudioRecord = new AudioRecord(audioSource,
					sampleRateInHz, channelConfig, audioFormat,
					bufferSizeInBytes);
			this.mAudioRecord = localAudioRecord;
			this.mAudioRecord.startRecording();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	// 获取sdcard路径
	File sdcardDir = Environment.getExternalStorageDirectory()
			.getAbsoluteFile();
	String path = sdcardDir + "/"+System.currentTimeMillis()+ ".amr";

	public void beginMediaRecording(int paramInt) {

		int audioSource = 0;
		int audioFormat = 0;
		int audioEncoder = 0;

		if (paramInt == LOCCAL_RECORD) {
			audioSource = MediaRecorder.AudioSource.MIC;
			audioFormat = MediaRecorder.OutputFormat.THREE_GPP;
			audioEncoder = MediaRecorder.AudioEncoder.AMR_NB;
		} else if (paramInt == CALL_RECORD) {
			audioSource = MediaRecorder.AudioSource.DEFAULT;
			audioFormat = MediaRecorder.OutputFormat.DEFAULT;
			audioEncoder = MediaRecorder.AudioEncoder.DEFAULT;
		}

		mMediaRecord = new MediaRecorder();
		// 设置麦克风
		mMediaRecord.setAudioSource(audioSource);
		// 输出文件格式
		mMediaRecord.setOutputFormat(audioFormat);
		// 音频文件编码
		mMediaRecord.setAudioEncoder(audioEncoder);
		// mMediaRecorder.setVideoSize(176, 144);

		mMediaRecord.setOutputFile(path);

		try {
			mMediaRecord.prepare();
			if (mMediaRecord != null)
				mMediaRecord.start();

		} catch (Exception e) {
			Log.e("AndroidRuntime", "", e);

		}
	}

	public void endAudioRecording() {
		try {
			if (this.mAudioRecord != null) {
				this.mAudioRecord.release();
				this.mAudioRecord = null;
			}

			return;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	public void endMediaRecording() {
		try {
			if (this.mMediaRecord != null) {
				this.mMediaRecord.release();
				this.mMediaRecord = null;
			}

			return;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}
	
	public String fileLen(){
		String fileleng="0 Bytes";
		try {
			File df=new File(path);
			FileInputStream fis=new FileInputStream(df);
			fileleng=fis.available()+" Bytes";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return fileleng;
		
	}

}
