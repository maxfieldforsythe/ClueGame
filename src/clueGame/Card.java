package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public Card(String cardName, CardType cardType) {
		this.cardName = cardName;
		this.cardType = cardType;
	}
	
	public String getName() {
		return cardName;
	}
	
	public CardType getType() {
		return cardType;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	

}
