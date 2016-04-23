import java.util.Scanner;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class Game extends JPanel{
	static Slot[][] board = new Slot[7][6];
	static int p1Score = 0, p2Score = 0;
	static boolean isComputerPlay = false;
	public static void main(String[] args) {
		//-----------------------------------------------------------
		isComputerPlay = computerPlay();
		do {
			createBoard();
			displayBoard();					//Two-dimensional array of board is built.
			Player p1 = new Player('x');  	//Players initialized.
			Player p2 = new Player('o');
			ComputerPlayer com = new ComputerPlayer();
			
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
				if (!isComputerPlay) {
					System.out.println("Player 2 - Chips Remaining: "+p2.chipsRemaining());
					p2.dropChip(board);
					displayBoard();
					if (p2.isWinner() == true){
						System.out.print("Player 2 is the winner!");
						p2Score++;
						break;
					}
				} else { // Computer's turn
					System.out.println("Computer Player - Chips Remaining: "+com.chipsRemaining());
					com.dropChip(board);
					displayBoard();
					if (com.isWinner() == true){
						System.out.print("Computer Player is the winner!");
						p2Score++;
						break;
					}

					
				}
				System.out.println();
			}
			printScore();
			
		} while(playAgain());
	}//---------------------------------------------------------------------------------------------------
	private static void createBoard(){
		for(int i=0;i<7;i++){
			for(int j=0;j<6;j++){
				board[i][j] = new Slot(' ');
			}
		}
	}//---------------------------------------------------------------------------------------------------
	private static void displayBoard(){
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
	private static void printScore() {
		System.out.print("\n\nSCOREBOARD\nPlayer 1 : " + p1Score + "\n");
		if (isComputerPlay) {
			System.out.print("Computer Player : " + p2Score + "\n");
		}
		else {
			System.out.print("Player 2 : " + p2Score + "\n");
		}
	}
	
	private static boolean computerPlay() {
		String buffer; // buffer string
		char entry;
		boolean validEntry = false, isComputerPlay = false;
		Scanner s; // scanner
		while (!validEntry) {
			System.out.print("Would you like to play against a human (h) or the computer (c)? :");			
			s = new Scanner(System.in);
			buffer = s.next();
			entry = Character.toLowerCase(buffer.charAt(0));
			if (entry == 'h') { // human is chosen
				isComputerPlay = false;
				validEntry = true;
			} else if (entry == 'c') { // computer is chosen
				isComputerPlay = true;
				validEntry = true;
			} else { //invalid entry
				System.out.print("\nInvalid entry. ");
			}
		}
		return isComputerPlay;
	}
	private static boolean playAgain() {
		String buffer; // buffer string
		char entry = ' ';
		boolean validEntry = false, playAgain = false;
		Scanner s; // scanner
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