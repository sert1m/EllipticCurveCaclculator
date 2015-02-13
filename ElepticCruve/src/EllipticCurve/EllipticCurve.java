package EllipticCurve;

import java.math.BigInteger;
import GaluaField.GaluaField;
//��������� ��� �������� �����. ������� 䳿, �� ����� ���������� � ��������� ������
public interface EllipticCurve {
	
	public BigInteger getA();//�������� ������ �������� �������� �����
	public BigInteger getB();//�������� ������ ��������
	public GaluaField getField();//�������� ���� �����, ��� ���� ���������� �������� �����
	public Point duplicate (Point R);//�������� �����
	public Point add (Point R1, Point R2);//��������� �����
	public Point multiply (BigInteger d, Point R);//�������� ��������
	public BigInteger countY (BigInteger x);
	public boolean check (Point R); //��������, �� �������� ����� �������� �����
	
}
