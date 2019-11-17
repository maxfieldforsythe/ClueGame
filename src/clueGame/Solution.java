package clueGame;

public class Solution {

	public String person;
	public String room;
	public String weapon;
	
	public Solution() {
		this.person = null;
		this.room = null;
		this.weapon = null;
	}

	public Solution(String person, String weapon, String room) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
}
