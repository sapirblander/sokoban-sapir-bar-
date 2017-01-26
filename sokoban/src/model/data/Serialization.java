package model.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Serialization 
{
	public static Object deserialize(InputStream in) throws IOException,ClassNotFoundException 
	{
		ObjectInputStream ois = new ObjectInputStream(in);
		Object obj = ois.readObject();
		ois.close();
		return obj;
	}

	public static void serialize(Object obj, OutputStream out)throws IOException 
	{
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(obj);
		oos.close();
	}
}
