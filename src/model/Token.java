package model;

import java.io.Serializable;

public class Token implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int collectedSeeds;
	private Character asciiSymbol;

	public Token(Character asciiSymbol) {
		this.collectedSeeds = 0;
		this.asciiSymbol = asciiSymbol;
	}

	public Token() {

	}

	public int getCollectedSeeds() {
		return collectedSeeds;
	}

	public void setCollectedSeeds(int collectedSeeds) {
		this.collectedSeeds = collectedSeeds;
	}

	public void addCollectedSeeds(int collectedSeeds) {
		this.collectedSeeds += collectedSeeds;
	}

	public void addCollectedSeeds() {
		this.collectedSeeds++;
	}

	public Character getAsciiSymbol() {
		return asciiSymbol;
	}

	public void setAsciiSymbol(Character asciiSymbol) {
		this.asciiSymbol = asciiSymbol;
	}

}
