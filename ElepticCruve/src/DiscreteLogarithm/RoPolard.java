package DiscreteLogarithm;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import EllipticCurve.EllipticCurve;
import EllipticCurve.PointXY;
import GaluaField.GaluaField;


public class RoPolard {

	private final EllipticCurve ec;
	private final PointXY G, Q;
	private List<PointXY> Z;
	private List <BigInteger> a, b; 
	private BigInteger d, n, p[], two;
	private GaluaField gf;
	private int i, j;
	
	public RoPolard (EllipticCurve ec, PointXY G, PointXY Q, BigInteger n)
	{
		this.ec = ec;
		this.G = new PointXY(G);
		this.Q = new PointXY(Q);
		Z = new ArrayList<PointXY>();
		a = new ArrayList<BigInteger>();
		b = new ArrayList<BigInteger>();
		this.n = new BigInteger (n.toByteArray());
		
		two = new BigInteger("2");
		p = new BigInteger [2];
		gf = ec.getField();
		p[0] = gf.getPrime().divide(new BigInteger("3"));
		p[1] = p[0].multiply(new BigInteger("2"));

		BigInteger temp = new BigInteger(n.bitLength(), new SecureRandom());
		a.add(temp.mod(n));
		temp = new BigInteger(n.bitLength(), new SecureRandom());
		b.add(temp.mod(n));
		
		Z.add((PointXY) ec.add(ec.multiply(a.get(0), G), ec.multiply(b.get(0), Q)));
		
	}
	
	private void count ()
	{
			PointXY temp = Z.get(Z.size()-1); 
			BigInteger X = temp.getX(); 
			int s = a.size()-1;
			if (X.compareTo(p[0]) <= 0)
			{
				Z.add((PointXY) ec.duplicate(temp));
				a.add(a.get(s).multiply(two).mod(n));
				b.add(b.get(s).multiply(two).mod(n));
			}
			else if (X.compareTo(p[1]) <= 0)
			{
				Z.add((PointXY) ec.add(temp, G));
				a.add(a.get(s).add(BigInteger.ONE).mod(n));
				b.add(b.get(b.size()-1));
			}
			else
			{
				Z.add((PointXY) ec.add(temp, Q));	
				b.add(b.get(s).add(BigInteger.ONE).mod(n));
				a.add(a.get(a.size()-1));
			}
		}
	public boolean check ()
	{
		PointXY temp = Z.get(Z.size()-1);
		int k = 0;
		while (k < Z.size()-2)
		{
			if (temp.equals(Z.get(k)))
			{
				i = k;
				j = Z.size()-1;
				return true;
			}
			k++;
		}
		return false;
	}
	
	public boolean countD ()
	{
		if (b.get(j).equals(b.get(i)))
			return false;
		BigInteger temp = a.get(i).subtract(a.get(j)).mod(n);
		while (temp.compareTo(BigInteger.ZERO) == -1)
			temp = temp.add(n);
		BigInteger temp2 = b.get(j).subtract(b.get(i)).mod(n);
		while (temp2.compareTo(BigInteger.ZERO) == -1)
			temp2 = temp2.add(n);
		//System.out.print(temp2);

		d = temp.multiply(temp2.modInverse(n)).mod(n);

		return true;
	}
	public void find()
	{
		do{
			do
			{
				count();
			}while (!check());
		}while(!countD());
	}
	public BigInteger getD()
	{
		return d;
	}
}

