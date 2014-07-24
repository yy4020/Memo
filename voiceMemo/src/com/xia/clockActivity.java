package com.xia;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.voicememo.R;
import com.xia.ui.component.MyDateTimePickerDialog;
import com.xia.ui.component.MyDateTimePickerDialog.OnDateTimeSetListener;

import java.util.Date;
public class clockActivity extends Activity {
     TextView text,text2;
//    private TextView text2;
    Spinner setnote_spinner;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settime_layout);
        text=(TextView)findViewById(R.id.show_time_textView);
        text2=(TextView)findViewById(R.id.set_note_textView1);
        //TextView setTime=(TextView)findViewById(R.id.set_time_button);
        
        //LinearLayout l=new LinearLayout(this);
        //Button btn=new Button(this);
       // text=new TextView(this);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-M-dd hh:mm");
        String nowTime=format.format(new Date());
        text.setText(nowTime);
        //l.addView(text);
        //l.addView(btn);
        //btn.setText("时间测试");
        
     
        text.setOnClickListener(new OnClickListener() {
	       
			@Override
			public void onClick(View v) {
				new MyDateTimePickerDialog(clockActivity.this, new OnDateTimeSetListener() {
					
					@Override
					public void onDateTimeSet(int year, int monthOfYear, int dayOfMonth,
							int hour, int minute) {
					  text.setText(year+"-"+monthOfYear+"-"+dayOfMonth+" "+hour+":"+minute+"");	
					}
				}).show();
				
				Log.i("123", "123");
			}
			
		});
        
        setnote_spinner=(Spinner)findViewById(R.id.spinner1); 
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.setnote_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setnote_spinner.setAdapter(adapter);
        setnote_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//text2.setText("S"+position+"ge"+id+"nei"+setnote_spinner.getSelectedItem().toString());
				text2.setText("设置事件");
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// O Auto-generated method stub
				setTitle("meiyou");
			}

    });
    }

}
