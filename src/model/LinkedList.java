package model;

import java.util.concurrent.ThreadLocalRandom;

public class LinkedList {

	// Head
	private Node head;
	private int size;
	private int boxesQuant;

	public LinkedList(int boxesQuant) {
		this.boxesQuant = boxesQuant;
		setSize(0);
	}

	/**
	 * 
	 * This method initializes the board with the parameters that the user has
	 * chosen
	 * 
	 * @param n,     int, Number of boxes, given by the product between the rows and
	 *               the columns previously entered by the user
	 * @param seeds, int, Number of seeds that the user decided to generate on the
	 *               board
	 * @param links, int Number of links that the user decided to generate on the
	 *               board
	 */
	public void init(int n, int seeds, int links) {
		if (n == 0) {
			setValuesOfBoard(seeds, links);
			return;
		}
		size++;

		if (head == null) {
			boxesQuant = n;
			Node node = new Node(1);
			head = (Node) node;
			head.setNext(head);
			head.setPrev(head);
			init(n - 1, seeds, links);

		} else {
			Node node = new Node();
			Node tail = node;

			tail.setNext(head);
			tail.setPrev(head.getPrev());

			tail.setIndex(tail.getPrev().getIndex() + 1);

			head.getPrev().setNext(tail);
			head.setPrev(tail);
			init(n - 1, seeds, links);
		}
	}

	/**
	 * This method randomly generates the seeds and links on the board, and also
	 * randomly places Rick and Morty.
	 * 
	 * @param seeds, int, Number of seeds that the user decided to generate on the
	 *               board
	 * @param links, int Number of links that the user decided to generate on the
	 *               board
	 */
	private void setValuesOfBoard(int seeds, int links) {
		// TODO Auto-generated method stub
		putSeeds(seeds);
		generateLinks(links);
		setRickAndMorty();
		setValuesOfLinks();
	}

	/**
	 * This method randomly places Rick and Morty around the board.
	 */
	private void setRickAndMorty() {
		int i = 1 + (int) (Math.random() * boxesQuant - 1);
		int j = 1 + (int) (Math.random() * boxesQuant - 1);
		if (i != j && !get(head, i).getSeed() && !get(head, j).getSeed()) {
			get(head, i).setRick(new Token('R'));
			get(head, j).setMorty(new Token('M'));
		} else {
			setRickAndMorty();
		}

	}

	/**
	 * This method randomly generates the pairs of links that the user has decided
	 * to generate on the board
	 * 
	 * @param links, int Number of links that the user decided to generate on the
	 *               board
	 */
	private void generateLinks(int links) {
		int i = 0;
		int j = 0;
		while (i == j) {
			i = 1 + (int) (Math.random() * (boxesQuant - 1));
			j = 1 + (int) (Math.random() * (boxesQuant - 1));
		}

		if (links != 0) {
			if (get(head, i).getLink() != null || get(head, j).getLink() != null) {
				generateLinks(links);
			} else {
				get(head, i).setLink(get(head, j));
				get(head, j).setLink(get(head, i));
				generateLinks(links - 1);
			}
		}
	}

	/**
	 * This method randomly generates the seeds that the user has decided to
	 * generate on the board
	 * 
	 * @param seeds, int, Number of seeds that the user decided to generate on the
	 *               board
	 */
	private void putSeeds(int seeds) {
		int i = 1 + (int) (Math.random() * boxesQuant - 1);

		if (seeds != 0) {
			// If the node with the index i has already a seed, the program loops again
			if (get(head, i).getSeed()) {
				putSeeds(seeds);
			} else {
				get(head, i).setSeed(true);
				putSeeds(seeds - 1);
			}
		}
	}

