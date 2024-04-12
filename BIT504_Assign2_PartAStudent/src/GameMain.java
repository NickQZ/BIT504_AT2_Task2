import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel implements MouseListener{
	// Constants for game 
	// Number of ROWS by COLS cell constants 
	public static final int ROWS = 3;     
	public static final int COLS = 3;  
	public static final String TITLE = "Tic Tac Toe";

	// constants for dimensions used for drawing
	// cell width and height
	public static final int CELL_SIZE = 100;
	//drawing canvas
	public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
	public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
	// Noughts and Crosses are displayed inside a cell, with padding from border
	public static final int CELL_PADDING = CELL_SIZE / 6;    
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;    
	public static final int SYMBOL_STROKE_WIDTH = 8;

	/*declare game object variables*/
	// The game board 
	private Board board;
	// Created enumeration for the variable Game State.
	// This Included; Playing, Draw, Cross_won, Nought_won
	private GameState currentState; 
	// The current player
	private Player currentPlayer; 
	// For displaying game status message
	private JLabel statusBar;       


	/** Constructor to setup the UI and game components on the panel */
	public GameMain() {   

		// Added a mouse listener to JPanel        
		addMouseListener(this);

		// Setup the status bar (JLabel) to display status message       
		statusBar = new JLabel("         ");       
		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));       
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));       
		statusBar.setOpaque(true);       
		statusBar.setBackground(Color.LIGHT_GRAY);  

		// Layout of the panel is in border layout
		setLayout(new BorderLayout());       
		add(statusBar, BorderLayout.SOUTH);
		// account for statusBar height in overall height
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

		// Create a new instance of the game board
		board = new Board();

		// Initialize the game board
		board.initBoard();
	}


	public static void main(String[] args) { // Open Main Class
		// Run GUI code in Event Dispatch thread for thread safety.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() { // Open Run 
				// Create a main window to contain the JFrame
				JFrame frame = new JFrame(TITLE);

				// Created the new GameMain panel and add it to the JFrame
				// This code creates a new frame and is used for the main visual and interactive part of my game (Think of this as paper that the below code will then paint on)
				// As shown below this code
				// GameMain gameMain = new GameMain
				GameMain gameMain = new GameMain();
				// The add gameMain to frame/JFrame
				frame.add(gameMain);

				// When Game Main is closed it will terminate the Frame/JFrame
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();             
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

			} // Close Run
		});
	} // Close Main Class
	/** Custom painting codes on this JPanel */

	// Add Paint Component to Board
	public void paintComponent(Graphics g) {
		// Fill background and set colour to white
		super.paintComponent(g);
		// Set Background JFrame to Black
		setBackground(Color.BLACK);
		// Ask the game board to paint itself
		board.paint(g);

		// Set status bar message
		// If Game state is equal to Playing,
		if (currentState == GameState.Playing) {
			// Set foreground to Black
			statusBar.setForeground(Color.BLACK); 
			// If Current Player equals Cross
			if (currentPlayer == Player.Cross) {   
				
				// Print on status bar, the message "X"'s Turn
				statusBar.setText("X's Turn");

			} else {    
				// If Player is Playing, StatusBar set foreground to Black but
				// CurrentPlayer == Player.Nought. Run Code Below and Display
				// O's Turn on status bar
				statusBar.setText("O's Turn");
			}
			
			//Else If Game state equals 'Draw'
		} else if (currentState == GameState.Draw) { 
			// Set Foreground Color to 'RED'
			statusBar.setForeground(Color.RED);
			// Display text on status bar 'It's a Draw! Click to play again.'
			statusBar.setText("It's a Draw! Click to play again."); 
			
			// Else if Game State equals 'Cross_won'
		} else if (currentState == GameState.Cross_won) {
			// Set Foreground Color to 'RED'
			statusBar.setForeground(Color.RED);
			// Display text on status bar ''X' Won! Click to play again.'
			statusBar.setText("'X' Won! Click to play again."); 
			
			// Else if Game State equals 'Nought_won'
		} else if (currentState == GameState.Nought_won) { 
			// Set Foreground Color to 'RED'
			statusBar.setForeground(Color.RED);  
			// Display text on status bar ''O' Won! Click to play again.'
			statusBar.setText("'O' Won! Click to play again.");       
		}
	}

	/** Initialize the game-board contents and the current status of GameState and Player) */
	public void initGame() {
		// Iterates over the rows of the board, checking that the 'ROWS' value is consistent with the required Rows needed to start the game according the GameMain values set '3 by 3'.
		for (int row = 0; row < ROWS; ++row) { 
			// This code does the same as above but for the columns
			for (int col = 0; col < COLS; ++col) {  
				// When all cells are empty and match required cells needed to start the game, set any content inside cells to 'EMPTY'
				board.cells[row][col].content = Player.Empty;           
			}
		}
		// Set Game State to Playing
		currentState = GameState.Playing;
		
		// Set Current Player to Cross, as Cross gets the first turn of the game.
		currentPlayer = Player.Cross;
	}


	/**After each turn check to see if the current player hasWon by putting their symbol in that position, 
	 * If they have the GameState is set to won for that player
	 * If no winner then isDraw is called to see if deadlock, if not GameState stays as PLAYING
	 *   
	 */
	public void updateGame(Player thePlayer, int row, int col) {
		// If board.hasWon 
		if(board.hasWon(thePlayer, row, col)) {

			// If the Player is playing Cross set Game State to Cross has won 
			if (thePlayer == Player.Cross) {
				// If Game State equals 'Cross_won' go to loop: 
				// "else if (currentState == GameState.Cross_won)"
				currentState = GameState.Cross_won;

				// Else set Game State to Nought has won
				// go to Loop: "else if (currentState == GameState.Nought_won)"
			} else {
				currentState = GameState.Nought_won;
			}

			// Else if no Player has won set Game State to draw
			// go to Loop: "else if (currentState == GameState.Draw)"
		} else if (board.isDraw ()) {
			currentState = GameState.Draw;
		}
		// Otherwise no change to current state of playing, will keep running loop until either Cross_won, Nought_won or Draw
	}



	/** Event handler for the mouse click on the JPanel. If selected cell is valid and Empty then current player is added to cell content.
	 *  UpdateGame is called which will call the methods to check for winner or Draw. if none then GameState remains playing.
	 *  If win or Draw then call is made to method that resets the game board.  Finally a call is made to refresh the canvas so that new symbol appears*/

	public void mouseClicked(MouseEvent e) {  
		// Get the coordinates of where the click event happened            
		int mouseX = e.getX();             
		int mouseY = e.getY();             
		// Get the row and column clicked             
		int rowSelected = mouseY / CELL_SIZE;             
		int colSelected = mouseX / CELL_SIZE;               			
		if (currentState == GameState.Playing) {                
			if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS && board.cells[rowSelected][colSelected].content == Player.Empty) {
				// Move  
				board.cells[rowSelected][colSelected].content = currentPlayer; 
				// Update currentState                  
				updateGame(currentPlayer, rowSelected, colSelected); 
				// Switch player
				if (currentPlayer == Player.Cross) {
					currentPlayer =  Player.Nought;
				}
				else {
					currentPlayer = Player.Cross;
				}
			}             
		} else {        
			// Game over and restart board              
			initGame();            
		}   

		// Repaints the UI         
		repaint();
	}


	@Override
	public void mousePressed(MouseEvent e) {
		//  Auto-generated, event not used

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//  Auto-generated, event not used

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// Auto-generated,event not used

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// Auto-generated, event not used

	}

}
