import java.util.*;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;
import java.io.*;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataStore {

	static Scanner s = new Scanner(System.in);

	@SuppressWarnings("unchecked")

	// Create a new entry when time-to-live property is not given
	static void create_new() {
		try {
			File file = new File("D:\\File1.txt");
			// Checking if file already exists
			if (file.exists())
				System.out.println("\nFile already exists!");
			else {
				file.createNewFile();
				System.out.println("\nNew File created as:" + file.getName());
			}

			boolean notExists = true;
			System.out.println("\nEnter key:");
			String key = new String();
			Scanner fileScanner = new Scanner(file);
			while (notExists) {
				key = s.next();

				// Checking length of key
				if (key.length() > 32) {
					while (key.length() > 32) {
						System.out.println("\nKey size limit exceeded, Enter a new key:");
						key = s.next();
					}
				}
				boolean found = false;

				// Checking if key already exists
				while (fileScanner.hasNextLine()) {
					found = false;
					String data = fileScanner.nextLine();
					String words[] = data.split(":");
					if (words[0].equalsIgnoreCase(key)) {
						System.out.println("\nKey already exists, Enter a new key:");
						found = true;
					}
					if (found == true)
						break;
				}
				if (found == false)
					break;

			}
			fileScanner.close();

			// Creating a JSON object
			JSONObject jsonObject = new JSONObject();
			System.out.println("\nEnter number of values in JSON Object:");
			int n = s.nextInt();
			System.out.println("!!!Please enter in this format 'key:value'!!!");
			for (int i = 0; i < n; i++) {
				String data = s.next();
				String words[] = data.split(":");
				jsonObject.put(words[0], words[1]);
				words = null;
			}

			System.out.println("\nJSON object created: " + jsonObject);

			// Writing contents to the file
			FileWriter obj = new FileWriter("D:\\File1.txt", true);
			BufferedWriter out = new BufferedWriter(obj);
			out.write(key + ":" + jsonObject.toJSONString() + "\n");
			out.close();
			System.out.println("Successfully written to the file!!!\n\n");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Read JSON object from file
	static void read() {
		try {
			File file = new File("D:\\File1.txt");
			boolean found = false;
			Scanner fileScanner = new Scanner(file);
			System.out.println("Enter key to read:");
			String key = s.next();
			while (fileScanner.hasNextLine()) {
				String data = fileScanner.nextLine();
				String words[] = data.split(":");
				int position = data.indexOf(":");
				String val = data.substring(position + 1);

				if (words[0].equalsIgnoreCase(key)) {
					System.out.println("\n" + val + "\n\n");
					found = true;
				}
			}
			if (found == false)
				System.out.println("\nKey not found!!\n\n");
			fileScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	// Delete an entry from the file
	static void delete() {
		try {
			File newFile = new File("D:\\temp.txt");
			File oldFile = new File("D:\\File1.txt");
			FileWriter obj = new FileWriter("D:\\temp.txt", true);
			BufferedWriter out = new BufferedWriter(obj);
			boolean found = false;
			Scanner fileScanner = new Scanner(oldFile);
			BufferedReader br1 = new BufferedReader(new FileReader(oldFile));
			String line1 = br1.readLine();
			System.out.println("Enter key to delete:");
			String key = s.next();

			// Checking if the given key matches, if matched ignore the pair and copy other
			// pairs to a temporary file
			while (fileScanner.hasNextLine()) {
				String data = fileScanner.nextLine();
				String words[] = data.split(":");
				if (words[0].equalsIgnoreCase(key)) {
					found = true;
				}
			}
			if (found == false) {
				System.out.println("Key not found!!\n\n");
				br1.close();
				br1 = null;
				out.close();
				fileScanner.close();
				return;
			}
			while (line1 != null) {
				boolean flag = false;
				String words[] = line1.split(":");
				if (words[0].equalsIgnoreCase(key)) {
					flag = true;
				}

				if (flag == false) {
					out.write(line1 + "\n");
				}

				line1 = br1.readLine();
			}
			out.close();
			br1.close();
			br1 = null;
			obj.close();
			fileScanner.close();

			// Deleting the old file and renaming the temporary file
			FileUtils.forceDelete(oldFile);
			File renamed = new File("D:\\File1.txt");
			if (newFile.renameTo(renamed))
				System.out.println("\nDeleted Successfully!!\n");

			// Checking is the file is empty and deleting if empty
			if (renamed.length() == 0) {
				renamed.delete();
				System.out.println("File empty:Deleted!!");
			}
		}

		catch (Exception e) {
			System.out.println(e);
		}

	}

	// Delete function for time-to-live
	static void del_with_time(String key) {
		try {
			File newFile = new File("D:\\temp.txt");
			File oldFile = new File("D:\\File1.txt");
			FileWriter obj = new FileWriter("D:\\temp.txt", true);
			BufferedWriter out = new BufferedWriter(obj);
			boolean found = false;
			Scanner fileScanner = new Scanner(oldFile);
			BufferedReader br1 = new BufferedReader(new FileReader(oldFile));
			String line1 = br1.readLine();

			// Checking if the given key matches, if matched ignore the pair and copy other
			// pairs to a temporary file
			while (fileScanner.hasNextLine()) {
				String data = fileScanner.nextLine();
				String words[] = data.split(":");
				if (words[0].equalsIgnoreCase(key)) {
					found = true;
				}
			}
			if (found == false) {
				System.out.println("Key not found!!\n\n");
				br1.close();
				br1 = null;
				out.close();
				fileScanner.close();
				return;
			}
			while (line1 != null) {
				boolean flag = false;
				String words[] = line1.split(":");
				if (words[0].equalsIgnoreCase(key)) {
					flag = true;
				}

				if (flag == false) {
					out.write(line1 + "\n");
				}

				line1 = br1.readLine();
			}
			out.close();
			br1.close();
			br1 = null;
			obj.close();
			fileScanner.close();

			// Deleting the old file and renaming the temporary file
			FileUtils.forceDelete(oldFile);
			File renamed = new File("D:\\File1.txt");
			if (newFile.renameTo(renamed))
				System.out.println("\nDeleted Successfully!!\n");

			// Checking is the file is empty and deleting if empty
			if (renamed.length() == 0) {
				renamed.delete();
				System.out.println("File empty:Deleted!!");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@SuppressWarnings("unchecked")

	// Creating new entry with time-to-live property
	static void create_with_time() {
		try {
			File file = new File("D:\\File1.txt");

			// Checking if file already exists
			if (file.exists())
				System.out.println("\nFile already exists!");
			else {
				file.createNewFile();
				System.out.println("\nNew File created as:" + file.getName());
			}

			boolean notExists = true;
			String key = new String();

			System.out.println("\nEnter time in seconds:");
			int temp = s.nextInt();
			int time = temp * 1000;

			Scanner fileScanner = new Scanner(file);

			System.out.println("\nEnter key:");
			while (notExists) {
				key = s.next();

				// Checking length of the key
				if (key.length() > 32) {
					while (key.length() > 32) {
						System.out.println("\nKey size limit exceeded, Enter a new key:");
						key = s.next();
					}
				}
				boolean found = false;

				// Checking if key already exists
				while (fileScanner.hasNextLine()) {
					found = false;
					String data = fileScanner.nextLine();
					String words[] = data.split(":");
					if (words[0].equalsIgnoreCase(key)) {
						System.out.println("\nKey already exists, Enter a new key:");
						found = true;
					}
					if (found == true)
						break;
				}
				if (found == false)
					break;

			}
			fileScanner.close();

			// Creating a JSON Object
			JSONObject jsonObject = new JSONObject();
			System.out.println("\nEnter number of values in JSON Object:");
			int n = s.nextInt();
			System.out.println("!!! Please enter in this format 'key:value'!!!");
			for (int i = 0; i < n; i++) {
				String data = s.next();
				String words[] = data.split(":");
				jsonObject.put(words[0], words[1]);
				words = null;
			}

			System.out.println("\nJSON object created: " + jsonObject);

			// Writing contents to the file
			FileWriter obj = new FileWriter("D:\\File1.txt", true);
			BufferedWriter out = new BufferedWriter(obj);
			out.write(key + ":" + jsonObject.toJSONString() + "\n");
			out.close();
			System.out.println("Successfully written to the file!!!\n\n");

			// Calculating the time to live
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			long current = timestamp.getTime();
			System.out.println("Current Time:" + timestamp.toString());
			long future = current + time;

			DateFormat simple = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSS");
			Date result = new Date(future);
			System.out.println("File will be deleted at:" + simple.format(result));

			TimeUnit.MILLISECONDS.sleep(time);

			Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
			current = timestamp1.getTime();

			// Deleting after it expires
			if (current > future)
				del_with_time(key);

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		boolean t = true;

		// Main menu
		while (t) {
			System.out.println("*************************************************");
			System.out.println("1. Create a new key-value pair");
			System.out.println("2. Create a new key-value pair with Time-To-Live");
			System.out.println("3. Read a key-value pair");
			System.out.println("4. Delete a key-value pair");
			System.out.println("0. Exit");
			System.out.println("*************************************************");

			System.out.println("Enter your option:");
			int option = s.nextInt();
			switch (option) {
			case 0:
				t = false;
				System.out.println("Thankyou!!");
				break;
			case 1:
				create_new();
				break;
			case 2:
				create_with_time();
				break;
			case 3:
				read();
				break;
			case 4:
				delete();
				break;
			default:
				System.out.println("Please enter a valid value!!");
				break;
			}
		}
	}
}
