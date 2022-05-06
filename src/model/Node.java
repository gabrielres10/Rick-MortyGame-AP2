package model;

public class Node {
	//Datos
	private int index;
	private Token rick;
	private Token morty;
	private String linkTo;
	private boolean linked;
	private boolean seed;


	//Enlaces
	private Node prev;
	private Node next;
	private Node link;
	

	public Node getLink() {
		return link;
	}


	public void setLink(Node link) {
		this.link = link;
	}


	public Node(int index) {
		this.index = index;
		this.linkTo = " ";
	}
	

	public Node() {
		this.linkTo = " ";
	}
	
	
	public boolean getSeed() {
		return seed;
	}

	public void setSeed(boolean seed) {
		this.seed = seed;
	}
	
	public Token getRick() {
		return rick;
	}

	public void setRick(Token rick) {
		this.rick = rick;
	}
	
	public Token getMorty() {
		return morty;
	}

	public void setMorty(Token morty) {
		this.morty = morty;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Node getPrev() {
		return prev;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}


	/**
	 * @return the linkTo
	 */
	public String getLinkTo() {
		return linkTo;
	}


	/**
	 * @param linkTo the linkTo to set
	 */
	public void setLinkTo(String linkTo) {
		this.linkTo = linkTo;
	}


	/**
	 * @return the linked
	 */
	public boolean isLinked() {
		return linked;
	}


	/**
	 * @param linked the linked to set
	 */
	public void setLinked(boolean linked) {
		this.linked = linked;
	}
	
	
}
