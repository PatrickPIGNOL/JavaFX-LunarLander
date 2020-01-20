package Game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Lander 
{
	private double aX;
	private double aY;
	private double aAngle;
	private double aSpeedX;
	private double aSpeedY;
	private Image aShip;
	private Image aLowThrust;
	private Image aHighThrust;
	private boolean aIsThrusting;
	private boolean aThruster;
	
	public Lander(Image pShip, Image pLowThrust, Image pHighTrust)
	{
		this.aIsThrusting = false;
		this.aShip = pShip;
		this.aLowThrust = pLowThrust;
		this.aHighThrust = pHighTrust;
	}
	
	public double mX()
	{
		return this.aX;
	}
	
	public void mX(double pX)
	{
		this.aX = pX;
	}
	
	public double mY()
	{
		return this.aY;
	}
	
	public void mY(double pY)
	{
		this.aY = pY;
	}
	
	public double mAngle()
	{
		return this.aAngle;
	}
	
	public void mAngle(double pAngle)
	{
		this.aAngle = pAngle;
	}
	
	public double mSpeedX()
	{
		return this.aSpeedX;
	}
	
	public void mSpeedX(double pSpeedX)
	{
		this.aSpeedX = pSpeedX;
	}
	
	public double mSpeedY()
	{
		return this.aSpeedY;
	}
	
	public void mSpeedY(double pSpeedY)
	{
		this.aSpeedY = pSpeedY;
	}
	
	public boolean mIsThrusting()
	{
		return this.aIsThrusting;
	}
	
	public void mIsThrusting(boolean pIsThrusting)
	{
		this.aIsThrusting = pIsThrusting;
	}
	
	public void mUpdate(double pDeltaTime)
	{
		this.mOnUpdate(pDeltaTime);
	}
	
	private void mOnUpdate(double pDeltaTime)
	{
		
	}
	
	public void mDraw(GraphicsContext pGraphicsContext)
	{
		if(this.aIsThrusting)
		{
			this.aThruster = !this.aThruster;
			if(this.aThruster)
			{
				pGraphicsContext.drawImage(this.aHighThrust, this.aX - (this.aShip.getWidth()/2), this.aY - (this.aShip.getHeight() / 2));		
			}
			else
			{
				pGraphicsContext.drawImage(this.aLowThrust, this.aX, this.aY);	
			}
		}
		pGraphicsContext.drawImage(this.aShip, this.aX, this.aY);
	}
}
