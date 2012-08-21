package ua.kiev.air_hockey;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
	
	//private GameField game;
	private Game gm;
	
	private boolean runFlag = false;
	private SurfaceHolder surfaceHolder;
	private Bitmap picture;
	private short[] score;
	private Matrix matrix;
	private Resources res;
	private long prevTime;
	private long prevTime1;
	private int dispHeight;
	private int dispWidth;
	TouchContainer touch;
	private int gateStartX;
	private int gateEndY;
	private short fad;
	Float fx;
	private int goalDegr = -1;
	
	public DrawThread(SurfaceHolder surfaceHolder, Resources resources, int dispHeight, int dispWidth, 
			TouchContainer touch) {
		this.touch = touch;
		
		fad = 255;
		
		
		this.score = new short[] {99, 99};
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
		prevTime1 = System.currentTimeMillis();
	//	game = new GameField(dispHeight, dispWidth, false, this.touch);
		
		gm = new Game(dispWidth, dispHeight, touch);
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
		float[] ff = new float[3];
		boolean[] goal = new boolean[3];

		while (runFlag) {
			// получаем текущее время и вычисляем разницу с предыдущим
			// сохраненным моментом времени
			long now = System.currentTimeMillis();
			long elapsedTime = now - prevTime;
			
			
			
			//game.getPlayers()[0].setDx(10);
			if (elapsedTime > 1) {
				
			/*	if(game.getPlayers()[0].getOnTable()) {
				
				game.getPlayers()[0].setDx((game.getPlayers()[0].getX() - oldx) );
				game.getPlayers()[0].setDy((game.getPlayers()[0].getY() - oldy) );
				oldx = game.getPlayers()[0].getX();
				oldy = game.getPlayers()[0].getY();
				} */
				zzz = elapsedTime;
				gm.move((float) (40.0 / elapsedTime));
				goal = gm.getGoal();
				
				if (goal[2]) {
					fad = 255;

					
					if (goal[0]) {
						
						goalDegr = 0;
			    
					}
				
					if (goal[1]) {

						goalDegr = 180;
					}
					gm.resetGoal();
					
				}
				

				prevTime = now;
			}
			
			if (touch.getFirstNewTouch()) {
				gm.resetFirstD();
			}
			
			if (touch.getSecondNewTouch()) {
				gm.resetSecondD();
			}
			
			gm.setFirstOnTable(touch.getFirstOnTable());
			gm.setSecondOnTable(touch.getSecondOnTable());
			//gm.setSecondOnTable(touch.getSecondOnTable());
			//gm.chgCoord();
			
			
		//	game.getPlayers()[0].setOnTable(touch.getFirstOnTable());
			//if (gm.firstOnTable() || gm.secondOnTable()) {
			gm.chgCoord(); 
		//	game.getPlayers()[0].setX(touch.getFirstPlayerCoord()[0]);
		//	game.getPlayers()[0].setY(touch.getFirstPlayerCoord()[1]);
			
		//	if (game.getPlayers()[0].isStrike(game.getDisk())) {
		//		game.getPlayers()[0].strike(game.getDisk());
			//}

			
			canvas = null;

			try {

				// получаем объект Canvas и выполняем отрисовку
				canvas = surfaceHolder.lockCanvas(null);
				synchronized (surfaceHolder) {
					
					drawField(canvas, p);
					p.setStyle(Paint.Style.FILL);

					score = gm.getScore();
					
					
					//canvas.drawText("" + goalCase(), 200, 200, p);
				//	canvas.drawText(alpha + "", 400, 310, p);
				//	canvas.drawText("dx =  " + dx + " " + "dy = " + dy, 500, 500, p);
				//	canvas.drawText("dxm = " + dxm + " dym = " + dym, 500, 520, p);
				//	canvas.drawText("first " +  touch.getFirstNewTouch(), 500, 540, p);
				//	canvas.drawText("second " + touch.getSecondNewTouch(), 500, 560, p);
				//	canvas.drawText(speed + "+" + speedPl + "=" + speedRes, 100, 580, p);
				//	canvas.drawText(displaymetrics.widthPixels + " "
				//			+ displaymetrics.heightPixels, 100, 100, p);
					//canvas.drawText(str.toString(), 300, 300, p);
					// canvas.drawBitmap(picture, matrix, null);
				//	radius = (int) dispHeight / 20;
					//
					p.setARGB(255, 200, 10, 10);
					//canvas.drawText(zzz + " ", 100, 100, p);
				//	canvas.drawText(score[0] + " " + score[1], 200, 200, p);
					
					
					
			//		if (game.getPlayers()[0].getOnTable()) {
			//		canvas.drawCircle(game.getPlayers()[0].getX(), 
				//		game.getPlayers()[0].getY(), game.getPlayers()[0].getRadius(), p); }
					
					if (gm.firstOnTable()) {
						ff = gm.firstPlayerFields();
						drawFirstPlayer(canvas, p, ff);
					}
					
					if (gm.secondOnTable()) {
						ff = gm.secondPlayerFields();
						drawSecondPlayer(canvas, p, ff);
					}
					
					p.setARGB(255, 200, 10, 10);
					p.setStyle(Paint.Style.FILL);
					
					ff = gm.diskFields();
					drawDisk(canvas, p, ff);
					
					if ((goalDegr == 0) || (goalDegr == 180)) {
						fad = (short) (fad - 1); 
						drawScore(canvas, p, goalDegr, "Goal");
					}
					
					
				    
					//canvas.drawText(mm.x + " " + mm.y, 300, 300, p);
					
					//canvas.drawCircle(mm.x, mm.y, game.getPlayers()[0].getRadius(), p);
					
					//canvas.drawText(mm.x + "", 100, 100, p);
					
					//canvas.drawCircle(game.getDisk().getX(),
					//		game.getDisk().getY() , game.getDisk().getRadius(), p);
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
				//	for (ExtraPoint pz : game.getExtraPoints()) {
					//	canvas.drawCircle(pz.getX(), pz.getY(), 5, p);
					//}
					
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
		
		canvas.drawColor(Color.rgb(240, 240, 240));
		p.setStrokeWidth(5);
		
		p.setStyle(Paint.Style.STROKE);
		
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
		
		
		p.setARGB(255, 240, 240, 240);
		canvas.drawLine(gateStartX, 2, gateEndY, 2, p);
		canvas.drawLine(gateStartX, dispHeight -2, gateEndY, dispHeight - 2, p);
		
		float smallPiece = (dispWidth - 4f / 10 * dispHeight) / 4;
		
		p.setARGB(255, 0, 46, 184);
		p.setTextSize(dispHeight / 15);
		
		RectF playerTwo = new RectF(smallPiece + dispHeight / 10, dispHeight / 2, 
				smallPiece, dispHeight * 2 / 5);
		RectF playerOne = new RectF(dispWidth - smallPiece - dispHeight / 10, dispHeight / 2, 
				dispWidth - smallPiece, dispHeight * 3 / 5);
		
		//canvas.drawRect(smallPiece + dispHeight / 10, dispHeight / 2, smallPiece, dispHeight * 2 / 5, p);
		canvas.drawRect(playerTwo, p);
		canvas.drawRect(playerOne, p);
		p.setStyle(Paint.Style.FILL);
		p.setTextAlign(Paint.Align.CENTER);
		Rect rect = new Rect();

		canvas.drawText(score[0] + "", playerOne.centerX(), playerOne.centerY() + dispHeight / 40, p);
		canvas.rotate(180, dispWidth / 2 + rect.exactCenterX(), dispHeight / 2 + rect.exactCenterY());
		canvas.drawText(score[1] + "", playerOne.centerX(), playerOne.centerY() + dispHeight / 40, p);
		canvas.rotate(180, dispWidth / 2 + rect.exactCenterX(), dispHeight / 2 + rect.exactCenterY());
		
		
	}
	
	private void drawScore(Canvas canvas, Paint p, int degr, String str) {
	    p.setTextSize(dispHeight / 5);
	    p.setTextAlign(Paint.Align.CENTER);
	   

	    // Создаем ограничивающий прямоугольник для наклонного текста
	    Rect rect = new Rect();
	    p.setARGB(fad, 100, 100, 100);
	    // поворачиваем холст по центру текста
	    canvas.rotate(degr, dispWidth / 2 + rect.exactCenterX(), dispHeight / 2 + rect.exactCenterY());
	  //  canvas.rotate(-180);
	    // Рисуем текст
	    p.setStyle(Paint.Style.FILL);
	    

			
			canvas.drawText(str, dispWidth / 2, dispHeight * 9 / 20, p);
			if (fad <= 0) {
				goalDegr = -1;
			fad = 255;}
	    
	}
	
	private void drawDisk(Canvas canvas, Paint p, float[] ff) {
		
		//p.setARGB(255, 200, 40, 40);
		p.setARGB(255, 210, 90, 10);
		canvas.drawCircle(ff[0], ff[1], ff[2], p);
		
		p.setARGB(255, 180, 90, 10);
		p.setStrokeWidth(6);
		p.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(ff[0], ff[1], ff[2] - 3, p);

		
	}
	
	private void drawFirstPlayer(Canvas canvas, Paint p, float[] ff) {
		
		//p.setARGB(255, 200, 40, 40);
		p.setARGB(255, 215, 40, 200);
		canvas.drawCircle(ff[0], ff[1], ff[2], p);
		
		//p.setARGB(255, 500, 5, 5);
		p.setARGB(255, -100, 5, 500);
		p.setStrokeWidth(4);
		p.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(ff[0], ff[1], ff[2] - 2, p);
		p.setStyle(Paint.Style.FILL);
		canvas.drawCircle(ff[0], ff[1], ff[2] / 3, p);
	}
	
private void drawSecondPlayer(Canvas canvas, Paint p, float[] ff) {
		
		//p.setARGB(255, 200, 40, 40);
		p.setARGB(255, 40, 215, 200);
		canvas.drawCircle(ff[0], ff[1], ff[2], p);
		
		//p.setARGB(255, 500, 5, 5);
		p.setARGB(255, 5, -100, 500);
		p.setStrokeWidth(4);
		p.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(ff[0], ff[1], ff[2] - 2, p);
		p.setStyle(Paint.Style.FILL);
		canvas.drawCircle(ff[0], ff[1], ff[2] / 3, p);
	}

}
