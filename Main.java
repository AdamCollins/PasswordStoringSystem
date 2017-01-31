import java.util.HashMap;
import java.util.Scanner;
import java.io.Console;
class Main
{
	static HashMap<String, Account> accountMap;
	public static void main(String[] ags)
	{
		accountMap = new HashMap<>();
		Scanner in = new Scanner(System.in);
		accountMap = DBWritter.loadHashMap();
		while(true)
		{
			System.out.print("\nEnter \"signup\" or \"signin\": ");
			String input = in.nextLine();
			if(input.equalsIgnoreCase("signup"))
			{
				createAccount();
				updateDB();
			}
			else if(input.equalsIgnoreCase("signin"))
			{
				signIn();
			}
			else
			{
				System.out.println("Invalud input. Please try agin.");
			}

			
		}
		
	}

	public static void createAccount()
	{
			String email;
			String pass;
			String confPass;
			String salt;
			Scanner in = new Scanner(System.in);
			Console c = System.console();
			
			do
			{
				System.out.print("Enter email: ");
				email = in.nextLine();
				if(!Account.isValidEmail(email))
					System.out.println("Invalid Email. Try again.");
			}while(!Account.isValidEmail(email));

			
			do{
				pass = new String(c.readPassword("Enter Password: "));
				confPass = new String(c.readPassword("Confirm Password: "));
				if(!pass.equals(confPass))
					System.out.println("Passwords do not match. Try again.");
			}while(!pass.equals(confPass));
			confPass = null;

			salt = Hasher.generateSalt();
			pass = Hasher.hash(pass, salt);
			
			if(!accountMap.containsKey(email))
				accountMap.put(email,new Account(email, pass, salt));
			else
			{
				System.out.println("There is already an account using this email!");
				return;
			}

			System.out.println("\nAccount created!\n");
	}

	public static void signIn()
	{
		String email;
		String hashedPass;
		boolean signInSuccess = false;
		Account account;
		Scanner in = new Scanner(System.in);
		Console c = System.console();
		do
		{
			do
			{
				System.out.print("Enter email: ");
				email = in.nextLine();
				if(!Account.isValidEmail(email))
					System.out.println("Invalid Email. Try again.");
			}while(!Account.isValidEmail(email));

			account = accountMap.get(email);
			

			try
			{
				hashedPass = Hasher.hash(new String(c.readPassword("Password: ")), account.getSalt());
			}catch(NullPointerException e)
			{
				System.out.print("\nEmail is not in use.");
				return;
			}

			if(hashedPass.equals(accountMap.get(email).getPasswordHash()))
					signInSuccess = true;

			if(!signInSuccess)
				System.out.println("Incorrect info. Try again.");
		
		}while(!signInSuccess);

		System.out.printf("You are now signed into %s!\n",email);

	}

	public static void updateDB()
	{
		DBWritter.write(accountMap);
	}

}