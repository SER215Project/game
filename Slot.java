public class Slot {
	private char status;
	public Slot(){
		this.status = ' ';
	}
	public Slot(char symbol){
		this.status = symbol;
	}
	public char getStatus() { // returns status of slot
		return this.status;
	}
	public void setStatus(char symbol) { // sets status to player's symbol
		this.status = symbol;
	}
}