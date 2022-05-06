package model;

import java.io.Serializable;

public class Player implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public double score;
	public String nickname;
	
	public Player(String nickname) {
		this.nickname = nickname;
		this.score = 0;
	}
	
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getNickName() {
		return nickname;
	}
	public void setNickName(String nickName) {
		this.nickname = nickName;
	}
}
