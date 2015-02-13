package EllipticCurve;

import java.math.BigInteger;

import GaluaField.GFFp;
import GaluaField.GaluaField;
//еліптинчна крива над полем Галуа
public class ECFp implements EllipticCurve{

	private final BigInteger A, B;//параметри еліптичної кривої та дві 
	private final BigInteger three;//константи, які часто використовуються в обчисленні
	private final GFFp f;//поле Галуа, над яким побудована крива
	//конструктор
	public ECFp (BigInteger A, BigInteger B, BigInteger P)
	{
		this.A = new BigInteger (A.toByteArray());
		this.B = new BigInteger (B.toByteArray());
		f = new GFFp (P);
		three = new BigInteger ("3");
	}
	public ECFp (String A, String B, String P)
	{
		this.A = new BigInteger (A);
		this.B = new BigInteger (B);
		f = new GFFp (P);
		three = new BigInteger ("3");
	}
	public ECFp (String A, String B, String P, int radix)
	{
		this.A = new BigInteger (A, radix);
		this.B = new BigInteger (B, radix);
		f = new GFFp (P, radix);
		three = new BigInteger ("3");
	}
	public ECFp (ECFp ec)
	{
		A = new BigInteger (ec.getA().toByteArray());
		B = new BigInteger (ec.getB().toByteArray());
		f = new GFFp (ec.getField().getPrime());
		three = new BigInteger ("3");
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
	public Point duplicate (Point R) 
	{
		if (R.equals(PointXY.O))
			return R;
		//параметри для подвоєння
		BigInteger r, x, y;
		BigInteger X = R.getX();
		BigInteger Y = R.getY();
		BigInteger temp = f.add(Y, Y);
		if (temp.equals(BigInteger.ZERO))
			return PointXY.O;
		
		r = f.divide(f.add(f.multiply(three, f.pow(X, 2)), A), temp);
		x = f.subtract(f.pow(r,2), f.add(X, X));
		y = f.subtract(f.multiply(r, f.subtract(X, x)), Y);
		return new PointXY (x,y);
	}
	public Point add(Point R1, Point R2) 
	{
		if (R1.equals(PointXY.O))
			return R2;//повертаємо відповідну точку
		else if (R2.equals(PointXY.O))
			return R1;
		if (R1.equals(R2))//якщо точки однакові, то це подвоєння
			return duplicate (R1);
		//параметри для додавання 
		BigInteger r, x, y;
		BigInteger X1 = R1.getX();
		BigInteger Y1 = R1.getY();
		BigInteger X2 = R2.getX();
		BigInteger Y2 = R2.getY();
		BigInteger temp = f.subtract(X2, X1);
		if (temp.equals(BigInteger.ZERO))
			return PointXY.O;
		
		r = f.divide(f.subtract(Y2, Y1), temp);
		x = f.subtract(f.pow(r, 2), f.add(X1, X2));
		y = f.subtract(f.multiply(r, f.subtract(X1, x)), Y1);
		
		return new PointXY(x,y);
	}
	public Point multiply(BigInteger d, Point R) 
	{
		if (R.equals(PointXY.O))
			return R;//повертаємо точку
		if (d.equals(BigInteger.ZERO))
			return PointXY.O;
		//масив у який будемо записувати значення подвоєної точки при відповідній степені двійки
		Point arr[] = new PointXY [d.bitCount()];
		Point temp = new PointXY ((PointXY) R);
		int index = 0;
		BigInteger t = new BigInteger ("1");
		//подвоюємо точку і у випадку якщо відповідний біт встановлен у коефіцієнті множення запамятовуємо значення
		int i = 0;
		while (t.compareTo(d) != 1)
		{
			if (d.testBit(i++))
				arr[index++] = temp;
			t = t.shiftLeft(1);
			temp = duplicate (temp);
		}
		temp = arr[0];//і додаємо усі записані точки
		for (i = 0; i < arr.length-1; i++)
			temp = add(temp, arr[i+1]);
		
		return temp;
	}
	public boolean check(Point R) 
	{
		BigInteger X = R.getX();
		BigInteger Y = R.getY(); 
		if (f.multiply(Y, Y).equals(f.add(f.pow(X, 3), f.add(f.multiply(A, X), B))))
			return true;
		else 
			return false;
	}
	public BigInteger countY (BigInteger x)
	{
		BigInteger temp = f.add(f.pow(x, 3),f.add(f.multiply(A, x), B));
		if (f.Lejandr(temp).equals(BigInteger.ONE))
			return f.sqrt(temp);
		if (f.Lejandr(temp).equals(BigInteger.ZERO))
			return BigInteger.ZERO;
		return null;
	}
	public String toString ()
	{
		return "EC on GF(P):\nA = " + A.toString() + "\nB = " + B.toString() + "\nP = " + f.getPrime().toString();
	}
	public String toString (int radix)
	{
		return "EC on GF(P):\nA = " + A.toString(radix) + "\nB = " + B.toString(radix) + "\nP = " + f.getPrime().toString(radix);
	}
}
