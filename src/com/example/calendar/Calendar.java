/**
 * 
 * @Title: 
 * @Description: TODO()
 * @throws
 */
package com.example.calendar;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;



/**
 * @author hecl
 *
 */
public class Calendar extends ViewFlipper {
	  

	  public static final int COLOR_RED = Color.parseColor("#FF4167"); // ��ɫ
	  public static final int COLOR_WIT= Color.parseColor("#FFFFFF"); // ��ɫ
//	  public static final int COLOR_BU = Color.parseColor("#00080B"); // ��ɫ
	  
	  private int ROWS_TOTAL = 6; // ����������
	  private int COLS_TOTAL = 7; // ����������
	  private String[][] dates = new String[6][7]; // ��ǰ��������
	  private String[] weekday = new String[] {"��", "һ", "��", "��", "��", "��", "��"}; // ���ڱ���
	  
	  private int calendarYear; // �������
	  private int calendarMonth; // �����·�
	  private Date thisday = new Date(); // ����
	  private Date calendarday; // ��������µ�һ��(1��)
	  
	  private LinearLayout currentCalendar; // ��������
	  private OnCalendarClickListener onCalendarClickListener; // ����ѡ�����
	  private LongClickListener onLongClickListener; // ����ѡ�����
	  
	  private Map<Integer, Integer> marksMap = new HashMap<Integer, Integer>(); // ����ĳ�����ӱ���ע(Integer
	  
	  
	  /**
		 * @param context
		 * @param attrs
		 */
		public Calendar(Context context, AttributeSet attrs) {
			super(context, attrs);
			// TODO Auto-generated constructor stub
			init();
		}


	/**
	 * 
	 */
	@SuppressWarnings("deprecation")
	private void init() {
		// TODO Auto-generated method stub
		currentCalendar = new LinearLayout(getContext());
	    currentCalendar.setOrientation(LinearLayout.VERTICAL);
	    currentCalendar.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
	    // ����ViewFlipper
	    addView(currentCalendar);
	 // �����������
	    drawFrame(currentCalendar);
	 // ���������ϵ�����(1��)
	    calendarYear = thisday.getYear() + 1900;
	    calendarMonth = thisday.getMonth();
	    calendarday = new Date(calendarYear - 1900, calendarMonth, 1);
	    // ���չʾ����
	    setCalendarDate();
	}
	


