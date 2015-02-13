package EncodingInformation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import EllipticCurve.PointXY;

public class EncodingMessage 
{
	private BigInteger message;
	
	public EncodingMessage (byte[] message)
	{
		this.message = new BigInteger (message);	
	}
	public EncodingMessage (String message)
	{
		this.message = new BigInteger (message);	
	}
	public EncodingMessage ()
	{
		message = null;
	}
	public void readMessage (String filename) throws FileNotFoundException
	{
		String s = "";
		Scanner in = null;

		in = new Scanner(new File(filename));

		while(in.hasNext())
		s += in.nextLine() + "\r\n";
		in.close();
		message = new BigInteger(s.getBytes());
	}
	public List<PointXY> encodeMessage(EncodingKoblitz ek)
	{
		List<PointXY> points = new ArrayList<PointXY>();
		int blockSize = ek.getEC().getField().getPrime().divide(ek.getK()).bitLength()-1;
		int k, i, j;
		if (message.bitLength() % blockSize == 0)
			k = message.bitLength() / blockSize;
		else 
			k = message.bitLength() / blockSize + 1;
		for (i = 0; i < k; i++)
		{
			BigInteger temp = new BigInteger ("1").shiftLeft(blockSize);
			temp = temp.clearBit(blockSize);
			if (i == k-1)
			{
				int t = blockSize - (blockSize*k - message.bitLength());

				while (t % 8 != 0)
					t++;
				for (j = 0; j < t; j ++)
					if (message.testBit(i*blockSize + j))
						temp = temp.setBit(j);	
			}
			else
				for (j = 0; j < blockSize; j ++)
					if (message.testBit(i*blockSize + j))
						temp = temp.setBit(j);
			
			points.add(ek.code(temp));
		}
		return points;
	}
	public String decodeMessage (List<PointXY> points, EncodingKoblitz ek)
	{
		BigInteger dM = BigInteger.ZERO, temp;
		int blockSize = ek.getEC().getField().getPrime().divide(ek.getK()).bitLength()-1;
		for (int i = 0; i < points.size(); i++)
		{
			temp = ek.decode(points.get(i));
			dM = dM.xor(temp.shiftLeft(i*blockSize));
		}
		String s = new String (dM.toByteArray());
		return s.substring(1);
	}
	public List<PointXY> encryptMessage (List<PointXY> points, EncryptionElGamal eg)
	{
		List<PointXY> encryptPoints = new ArrayList<PointXY>();
		for (int i = 0; i < points.size(); i++)
			encryptPoints.add((PointXY) eg.encrypt(points.get(i)));
		return encryptPoints;
	}
	public List<PointXY> decryptMessage (List<PointXY> encryptPoints, EncryptionElGamal eg, BigInteger d, PointXY R)
	{
		List<PointXY> points = new ArrayList<PointXY>();
		
		for (int i = 0; i < encryptPoints.size(); i++)
			points.add((PointXY) eg.decrypt(d, encryptPoints.get(i), R));
		return points;
	}
	public void writePoints (List<PointXY> points, String filename)
	{
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try
		{
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(points);
			out.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public List<PointXY> readPoints (List<PointXY> p, String filename) throws IOException, ClassNotFoundException
	{
		if (p != null)
			p.clear();
		FileInputStream fis = null;
		ObjectInputStream in = null;
		fis = new FileInputStream(filename);
		in = new ObjectInputStream(fis);		
		p = (List<PointXY>) in.readObject();
		in.close();
		return p;
	}
}
