import java.util.regex.Matcher;
import java.util.regex.Pattern;
class Account
{
	private String email;
	private String passwordHash;
	private String salt;
	Account(String email, String passwordHash, String salt)
	{
		this.email = email;
		this.passwordHash = passwordHash;
		this.salt = salt;
	}

	public static boolean isValidEmail(String email)
	{
		String regex = "\\w*@.*\\.(\\w{3}|\\w{2})";
		return Pattern.matches(regex, email);
	}

	public String getPasswordHash()
	{
		return passwordHash;
	}

	public String getEmail()
	{
		return email;
	}

	public String getSalt()
	{
		return salt;
	}

}