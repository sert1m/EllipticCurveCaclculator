package EllipticCurve;

import java.io.Serializable;
import java.math.BigInteger;
//точка в афіних координатах
@SuppressWarnings("serial")
public class PointXY implements Point, Serializable{

	protected BigInteger x, y;
	public static final PointXY O = new PointXY(BigInteger.ZERO,BigInteger.ZERO);//умовне позначення точки на нескінченності 
	//конструктори
	public PointXY (BigInteger x, BigInteger y)
	{
		this.x = new BigInteger (x.toByteArray());
		this.y = new BigInteger (y.toByteArray());
	}
	public PointXY (PointXY p)
	{
		x = new BigInteger (p.getX().toByteArray());
		y = new BigInteger (p.getY().toByteArray());
	}
	public PointXY (String X, String Y)
	{
		x = new BigInteger (X);
		y = new BigInteger (Y);
	}
	public PointXY (String X, String Y, int radix)
	{
		x = new BigInteger (X, radix);
		y = new BigInteger (Y, radix);
	}
	public boolean equals (PointXY p)
	{
		if (x.equals(p.getX())&&y.equals(p.getY()))
			return true;
		else 
			return false;
	}
	public String toString ()
	{
		if (this.equals(O))
			return "Point on Infinity";
		return "P(" + x + ",\n" + y + ")";
	}
	public String toString (int radix)
	{
		if (this.equals(O))
			return "Point on Infinity";
		return "P(" + x.toString(radix) + ",\n" + y.toString(radix) + ")";
	}
	public BigInteger getX() 
	{
		return x;
	}
	public BigInteger getY() 
	{
		return y;
	}
	public Point negate()
	{
		return new PointXY (x, y.negate());
	}
	public void setX(BigInteger x)
	{
		this.x = new BigInteger (x.toByteArray());
	}
	public void setY(BigInteger y)
	{
		this.y = new BigInteger (y.toByteArray());
	}
}
