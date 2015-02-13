package GaluaField;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

//����, �� ������ �������� ��� �����
//����������� ��� ��������� ����������� ��� ����������� ����

public class GFF2m implements GaluaField
{
	final private int m;//������ ����� � ������ ������ ���� ����
	private BigInteger fp;//���������� ������
	
	public GFF2m (BigInteger fp)
	{
		this.fp = new BigInteger (fp.toByteArray());
		m = fp.bitLength();
	}
	public GFF2m (String fp, int radix)
	{
		this.fp = new BigInteger (fp, radix);
		m = this.fp.bitLength();
	}
	public GFF2m (String fp)
	{
		this.fp = new BigInteger (fp);
		m = this.fp.bitLength();
	}
	
	public int getFieldSize() 
	{
		return m-1;
	}
	public BigInteger getPrime() //������� �� ������� ���������� ������ 
	{
		return fp;
	}
	
	public BigInteger add(BigInteger a, BigInteger b) 
	{
		return mod(a.xor(b));
	}
	public BigInteger subtract (BigInteger a, BigInteger b)
	{
		return mod(a.xor(b));
	}
	//�������, �� �������� ������ �� �������
	private BigInteger mod (BigInteger a)
	{//�������� ������ �� ���������� ������, ����� ���� ������� � ��� �� �������� �� ������� 2 
		while (a.bitLength() >= m)
			a = a.xor(fp.shiftLeft(a.bitLength() - m));
		return a;
	}
	//�������, �� ��������� �������� ������� � ��� 
	public BigInteger inverse (BigInteger a)
	{
		List <BigInteger> al = new ArrayList <BigInteger> ();//����� ��������� ��� ����������� ����������� ��������
		List <BigInteger> rl = new ArrayList <BigInteger> ();//����� �� ������
		
		BigInteger temp = new BigInteger(fp.toByteArray());
		int mu = -1;//������� ����������� �����
		//��������� ��������� �������
		while (!temp.equals(BigInteger.ONE))
		{
			BigInteger r = new BigInteger (BigInteger.ZERO.toByteArray());
			while (temp.bitLength() >= a.bitLength())//�����
			{
				int shift = temp.bitLength() - a.bitLength();
				temp = temp.xor(a.shiftLeft(shift));
				r = r.setBit(shift);//��������� ���� �������
			}
			rl.add(r);
			//������ �������� ������ � �������� ������� ��������� �����
			BigInteger t = new BigInteger (a.toByteArray());  
			a = new BigInteger (temp.toByteArray());
			temp = new BigInteger (t.toByteArray());
			mu++;
		}
		al.add(rl.get(0));//���������� ������ ��������
		if (rl.size() > 1)//������,���� �
			al.add(add(multiply(rl.get(0),rl.get(1)), BigInteger.ONE));
		//���������� ���������� ������� ���������, ���� ���������
		for (int i = 2; i < rl.size(); i++)
			 al.add(add(multiply(rl.get(i), al.get(i-1)), al.get(i-2)));	
		return al.get(mu - 1);
	}
	public BigInteger multiply(BigInteger a, BigInteger b) 
	{//��������, ���� ������� �� ����������� ������� 2 � ������� ������ ������� �����������
		BigInteger temp = new BigInteger ("0");
		for (int i = 0; i < b.bitLength();i++)
			if (b.testBit(i))
				temp = temp.xor(a.shiftLeft(i));
		return mod(temp);
	}
	public BigInteger divide(BigInteger a, BigInteger b) 
	{//������
		if (b.equals(getIdentity()))
			return a;
		else if (b.equals(BigInteger.ZERO))
			return null;
		return multiply(a, inverse(b));
	}
	public BigInteger pow (BigInteger a, int b) 
	{//��������� �� �������, ��������� ������� ��������� ��� �������� ����������
		//�� ���������������� ��� ��������� ������� ����� 10
		BigInteger temp = new BigInteger (a.toByteArray());
		for (int i = 1; i < b; i++)
			temp = multiply (temp, a);
		return temp;
	}
	public BigInteger pow(BigInteger a, BigInteger b) 
	{//��������� � ������� ��� ������� ���������� �������(����� 10)
		BigInteger arr[] = new BigInteger [b.bitCount()];
		BigInteger temp = new BigInteger (a.toByteArray());
		int index = 0;
		if (b.testBit(0))
			arr[index++] = new BigInteger (a.toByteArray());//���� ������ �������, ��������� � � ������
		//�������� �� ���� �� � �� ������������ ��������, ���� � ����� �� � ����������� 1 �� ����������� ������� ����
		for (int i = 1; i < b.bitLength(); i++)
		{
			temp = multiply (temp, temp);
			if (b.testBit(i))
				arr[index++] = temp;
		}
		//� ����������� �� ��������
		temp = arr[0];
		for (int i = 0; i < arr.length-1; i ++)
			temp = multiply(temp, arr[i+1]);
		return temp;
	}
	public BigInteger sqrt (BigInteger a)
	{
		return pow (a, BigInteger.ONE.shiftLeft(m-2));
	}
	public BigInteger tr (BigInteger a)
	{
		BigInteger t = new BigInteger (a.toByteArray());
		for (int i = 0; i < m - 2; i++)
		{
			t = pow(t, 2);
			t = add(t, a);
		}
		return t;
	}
	public BigInteger htr (BigInteger a)
	{
		BigInteger t = new BigInteger (a.toByteArray());
		for (int i = 0; i < (m - 2)/2; i++)
		{
			t = pow(t, 4);
			t = add(t, a);
		}
		return t;
	}
	public BigInteger getIdentity() 
	{
		return BigInteger.ONE;
	}
}
