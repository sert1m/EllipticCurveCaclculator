package EllipticCurve;

import java.math.BigInteger;

public interface Point {
	
	public BigInteger getX ();
	public BigInteger getY ();
	public void setX(BigInteger x);
	public void setY(BigInteger y);
	public Point negate();
	public String toString (int radix);
}
