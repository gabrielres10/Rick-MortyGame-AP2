package model;

public class Board {

	private int rick; // this is the index of the player which moves as rick on the game
	private int morty; // this is the index of the player which moves as morty on the game
	private int n; // this is the amount of rows
	private int m; // this is the amount of columns
	private Player winner; // this is the winner of the game

	private LinkedList board = new LinkedList(n * m);

	/**
	 * This is the constructor method for the class Board
	 * 
	 * @param rick,  Player, this is the player which is going to move over the
	 *               table as Rick
	 * @param morty, Player, this is the player which is going to move over the
	 *               table as Morty
	 * @param n,     int, this is the amount of rows
	 * @param m,     int, this is the amount of columns
	 * @param q,     int, this is the amount of seeds over the board
	 * @param p,     int, this is the amount of links over the boards
	 */
	public Board(int rick, int morty, int n, int m, int q, int p) {
		this.m = m;
		this.n = n;
		this.rick = rick;
		this.morty = morty;
		this.setWinner(null);
		createBoard(n, m, q, p);

	}

	public void createBoard(int n, int m, int q, int p) {
		// int n, int seeds, int links
		board.init((n * m), q, p);
	}

	/**
	 * @return the rick
	 */
	public int getRick() {
		return rick;
	}

	/**
	 * @param rick the rick to set
	 */
	public void setRick(int rick) {
		this.rick = rick;
	}

	/**
	 * @return the morty
	 */
	public int getMorty() {
		return morty;
	}

	/**
	 * @param morty the morty to set
	 */
	public void setMorty(int morty) {
		this.morty = morty;
	}

	/**
	 * @return the n
	 */
	public int getN() {
		return n;
	}

	/**
	 * @param n the n to set
	 */
	public void setN(int n) {
		this.n = n;
	}

	/**
	 * @return the m
	 */
	public int getM() {
		return m;
	}

	/**
	 * @param m the m to set
	 */
	public void setM(int m) {
		this.m = m;
	}

	/**
	 * @return the winner
	 */
	public Player getWinner() {
		return winner;
	}

	/**
	 * @param winner the winner to set
	 */
	public void setWinner(Player winner) {
		this.winner = winner;
	}

	
	public LinkedList getBoard() {
		return board;
	}

	public void setBoard(LinkedList board) {
		this.board = board;
	}

	public String showBoard() {
		return board.toString(m, n);
	}
	
	/**
	 * This method gets the String of the board with the links
	 * @return String, this is the string of the board with the links
	 */
	public String showLinks() {
		return board.toStringLinks(m, n);
	}

	/**
	 * This method returns a string with the winner information
	 * @param duration, long, this is the duration of the program 
	 * @return output, String, this is the information of the winner
	 */
	public String determineWinner(long duration) {
		// TODO Auto-generated method stub
		String output = "";
		int mortySeeds = board.getMortyScore();
		int rickSeeds = board.getRickScore();
		int rickScore = (int) (rickSeeds * 120 - duration);
		int mortyScore = (int) (mortySeeds * 120 - duration);
		if(rickScore>mortyScore) {
			UserData.addAndSort(UserData.userData.get(rick));
			output = "Rick ha ganado con " + rickSeeds;
		}else if (rickScore<mortyScore) {
			UserData.addAndSort(UserData.userData.get(morty));
			output = "Morty ha ganado con " + mortySeeds;
		}else {
			output = "EMPATE";
		}
		return output;
	}

}