	/**
	 * This method returns a node given the ordinal that identifies it
	 * 
	 * @param current, Node, This is the node over which the recursion iteration is
	 *                 being performed
	 * @param i,       int, This is the ordinal that identifies the node we want the
	 *                 method to return
	 * @return Node, Null if the node with the indicated ordinal is not found on the
	 *         board, otherwise it returns the node to which the ordinal belongs
	 */
	private Node get(Node current, int i) {
		if (current.getNext() != head) {
			if (current.getIndex() == i) {
				return current;
			}
			return get(current.getNext(), i);
		} else {
			return null;
		}

	}

	/**
	 * Trigger method of the to String method
	 * 
	 * @param m, int, stop condition
	 * @return it returns an ASCII representation of the board
	 */
	public String toString(int m, int n) {
		return toString(head, "", m, n, 0);
	}

	/**
	 * * This method creates a graphical representation in ASCII characters of the
	 * board.
	 * 
	 * @param current, Node, this is the node of the recursion, this node will
	 *                 change according to the conditions
	 * @param acc,     String, accumulated ASCII representation of the board
	 * @param m,       this is the number of columns of the board
	 * @param n,       this is the number of nodes in the LinkedList
	 * @param j,       this is the number of rows of the board
	 * @return acc, String, accumulated ASCII representation of the board
	 */
	public String toString(Node current, String acc, int m, int n, int j) {
		if (j == n) {
			return acc;
		}

		if (j % 2 == 0) {
			acc += getIndexesRight(current, 0, m, "") + "\n";
		} else {
			acc += getIndexesLeft(current, 0, m, "") + "\n";
		}

		return toString(increaseNode(current, 0, m), acc, m, n, j + 1);
	}

	/**
	 * This method increases <b>m</b> times the node. It means the resulting node
	 * will be this number of times in front the one entered as a parameter
	 * 
	 * @param current, Node, this is the node of the recursion, this node can vary
	 *                 according to the preconditions of the algorithm
	 * @param i,       int, this is the variable of increment
	 * @param limit,   int, this is the limit. The recursion ends when <b>i</b> is
	 *                 equal to it
	 * @return current, Node, this is the increased node
	 */
	public Node increaseNode(Node current, int i, int limit) {
		if (i == limit) {
			return current;
		}
		return increaseNode(current.getNext(), i + 1, limit);
	}

	/**
	 * This method gets the index from a node until the limit is reached, it returns
	 * a String with the values of the nodes added by the right side
	 * 
	 * @param current, Node, this is the node of the recursion, this node can vary
	 *                 according to the preconditions of the algorithm
	 * @param limit,   int, this is the limit. The recursion ends when <b>i</b> is
	 *                 equal to it
	 * @param i,       int, this is the variable of increment
	 * @param output,  String, this is a string formed by adding the values of the
	 *                 nodes ( valueOfNode(current) ) to the right
	 * @return output, String, this is a string formed by adding the values of the
	 *         nodes ( valueOfNode(current) ) to the right
	 */
	public String getIndexesRight(Node current, int i, int limit, String output) {
		if (i == limit) {
			return output;
		}
		output += valueOfNode(current);
		return getIndexesRight(current.getNext(), i + 1, limit, output);

	}

	/**
	 * This method gets the index from a node until the limit is reached, it returns
	 * a String with the values of the nodes added by the left side
	 * 
	 * @param current, Node, this is the node of the recursion, this node can vary
	 *                 according to the preconditions of the algorithm
	 * @param limit,   int, this is the limit. The recursion ends when <b>i</b> is
	 *                 equal to it
	 * @param i,       int, this is the variable of increment
	 * @param output,  String, this is a string formed by adding the values of the
	 *                 nodes ( valueOfNode(current) ) to the left
	 * @return output, String, this is a string formed by adding the values of the
	 *         nodes ( valueOfNode(current) ) to the left
	 */
	public String getIndexesLeft(Node current, int i, int limit, String output) {
		if (i == limit) {
			return output;
		}
		output = valueOfNode(current) + output;
		return getIndexesLeft(current.getNext(), i + 1, limit, output);

	}

