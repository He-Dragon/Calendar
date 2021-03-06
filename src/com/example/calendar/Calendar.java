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
	  

	  public static final int COLOR_RED = Color.parseColor("#FF4167"); // 红色
	  public static final int COLOR_WIT= Color.parseColor("#FFFFFF"); // 白色
//	  public static final int COLOR_BU = Color.parseColor("#00080B"); // 黑色
	  
	  private int ROWS_TOTAL = 6; // 日历的行数
	  private int COLS_TOTAL = 7; // 日历的列数
	  private String[][] dates = new String[6][7]; // 当前日历日期
	  private String[] weekday = new String[] {"日", "一", "二", "三", "四", "五", "六"}; // 星期标题
	  
	  private int calendarYear; // 日历年份
	  private int calendarMonth; // 日历月份
	  private Date thisday = new Date(); // 今天
	  private Date calendarday; // 日历这个月第一天(1号)
	  
	  private LinearLayout currentCalendar; // 日历布局
	  private OnCalendarClickListener onCalendarClickListener; // 日期选择监听
	  private LongClickListener onLongClickListener; // 日期选择监听
	  
	  private Map<Integer, Integer> marksMap = new HashMap<Integer, Integer>(); // 储存某个日子被标注(Integer
	  
	  
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
	    // 加入ViewFlipper
	    addView(currentCalendar);
	 // 绘制线条框架
	    drawFrame(currentCalendar);
	 // 设置日历上的日子(1号)
	    calendarYear = thisday.getYear() + 1900;
	    calendarMonth = thisday.getMonth();
	    calendarday = new Date(calendarYear - 1900, calendarMonth, 1);
	    // 填充展示日历
	    setCalendarDate();
	}
	


	/**
	 * @Title：
	 * @author：
	 * @annotation：布局设置
	 */
	@SuppressLint("ResourceAsColor")
	private void drawFrame(LinearLayout oneCalendar) {
	    // 添加周末线性布局
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

	    // 添加周末TextView
	    for (int i = 0; i < COLS_TOTAL; i++) {
	      TextView view = new TextView(getContext());
	      view.setGravity(Gravity.CENTER);
	      view.setPadding(0, 10, 0, 10);
	      view.setText(weekday[i]);
	      view.setTextColor(Color.WHITE);
	      view.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 1));
	      title.addView(view);
	    }

	    // 添加日期布局
	    LinearLayout content = new LinearLayout(getContext());
	    content.setOrientation(LinearLayout.VERTICAL);
	    content.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 7f));
	    oneCalendar.addView(content);

	    // 添加日期TextView
	    for (int i = 0; i < ROWS_TOTAL; i++) {
	      LinearLayout row = new LinearLayout(getContext());
	      row.setOrientation(LinearLayout.HORIZONTAL);
	      row.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
	      content.addView(row);
	      // 绘制日历上的列
	      for (int j = 0; j < COLS_TOTAL; j++) {
	        RelativeLayout col = new RelativeLayout(getContext());
	        col.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
	        col.setBackgroundResource(R.color.home_w);
	        // col.setBackgroundResource(R.drawable.sign_dialog_day_bg);
	        col.setClickable(false);
	        row.addView(col);
	        // 给每一个日子加上监听
	        col.setOnClickListener(new OnClickListener() {
	          @Override
	          public void onClick(View v) {
	            ViewGroup parent = (ViewGroup) v.getParent();
	            int row = 0, col = 0;

	            // 获取列坐标
	            for (int i = 0; i < parent.getChildCount(); i++) {
	              if (v.equals(parent.getChildAt(i))) {
	                col = i;
	                break;
	              }
	            }
	            // 获取行坐标
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

		            // 获取列坐标
		            for (int i = 0; i < parent.getChildCount(); i++) {
		              if (v.equals(parent.getChildAt(i))) {
		                col = i;
		                break;
		              }
		            }
		            // 获取行坐标
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
	   * 填充日历(包含日期、标记、背景等)
	   */
	  @SuppressWarnings("deprecation")
	@SuppressLint("ResourceAsColor")
	private void setCalendarDate() {
	    // 根据日历的日子获取这一天是星期几
	    int weekday = calendarday.getDay();
	    // 每个月第一天
	    int firstDay = 1;
	    // 每个月中间号,根据循环会自动++
	    int day = firstDay;
	    // 每个月的最后一天
	    @SuppressWarnings("deprecation")
		int lastDay = getDateNum(calendarday.getYear(), calendarday.getMonth());

	    // 填充每一个空格
	    for (int i = 0; i < ROWS_TOTAL; i++) {
	      for (int j = 0; j < COLS_TOTAL; j++) {
	        // 这个月第一天不是礼拜天,则需要绘制上个月的剩余几天
	        if (i == 0 && j == 0 && weekday != 0) {
	          int year = 0;
	          int month = 0;
	          int lastMonthDays = 0;
	          // 如果这个月是1月，上一个月就是去年的12月
	          if (calendarday.getMonth() == 0) {
	            year = calendarday.getYear() - 1;
	            month = java.util.Calendar.DECEMBER;
	          } else {
	            year = calendarday.getYear();
	            month = calendarday.getMonth() - 1;
	          }
	          // 上个月的最后一天是几号
	          lastMonthDays = getDateNum(year, month);
	          // 第一个格子展示的是几号
	          int firstShowDay = lastMonthDays - weekday + 1;
	          // 上月
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
	          // 这个月第一天是礼拜天，不用绘制上个月的日期，直接绘制这个月的日期
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

	          // 本月
	          if (day <= lastDay) {
	            dates[i][j] = format(new Date(calendarday.getYear(), calendarday.getMonth(), day));
	            view.setText(Integer.toString(day));
	            // 当天
	            if (thisday.getDate() == day && thisday.getMonth() == calendarday.getMonth()
	                && thisday.getYear() == calendarday.getYear()) {
	              // view.setText("今天");
	              view.setTextColor(COLOR_RED);
	               view.setBackgroundResource(R.drawable.mark2);
	            }else if (thisday.getMonth() == calendarday.getMonth()&& thisday.getYear() == calendarday.getYear()) {
	            	//绘制本月的颜色
	            	view.setTextColor(R.color.home_b);
	            }else {
	            	//其他日期
	              view.setTextColor(R.color.home_w);
	            }
	            // 设置标记
	            setMarker(view, i, j,day);
	            day++;
	            // 下个月
	          }else{
	            view.setText("");
	          }
	        }
	      }
	    }
	  }
	
	 /**
	   * 计算某年某月有多少天
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
	   * 根据行列号获得包装每一个日子的LinearLayout
	   * 
	   * @param row
	   * @param col
	   * @return
	   */
	  private RelativeLayout getDateView(int row, int col) {
	    return (RelativeLayout) ((LinearLayout) ((LinearLayout) currentCalendar.getChildAt(1))
	        .getChildAt(row)).getChildAt(col);
	  }
	  
	//设置标记
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
	   * 将Date转化成字符串->2013-3-3
	   */
	  private String format(Date d) {
	    return addZero(d.getYear() + 1900, 4) + "-" + addZero(d.getMonth() + 1, 2) + "-"
	        + addZero(d.getDate(), 2);
	  }
	  // 2或4
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
	   * onClick接口回调
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
