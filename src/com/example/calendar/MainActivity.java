package com.example.calendar;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;

import com.example.calendar.Calendar.LongClickListener;
import com.example.calendar.Calendar.OnCalendarClickListener;

public class MainActivity extends Activity {

	Calendar calendar;
	Date data = new Date();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		calendar = (Calendar) findViewById(R.id.calendar);
		calendar.addMark(10, 10);
		calendar.addMark(12, 12);
		calendar.addMark(13, 13);
		calendar.addMark(14, 14);
		//日期点击的监听
		calendar.OnCalendarClickListener(new OnCalendarClickListener() {
			
			@Override
			public void onCalendarClick(int row, int col, String dateFormat) {
				// TODO Auto-generated method stub
				
				System.out.println(">dateFormat>>>>>>>>>>>>>>>>>>>>"+dateFormat);
			}
		});
		//日期长按的监听
		calendar.LongClickListener(new LongClickListener() {
			
			@Override
			public void onCalendarClick(int row, int col, String dateFormat) {
				// TODO Auto-generated method stub
				System.out.println(">dateFormat>>>>>>>>>>>>>>>>>>>>"+dateFormat);
			}
		});
	}
	
	
}
