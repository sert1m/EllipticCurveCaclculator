package GaluaField;

import java.math.BigInteger;

//��������� ��� ��������� �������� � ��� ����� 

public interface GaluaField 
{
	public int getFieldSize ();//������� ��������� ������ ����
	public BigInteger getPrime ();//������� ��� ��������� �����, ��� ���� ���������� ����
	public BigInteger add (BigInteger a, BigInteger b);//������� ��������� ����� � ���
	public BigInteger subtract (BigInteger a, BigInteger b);//��������
	public BigInteger inverse (BigInteger a);//�������, �� ��������� �������� ������� � ��� 
	public BigInteger multiply (BigInteger a, BigInteger b);//��������
	public BigInteger divide (BigInteger a, BigInteger b);//������
	public BigInteger pow (BigInteger a, int b);//��������� �� �������
	public BigInteger pow (BigInteger a, BigInteger b);
	public BigInteger sqrt (BigInteger a);
	public BigInteger getIdentity ();//��������� ���������� �������� ��� ������� �������� 	

}
