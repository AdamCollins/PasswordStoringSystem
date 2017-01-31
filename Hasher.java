import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

class Hasher
{
	public static String hash(String text,String salt)
	{
		String hash = "";
		hash = getSHA256(getSHA256(getSHA256(text+salt)+salt)+salt);
		return hash;
	}

	private static String getSHA256(String text)
	{
		try
		{
		MessageDigest sha256  = MessageDigest.getInstance("SHA-256");
		sha256.update(text.getBytes("UTF-8"));
		byte[] digest = sha256.digest();
		return DatatypeConverter.printHexBinary(digest);
		}catch(Exception e)
		{
			System.out.println(e);
		}	
		return null;
	}

	public static String generateSalt()
	{
		String salt = "";
		for(int i = 0; i<8; i++)
			salt+= ""+(char)((int)(Math.random()*90)+35);
		return salt;
	}

	public static boolean checkPassword(String pass, String hash, String salt)
	{
		return hash.equals(hash(pass,salt));
	}

}