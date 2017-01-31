import java.io.*;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Scanner;
class DBWritter
{
	static File table = new File("data.csv"); 
	public static void write(HashMap<String, Account> accountMap)
	{
		try
		{
			FileWriter writer = new FileWriter(table);
			for(Account a : accountMap.values())
			{
				writer.write(a.getEmail()+","+a.getPasswordHash()+","+a.getSalt()+"\n");
			}

			writer.flush();
			writer.close();
		}catch(IOException e)
		{
			System.out.println("Error writing to DB"+ e);
		}


	}

	public static HashMap<String, Account> loadHashMap()
	{
		HashMap<String, Account> hashMap = new HashMap<>();
		try
		{
			Scanner sc = new Scanner(table);


			while(sc.hasNext())
			{
				String row = sc.nextLine();
				String[] cols = row.split(",");
				hashMap.put(cols[0],new Account(cols[0], cols[1], cols[2]));
				System.out.println(Arrays.toString(cols));

			}

		}catch(FileNotFoundException e)
		{
			System.out.println("File Not Found.");
			return null;
		}

		return hashMap;

	}
}