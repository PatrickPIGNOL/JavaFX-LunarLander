package Game;


import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GameApplication extends Application
{
	private Stage aStage;
	private Scene aScene;
	private Group aGroup;
	private Canvas aCanvas;
	private GraphicsContext aGraphicsContext;
	private double aFPS;
	private double aFPSTime;
	private double aFrameCount;
	private double aNanoTime;
	private double aTime;
	private Lander aLander;
	private boolean aTurnLeft;
	private boolean aTurnRight;
	
	@Override
	public void start(Stage pStage) throws Exception 
	{
		this.aStage = pStage;
	    this.aStage.setTitle("JavaFX : LunarLander");	    
		this.aGroup = new Group();
	    this.aScene = new Scene(this.aGroup);
	    this.aStage.setScene(this.aScene);
	    this.aCanvas = new Canvas( 800, 600 );
	    this.aGroup.getChildren().add(this.aCanvas);
	    this.aGraphicsContext = this.aCanvas.getGraphicsContext2D();
	    this.aNanoTime = System.nanoTime();
	    this.aFrameCount = this.aNanoTime;
	    this.aScene.setOnKeyPressed
	    (
	    	new EventHandler<KeyEvent>()
	        {
	    		public void handle(KeyEvent e)
	            {
	                 mOnKeyPressed(e);
	            }
	        }
	    );
	    this.aScene.setOnKeyReleased
	    (
	    	new EventHandler<KeyEvent>()
	    	{
	    		public void handle(KeyEvent e)
	    		{
	    			mOnKeyReleased(e);
	    		}
	    	}
	    );
	    this.aScene.setOnMouseMoved
	    (
	    	new EventHandler<MouseEvent>()
	    	{
	    		public void handle(MouseEvent e)
	    		{
	    			mOnMouseMoved(e);
	            }
	    	}
	    );
	    this.aScene.setOnMouseClicked
	    (
	    	new EventHandler<MouseEvent>()
	    	{
	    		public void handle(MouseEvent e)
	    		{
	    			mOnMouseClicked(e);
	            }
	    	}
	    );

	    this.aStage.setResizable(false);
	    this.mLoad();
	    
	    new AnimationTimer()
	    {
	    	public void handle(long pCurrentNanoTime)
	    	{
	    		double vCurrentNanoTime = Double.valueOf(pCurrentNanoTime);
	    		mLoop(vCurrentNanoTime);
	    	}
	    }.start();	    
		this.aStage.show();
	}
	
	private void mOnKeyPressed(KeyEvent e)
	{
		if(e.getCode()==KeyCode.RIGHT)
		{
			this.aTurnRight = true;
		}
		if(e.getCode()==KeyCode.LEFT)
		{
			this.aTurnLeft = true;
		}
		if(e.getCode()==KeyCode.UP)
		{
			this.aLander.mIsLanded(false);
			this.aLander.mIsThrusting(true);
		}
	}
	private void mOnKeyReleased(KeyEvent e)
	{
		if(e.getCode()==KeyCode.RIGHT)
		{
			this.aTurnRight = false;
		}
		if(e.getCode()==KeyCode.LEFT)
		{
			this.aTurnLeft = false;
		}
		if(e.getCode()==KeyCode.UP)		
		{
			this.aLander.mIsThrusting(false);
		}
	}
	
	private void mLoop(double pCurrentNanoTime)
	{		
		double vDeltaTime = pCurrentNanoTime - this.aNanoTime;
		this.aTime += vDeltaTime;
		this.aFPSTime += vDeltaTime;
		
		double vNanoTimePerSeconds = 1000000000.0;
		double vMiliTimePerSeconds = vNanoTimePerSeconds / 1000.0;
		double vFPS = 120;
		double vNanoTimePerFPS = vNanoTimePerSeconds / vFPS;
		this.mUpdate(vDeltaTime/vNanoTimePerSeconds);
        // limit acceleration card overheat and CPU usage ...
		if(this.aFPSTime > vNanoTimePerFPS)
        {
			this.aFrameCount++;
	    	this.aGraphicsContext.setFill(Color.BLACK);
	    	this.aGraphicsContext.fillRect(0.0, 0.0, this.aCanvas.getWidth(), this.aCanvas.getHeight());
	    	this.mDraw(this.aGraphicsContext);
			//this.mDrawFPS(vDeltaTime);
			this.aFPSTime = 0.0;
		}	
		
        if(this.aTime > vNanoTimePerSeconds)
		{			
        	this.aFPS = this.aFrameCount;
			this.aFrameCount = 0.0;
			this.aTime = 0.0;
		}
		
		this.aNanoTime = pCurrentNanoTime;
	}
	
	public void mLoad()
	{
		this.aLander = new Lander(new Image("LunarLander.png"), new Image("LowThrust.png"), new Image("HighThrust.png"));
	    this.mStart();
	}
	
	public void mStart()
	{
	    this.aLander.mX(this.aCanvas.getWidth() / 2.0);
	    this.aLander.mY(this.aLander.mHeight() / 2.0);
	}
	
	public void mUpdate(double pDeltaTime)
    {				
		this.mOnUpdate(pDeltaTime);        
    }
	
	private void mOnUpdate(double pDeltaTime)
	{
		if(this.aLander.mIsLanded())
		{
			this.aLander.mSpeedX(0.0);
			this.aLander.mSpeedY(0.0);
			this.aLander.mAngle(0.0);
		}
		else
		{
			this.aLander.mSpeedY(this.aLander.mSpeedY() + (1.62 * pDeltaTime));
		}
		
		this.aLander.mX(this.aLander.mX() + this.aLander.mSpeedX());
		this.aLander.mY(this.aLander.mY() + this.aLander.mSpeedY());
		if(this.aLander.mY() - this.aLander.mHeight() / 2.0 > this.aCanvas.getHeight())
		{
			this.aLander.mY(0.0);
			this.aLander.mSpeedY(0.0);
		}
		if(this.aLander.mX() < 0)
		{
			this.aLander.mX(this.aCanvas.getWidth());
		}
		if(this.aLander.mX() > this.aCanvas.getWidth())
		{
			this.aLander.mX(0.0);
		}
		
		if(this.aLander.mIsThrusting())
		{
			double vRadians = Math.toRadians(this.aLander.mAngle() - 90);
			double vForceX = Math.cos(vRadians) * this.aLander.mSpeed() * pDeltaTime;
			double vForceY = Math.sin(vRadians) * this.aLander.mSpeed() * pDeltaTime;
			this.aLander.mSpeedX(this.aLander.mSpeedX() + vForceX);
			this.aLander.mSpeedY(this.aLander.mSpeedY() + vForceY);
		}
		if(this.aTurnLeft)
		{
			this.aLander.mAngle(this.aLander.mAngle() - (10 * pDeltaTime));
		}
		if(this.aTurnRight)
		{
			this.aLander.mAngle(this.aLander.mAngle() + (10 * pDeltaTime));
		}
		if(this.aLander.mY() > this.aCanvas.getHeight())
		{
			if((this.aLander.mSpeedY() < 0.5) && (this.aLander.mSpeedX() < 0.5) && (Math.abs(this.aLander.mAngle() % 360) < 2))
			{
				this.aLander.mIsLanded(true);
				this.aLander.mY(this.aCanvas.getHeight());
			}
		}
	}
	
	public void mDraw(GraphicsContext pGraphicsContext)
	{
		Font vFont = Font.font( "Times New Roman", FontWeight.BOLD, 14 );
		this.mDrawText(20, 20, vFont, "SpeedX = " + this.aLander.mSpeedX(), 0.0, Color.GREEN, Color.GREEN);
		this.mDrawText(20, 40, vFont, "SpeedY = " + this.aLander.mSpeedY(), 0.0, Color.GREEN, Color.GREEN);
		this.mDrawText(20, 60, vFont, "Angle =" + this.aLander.mAngle(), 0.0, Color.GREEN, Color.GREEN);
		//this.mDrawText(20, 80, vFont, "Y =" + this.aLander.mY(), 0.0, Color.GREEN, Color.GREEN);
	    this.aLander.mDraw(pGraphicsContext);
	}
	
	private void mOnMouseMoved(MouseEvent e)
	{
		
	}
	
	private void mOnMouseClicked(MouseEvent e)
	{
		
	}
	
	private void mDrawFPS(double pDeltaTime)
	{
		double vNanoTimePerSeconds = 1000000000.0;
		double vFPS = 240;
		double vNanoTimePerFPS = vNanoTimePerSeconds / vFPS;
		
		Font vFont = Font.font( "Times New Roman", FontWeight.BOLD, 14 );
		this.mDrawText(10.0, 20.0, vFont, "Delta: " + pDeltaTime, 0.0, Color.GREEN, Color.BLACK);
		this.mDrawText(10.0, 40.0, vFont, "FPS: " + this.aFPS, 0.0, Color.GREEN, Color.BLACK);
		this.mDrawText(10.0, 60.0, vFont, "FPStime: " + this.aFPSTime / vNanoTimePerSeconds, 0.0, Color.GREEN, Color.BLACK);
		this.mDrawText(10.0, 80.0, vFont, "n/FPS: " + vNanoTimePerFPS / vNanoTimePerSeconds, 0.0, Color.GREEN, Color.BLACK);
	}
	
	private void mDrawText(double pX, double pY, Font pFont, String pText, double pLineWidth, Paint pFillColor, Paint pStrokeColor)
	{
		this.aGraphicsContext.setFill(pFillColor);
		this.aGraphicsContext.setFont(pFont);
	    this.aGraphicsContext.fillText(pText, pX, pY);
		if(pLineWidth > 0.0)
		{
			this.aGraphicsContext.setStroke(pStrokeColor);
			this.aGraphicsContext.setLineWidth(pLineWidth);
		    this.aGraphicsContext.strokeText(pText, pX, pY);
		}
	}
}
