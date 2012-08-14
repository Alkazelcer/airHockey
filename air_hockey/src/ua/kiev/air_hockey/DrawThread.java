package ua.kiev.air_hockey;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
	
	private GameField game;
	
	private boolean runFlag = false;
	private SurfaceHolder surfaceHolder;
	private Bitmap picture;
	private Matrix matrix;
	private Resources res;
	private long prevTime;
	private int dispHeight;
	private int dispWidth;
	TouchContainer touch;
	private int gateStartX;
	private int gateEndY;
	Float fx;
	
	public DrawThread(SurfaceHolder surfaceHolder, Resources resources, int dispHeight, int dispWidth, 
			TouchContainer touch) {
		this.touch = touch;
		
		this.surfaceHolder = surfaceHolder;
		this.dispHeight = dispHeight;
		this.dispWidth = dispWidth;
		this.gateStartX = this.dispWidth / 2 - dispHeight / 5 + 2;
		this.gateEndY = this.dispWidth / 2 + dispHeight / 5 - 2;
		this.fx = fx;
		res = resources;

		// загружаем картинку, которую будем отрисовывать
		//picture = BitmapFactory.decodeResource(resources, R.drawable.icon);

		// формируем матрицу преобразований для картинки
		matrix = new Matrix();
		matrix.postScale(3.0f, 3.0f);
		matrix.postTranslate(100.0f, 100.0f);

		// сохраняем текущее время
		prevTime = System.currentTimeMillis();
		game = new GameField(dispHeight, dispWidth, false, this.touch);
	}
	
	public void setRunning(boolean run) {
		runFlag = run;
	}
	
	public void run() {
		Canvas canvas;
		long zzz = 0;
		Paint p = new Paint();
		DisplayMetrics displaymetrics = res.getDisplayMetrics();
		float oldx = 0;
		float oldy = 0;

		while (runFlag) {
			// получаем текущее время и вычисляем разницу с предыдущим
			// сохраненным моментом времени
			long now = System.currentTimeMillis();
			long elapsedTime = now - prevTime;
			
			
			//game.getPlayers()[0].setDx(10);
			if (elapsedTime > 1) {
				
				if(game.getPlayers()[0].getOnTable()) {
				
				game.getPlayers()[0].setDx((game.getPlayers()[0].getX() - oldx) );
				game.getPlayers()[0].setDy((game.getPlayers()[0].getY() - oldy) );
				oldx = game.getPlayers()[0].getX();
				oldy = game.getPlayers()[0].getY();
				}
				zzz = elapsedTime;
				game.move((float) (30.0 / elapsedTime));

				
				

				prevTime = now;
			}
			
			if (touch.newTouch) {
				game.getPlayers()[0].setDx(0);
				game.getPlayers()[0].setDy(0);
			}
			game.getPlayers()[0].setOnTable(touch.isOnTableFirst());
			
			game.getPlayers()[0].setX(touch.getFirstPlayerCoord()[0]);
			game.getPlayers()[0].setY(touch.getFirstPlayerCoord()[1]);
			
		//	if (game.getPlayers()[0].isStrike(game.getDisk())) {
		//		game.getPlayers()[0].strike(game.getDisk());
			//}

			
			canvas = null;

			try {

				// получаем объект Canvas и выполняем отрисовку
				canvas = surfaceHolder.lockCanvas(null);
				synchronized (surfaceHolder) {

					p.setStyle(Paint.Style.FILL);
					canvas.drawColor(Color.rgb(240, 240, 240));
					p.setStrokeWidth(5);
					
					p.setStyle(Paint.Style.STROKE);
					drawField(canvas, p);
					p.setStyle(Paint.Style.FILL);
					//drawField(canvas, p);
					
					
					//canvas.drawText("" + goalCase(), 200, 200, p);
				//	canvas.drawText(alpha + "", 400, 310, p);
				//	canvas.drawText("dx =  " + dx + " " + "dy = " + dy, 500, 500, p);
				//	canvas.drawText("dxm = " + dxm + " dym = " + dym, 500, 520, p);
				//	canvas.drawText("speed = " +  Math.sqrt((Math.pow(dx,2) + Math.pow(dy,2))), 500, 540, p);
				//	canvas.drawText("speed = " + speedPl, 500, 560, p);
				//	canvas.drawText(speed + "+" + speedPl + "=" + speedRes, 100, 580, p);
				//	canvas.drawText(displaymetrics.widthPixels + " "
				//			+ displaymetrics.heightPixels, 100, 100, p);
					//canvas.drawText(str.toString(), 300, 300, p);
					// canvas.drawBitmap(picture, matrix, null);
				//	radius = (int) dispHeight / 20;
					//
					p.setARGB(255, 200, 10, 10);
					canvas.drawText(zzz + " ", 100, 100, p);
					
					if (game.getPlayers()[0].getOnTable()) {
					canvas.drawCircle(game.getPlayers()[0].getX(), 
						game.getPlayers()[0].getY(), game.getPlayers()[0].getRadius(), p); }
					
					canvas.drawCircle(game.getPlayers()[1].getX(), 
							game.getPlayers()[1].getY(), game.getPlayers()[1].getRadius(), p); 
					//canvas.drawText(mm.x + " " + mm.y, 300, 300, p);
					
					//canvas.drawCircle(mm.x, mm.y, game.getPlayers()[0].getRadius(), p);
					
					//canvas.drawText(mm.x + "", 100, 100, p);
					
					canvas.drawCircle(game.getDisk().getX(),
							game.getDisk().getY() , game.getDisk().getRadius(), p);
					//canvas.drawLine(0, 0, 800, 10, p);
					
					//canvas.drawRect(2, 2, dispWidth - 2, dispHeight -2, p);
					//pole
			/*		canvas.drawLine(2, 2, 2, dispHeight - 2, p);
					canvas.drawLine(2, 2, dispWidth - 2, 2, p);
					canvas.drawLine(2, dispHeight - 2, dispWidth - 2, dispHeight - 2, p);
					canvas.drawLine(dispWidth - 2, 2, dispWidth - 2, dispHeight - 2, p);
					//canvas.drawLine(dispWidth / 2, 0, dispWidth / 2, dispHeight, p);
					canvas.drawLine(0, dispHeight / 2, dispWidth, dispHeight / 2, p);
					//centr
					canvas.drawCircle(dispWidth / 2, dispHeight / 2, dispHeight / 10, p);
					//vorota
					canvas.drawCircle(dispWidth / 2, 2, dispHeight / 5, p);
					canvas.drawCircle(dispWidth / 2, dispHeight - 2, dispHeight / 5, p); */
					//p.setARGB(255, 255, 255, 255);
					//vorota
					//canvas.drawLine(gateStartX, 2, gateEndY, 2, p);
					//canvas.drawLine(gateStartX, dispHeight -2, gateEndY, dispHeight - 2, p);
					p.setARGB(150, 0, 255, 0);
					for (ExtraPoint pz : game.getExtraPoints()) {
						canvas.drawCircle(pz.getX(), pz.getY(), 5, p);
					}
					
				}
			} finally {
				if (canvas != null) {
					// отрисовка выполнена. выводим результат на экран
					
					surfaceHolder.unlockCanvasAndPost(canvas);

				}
			}
		}
	}
	
	private void drawField(Canvas canvas, Paint p) {
		
		p.setARGB(255, 0, 46, 184);
		
		canvas.drawLine(2, 2, 2, dispHeight - 2, p);
		canvas.drawLine(2, 2, dispWidth - 2, 2, p);
		canvas.drawLine(2, dispHeight - 2, dispWidth - 2, dispHeight - 2, p);
		canvas.drawLine(dispWidth - 2, 2, dispWidth - 2, dispHeight - 2, p);
		//canvas.drawLine(dispWidth / 2, 0, dispWidth / 2, dispHeight, p);
		canvas.drawLine(0, dispHeight / 2, dispWidth, dispHeight / 2, p);
		//centr
		canvas.drawCircle(dispWidth / 2, dispHeight / 2, dispHeight / 10, p);
		//vorota
		canvas.drawCircle(dispWidth / 2, 2, dispHeight / 5, p);
		canvas.drawCircle(dispWidth / 2, dispHeight - 2, dispHeight / 5, p);
		
	}

}
