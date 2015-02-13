package EncodingInformation;

import java.math.BigInteger;

import EllipticCurve.EllipticCurve;
import EllipticCurve.PointXY;
import GaluaField.GaluaField;

public class EncodingKoblitz 
{
	private BigInteger k;
	private EllipticCurve ec;
	
	public EncodingKoblitz (BigInteger k, EllipticCurve ec)
	{
		this.k = new BigInteger (k.toByteArray());
		this.ec = ec; 
	}
	public EncodingKoblitz (String k, EllipticCurve ec)
	{
		this.k = new BigInteger (k);
		this.ec = ec; 
	}
	
	public void setK (BigInteger k)
	{
		this.k = new BigInteger (k.toByteArray());
	}
	public void setEC (EllipticCurve ec)
	{
		this.ec = ec;
	}
	public BigInteger getK ()
	{
		return k;
	}
	public EllipticCurve getEC ()
	{
		return ec;
	}
	
	public PointXY code (BigInteger x)
	{
		if (x.multiply(k).compareTo(ec.getField().getPrime()) >= 0)
			return null;
		GaluaField gf =  ec.getField(); 
		for (BigInteger i = x.multiply(k); !i.equals(gf.getPrime()); i = i.add(BigInteger.ONE))
		{
			BigInteger temp = ec.countY(i);
			if (temp != null)
				return new PointXY (i, temp); 
		}
		return null;
	}
	public BigInteger decode (PointXY point)
	{
		BigInteger x = point.getX();
		if (x.mod(k).equals(BigInteger.ZERO))
			return x.divide(k);
		else 
			return x.subtract(x.mod(k)).divide(k);
	}
}
