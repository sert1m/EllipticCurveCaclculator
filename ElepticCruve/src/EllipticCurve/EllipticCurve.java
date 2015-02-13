package EllipticCurve;

import java.math.BigInteger;
import GaluaField.GaluaField;
//інтерфейс для еліптичної кривої. Визначає дії, які можна виконувати з еліптичною кривою
public interface EllipticCurve {
	
	public BigInteger getA();//отримати перший параметр еліптичної кривої
	public BigInteger getB();//отримати другий параметр
	public GaluaField getField();//отримати поле Галуа, над яким побудована еліптична крива
	public Point duplicate (Point R);//подвоєння точки
	public Point add (Point R1, Point R2);//додавання точок
	public Point multiply (BigInteger d, Point R);//скалярне множення
	public BigInteger countY (BigInteger x);
	public boolean check (Point R); //перевірка, чи належить точка еліптичній кривій
	
}
