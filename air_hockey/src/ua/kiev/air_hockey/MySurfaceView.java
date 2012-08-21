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
	private int player;
	private int firstPlayerId;
	private int secondPlayerId;

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

		drawThread = new DrawThread(getHolder(), getResources(), displayHeight,
				displayWidth, touch);
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

		// событие
		int actionMask = event.getActionMasked();
		// индекс касания
		int pointerIndex = event.getActionIndex();
		// число касаний
		int pointerCount = event.getPointerCount();
		
		//touch.setFirstPlayerCoord(new float[] { event.getX(), event.getY() });
		int Action = event.getAction();
		// str.setLength(0);
		switch (actionMask) {
		case MotionEvent.ACTION_DOWN:
			if (event.getY() > displayHeight / 2) {
				touch.setFirstNewTouch(true);
				touch.setFirstOnTable(true);

				
				touch.setFirstPlayerCoord(new float[] { event.getX(), event.getY() });
				player = 1;
				firstPlayerId = 0;
			} else {
				touch.setSecondNewTouch(true);
				touch.setSecondOnTable(true);

				
				touch.setSecondPlayerCoord(new float[] { event.getX(), event.getY() });
				player = -1;
				secondPlayerId = 0;
			}
			break;
			
		case MotionEvent.ACTION_POINTER_DOWN: // последующие касания
			switch (player) {
			case 1 : 
				touch.setSecondNewTouch(true);
				touch.setSecondOnTable(true);
				touch.setSecondPlayerCoord(new float[] { event.getX(), event.getY() });
				player = 0;
				secondPlayerId = 1;
				break;
				
			case -1 :
				touch.setFirstNewTouch(true);
				touch.setFirstOnTable(true);
				touch.setFirstPlayerCoord(new float[] { event.getX(), event.getY() });
				player = 0;
				firstPlayerId = 1;
				break;
			}
			
		      break;
		      
		case MotionEvent.ACTION_MOVE:
			//touch.setFirstOnTable(true);
			//touch.setFirstNewTouch(false);
			//touch.setFirstPlayerCoord(new float[] { event.getX(), event.getY() });
			
		/*	if (event.getY() > displayHeight / 2) {
				touch.setFirstPlayerCoord(new float[] { event.getX(), event.getY() });
				touch.setFirstOnTable(true);
				touch.setFirstNewTouch(false);
				
				touch.setSecondOnTable(false);
				touch.setSecondNewTouch(true);
			
			} else {
				touch.setSecondPlayerCoord(new float[] { event.getX(), event.getY() });
				touch.setSecondOnTable(true);
				touch.setSecondNewTouch(false);
				
				touch.setFirstOnTable(false);
				touch.setFirstNewTouch(true);
			} */
			
			int firstPlayerIndex = event.findPointerIndex(firstPlayerId);
			int secondPlayerIndex = event.findPointerIndex(secondPlayerId);
			
			if ((firstPlayerIndex >= 0) && (event.getY(firstPlayerIndex) > displayHeight / 2)) {
				touch.setFirstPlayerCoord(new float[] 
						{event.getX(firstPlayerIndex), event.getY(firstPlayerIndex) });
				touch.setFirstNewTouch(false);
			}
			
			if ((secondPlayerIndex >= 0) && (event.getY(secondPlayerIndex) < displayHeight / 2)) {
				touch.setSecondPlayerCoord(new float[] 
						{event.getX(secondPlayerIndex), event.getY(secondPlayerIndex) });
				touch.setSecondNewTouch(false);
			}
			

			break;
			
		case MotionEvent.ACTION_UP:
			touch.setFirstOnTable(false);
			touch.setFirstNewTouch(false);

			touch.setSecondOnTable(false);
			touch.setSecondNewTouch(false);

			break;

		case MotionEvent.ACTION_POINTER_UP:
			if ((event.getPointerId(0) != firstPlayerId) && (event.getPointerId(1) != firstPlayerId)) {
				touch.setFirstOnTable(false);
				touch.setFirstNewTouch(false);
			}

			if ((event.getPointerId(0) != secondPlayerId) && (event.getPointerId(1) != secondPlayerId)) {
				touch.setSecondOnTable(false);
				touch.setSecondNewTouch(false);
			}
			break;
		}
		return true;
	}
	
	private boolean coordChange(float x, float y, boolean player) {
		return false;
	}

}
