import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Serializer 
{
	public void serialize(ArrayList<Transaction> list, String fileName) 
	{
		try {
			ObjectOutputStream ouputStream = new ObjectOutputStream(new FileOutputStream(fileName));
			ouputStream.writeObject(list);
			ouputStream.close();
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			
		}
	}
	public void serialize(Object o, String fileName){
		try {
			ObjectOutputStream ouputStream = new ObjectOutputStream(new FileOutputStream(fileName));
			ouputStream.writeObject(o);
			ouputStream.close();
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			
		}		
	}	
	public ArrayList<Transaction> deserialize(String fileName) 	
	{	
		ArrayList<Transaction> list = null;
		
		try{
			 FileInputStream inputStream = new FileInputStream(fileName);
			 ObjectInputStream reader = new ObjectInputStream(inputStream);

			 list = (ArrayList<Transaction>)reader.readObject();
			 
			 }
		catch (IOException e)
		{
		
		}
		catch (ClassNotFoundException e)
		{
		
		}
		return list;
	}
	public Object deserializeToObject(String fileName) 	
	{	
		Object o = null;
		
		try{
			 FileInputStream inputStream = new FileInputStream(fileName);
			 ObjectInputStream reader = new ObjectInputStream(inputStream);

			 o = reader.readObject();
			 
			 }
		catch (IOException e)
		{
			
		}
		catch (ClassNotFoundException e)
		{
			
		}
		return o;
  }
}

