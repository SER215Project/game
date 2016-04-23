import java.util.Random;
public class ComputerPlayer extends Player{
	private static char enemySymbol = 'x';
	private int[][][] advantage = new int[7][6][2]; //index [x][y][0] isAdvantage- 1T 0F 
	private int[][][] threat = new int[7][6][2]; //  index  [x][y][1] emptySpace - number of empty slots below target	
	public ComputerPlayer() { // Constructor
		super('o');
	}
	
	public Slot[][] dropChip(Slot[][] board) {
		int column, row=5;
		checkAdvantage(board);
		checkThreat(board);
		column = analyzeThreats(board);
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
	private void checkAdvantage(Slot[][] board) { 
		this.advantage = new int[7][6][2]; // wipe advantage data
		//Should have 13 checks total
		 // Doesn't check if a 4 chips haven't been placed 
		for (int x = 0; x < 7; x++) 
			for (int y = 0; y < 6; y++) { // iterates through all x and y coordinates
				if (board[x][y].getStatus() == ' ') { // only checks blank slots
					if (y > 2) { 
						for (int i=1; i < 4; i++) { // Check 1: vertical
							if ((board[x][y - i].getStatus()==this.symbol)&&(board[x][y - i].getStatus()!=enemySymbol)){
								if (i == 3) { // has advantage, check slots below
									this.advantage[x][y][0] = 1;
									this.advantage[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
								}
							}
							else break; // breaks for loop if chain ends
						}
						if (x < 4) {
							for (int i=1; i < 4; i++) { // Check 2: diagonal down right
								if (board[x+i][y - i].getStatus()==this.symbol){
									if (i == 3) { // has advantage, check slots below
										this.advantage[x][y][0] = 1;
										this.advantage[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
									}
								}
								else break; // breaks for loop if chain ends
							}
						}
						if (x > 2) {
							for (int i=1; i < 4; i++) {  // Check 3: diagonal down left
								if (board[x-i][y - i].getStatus()==this.symbol){
									if (i == 3) { // has advantage, check slots below
										this.advantage[x][y][0] = 1;
										this.advantage[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
									}
								}
								else break; // breaks for loop if chain ends
				
							}
						}
					}
					if (x < 4) {
						for (int i=1; i < 4; i++) { // Check 4: Horizontal right
							if (board[x+i][y].getStatus()==this.symbol){
								if (i == 3) { // has advantage, check slots below
									this.advantage[x][y][0] = 1;
									this.advantage[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
								}
							}
							else break; // breaks for loop if chain ends
						}
						if (y< 3) {
							for (int i=1; i < 4; i++) { // Check 5: diagonal up right
								if (board[x+i][y+i].getStatus()==this.symbol){
									if (i == 3) { // has advantage, check slots below
										this.advantage[x][y][0] = 1;
										this.advantage[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
									}
								}
								else break; // breaks for loop if chain ends
							}
						}
					}
					if (x >2) {
						for (int i=1; i < 4; i++) { // Check 6: Horizontal left
							if (board[x-i][y].getStatus()==this.symbol){
								if (i == 3) { // has advantage, check slots below
									this.advantage[x][y][0] = 1;
									this.advantage[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
								}
							}
							else break; // breaks for loop if chain ends
						}
						if ( y < 3) {
							for (int i=1; i < 4; i++) { // Check 7: diagonal up left
								if (board[x-i][y+i].getStatus()==this.symbol){
									if (i == 3) { // has advantage, check slots below
										this.advantage[x][y][0] = 1;
										this.advantage[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
									}
								}
								else break; // breaks for loop if chain ends
							}
						}
					}
					if ((x>0)&&(x<5)) {
						for (int i=0; i < 4; i++) { // Check 8: Horizontal right offset
							if (board[x+i-1][y].getStatus()==this.symbol){
								if (i == 3) { // has advantage, check slots below
									this.advantage[x][y][0] = 1;
									this.advantage[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
								}
							}
							else if (i == 1) {} // doesn't break for coordinate
							else break; // breaks for loop if chain ends
						}
						if ((y> 1)&&(y< 5)) {
							for (int i=0; i < 4; i++) { // Check 9: diagonal down right offset
								if (board[x+i-1][y - i +1].getStatus()==this.symbol){
									if (i == 3) { // has advantage, check slots below
										this.advantage[x][y][0] = 1;
										this.advantage[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
									}
								}
								else if (i == 1) {} // doesn't break for coordinate
								else break; // breaks for loop if chain ends
							}
						}
						if ((y> 0)&&(y< 4)) {
							for (int i=0; i < 4; i++) { // Check 10: diagonal up right offset
								if (board[x+i-1][y + i -1].getStatus()==this.symbol){
									if (i == 3) { // has advantage, check slots below
										this.advantage[x][y][0] = 1;
										this.advantage[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
									}
								}
								else if (i == 1) {} // doesn't break for coordinate
								else break; // breaks for loop if chain ends
							}
						}
					}
					if ((x>1)&&(x<6)) {
						for (int i=0; i < 4; i++) { // Check 11: Horizontal left offset
							if (board[x-i+1][y].getStatus()==this.symbol){
								if (i == 3) { // has advantage, check slots below
									this.advantage[x][y][0] = 1;
									this.advantage[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
								}
							}
							else if (i == 1) {} // doesn't break for coordinate
							else break; // breaks for loop if chain ends
						}
						if ((y> 1)&&(y< 5)) {
							for (int i=0; i < 4; i++) { // Check 12: diagonal down left offset
								if (board[x-i+1][y - i + 1].getStatus()==this.symbol){
									if (i == 3) { // has advantage, check slots below
										this.advantage[x][y][0] = 1;
										this.advantage[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
									}
								}
								else if (i == 1) {} // doesn't break for coordinate
								else break; // breaks for loop if chain ends
							}
						}
						if ((y> 0)&&(y< 4)) {
							for (int i=0; i < 4; i++) { // Check 13: diagonal up left offset
								if (board[x-i+1][y + i - 1].getStatus()==this.symbol){
									if (i == 3) { // has advantage, check slots below
										this.advantage[x][y][0] = 1;
										this.advantage[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
									}
								}
								else if (i == 1) {} // doesn't break for coordinate
								else break; // breaks for loop if chain ends
							}
						}
					}
				}
			}
			}
	private void checkThreat(Slot[][] board) { 
		this.threat = new int[7][6][2]; // wipe threat data
		//Should have 13 checks total
		for (int x = 0; x < 7; x++) 
			for (int y = 0; y < 6; y++) { // iterates through all x and y coordinates
				if (board[x][y].getStatus() == ' ') { // only checks blank slots
					if (y > 2) { 
						for (int i=1; i < 4; i++) { // Check 1: vertical
							if (board[x][y - i].getStatus()==enemySymbol){
								if (i == 3) { // has threat, check slots below
									this.threat[x][y][0] = 1;
									this.threat[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
								}
							}
							else break; // breaks for loop if chain ends
						}
						if (x < 4) {
							for (int i=1; i < 4; i++) { // Check 2: diagonal down right
								if (board[x+i][y - i].getStatus()==enemySymbol){
									if (i == 3) { // has threat, check slots below
										this.threat[x][y][0] = 1;
										this.threat[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
									}
								}
								else break; // breaks for loop if chain ends
							}
						}
						if (x > 2) {
							for (int i=1; i < 4; i++) {  // Check 3: diagonal down left
								if (board[x-i][y - i].getStatus()==enemySymbol){
									if (i == 3) { // has threat, check slots below
										this.threat[x][y][0] = 1;
										this.threat[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
									}
								}
								else break; // breaks for loop if chain ends
				
							}
						}
					}
					if (x < 4) {
						for (int i=1; i < 4; i++) { // Check 4: Horizontal right
							if (board[x+i][y].getStatus()==enemySymbol){
								if (i == 3) { // has threat, check slots below
									this.threat[x][y][0] = 1;
									this.threat[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
								}
							}
							else break; // breaks for loop if chain ends
						}
						if (y< 3) {
							for (int i=1; i < 4; i++) { // Check 5: diagonal up right
								if (board[x+i][y+i].getStatus()==enemySymbol){
									if (i == 3) { // has threat, check slots below
										this.threat[x][y][0] = 1;
										this.threat[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
									}
								}
								else break; // breaks for loop if chain ends
							}
						}
					}
					if (x >2) {
						for (int i=1; i < 4; i++) { // Check 6: Horizontal left
							if (board[x-i][y].getStatus()==enemySymbol){
								if (i == 3) { // has threat, check slots below
									this.threat[x][y][0] = 1;
									this.threat[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
								}
							}
							else break; // breaks for loop if chain ends
						}
						if ( y < 3) {
							for (int i=1; i < 4; i++) { // Check 7: diagonal up left
								if (board[x-i][y+i].getStatus()==enemySymbol){
									if (i == 3) { // has threat, check slots below
										this.threat[x][y][0] = 1;
										this.threat[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
									}
								}
								else break; // breaks for loop if chain ends
							}
						}
					}
					if ((x>0)&&(x<5)) {
						for (int i=0; i < 4; i++) { // Check 8: Horizontal right offset
							if (board[x+i-1][y].getStatus()==enemySymbol){
								if (i == 3) { // has threat, check slots below
									this.threat[x][y][0] = 1;
									this.threat[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
								}
							}
							else if (i == 1) {} // doesn't break for coordinate
							else break; // breaks for loop if chain ends
						}
						if ((y> 1)&&(y< 5)) {
							for (int i=0; i < 4; i++) { // Check 9: diagonal down right offset
								if (board[x+i-1][y - i +1].getStatus()==enemySymbol){
									if (i == 3) { // has threat, check slots below
										this.threat[x][y][0] = 1;
										this.threat[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
									}
								}
								else if (i == 1) {} // doesn't break for coordinate
								else break; // breaks for loop if chain ends
							}
						}
						if ((y> 0)&&(y< 4)) {
							for (int i=0; i < 4; i++) { // Check 10: diagonal up right offset
								if (board[x+i-1][y + i -1].getStatus()==enemySymbol){
									if (i == 3) { // has threat, check slots below
										this.threat[x][y][0] = 1;
										this.threat[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
									}
								}
								else if (i == 1) {} // doesn't break for coordinate
								else break; // breaks for loop if chain ends
							}
						}
					}
					if ((x>1)&&(x<6)) {
						for (int i=0; i < 4; i++) { // Check 11: Horizontal left offset
							if (board[x-i+1][y].getStatus()==enemySymbol){
								if (i == 3) { // has threat, check slots below
									this.threat[x][y][0] = 1;
									this.threat[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
								}
							}
							else if (i == 1) {} // doesn't break for coordinate
							else break; // breaks for loop if chain ends
						}
						if ((y> 1)&&(y< 5)) {
							for (int i=0; i < 4; i++) { // Check 12: diagonal down left offset
								if (board[x-i+1][y - i + 1].getStatus()==enemySymbol){
									if (i == 3) { // has threat, check slots below
										this.threat[x][y][0] = 1;
										this.threat[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
									}
								}
								else if (i == 1) {} // doesn't break for coordinate
								else break; // breaks for loop if chain ends
							}
						}
						if ((y> 0)&&(y< 4)) {
							for (int i=0; i < 4; i++) { // Check 13: diagonal up left offset
								if (board[x-i+1][y + i - 1].getStatus()==enemySymbol){
									if (i == 3) { // has threat, check slots below
										this.threat[x][y][0] = 1;
										this.threat[x][y][1] = emptySlotsBelow(x,y,board);
										System.out.println("\n" + x + "   " + y + "    " + this.threat[x][y][1]);
									}
								}
								else if (i == 1) {} // doesn't break for coordinate
								else break; // breaks for loop if chain ends
							}
						}
					}
				}
			}
			}
	private int emptySlotsBelow(int x, int y, Slot[][] board) { // Counts number of empty slots below coordinate and returns
		int emptySlots = 0;
		
		for (int i = 1; i <= y; i++) { // checks y coords below unless y=0
			if(board[x][y-i].getStatus() == ' ') {
				emptySlots++;
			}
			else break; // breaks when filled slot or end of board is reached
		}
		
		return emptySlots;
	}
	private int analyzeThreats(Slot[][] board) {
		int compareAdvantage = 6, compareThreat = 6, move = 1;
		int[] urgentAdvantage = new int[2], urgentThreat = new int[2]; // both in form {x,y}
		
		boolean instantAdvantage = false, instantThreat = false;
		for (int x = 0; x < 7; x++) 
			for (int y = 0; y < 6; y++) { // iterates through all coordinates
				if (advantage[x][y][0] == 1) {  		// checks for advantage
					if (advantage[x][y][1] < compareAdvantage) { 		// compares empty slots
						urgentAdvantage[0] = x;
						urgentAdvantage[1] = y;
						compareAdvantage = advantage[x][y][1];
					}
					if (advantage[x][y][1] == 0) {
						urgentAdvantage[0] = x;
						urgentAdvantage[1] = y;
						instantAdvantage = true;
						compareAdvantage = advantage[x][y][1];
						break;							// breaks loop when instant win
					}
				}
				if (threat[x][y][0] == 1) { 
					if (threat[x][y][1] < compareThreat) {
						urgentThreat[0] = x;
						urgentThreat[1] = y;
						compareThreat = threat[x][y][1];
					}
					if (threat[x][y][1] == 0) {
						urgentThreat[0] = x;
						urgentThreat[1] = y;
						instantThreat = true;
						compareThreat = threat[x][y][1];
						break;
					}	
				}
			}
		if (instantAdvantage) {	 			// plays advantage if instant win
				move = urgentAdvantage[0];
		} else if (instantThreat) {			// else plays threat if instant loss
				move = urgentThreat[0];
		}
		else if (compareAdvantage < 2) { 	// plays near advantage if two chips away or less
			move = urgentAdvantage[0]; 
		}
		else if (board[3][5].getStatus() == ' ') {      // plays middle row if available
			move = 3; 		
		}
		else {										// Else plays random column
			do {
			Random random = new Random();
			move = random.nextInt(7);
			} while (board[move][5].getStatus() != ' '); // checks for full column
		}
		
		return move;
	}
}
