import java.util.Scanner;
public class Player {
	private int chips = 21;
	private char symbol;
	private boolean isWinner = false;
	public Player(char symbol){
		this.symbol = symbol;
	}
	public Slot[][] dropChip(Slot[][] board){
		int column = 0, row = 5; // initialize column to 0 for compilation, chip always starts at row 5 for checks
		String buffer; // buffer string
		boolean validEntry = false;
		Scanner s, bufferS; // scanner and bufferScanner
		
		if (this.chips == 0){
			System.out.println("You're out of chips!");
			return board;
		}

		while (!validEntry) { // Asks for input of column until valid entry
			System.out.print("Select where to drop your chip (1-7). ");
			s = new Scanner(System.in);
			buffer = s.next();
			bufferS = new Scanner(buffer);
			if (bufferS.hasNextInt()) {
				column = bufferS.nextInt() - 1; // reads buffered int entry 
				if((column < 0)||(column > 6)) {
					System.out.println("Invalid entry. ");
					validEntry = false;
					column = 0;          // resets column to zero if invalid integer
				}
				else {
					validEntry = true;
				}
			}
			else {
				System.out.println("Invalid entry. ");
				validEntry = false;
			}
			if (((board[column][row].getStatus() == ' ') == false)){ // column full if top row is not blank
				System.out.print("Column full. Select where to drop your chip (1-7). ");
				column = s.nextInt() - 1;
			}
		}
		
		while(row > 0){ // stops loop at bottom of board
			if (board[column][row-1].getStatus() == ' '){ // chip falls one row if empty slot beneath
				row--;
			} else {
				board[column][row].setStatus(this.symbol); // chip rests on top of other chip
				break;
			}
			if (row == 0) { 
				board[column][row].setStatus(this.symbol);  // chip rests at bottom of board
			}
		}
		this.chips--;
		checkWinner(column,row, board);
		return board;
	}
	private void checkWinner(final int x, final int y, Slot[][] board){ //This is where the program will scan the chips to find a 4-in-a-row pattern
		boolean[] chipChain = {false, false, false, false}; // chipChain[3] == true is win scenario
// Should have 13 checks total
		
		if(this.chips < 18) { // Doesn't check if a 4 chips haven't been placed 
			if(y > 2) { // Check 1: vertically 
				for (int i = 0; i<4; i++) { // Checks for 4 in a single column
					if (board[x][y - i].getStatus()==this.symbol){
						chipChain[i] = true;
					}
					else { // breaks for loop if chain ends
						break;
					}
				}
				if ((x<4)&&(y>2)) {// Check 2: diagonally down right
					for (int i = 0; i<4; i++) { // Checks for 4 in a diagonal down right pattern
						if (board[x + i][y - i].getStatus()==this.symbol){
							chipChain[i] = true;
						}
						else { // breaks for loop if chain ends
							break;
						}
					}
				}
				if ((y>2)&&(x>2)) {// Check 3: diagonally down left
					for (int i = 0; i<4; i++) { // Checks for 4 in a diagonal down left pattern
						if (board[x - i][y - i].getStatus()==this.symbol){
							chipChain[i] = true;
						}
						else { // breaks for loop if chain ends
							break;
						}
					}
				}
				
			}
			if (x<4) { // Check 4: horizontal right
				for (int i = 0; i<4; i++) { // Checks for 4 in a horizontal right pattern
					if (board[x+i][y].getStatus()==this.symbol){
						chipChain[i] = true;
					}
					else { // breaks for loop if chain ends
						break;
					}
				}
			}
			if(x>2) { // Check 5: horizontal left
				for (int i = 0; i<4; i++) { // Checks for 4 in a horizontal left pattern
					if (board[x-i][y].getStatus()==this.symbol){
						chipChain[i] = true;
					}
					else { // breaks for loop if chain ends
						break;
					}
				}
			}
			if((x<4)&&(y<3)) { // Check 6: diagonal up right
				for (int i = 0; i<4; i++) { // Checks for 4 in a diagonal up right pattern
					if (board[x + i][y + i].getStatus()==this.symbol){
						chipChain[i] = true;
					}
					else { // breaks for loop if chain ends
						break;
					}
				}
			}
			if((x>2)&&(y<3)) { // Check 7: diagonal up left
				for (int i = 0; i<4; i++) { // Checks for 4 in a diagonal up left pattern
					if (board[x - i][y + i].getStatus()==this.symbol){
						chipChain[i] = true;
					}
					else { // breaks for loop if chain ends
						break;
					}
				}
			}
			if((x>0)&&(x<5)) { // Check 8: horizontal right offset
				for (int i = 0; i<4; i++) { // Checks for 4 in a horizontal right offset pattern
					if (board[x+i-1][y].getStatus()==this.symbol){
						chipChain[i] = true;
					}
					else { // breaks for loop if chain ends
						break;
					}
				}
			}
			if((x>1)&&(x<6)) { // Check 9: horizontal left offset
				for (int i = 0; i<4; i++) { // Checks for 4 in a horizontal left offset pattern
					if (board[x-i+1][y].getStatus()==this.symbol){
						chipChain[i] = true;
					}
					else { // breaks for loop if chain ends
						break;
					}
				}
			}
			if(((x>0)&&(x<5))&&((y>1)&&(y<5))) { // Check 10: diagonal down right offset
				for (int i = 0; i<4; i++) { // Checks for 4 in a diagonal down right offset pattern
					if (board[x+i-1][y-i+1].getStatus()==this.symbol){
						chipChain[i] = true;
					}
					else { // breaks for loop if chain ends
						break;
					}
				}
			}	
			if(((x>1)&&(x<6))&&((y<1)&&(y<5))) { // Check 11: diagonal down left offset
				for (int i = 0; i<4; i++) { // Checks for 4 in a diagonal down left offset pattern
					if (board[x-i+1][y-i+1].getStatus()==this.symbol){
						chipChain[i] = true;
					}
					else { // breaks for loop if chain ends
						break;
					}
				}
			}
			if(((x>0)&&(x<5))&&((y>0)&&(y<3))) { // Check 12: diagonal up right offset
				for (int i = 0; i<4; i++) { // Checks for 4 in a diagonal up right offset pattern
					if (board[x+i-1][y+i-1].getStatus()==this.symbol){
						chipChain[i] = true;
					}
					else { // breaks for loop if chain ends
						break;
					}
				}
			}
			if(((x>1)&&(x<6))&&((y>0)&&(y<3))) { // Check 13: diagonal up left offset
				for (int i = 0; i<4; i++) { // Checks for 4 in a diagonal up left offset pattern
					if (board[x+i-1][y+i-1].getStatus()==this.symbol){
						chipChain[i] = true;
					}
					else { // breaks for loop if chain ends
						break;
					}
				}
			}
			if(chipChain[3]==true) { // if at least one check is successful, player wins
				isWinner = true;
			}
			else { // else no win
				isWinner = false;
			}
		}
		
		
	}
	public int chipsRemaining() { // returns remaining chips
		return this.chips;
	}
	public boolean isWinner() { // returns isWInner
		return this.isWinner;
	}
}