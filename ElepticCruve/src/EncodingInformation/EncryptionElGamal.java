package EncodingInformation;

import java.math.BigInteger;
import java.security.SecureRandom;

import EllipticCurve.EllipticCurve;
import EllipticCurve.Point;

public class EncryptionElGamal 
{
	private EllipticCurve ec;
	private Point G, Q, R;
	private BigInteger n, k;
	
	public EncryptionElGamal (EllipticCurve ec, BigInteger n, Point G, Point Q, BigInteger k)
	{
		this.n = new BigInteger (n.toByteArray());
		this.ec = ec;
		this.G = G;
		this.Q = Q;
		if (k != null)
		{
			this.k = new BigInteger (k.toByteArray());
			R = ec.multiply(k, G);
		}
		else
			countR();
	}
	
	private void countR ()
	{
		k = new BigInteger (n.bitLength(), new SecureRandom()).mod(n);
		R = ec.multiply(k, G);
	}
	
	public Point getR ()
	{
		return R;
	}
	public Point encrypt (Point M)
	{
		return ec.add(M, ec.multiply(k, Q));
	}
	public Point decrypt (BigInteger d, Point C, Point R)
	{
		return ec.add(C, ec.multiply(d, R.negate()));
	}
}
