package GaluaField;

import java.math.BigInteger;

//інтерфейс для виконання операцій у полі Галуа 

public interface GaluaField 
{
	public int getFieldSize ();//функція отримання розміру поля
	public BigInteger getPrime ();//функція для отримання числа, над яким побудовано поле
	public BigInteger add (BigInteger a, BigInteger b);//функція додавання чисел у полі
	public BigInteger subtract (BigInteger a, BigInteger b);//віднімання
	public BigInteger inverse (BigInteger a);//функція, що знаходить зворотній елемент у полі 
	public BigInteger multiply (BigInteger a, BigInteger b);//множення
	public BigInteger divide (BigInteger a, BigInteger b);//ділення
	public BigInteger pow (BigInteger a, int b);//піднесення до степеня
	public BigInteger pow (BigInteger a, BigInteger b);
	public BigInteger sqrt (BigInteger a);
	public BigInteger getIdentity ();//отримання одиничного елемента для оперції множення 	

}