	/**
	 * This method gives a graphical representation of the value of Node: 1. The
	 * cells containing seeds are represented by a special ASCII character with the
	 * code 248 ("ø"). 2. If Rick is in the box, it is represented by a capital r
	 * ("R"). If Morty is in the box, it is represented by a capital m ("M"). 3. If
	 * both players, Rick and Morty, are in the same box, it will be represented
	 * with a special ASCII character, it has the code 127 ("").
	 * 
	 * @param node, Node, Node to which you want to represent its value
	 * 
	 * @return out, String, ASCII representation of the value of the variable node
	 */
	private String valueOfNode(Node node) {
		String out = "";
		boolean flag = false;
		if (node.getRick() != null && node.getMorty() != null) {
			char a = (char) 127;
			out = "" + a;
			flag = true;
		} else {
			if (node.getRick() != null) {
				out = "R";
			} else if (node.getMorty() != null) {
				out = "M";
			} else if (node.getSeed()) {
				char a = (char) 248;
				out = "" + a;
			} else {
				out = node.getIndex() + "";
			}
		}

		return "[ " + out + " ]";
	}

	/**
	 * This method executes the method to move the player backward
	 * 
	 * @param dice,        int, this is the number of steps to move the player
	 * @param tokenToMove, Token, this represents the type of token that must be
	 *                     moved (rick or morty)
	 */
	public void movePlayerBackward(int dice, Token tokenToMove) {
		Node playerNode = findPlayer(head, tokenToMove);

		// movePlayer(playerNode, dice);
		Token t = new Token();
		if (tokenToMove.getAsciiSymbol() == 'R') {
			t = playerNode.getRick();
			playerNode.setRick(null);
		} else {
			t = playerNode.getMorty();
			playerNode.setMorty(null);
		}
		movePlayerBackward(playerNode, t, dice, tokenToMove);

	}

	/**
	 * This method executes the method to move the player forward
	 * 
	 * @param dice,        int, this is the number of steps to move the player
	 * @param tokenToMove, Token, this represents the type of token that must be
	 *                     moved (rick or morty)
	 */
	public void movePlayerForward(int dice, Token tokenToMove) {
		Node playerNode = findPlayer(head, tokenToMove);
		// movePlayer(playerNode, dice);
		Token t = new Token();
		if (tokenToMove.getAsciiSymbol() == 'R') {
			t = playerNode.getRick();
			playerNode.setRick(null);
		} else {
			t = playerNode.getMorty();
			playerNode.setMorty(null);
		}
		movePlayerForward(playerNode, t, dice, tokenToMove);

	}

	/**
	 * This method moves the player forward according to the number in the parameter
	 * 
	 * @param current,     Node, this is the node where the token is in the first
	 *                     time
	 * @param t,           Token, this is the token that was on a position on the
	 *                     board, this node will be set to the resulting position of
	 *                     moving the amount of forward steps that the dice
	 *                     represents
	 * @param dice,        int, this is the number of places that the player must be
	 *                     moved
	 * @param tokenToMove, Token, this represents the type of token that must be
	 *                     moved (rick or morty)
	 */
	private void movePlayerForward(Node current, Token t, int dice, Token tokenToMove) {
		// condicion de parada
		if (dice == 0) {
			boolean link = false;
			boolean rick = false;
			int seed = 0;
			if (current.getSeed()) {
				current.setSeed(false);
				seed = 1;
			}
			if (current.getLink() != null) {
				link = true;
			}

			if (tokenToMove.getAsciiSymbol() == 'R') {
				t.addCollectedSeeds(seed);
				current.setRick(t);
				if (link) {
					rick = true;
				}
			} else {
				t.addCollectedSeeds(seed);
				current.setMorty(t);
			}
			travelByLink(current, rick);

			return;
		}
		// Metodo recursivo
		movePlayerForward(current.getNext(), t, dice - 1, tokenToMove);
	}

