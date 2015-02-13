package DiscreteLogarithm;

import java.math.BigInteger;

import EllipticCurve.EllipticCurve;
import EllipticCurve.PointXY;


public class BruteForce {
	
	private final EllipticCurve ec;
	private final PointXY G, Q;
	private BigInteger d, n;
	
	public BruteForce (EllipticCurve ec, PointXY G, PointXY Q, BigInteger n)
	{
		this.ec = ec;
		this.Q = new PointXY(Q);
		this.G = new PointXY(G);
		this.n = new BigInteger(n.toByteArray());
	}
	
	public void count()
	{
		PointXY temp = PointXY.O;
		d = new BigInteger ("0");
		while (!temp.equals(Q))
		{
			temp = (PointXY) ec.add(temp, G);
			d = d.add(BigInteger.ONE);
		}
	}
	public BigInteger getD()
	{
		return d;
	}
}
