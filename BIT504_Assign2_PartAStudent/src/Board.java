import java.awt.*;
//
public class Board { // Open Board
	// Grid line width
	public static final int GRID_WIDTH = 8;
	// Grid line half width
	public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;
	
	// 2D array of ROWS-by-COLS Cell instances
	Cell [][] cells;
	
	/** Constructor to create the game board */
	public Board() { // Open Board
		
	// Finished the Array with Rows and Col added into the new cell object
	cells = new Cell[GameMain.ROWS][GameMain.COLS];
	
	} // Close Board
	
	public void initBoard() { // Open initBoard
		for (int row = 0; row < GameMain.ROWS; ++row) {
			for (int col = 0; col < GameMain.COLS; ++col) {
				cells[row][col] = new Cell(row, col);
			}
		}
	} // Close initBoard
	
	

	 /** Return true if it is a draw (i.e., no more EMPTY cells) */ 
	public boolean isDraw() { // Open isDraw
		
		// Game Checks if row and col are full
		for (int row = 0; row < GameMain.ROWS; ++row) {
			for (int col = 0; col < GameMain.COLS; ++col) {
				// If cells (Row + Col) have an empty space then
				if (cells[row][col].content == Player.Empty) {
					// Game found an empty cell therefore the game is not a draw
					return false;
				}
			}
		}
		// Else If all Cells are filled, game is a draw
		return true;
	} // Close  isDraw
	
	/** Return true if the current player "thePlayer" has won after making their move  */
	public boolean hasWon(Player thePlayer, int playerRow, int playerCol) { // Open hasWon
		
		   /** Checks to see if Player has 3-in-that-row (left to right)*/
	
		// This checks the content of cell/s at the row the player selected,
		// and then checks the column '0' 'AKA the left side column' to see if it equals 'thePlayer'
		// If columns return 'false', it means that the player does not have three symbols in a row therefore the code moves to the next condition.
		// Unless it returns true, which would mean the player has a column of three indicating that player won the game.
		// (Repeats process for other columns [1](Middle Column), [2](Right Column))
	    if (cells[playerRow][0].content == thePlayer && 
	    	cells[playerRow][1].content == thePlayer && 
	    	cells[playerRow][2].content == thePlayer)
	        return true;

	    // Checks to see if Player has 3 in the column (Top to Bottom)
	    // This checks the top row [0] and (where the player made their move) which is equal to the player symbol ('X' or 'O')
	    // If this returns false(Can't find a Player move) that means that it 'returns false'. The code will move to the next cell in this case [1] 
	    // and then repeat the process. Unless it returns true, which would mean the player has a column of three indicating that player won the game. 
	    if (cells[0][playerCol].content == thePlayer && 
	    	cells[1][playerCol].content == thePlayer && 
	    	cells[2][playerCol].content == thePlayer)
	        return true;

	    // Checks to see if Player has 3-in-diagonal (Top-left to Bottom-right)
	    // This will repeat the same as above but instead of checking top to bottom it will check top-left, middle and bottom right cells.
	    if (cells[0][0].content == thePlayer && 
	    	cells[1][1].content == thePlayer && 
	    	cells[2][2].content == thePlayer)
	        return true;

	    // Check to see if the Player has 3 in diagonal (Top-right to Bottom-left)
	 // This will repeat the same as above but instead of checking Bottom-left to Top-right it will check top-right, middle and bottom left cells.
	    if (playerRow + playerCol == 2 && 
	    	cells[0][2].content == thePlayer && 
	    	cells[1][1].content == thePlayer && 
	    	cells[2][0].content == thePlayer)
	        return true;

	    // No winner, keep playing
	    return false;
	    
	} // Close hasWon
	
	/**
	 * Draws the grid (rows then columns) using constant sizes, then call on the
	 * Cells to paint themselves into the grid
	 */
	public void paint(Graphics g) { // Open Paint
		// Draw the grid
		g.setColor(Color.BLACK);
		for (int row = 1; row < GameMain.ROWS; ++row) {          
			g.fillRoundRect(0, GameMain.CELL_SIZE * row - GRID_WIDHT_HALF,                
					GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,                
					GRID_WIDTH, GRID_WIDTH);       
			}
		for (int col = 1; col < GameMain.COLS; ++col) {          
			g.fillRoundRect(GameMain.CELL_SIZE * col - GRID_WIDHT_HALF, 0,                
					GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,                
					GRID_WIDTH, GRID_WIDTH);
		}
		
		// Draw the cells
		for (int row = 0; row < GameMain.ROWS; ++row) {          
			for (int col = 0; col < GameMain.COLS; ++col) {  
				cells[row][col].paint(g);
			}
		}
	} // Close Paint
	

} // Close Board