	/**
	 * This method sets the token of a node into its link
	 * 
	 * @param node, Node, this is the node that has a link
	 * @param rick, boolean, it is true if the token is rick, false if it is morty
	 */
	private void travelByLink(Node node, boolean rick) {
		Node found = get(head, node.getIndex());
		found.setRick(null);
		found.setMorty(null);
		if(node.getLink()!=null) {
			if (rick) {
				node.getLink().setRick(new Token('R'));
				if (node.getSeed()) {
					node.getLink().setSeed(false);
					node.getLink().getRick().addCollectedSeeds();
					;
				}
			} else {
				node.getLink().setMorty(new Token('M'));
				if (node.getSeed()) {
					node.getLink().setSeed(false);
					node.getLink().getMorty().addCollectedSeeds();
					;
				}
			}
		}
		
	}

	/**
	 * This method moves the player backward according to the number in the
	 * parameter
	 * 
	 * @param current,     Node, this is the node where the token is in the first
	 *                     time
	 * @param t,           Token, this is the token that was on a position on the
	 *                     board, this node will be set to the resulting position of
	 *                     moving the amount of backward steps that the dice
	 *                     represents
	 * @param dice,        int, this is the number of places that the player must be
	 *                     moved
	 * @param tokenToMove, Token, this represents the type of token that must be
	 *                     moved (rick or morty)
	 */
	private void movePlayerBackward(Node current, Token t, int dice, Token tokenToMove) {
		// condicion de parada
		if (dice == 0) {
			boolean link = false;
			boolean rick = false;
			int seed = 0;
			if (current.getSeed()) {
				current.setSeed(false);
				seed = 1;
			}
			if (current.getLink() != null) {
				link = true;
			}

			if (tokenToMove.getAsciiSymbol() == 'M') {
				t.addCollectedSeeds(seed);
				current.setRick(t);
			} else {
				t.addCollectedSeeds(seed);
				current.setMorty(t);
				if (link) {
					rick = true;
				}
			}

			travelByLink(current, rick);

			return;
		}
		// Metodo recursivo
		movePlayerBackward(current.getPrev(), t, dice - 1, tokenToMove);
	}

	/**
	 * This method gets the player selected as a parameter
	 * 
	 * @param current,     Node, this is the node of the recursion, in the first
	 *                     time this node will be the head of the linked list
	 * @param tokenToFind, Token, This is the token that must be found
	 * @return
	 */
	private Node findPlayer(Node current, Token tokenToFind) {
		if (current.getRick() != null) {
			if (current.getRick().getAsciiSymbol() == tokenToFind.getAsciiSymbol()) {
				return current;
			}
		}
		if (current.getMorty() != null) {
			if (current.getMorty().getAsciiSymbol() == tokenToFind.getAsciiSymbol()) {
				return current;
			}
		}
		return findPlayer(current.getNext(), tokenToFind);
	}

	/**
	 * This method returns a String with the information of the collected seeds of
	 * rick and morty
	 * 
	 * @return String, this is a string with the number of seeds that each player
	 *         has currently
	 */
	public String showScores() {
		Node rick = findPlayer(head, new Token('R'));
		Node morty = findPlayer(head, new Token('M'));
		return " Rick: " + rick.getRick().getCollectedSeeds() + " seeds \n " + "Morty: "
				+ morty.getMorty().getCollectedSeeds() + " seeds";
	}

	/**
	 * This method executes the one that verifies if the game is over
	 * 
	 * @return
	 */
	public boolean gameOver() {
		return gameOver(head, true);
	}

	/**
	 * This method verifies if the board has at least one seed
	 * 
	 * @param current, Node, this is the node of the recursion, it will be the head
	 *                 in the first time
	 * @param out,     boolean, it is true if one seed is found, false otherwise
	 * @return out, boolean, true if one seed is found, false otherwise
	 */
	private boolean gameOver(Node current, boolean out) {
		if (current.getSeed()) {
			out = false;
		}

		if (!out || current.getNext().equals(head)) {
			return out;
		}

		return gameOver(current.getNext(), out);
	}

