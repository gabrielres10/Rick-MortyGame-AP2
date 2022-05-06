package ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import model.Board;
import model.Player;
import model.Token;
import model.UserData;

/**
 * This is the main class, it will execute all the operations of the video game
 * "Rick and Morty"
 * 
 * @author Gabriel Restrepo
 * @author Yessica Santander
 * @author Camilo Gonzalez
 *
 */
public class Main {
	private static Board board;
	static Scanner sc = new Scanner(System.in);

	/**
	 * Main method of JAVA
	 * 
	 * @param args, String[], contains the information supplied to the command line
	 *              arguments as an Array of String objects.
	 */
	public static void main(String[] args) {

		if (fileExists("data/userData.txt")) {
			loadUserData();
		}
		if (fileExists("data/podium.txt")) {
			loadPodium();
		}
		int rickIndex = -1;
		int mortyIndex;
		System.out.println("Rick: ");
		rickIndex = login(rickIndex);
		System.out.println("Morty: ");
		mortyIndex = login(rickIndex);
		initBoard(rickIndex, mortyIndex);
		int i = 0;

		long startTime = System.nanoTime();

		while (board.getWinner() == null) {
			if (i == 0) {
				showMenu(i);
				i = 1;
			} else {
				showMenu(i);
				i = 0;
			}

			if (board.getBoard().gameOver()) {
				System.out.println("-----No more seeds on the board-----");
				long endTime = System.nanoTime();
				long duration = (long) ((endTime - startTime) * 0.000000001);
				System.out.println(board.determineWinner(duration));
				System.out.println(board.getPodium());
				savePodiumAsJavaByteCode();
				break;
			}
		}

	}

	/**
	 * This method asks for the user its <b>nickname</b>, in order to verify if it
	 * is currently registered. If the user is'n registered yet, it will be added to
	 * the static variable <b>dataUser</b>
	 * 
	 * @param rick, int, this is the index of the user who will play rick
	 * @return playerIndex, int, this is the index of the player that is trying to
	 *         login, it will be the size of the array if its a new player,
	 *         otherwise, it will correspond to its first occurrence into the array
	 */
	private static int login(int rick) {
		// TODO Auto-generated method stub
		Player newPlayer = null;
		String nickname = "";
		int playerIndex = 0;
		boolean flag = false;
		do {
			System.out.println("Type your username:");
			nickname = sc.next();
			newPlayer = new Player(nickname);

			if (rick != -1 && nickname.equals(UserData.userData.get(rick).getNickName())) {
				System.out.println("You can't login twice");
				flag = true;
			} else if (!verifyUserExists(nickname)) {
				UserData.userData.add(newPlayer);
				saveUsersAsJavaByteCode();
				System.out.println("New player " + nickname + " registered");
				playerIndex = UserData.userData.indexOf(newPlayer);
				break;
			} else {
				System.out.println("Welcome again " + nickname);
				playerIndex = getIndexOf(nickname);
				flag = false;
			}
		} while (flag);
		return playerIndex;
	}

