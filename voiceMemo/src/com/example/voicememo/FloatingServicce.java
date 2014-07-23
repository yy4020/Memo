package com.example.voicememo;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 悬浮窗Service 该服务会在后台一直运行一个悬浮的透明的窗体。
 * 
 * @author caiyingyuan
 * */
public class FloatingServicce extends Service {

	
	private String FlAG_LOG="FloatingServicce";
	private int statusBarHeight;// 状态栏高度
	private View view;// 透明窗体
	private boolean viewAdded = false;// 透明窗体是否已经显示
	private WindowManager windowManager;
	private WindowManager.LayoutParams layoutParams;
	private boolean hideItem=true;
	private RelativeLayout itemLayout;
	private Button setting;
	private Button record;
	private Button check;
	private Button add;
	TranslateAnimation left, right;
	Animation up, down;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		//LayoutInflater是用来找layout下xml布局文件，并且实例化！而findViewById()是找具体xml下的具体 widget控件.
		view = LayoutInflater.from(this).inflate(R.layout.floating, null);
		itemLayout=(RelativeLayout)view.findViewById(R.id.item);
		
		setting=(Button)view.findViewById(R.id.setting);
		record=(Button)view.findViewById(R.id.record);
		check=(Button)view.findViewById(R.id.check);
		add=(Button)view.findViewById(R.id.add);
		
		setting.setOnClickListener(new onClickBtnListener());
		record.setOnClickListener(new onClickBtnListener());
		check.setOnClickListener(new onClickBtnListener());
		add.setOnClickListener(new onClickBtnListener());
		
		windowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
		/*
		 * LayoutParams.TYPE_SYSTEM_ERROR：保证该悬浮窗所有View的最上层
		 * LayoutParams.FLAG_NOT_FOCUSABLE:该浮动窗不会获得焦点，但可以获得拖动
		 * PixelFormat.TRANSPARENT：悬浮窗透明
		 */
		layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, LayoutParams.TYPE_SYSTEM_ERROR,
				LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT);
		// layoutParams.gravity = Gravity.RIGHT|Gravity.BOTTOM; //悬浮窗开始在右下角显示
		layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
		Log.d("FloatingServicce", "create...");
		
		view.setOnTouchListener(new OnTouchListener() {
			float[] temp = new float[] { 0f, 0f };
			public boolean onTouch(View v, MotionEvent event) {
				
				layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
				int eventaction = event.getAction();
				switch (eventaction) {
				case MotionEvent.ACTION_DOWN: // 按下事件，记录按下时手指在悬浮窗的XY坐标值
					temp[0] = event.getX();
					temp[1] = event.getY();
					break;

				case MotionEvent.ACTION_MOVE:
					refreshView((int) (event.getRawX() - temp[0]),
							(int) (event.getRawY() - temp[1]));
					break;
				case MotionEvent.ACTION_UP:
					
//					if(event.getRawX()== temp[0]&&event.getRawY() == temp[1]){
						showItem();		
//					}
								
					break;
				}
				return true;
			}
			
		});
		
	}

	/**
	 * 刷新悬浮窗
	 * 
	 * @param x
	 *            拖动后的X轴坐标
	 * @param y
	 *            拖动后的Y轴坐标
	 */
	public void refreshView(int x, int y) {
		// 状态栏高度不能立即取，不然得到的值是0
		if (statusBarHeight == 0) {
			View rootView = view.getRootView();
			Rect r = new Rect();
			rootView.getWindowVisibleDisplayFrame(r);
			statusBarHeight = r.top;
		}

		layoutParams.x = x;
		// y轴减去状态栏的高度，因为状态栏不是用户可以绘制的区域，不然拖动的时候会有跳动
		layoutParams.y = y - statusBarHeight;// STATUS_HEIGHT;
		refresh();
	}

	/**
	 * 添加悬浮窗或者更新悬浮窗 如果悬浮窗还没添加则添加 如果已经添加则更新其位置
	 */
	private void refresh() {
		if (viewAdded) {
			windowManager.updateViewLayout(view, layoutParams);
		} else {
			windowManager.addView(view, layoutParams);
			viewAdded = true;
		}
	}

	/**
	 * 关闭悬浮窗
	 */
	public void removeView() {
		if (viewAdded) {
			windowManager.removeView(view);
			viewAdded = false;
		}
	}
	private void showItem() {
		// TODO Auto-generated method stub
		
		if(hideItem){
			//加载动画XML文件,生成动画指令 
	        Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale); 
	        Animation animTranslate=AnimationUtils.loadAnimation(this, R.anim.translate);
	        Animation animRorate = AnimationUtils.loadAnimation(this, R.anim.rorate); 
	        Animation anim = AnimationUtils.loadAnimation(this, R.anim.rorate); 

	      //开始执行动画 
	        itemLayout.startAnimation(animScale); 
//	        itemLayout.startAnimation(animRorate); 
//	        itemLayout.startAnimation(animTranslate); 
			itemLayout.setVisibility(View.VISIBLE);
			hideItem=false;
		}else{
			itemLayout.setVisibility(View.GONE);
			hideItem=true;
		}
		
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		refresh();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		removeView();
	}

	public class onClickBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub		
			if(v.getId()==R.id.setting){
//				settingView = LayoutInflater.from(FloatingServicce.this).inflate(R.layout.setting, null);
//				windowManager.addView(settingView, mParams);
				Intent intent=new Intent(FloatingServicce.this, recordActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				showItem( );
			}
			if(v.getId()==R.id.record){
				Intent intent=new Intent(FloatingServicce.this, recordActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				showItem( );
			}
			if(v.getId()==R.id.check){
				Intent intent=new Intent(FloatingServicce.this, QueryActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				showItem( );
			}
			if(v.getId()==R.id.add){
				Toast.makeText(FloatingServicce.this, "click", Toast.LENGTH_SHORT).show();
				getAllApps();
				launchApp();
			}
		}

	}
	
	/**获得手机内应用的包名，返回一个List集合**/   
    public List<PackageInfo> getAllApps() {     
            List<PackageInfo> apps = new ArrayList<PackageInfo>();     
            PackageManager packageManager = this.getPackageManager();     
            //获取手机内所有应用     
            List<PackageInfo> paklist = packageManager.getInstalledPackages(0);     
            for (int i = 0; i < paklist.size(); i++) {     
                PackageInfo pak = (PackageInfo) paklist.get(i);     
                //判断是否为非系统预装的应用  (大于0为系统预装应用，小于等于0为非系统应用)   
                if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {     
                    apps.add(pak);     
                }     
            }     
            return apps;     
    }   
    //获得包名后，就可以通过获得要启动的包名启动应用了 
    public void launchApp() {   
        PackageManager packageManager = this.getPackageManager();   
        List<PackageInfo> packages = getAllApps();   
        PackageInfo pa = null;   
        for(int i=0;i<packages.size();i++){   
            pa = packages.get(i);   
            //获得应用名   
            String appLabel = packageManager.getApplicationLabel(pa.applicationInfo).toString();   
            //获得包名   
            String appPackage = pa.packageName;   
            Log.d(FlAG_LOG+i, appLabel+"  "+appPackage);   
        }   
        Intent intent = packageManager.getLaunchIntentForPackage("jp.co.johospace.jorte");//"jp.co.johospace.jorte"就是我们获得要启动应用的包名   
        startActivity(intent);   
    }   


}