	/**
	 * This method gets the number of seeds of Rick
	 * 
	 * @return int , number of seeds of Rick
	 */
	public int getRickScore() {
		return findPlayer(head, new Token('R')).getRick().getCollectedSeeds();
	}

	/**
	 * This method gets the number of seeds of Morty
	 * 
	 * @return int , number of seeds of Morty
	 */
	public int getMortyScore() {
		return findPlayer(head, new Token('M')).getMorty().getCollectedSeeds();
	}

	/**
	 * Trigger method of the to String method
	 * 
	 * @param m, int, stop condition
	 * @return it returns an ASCII representation of the board
	 */
	public String toStringLinks(int m, int n) {
		return toStringLinks(head, "", m, n, 0);
	}

	public String toStringLinks(Node current, String acc, int m, int n, int j) {
		if (j == n) {
			return acc;
		}

		if (j % 2 == 0) {
			acc += getIndexesRightLinks(current, 0, m, "") + "\n";
		} else {
			acc += getIndexesLeftLinks(current, 0, m, "") + "\n";
		}

		return toStringLinks(increaseNode(current, 0, m), acc, m, n, j + 1);
	}

	/**
	 * This method increases <b>m</b> times the node. It means the resulting node
	 * will be this number of times in front the one entered as a parameter
	 * 
	 * @param current, Node, this is the node of the recursion, this node can
	 *                 variate according to the pre - conditions of the algorithm
	 * @param i
	 * @param limit
	 * @return
	 */
	public Node increaseNodeLinks(Node current, int i, int limit) {
		if (i == limit) {
			return current;
		}
		return increaseNodeLinks(current.getNext(), i + 1, limit);
	}

	/**
	 * This method gets the index from a node until it
	 * 
	 * @param current
	 * @param i
	 * @return
	 */
	public String getIndexesRightLinks(Node current, int i, int limit, String output) {
		if (i == limit) {
			return output;
		}
		output += valueOfNodeForLinks(current);
		return getIndexesRightLinks(current.getNext(), i + 1, limit, output);

	}

	/**
	 * This method gets the index from a node until it
	 * 
	 * @param current
	 * @param i
	 * @return
	 */
	public String getIndexesLeftLinks(Node current, int i, int limit, String output) {
		if (i == limit) {
			return output;
		}
		output = valueOfNodeForLinks(current) + output;
		return getIndexesLeftLinks(current.getNext(), i + 1, limit, output);

	}

	private String valueOfNodeForLinks(Node node) {
		if (node.getLink() != null) {
			return "[" + node.getLinkTo() + "]";
		} else {
			return "[  ]";
		}

	}

	/**
	 * This method is the "trigger" of setValuesOfLinks
	 */
	private void setValuesOfLinks() {
		setValuesOfLinks(head);
	}

	/**
	 * This method travel all the linked list to set the value of the each node´s
	 * links
	 * 
	 * @param current
	 */
	private void setValuesOfLinks(Node current) {
		if (current.getNext() == head) {
			return;
		}
		setValueOfNodeForLinks(current);
		setValuesOfLinks(current.getNext());
	}

	/**
	 * This method sets the value of the pair of nodes that are linked
	 * 
	 * @param node
	 */
	private void setValueOfNodeForLinks(Node node) {
		String linkValue = "";
		int random = ThreadLocalRandom.current().nextInt(0, 25);
		char a = (char) (65 + random);
		linkValue = "" + a;

		if (!node.isLinked() && node.getLink() != null) {
			node.setLinkTo(linkValue);
			node.setLinked(true);
			node.getLink().setLinkTo(linkValue);
			node.getLink().setLinked(true);
		}
	}

	/**
	 * @return the size
	 */
	public int size() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

}