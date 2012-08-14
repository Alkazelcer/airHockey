package ua.kiev.air_hockey;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {

	private int displayWidth;
	private int displayHeight;
	
	TouchContainer touch;

	
	private DrawThread drawThread;


	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);

		getHolder().addCallback(this);

		DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
		
		touch = new TouchContainer();

		displayWidth = displaymetrics.widthPixels;
		displayHeight = displaymetrics.heightPixels;
		// TODO Auto-generated constructor stub
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceCreated(SurfaceHolder holder) {

		drawThread = new DrawThread(getHolder(), getResources(), displayHeight, displayWidth, touch);
		drawThread.setRunning(true);
		drawThread.start();

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		// завершаем работу потока
		drawThread.setRunning(false);
		while (retry) {
			try {
				drawThread.join();
				retry = false;
			} catch (InterruptedException e) {
				// если не получилось, то будем пытаться еще и еще
			}
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		
		touch.setFirstPlayerCoord(new float[] {event.getX(), event.getY()});
		int Action = event.getAction();
		// str.setLength(0);
		switch (Action) {
		case MotionEvent.ACTION_DOWN:
			touch.newTouch = true;
			touch.setFirstOnTable(true);

			break;
		case MotionEvent.ACTION_MOVE:
			touch.setFirstOnTable(true);
			touch.newTouch = false;

			break;
		case MotionEvent.ACTION_UP:
			touch.setFirstOnTable(false);
			touch.newTouch = false;

			break;
		}
		return true;
	}

}
