package postApp;
import java.io.*;
import java.util.*;

public class UserKeys {
	static PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	static StringTokenizer st;
	
	public static Map<String, Value> users = new HashMap();
	public static int numUsers=0;
	
	public UserKeys() throws Exception{
		loadUsers();
	}
	
	private void loadUsers() throws IOException {
		BufferedReader br = new BufferedReader(new java.io.FileReader(new File("Files/UsernameAndPasswords.txt")));
		
		if(!checkLoad())return;
		String line; //file formatting:   User Number:Username:Password (hashed)
		while((line=br.readLine())!=null) {
			String str[] = line.trim().split(":");
			users.put(str[1],new Value(Integer.parseInt(str[0]),str[2]));
			numUsers=Integer.parseInt(str[0]);
		}
		br.close();
		
	}
	
	public static void addUser(String username, String password) throws Exception {
		if(checkUsername(username)) return;
		String hash = byteArrayToHexString(computeHash(password));
		users.put(username,new Value(++numUsers,hash));
		try {
			BufferedWriter pr = new BufferedWriter(new FileWriter(new File("Files/UsernameAndPasswords.txt"),true));
			pr.write(String.format("%d:%s:%s\n", numUsers,username,hash));
			pr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static boolean checkUsername(String username) {
		return users.containsKey(username);
	}
	
	public static boolean checkPassword(String username, String password) throws Exception {
		//converts the normal password to a hash
		String hash = byteArrayToHexString(computeHash(password));
		//checks if the passwords match
		if(checkUsername(username))
			return users.get(username).getPassword().equals(hash);
		return false;
	}
	
	
	public static byte[] computeHash(String password) throws Exception {
		java.security.MessageDigest digest =null;
		digest = java.security.MessageDigest.getInstance("SHA-1");
		digest.reset();
		digest.update(password.getBytes());
		return  digest.digest();
	}

	//converts a byte array to a hex string
    public static String byteArrayToHexString(byte[] hash){
		StringBuffer hexString = new StringBuffer(hash.length * 2);
		for (int i = 0; i < hash.length; i++){
			int hex = hash[i] & 0xff;
			if (hex < 16)
				hexString.append('0');
		    hexString.append(Integer.toHexString(hex));
		}
		return hexString.toString().toUpperCase();
    }
    
    //checks if the file is empty
    private boolean checkLoad() {
		File newFile = new File("Files/UsernameAndPasswords.txt");
	    if (newFile.length() == 0) return false;
		return true;
	}
	
	
}

	class Value{
		String password;
		int userNum;
		
		public Value(int userNum,String password) {
			this.password=password;
			this.userNum = userNum;
		}
		
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public int getUserNum() {
			return userNum;
		}
		public void setUserNum(int userNum) {
			this.userNum = userNum;
		}
		
		
	}
