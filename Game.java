import java.util.Scanner;
public class Game {
	static Slot[][] board = new Slot[7][6];
	static int p1Score = 0, p2Score = 0;
	public static void main(String[] args) {
		//-----------------------------------------------------------
		do {
			createBoard();
			displayBoard();					//Two-dimensional array of board is built.
			Player p1 = new Player('x');  	//Players initialized.
			Player p2 = new Player('o');
			
			while (true){ 
				if (p1.chipsRemaining() == 0) {
					System.out.println("All chips have been played, it is a tie!");
					break;
				}
				//Player 1's turn
				System.out.println("Player 1 - Chips Remaining: "+p1.chipsRemaining());
				p1.dropChip(board);
				displayBoard();
				if (p1.isWinner() == true){
					System.out.print("Player 1 is the winner!");
					p1Score++;
					break;
				} 		  //Player 2's turn
				System.out.println();
				System.out.println("Player 2 - Chips Remaining: "+p2.chipsRemaining());
				p2.dropChip(board);
				displayBoard();
				if (p2.isWinner() == true){
					System.out.print("Player 2 is the winner!");
					p2Score++;
					break;
				}
				System.out.println();
			}
			printScore();
			
		} while(playAgain());
	}//---------------------------------------------------------------------------------------------------
	static void createBoard(){
		for(int i=0;i<7;i++){
			for(int j=0;j<6;j++){
				board[i][j] = new Slot(' ');
			}
		}
	}//---------------------------------------------------------------------------------------------------
	static void displayBoard(){
		System.out.println("| 1  2  3  4  5  6  7 |");
		for(int i=5;i>=0;i--){
			System.out.print("|");
			for(int j=0;j<7;j++){
				System.out.print("("+board[j][i].getStatus()+")");
			}
			System.out.print("|");
			System.out.println();
		}
		System.out.println();
	}
	static void printScore() {
		System.out.println("\n\nSCOREBOARD\nPlayer 1 : " + p1Score + "\nPlayer 2 : " + p2Score);
	}
	
	static boolean playAgain() {
		String buffer; // buffer string
		char entry = ' ';
		boolean validEntry = false, playAgain = false;
		Scanner s; // scanner and bufferScanner
		while (!validEntry) {
			System.out.print("Would you like to play again? (y/n)");
			s = new Scanner(System.in);
			buffer = s.next();
			entry = Character.toLowerCase(buffer.charAt(0));
			
			if (entry == 'y') {
				playAgain = true;
				validEntry = true;
			}
			else if (entry == 'n') {
				playAgain = false;
				validEntry = true;
				System.out.println("Thank you for playing Connect4!");
			}
			else {
				System.out.println("\nInvalid entry.");
				validEntry = false;
			}
		}
		return playAgain;
	}
	
}