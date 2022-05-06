package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ArrayList<Player> userData = new ArrayList<>();
	public static ArrayList<Player> podium = new ArrayList<>();
	
	/**
	 * This method adds a new player and sorts the list
	 * @param player, Player, this is the new player
	 */
	public static void addAndSort(Player player) {
		podium.add(player);
		Collections.sort(userData, new Comparator<Player>() {

			@Override
			public int compare(Player A, Player B) {
				if(A.getScore() == B.getScore()) {
					return 0;
				}else {
					if(A.getScore() > B.getScore()) {
						return 1;
					}else {
						return -1;
					}
				}
			}
			
		});
	}
}
