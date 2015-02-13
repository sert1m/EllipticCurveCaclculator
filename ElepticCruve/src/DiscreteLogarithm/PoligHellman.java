package DiscreteLogarithm;

import java.math.BigInteger;

import EllipticCurve.EllipticCurve;
import EllipticCurve.Point;
import EllipticCurve.PointXY;

public class PoligHellman {
	private final EllipticCurve ec;
	private final PointXY G, Q;
	private final BigInteger[] p, l1, m1;
	private final int[] k;
	private BigInteger n; 
	private int kol;
	private BigInteger d;
	
	public PoligHellman (EllipticCurve ec, PointXY G, PointXY Q, BigInteger[] p, int[] k)
	{
		this.ec = ec;
		this.G = new PointXY(G);
		this.Q = new PointXY(Q);
		this.p = p;
		this.k = k;
		kol = 0;
		for (int i = 0; i < k.length; i++)
			kol += k[i];
		l1 = new BigInteger[kol];
		m1 = new BigInteger[kol];
		n = BigInteger.ONE;
		for (int i = 0; i < p.length; i++)
			n = n.multiply(p[i].pow(k[i]));
	}
	
	public void countD()
	{
		BigInteger r1[] = new BigInteger [kol]; 
		BigInteger r2[] = new BigInteger [kol];
		Point[] G1 = new PointXY [kol];
		Point[] Q1 = new PointXY [kol];
		BruteForce bf;
		r1[0] = BigInteger.ONE;
		r2[0] = n;
		Q1[0] = Q;
		G1[0] = G;
		for (int i = 0; i < kol - 1; i++)
		{
			
			if (!r2[i].isProbablePrime(1))
			{
				int j;
				int sum = k[0];
				// перепроверить условие!!!!
				for (j = 0; j + 1 < k.length && sum <= i; j++)
						sum += k[j + 1];
				r1[i + 1] = p[j];
				r2[i + 1] = r2[i].divide(p[j]);
			}
		
			Q1[i + 1] = ec.multiply(r1[i + 1], Q1[i]);
			G1[i + 1] = ec.multiply(r1[i + 1], G1[i]);
		}

		bf = new BruteForce (ec, (PointXY) G1[kol - 1], (PointXY) Q1[kol - 1], r2[kol - 1]);
		bf.count();
		m1[kol - 1] = bf.getD();
		
		BigInteger temp = r2[kol - 2].subtract(m1[kol - 1]);
		bf = new BruteForce (ec, (PointXY) ec.multiply(r2[kol - 1], G1[kol - 2]), (PointXY) ec.add(Q1[kol - 2], ec.multiply(temp, G1[kol - 2])), r2[kol - 1]);
		bf.count();
		l1[kol - 1] = bf.getD();
		System.out.print(m1[kol - 1] + " " + l1[kol - 1] + "\n");
		for (int i = kol - 2; i > 0; i--)
		{
			m1[i] = r2[i + 1].multiply(l1[i + 1]).add(m1[i + 1]);
			temp = r2[i - 1].subtract(m1[i]);
			bf = new BruteForce (ec, (PointXY) ec.multiply(r2[i], G1[i - 1]), (PointXY) ec.add(Q1[i - 1], ec.multiply(temp, G1[i - 1])), r2[i]);
			bf.count();
			l1[i] = bf.getD();
		}
		d = r2[1].multiply(l1[1]).add(m1[1]);
		
		/*for (int i = 0; i < r1.length; i++)
		{
			System.out.print("r1[" + i +"] = " + r1[i] + "\n");
			System.out.print("r2[" + i +"] = " + r2[i] + "\n");
		}
		for (int i = 0; i < Q1.length; i++)
		{
			System.out.print("G1[" + i +"] = " + G1[i] + "\n");
			System.out.print("Q2[" + i +"] = " + Q1[i] + "\n");
		}*/
	}
	public BigInteger getD()
	{
		return d;
	}
}