	/**
	 * This method gets the position of a player with a specific name
	 * 
	 * @param nickname, this is the name to search
	 */
	private static int getIndexOf(String nickname) {
		for (int i = 0; i < UserData.userData.size(); i++) {
			if (UserData.userData.get(i).getNickName().equals(nickname)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * This method verifies if the user with the nickname entered as a parameter
	 * exists.
	 * 
	 * @param nickname, String, this is the nickname of the player to be found
	 * @return True, it returns true if the user with the nickname exists, false
	 *         otherwise
	 */
	private static boolean verifyUserExists(String nickname) {
		for (int i = 0; i < UserData.userData.size(); i++) {
			if (UserData.userData.get(i).getNickName().equals(nickname)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method confirms if the file named "UserData" exists
	 * 
	 * @return out, true if the data file exists
	 */
	public static boolean fileExists(String path) {
		boolean out = false;
		File aux = new File(path);
		if (aux.exists()) {
			out = true;
		}
		return out;

	}

	/**
	 * This method saves all the data contained on the ArrayList
	 * "UserData.userData"
	 */
	public static void saveUsersAsJavaByteCode() {
		try {
			ArrayList<Player> userList = UserData.userData;
			File ref = new File("data/userData.txt");
			FileOutputStream fos = new FileOutputStream(ref);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(userList);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This Method loads the serialized information in the static variable
	 * UserData.UserData for its later use in execution
	 */
	public static void loadUserData() {

		try {
			File file = new File("data/userData.txt");
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ArrayList<Player> userList = (ArrayList<Player>) ois.readObject();
			UserData.userData = userList;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method saves all the data contained on the ArrayList
	 * "UserData.podium"
	 */
	public static void savePodiumAsJavaByteCode() {
		try {
			ArrayList<Player> podium = UserData.podium;
			File ref = new File("data/podium.txt");
			FileOutputStream fos = new FileOutputStream(ref);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(podium);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This Method loads the serialized information in the static variable
	 * UserData.podium for its later use in execution
	 */
	public static void loadPodium() {

		try {
			File file = new File("data/podium.txt");
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ArrayList<Player> podium = (ArrayList<Player>) ois.readObject();
			UserData.podium = podium;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * This is the method in charge of showing the different action options of the
	 * system, it also reads what the user wants to do according to the numbers
	 * assigned to each of the options
	 */
	public static void showMenu(int i) {
		boolean exit = false;
		int opcion = 0;

		while (!exit && board.getWinner() == null) {
			String player = i == 0 ? "Rick" : "Morty";

			System.out.println("It is " + player + "'s turn! ---What do you want to do?----\n" + "1. Roll dice\n"
					+ "2. See board\n" + "3. See links\n" + "4. Scoreboard \n" +

					"\nEnter an option\n");
			opcion = sc.nextInt();

			switch (opcion) {

			case 1:
				throwDice(i);
				exit = true;
				break;
			case 2:
				showBoard();
				break;
			case 3:
				showLinks();
				break;
			case 4:
				showScore();
				break;
			default:
				System.out.println("Please, type a valid option");
				break;
			}
		}
	}

	/**
	 * This method is responsible for generating a random integer between 1 and 6,
	 * representing the dice. When the die is generated and displayed, the user is
	 * asked if they want to move forward or backward.
	 */
	public static void throwDice(int i) {
		int option = 1;
		int dice = 1 + (int) (Math.random() * 6);
		System.out.println(dice + " is the value of the dice!\n");

		do {
			if (option != 1 && option != 2) {
				System.out.println("Type a valid value :)");
			}
			System.out.println("Where should you move to?\n" + "1. Forward \n" + "2. Backward");

			option = sc.nextInt();
		} while (option != 1 && option != 2);

		Token playerToMove = i == 0 ? new Token('R') : new Token('M');
		switch (option) {
		case 1:
			moveForward(dice, playerToMove);
			break;

		case 2:
			moveBackward(dice, playerToMove);
			break;
		}

	}

	/**
	 * This method shows a graphical representation in ASCII characters of the game
	 * board.
	 */
	public static void showBoard() {
		System.out.println(board.showBoard());
	}

	/**
	 * This method shows a graphical representation in ASCII characters of the links
	 * of the game board.
	 */
	public static void showLinks() {
		System.out.println(board.showLinks());
	}

	/**
	 * This method returns a historical score table of the users who have played the
	 * game
	 */
	public static void showScore() {
		System.out.println(board.getBoard().showScores());
	}

	/**
	 * This method triggers player movement forward
	 * 
	 * @param diceValue, int, Number of times the player will move forward
	 */
	public static void moveForward(int diceValue, Token tokenToMove) {
		board.getBoard().movePlayerForward(diceValue, tokenToMove);
		System.out.println(board.showBoard());
	}

	/**
	 * This method triggers player movement backward
	 * 
	 * @param diceValue, int, Number of times the player will move backward
	 */
	public static void moveBackward(int diceValue, Token tokenToMove) {
		board.getBoard().movePlayerBackward(diceValue, tokenToMove);
		System.out.println(board.showBoard());
	}

	/**
	 * This method reads the parameters that the user wants their board to have.
	 * 
	 * @param rickIndex,  int, this is the index of the player 1, "Rick", that is
	 *                    trying to login, it will be the size of the array if its a
	 *                    new player, otherwise, it will correspond to its first
	 *                    occurrence into the array
	 * @param mortyIndex, int, this is the index of the player 2, "Morty", that is
	 *                    trying to login, it will be the size of the array if its a
	 *                    new player, otherwise, it will correspond to its first
	 *                    occurrence into the array
	 */
	public static void initBoard(int rickIndex, int mortyIndex) {

		System.out.println("\n **** Creating the board! **** \n");
		int n, m, p, q;
		System.out.println("Enter the number of rows");
		m = sc.nextInt();
		System.out.println("Enter the number of columns");
		n = sc.nextInt();
		do {
			System.out.println("Enter the quantity of links");
			p = sc.nextInt();
			if (p > (n * m) / 2) {
				System.out.println("The amount of links must be less than " + (n * m) / 2);
			}
		} while (p > (n * m) / 2);

		System.out.println("Enter the quantity of seeds");
		do {
			q = sc.nextInt();
			if(q >= n * m) {
				System.out.println("The amount of seeds must be less or equal to " + (n * m) / 2);
			}
		} while (q >= n * m);

		board = new Board(rickIndex, mortyIndex, m, n, q, p);

		System.out.println("THE BOARD HAS BEEN CREATED SUCCESSFULLY");
		System.out.println(board.showBoard());

	}

}
