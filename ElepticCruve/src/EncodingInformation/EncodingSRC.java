package EncodingInformation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import EllipticCurve.ECFp;
import EllipticCurve.PointXY;
import GaluaField.GFFp;

public class EncodingSRC 
{
	private BigInteger M;
	private ECFp ec;
	private int p[];
	private PointXY P[][];
	private int k[][][];
	
	public EncodingSRC (ECFp ec, int p[])
	{
		this.ec = ec;
		
		this.p = new int [p.length];
		for (int i = 0; i < p.length; i++)
			this.p[i] = p[i];
		P  = new PointXY[p.length][];
		countP();
		
		M = BigInteger.ONE;
		for (int i = 0; i < p.length; i++)
			M = M.multiply(new BigInteger(((Integer)p[i]).toString()));
	}
	
	private void countP()
	{
		k = new int [p.length][][];
		for (int i = 0; i < p.length; i++)
			k[i] = new int [p[i]][p[i]];	
		for (int i = 0; i < p.length; i++)
		{
			int n = 0;
			GFFp gf = new GFFp (new BigInteger(((Integer)p[i]).toString()));
			List <PointXY> listP = new ArrayList<PointXY>();
			for (int x = 0; x < p[i]; x++)
			{
				BigInteger X = new BigInteger(((Integer)x).toString());
				BigInteger a = gf.add(gf.pow(X, 3),gf.add(gf.multiply(ec.getA(),X), ec.getB()));
				BigInteger l = gf.Lejandr(a);

				if (l.equals(BigInteger.ONE))
				{
					BigInteger temp = gf.sqrt(a);
					listP.add(new PointXY(X,temp));
					listP.add(new PointXY(X,gf.getPrime().subtract(temp)));
					k[i][x][temp.intValue()] = n++;
					k[i][x][p[i]-temp.intValue()] = n++;
				}
				else if (l.equals(BigInteger.ZERO))
				{
					listP.add(new PointXY(X,BigInteger.ZERO));
					k[i][x][0] = n++;
				}
			}
			P[i] = new PointXY[listP.size()];
			listP.toArray(P[i]);
		}
	}

	public PointXY[] code (BigInteger m)
	{
		PointXY points [] = new PointXY [p.length];
		for (int i = 0; i < p.length; i++)
		{
			BigInteger x = m.mod(new BigInteger (((Integer)p[i]).toString()));
			//System.out.print(p[i]+" " + P[i].length+"\n");
			points[i] = P[i][x.intValue()];
			
		}
		return points;
	}
	public BigInteger decode(PointXY[] points)
	{
		BigInteger m;
		BigInteger x = new BigInteger ("0");
		for (int i = 0; i < points.length; i++)
		{
			BigInteger temp = new BigInteger(
					((Integer)k[i][points[i].getX().intValue()][points[i].getY().intValue()]).toString());
			//System.out.print(temp+"\n");
			BigInteger tempp = new BigInteger(((Integer)p[i]).toString());
			m = M.divide(tempp);
			x = x.add(m.multiply(temp.multiply(m.modInverse(tempp)).mod(tempp))).mod(M);
		}
		return x;
	}
}
