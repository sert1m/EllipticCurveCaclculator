package EllipticCurve;

import java.math.BigInteger;

import GaluaField.GFF2m;
import GaluaField.GaluaField;
//�������� ����� ��� ����������� ����
public class ECF2m implements EllipticCurve {

	private final BigInteger A, B;//��������� �������� �����
	private final GFF2m f;//���������� ���� �����
	//�����������
	public ECF2m (BigInteger A, BigInteger B, BigInteger f)
	{
		this.A = new BigInteger (A.toByteArray());
		this.B = new BigInteger (B.toByteArray());
		this.f = new GFF2m (f);
	}
	public ECF2m (String A, String B, String f, int radix)
	{
		this.A = new BigInteger (A, radix);
		this.B = new BigInteger (B, radix);
		this.f = new GFF2m (f, radix);
	}
	public ECF2m (String A, String B, String f)
	{
		this.A = new BigInteger (A);
		this.B = new BigInteger (B);
		this.f = new GFF2m (f);
	}
	
	public BigInteger getA() 
	{
		return A;
	}
	public BigInteger getB() 
	{
		return B;
	}
	public GaluaField getField() 
	{
		return f;
	}
	public Point duplicate(Point R) 
	{
		if (R.equals(PointXY.O))
			return R;
		//��������� ��� ��������
		BigInteger r, x, y;
		BigInteger X = R.getX();
		BigInteger Y = R.getY();
		if (X.equals(BigInteger.ZERO))
			return PointXY.O;
		
		r = f.add(X, f.divide(X, Y));
		x = f.add(f.multiply(r, r), f.add(r, A));
		y = f.add(f.multiply(X, X), f.multiply(f.add(r, f.getIdentity()), x));
		
		return new PointXY (x,y);
	}
	public Point add(Point R1, Point R2) 
	{		
		if (R1.equals(PointXY.O))
			return R2;//��������� �������� �����
		else if (R2.equals(PointXY.O))
			return R1;
		if (R1.equals(R2))//���� ����� �������, �� �� ��������
			return duplicate (R1);
		//��������� ��� ��������� 
		BigInteger r, x, y;
		BigInteger X1 = R1.getX();
		BigInteger Y1 = R1.getY();
		BigInteger X2 = R2.getX();
		BigInteger Y2 = R2.getY();
		BigInteger temp = f.add(X2, X1);
		if (temp.equals(BigInteger.ZERO))
			return PointXY.O;
		r = f.divide(f.add(Y2, Y1), temp);
		x = f.add(f.multiply(r, r), f.add(r, f.add(X1, f.add(X2, A))));
		y = f.add(f.multiply(r, f.add(X1, x)), f.add(x, Y1));
		
		return new PointXY (x,y);
	}
	public Point multiply(BigInteger d, Point R) 
	{
		if (R.equals(PointXY.O))
			return R;//��������� �����
		if (d.equals(BigInteger.ZERO))
			return PointXY.O;
		//����� � ���� ������ ���������� �������� ������� ����� ��� �������� ������ �����
		Point arr[] = new PointXY [d.bitCount()];
		Point temp = new PointXY ((PointXY) R);
		//���� ���������� �������� 
		int index = 0;
		BigInteger t = new BigInteger ("1");
		//��������� ����� � � ������� ���� ��������� �� ���������� � ���������� �������� ������������ ��������
		int i = 0;
		while (t.compareTo(d) != 1)
		{
			if (d.testBit(i++))
				arr[index++] = temp;
			t = t.shiftLeft(1);
			temp = duplicate (temp);
		}
		temp = arr[0];//� ������ �� ������� �����
		for (i = 0; i < arr.length-1; i++)
			temp = add(temp, arr[i+1]);
		
		return temp;
	}
	public boolean check(Point R) 
	{
		BigInteger X = R.getX();
		BigInteger Y = R.getY();
		if (f.add(f.multiply(Y, Y), f.multiply(Y, X)).equals(
				f.add(f.pow(X, 3), f.add(f.multiply(A, f.multiply(X,X)), B))))
			return true;
		else
			return false;
	}
	public BigInteger countY (BigInteger x)
	{
		BigInteger u = new BigInteger (x.toByteArray());
		BigInteger w = f.add(f.pow(x, 3), f.add(f.multiply(A, f.multiply(x, x)), B));
		if (u.equals(BigInteger.ZERO))
			return f.sqrt(w);
		if (w.equals(BigInteger.ZERO))
			return BigInteger.ZERO;
		BigInteger v = f.multiply(w, f.inverse(f.pow(u, 2)));

		if (f.tr(v).equals(BigInteger.ONE))
			return null;
		
		return f.multiply(f.htr(v), u);
	}
	public String toString ()
	{
		return "EC on GF(2^m):\nA = " + A.toString() + "\nB = " + B.toString() + "\nf = " + f.getPrime().toString();
	}
	public String toString (int radix)
	{
		return "EC on GF(2^m):\nA = " + A.toString(radix) + "\nB = " + B.toString(radix) + "\nf = " + f.getPrime().toString(radix);
	}
}