	/**
	 * @Title��
	 * @author��
	 * @annotation����������
	 */
	@SuppressLint("ResourceAsColor")
	private void drawFrame(LinearLayout oneCalendar) {
	    // �����ĩ���Բ���
	    LinearLayout title = new LinearLayout(getContext());
	    title.setBackgroundColor(R.color.home_red);
	    title.setOrientation(LinearLayout.HORIZONTAL);
	    LinearLayout.LayoutParams layout =
	        new LinearLayout.LayoutParams(MarginLayoutParams.MATCH_PARENT,
	            MarginLayoutParams.WRAP_CONTENT, 0.5f);
	    Resources res = getResources();
	    // layout.setMargins(0, 0, 0, (int) (tb * 1.2));
	    title.setLayoutParams(layout);
	    oneCalendar.addView(title);

	    // �����ĩTextView
	    for (int i = 0; i < COLS_TOTAL; i++) {
	      TextView view = new TextView(getContext());
	      view.setGravity(Gravity.CENTER);
	      view.setPadding(0, 10, 0, 10);
	      view.setText(weekday[i]);
	      view.setTextColor(Color.WHITE);
	      view.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 1));
	      title.addView(view);
	    }

	    // ������ڲ���
	    LinearLayout content = new LinearLayout(getContext());
	    content.setOrientation(LinearLayout.VERTICAL);
	    content.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 7f));
	    oneCalendar.addView(content);

	    // �������TextView
	    for (int i = 0; i < ROWS_TOTAL; i++) {
	      LinearLayout row = new LinearLayout(getContext());
	      row.setOrientation(LinearLayout.HORIZONTAL);
	      row.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
	      content.addView(row);
	      // ���������ϵ���
	      for (int j = 0; j < COLS_TOTAL; j++) {
	        RelativeLayout col = new RelativeLayout(getContext());
	        col.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
	        col.setBackgroundResource(R.color.home_w);
	        // col.setBackgroundResource(R.drawable.sign_dialog_day_bg);
	        col.setClickable(false);
	        row.addView(col);
	        // ��ÿһ�����Ӽ��ϼ���
	        col.setOnClickListener(new OnClickListener() {
	          @Override
	          public void onClick(View v) {
	            ViewGroup parent = (ViewGroup) v.getParent();
	            int row = 0, col = 0;

	            // ��ȡ������
	            for (int i = 0; i < parent.getChildCount(); i++) {
	              if (v.equals(parent.getChildAt(i))) {
	                col = i;
	                break;
	              }
	            }
	            // ��ȡ������
	            ViewGroup pparent = (ViewGroup) parent.getParent();
	            for (int i = 0; i < pparent.getChildCount(); i++) {
	              if (parent.equals(pparent.getChildAt(i))) {
	                row = i;
	                break;
	              }
	            }
	            if (onCalendarClickListener != null) {
	              onCalendarClickListener.onCalendarClick(row, col, dates[row][col]);
	            }
	          }
	        });
	        col.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					ViewGroup parent = (ViewGroup) v.getParent();
		            int row = 0, col = 0;

		            // ��ȡ������
		            for (int i = 0; i < parent.getChildCount(); i++) {
		              if (v.equals(parent.getChildAt(i))) {
		                col = i;
		                break;
		              }
		            }
		            // ��ȡ������
		            ViewGroup pparent = (ViewGroup) parent.getParent();
		            for (int i = 0; i < pparent.getChildCount(); i++) {
		              if (parent.equals(pparent.getChildAt(i))) {
		                row = i;
		                break;
		              }
		            }
		            if (onLongClickListener != null) {
		            	onLongClickListener.onCalendarClick(row, col, dates[row][col]);
		            }
					return false;
				}
			});
	      }
	    }
	  }
	
	
	
	
	/**
	   * �������(�������ڡ���ǡ�������)
	   */
	  @SuppressWarnings("deprecation")
	@SuppressLint("ResourceAsColor")
	private void setCalendarDate() {
	    // �������������ӻ�ȡ��һ�������ڼ�
	    int weekday = calendarday.getDay();
	    // ÿ���µ�һ��
	    int firstDay = 1;
	    // ÿ�����м��,����ѭ�����Զ�++
	    int day = firstDay;
	    // ÿ���µ����һ��
	    @SuppressWarnings("deprecation")
		int lastDay = getDateNum(calendarday.getYear(), calendarday.getMonth());

	    // ���ÿһ���ո�
	    for (int i = 0; i < ROWS_TOTAL; i++) {
	      for (int j = 0; j < COLS_TOTAL; j++) {
	        // ����µ�һ�첻�������,����Ҫ�����ϸ��µ�ʣ�༸��
	        if (i == 0 && j == 0 && weekday != 0) {
	          int year = 0;
	          int month = 0;
	          int lastMonthDays = 0;
	          // ����������1�£���һ���¾���ȥ���12��
	          if (calendarday.getMonth() == 0) {
	            year = calendarday.getYear() - 1;
	            month = java.util.Calendar.DECEMBER;
	          } else {
	            year = calendarday.getYear();
	            month = calendarday.getMonth() - 1;
	          }
	          // �ϸ��µ����һ���Ǽ���
	          lastMonthDays = getDateNum(year, month);
	          // ��һ������չʾ���Ǽ���
	          int firstShowDay = lastMonthDays - weekday + 1;
	          // ����
	          for (int k = 0; k < weekday; k++) {
	            RelativeLayout group = getDateView(0, k);
	            group.setGravity(Gravity.CENTER);
	            TextView view = null;
	            if (group.getChildCount() > 0) {
	              view = (TextView) group.getChildAt(0);
	            } else {
	              LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1);
	              view = new TextView(getContext());
	              view.setLayoutParams(params);
	              view.setGravity(Gravity.CENTER);
	              group.addView(view);
	            }
	            view.setText("");
	          }
	          j = weekday - 1;
	          // ����µ�һ��������죬���û����ϸ��µ����ڣ�ֱ�ӻ�������µ�����
	        } else {
	          RelativeLayout group = getDateView(i, j);
	          group.setGravity(Gravity.CENTER);
	          TextView view = null;
	          if (group.getChildCount() > 0) {
	            view = (TextView) group.getChildAt(0);
	          } else {
	            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(70, 70);
	            view = new TextView(getContext());
	            view.setLayoutParams(params);
	            view.setGravity(Gravity.CENTER);
	            view.setTextSize(18);
	            group.addView(view);
	          }

	          // ����
	          if (day <= lastDay) {
	            dates[i][j] = format(new Date(calendarday.getYear(), calendarday.getMonth(), day));
	            view.setText(Integer.toString(day));
	            // ����
	            if (thisday.getDate() == day && thisday.getMonth() == calendarday.getMonth()
	                && thisday.getYear() == calendarday.getYear()) {
	              // view.setText("����");
	              view.setTextColor(COLOR_RED);
	               view.setBackgroundResource(R.drawable.mark2);
	            }else if (thisday.getMonth() == calendarday.getMonth()&& thisday.getYear() == calendarday.getYear()) {
	            	//���Ʊ��µ���ɫ
	            	view.setTextColor(R.color.home_b);
	            }else {
	            	//��������
	              view.setTextColor(R.color.home_w);
	            }
	            // ���ñ��
	            setMarker(view, i, j,day);
	            day++;
	            // �¸���
	          }else{
	            view.setText("");
	          }
	        }
	      }
	    }
	  }
	
	 /**
	   * ����ĳ��ĳ���ж�����
	   * 
	   * @param year
	   * @param month
	   * @return
	   */
	  private int getDateNum(int year, int month) {
	    java.util.Calendar time = java.util.Calendar.getInstance();
	    time.clear();
	    time.set(java.util.Calendar.YEAR, year + 1900);
	    time.set(java.util.Calendar.MONTH, month);
	    return time.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
	  }
	  
	  /**
	   * �������кŻ�ð�װÿһ�����ӵ�LinearLayout
	   * 
	   * @param row
	   * @param col
	   * @return
	   */
	  private RelativeLayout getDateView(int row, int col) {
	    return (RelativeLayout) ((LinearLayout) ((LinearLayout) currentCalendar.getChildAt(1))
	        .getChildAt(row)).getChildAt(col);
	  }
	  
	//���ñ��
	  private void setMarker(TextView view, int i, int j,int day) {
		  if (marksMap.get(day) != null) {
			  if (marksMap.get(day) == day) {
				  view.setBackgroundResource(R.drawable.mark1);
				  view.setTextColor(COLOR_WIT);
		}
		}
	  }
	  public void addMark(int date, int id) {
		    marksMap.put(date, id);
		    setCalendarDate();
		  }
	  
	  /**
	   * ��Dateת�����ַ���->2013-3-3
	   */
	  private String format(Date d) {
	    return addZero(d.getYear() + 1900, 4) + "-" + addZero(d.getMonth() + 1, 2) + "-"
	        + addZero(d.getDate(), 2);
	  }
	  // 2��4
	  private static String addZero(int i, int count) {
	    if (count == 2) {
	      if (i < 10) {
	        return "0" + i;
	      }
	    } else if (count == 4) {
	      if (i < 10) {
	        return "000" + i;
	      } else if (i < 100 && i > 10) {
	        return "00" + i;
	      } else if (i < 1000 && i > 100) {
	        return "0" + i;
	      }
	    }
	    return "" + i;
	  }
	
	
	  /**
	   * onClick�ӿڻص�
	   */
	  public interface OnCalendarClickListener {
	    void onCalendarClick(int row, int col, String dateFormat);
	  }
	  
	  public void OnCalendarClickListener(OnCalendarClickListener onCalendarClickListener) {
		    this.onCalendarClickListener = onCalendarClickListener;
		  }
	  public interface LongClickListener {
		  void onCalendarClick(int row, int col, String dateFormat);
	  }
	  
	  public void LongClickListener(LongClickListener LongClickListener) {
		  this.onLongClickListener = (com.example.calendar.Calendar.LongClickListener) LongClickListener;
	  }
}
