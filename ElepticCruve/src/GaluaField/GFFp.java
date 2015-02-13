package GaluaField;

import java.math.BigInteger;

//клас, що реалізує інтерфейс поля Галуа
//признрачений для виконання оперцій у полі над простим числом

public class GFFp implements GaluaField
{
	private final BigInteger p;//просте число
	private final BigInteger lastValue;
	public static final BigInteger two = new BigInteger ("2");
	//конструктор
	public GFFp (BigInteger p)
	{
		this.p = new BigInteger (p.toByteArray());
		lastValue = p.subtract(BigInteger.ONE);
	}
	public GFFp (String p)
	{
		this.p = new BigInteger (p);
		lastValue = this.p.subtract(BigInteger.ONE);
	}
	public GFFp (String p, int radix)
	{
		this.p = new BigInteger (p, radix);
		lastValue = this.p.subtract(BigInteger.ONE);
	}
	
	public int getFieldSize() 
	{
		return p.bitLength();
	}
	public BigInteger getPrime() 
	{
		return p;
	}
	public BigInteger add(BigInteger a, BigInteger b) 
	{
		return a.add(b).mod(p);
	}
	public BigInteger subtract (BigInteger a, BigInteger b)
	{
		return a.subtract(b).mod(p);
	}
	public BigInteger inverse (BigInteger a)
	{
		return a.modInverse(p);
	}
	public BigInteger multiply(BigInteger a, BigInteger b) 
	{
		return a.multiply(b).mod(p);
	}
	public BigInteger divide(BigInteger a, BigInteger b) 
	{
		return a.multiply(b.modInverse(p)).mod(p);
	}
	public BigInteger pow(BigInteger a, int b) 
	{
		return a.pow(b).mod(p);
	}
	public BigInteger pow(BigInteger a, BigInteger b) 
	{
		return a.modPow(b, p);
	}
	public BigInteger getIdentity() 
	{
		return BigInteger.ONE;
	}
	public BigInteger Lejandr (BigInteger a)
	{
		return a.modPow(lastValue.divide(two),p);
	}
	public BigInteger sqrt (BigInteger a)
	{
		BigInteger b = Lejandr (a);
		
		if (b.equals(BigInteger.ZERO))	
			return BigInteger.ZERO;
		else if (b.equals(lastValue))
			return null;
		b = new BigInteger (two.toByteArray());
		while (!Lejandr(b).equals(lastValue))
			b = b.add(BigInteger.ONE);
		int s = lastValue.bitLength()-1;
		
		BigInteger t,temp;
		do 
		{
			temp = two.pow(s);
			while (!lastValue.mod(temp).equals(BigInteger.ZERO))
			{
				s--;
				temp = two.pow(s);
			}
			t = lastValue.divide(temp);
			
		}while (t.mod(two).equals(BigInteger.ZERO));
		
		BigInteger ainv = a.modInverse(p); 
		BigInteger c = multiply(b,t);
		BigInteger r = a.modPow(t.add(BigInteger.ONE).divide(two), p);
		BigInteger d;
		for (int i = 1; i < s; i++)
		{
			d = r.pow(2).multiply(ainv).pow(2*s-i-1).mod(p);
			if (d.equals(lastValue))
				r = r.multiply(c).mod(p);
			c = c.pow(2).mod(p);
		}
		return r;
	}
}
