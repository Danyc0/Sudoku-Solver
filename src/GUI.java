import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Daniel Clark (dac46@aber.ac.uk)
 * The class that creates the main frame for the GUI
 */

public class GUI extends MyFrame implements ActionListener{
	private JFileChooser fileChooser = new JFileChooser();
	private SudokuCanvas sudokuCanvas;
	private JButton loadButton, saveButton, solveButton;
	private JPanel buttonPanel;
	
	/**
	 * Constructor which builds the JPanel to display all the data
	 */
	GUI(){
		buildViewerPanel();
	}
	
	/**
	 * Builds the JPanel to display all the data and buttons in the right places
	 */
	public void buildViewerPanel(){
		//Change to the SystemLookAndFeel because i think it looks nicer
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			System.out.println("SystemLookAndFeel not found");
		}
		this.setTitle("Sudoku Solver");
		//Make it so only .sud files show up in the FileChooser
		fileChooser.setFileFilter(new FileNameExtensionFilter("Sudoku Files Only", "sud"));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,4));
		
		loadButton = new JButton("Load");
		buttonPanel.add(loadButton);
		loadButton.addActionListener(this);
		
		saveButton = new JButton("Save");
		buttonPanel.add(saveButton);
		saveButton.addActionListener(this);
		
		solveButton = new JButton("Solve");
		buttonPanel.add(solveButton);
		solveButton.addActionListener(this);
		
		//Make all the buttons display at the bottom of the window
		setLayout(new BorderLayout());
		add(buttonPanel, BorderLayout.SOUTH);
		
		//Calculate the size the window has to be to fit the canvas in
		int gridSize = 9;
		Dimension minimumCanvasSize = new Dimension(((gridSize*45) + 5),((gridSize*45) + 5));
		//Stops the user from resizing the display
		this.setResizable(false);
		
		//Creates a new SudokuCanvas of the right size and shape, and puts it in the right place
		sudokuCanvas = new SudokuCanvas();
		sudokuCanvas.setPreferredSize(minimumCanvasSize);
		sudokuCanvas.setBorder(BorderFactory.createLineBorder(Color.black,5));
		add(sudokuCanvas, BorderLayout.CENTER);
		
		//Packs everything in and makes the GUI visible
		this.pack();
		setVisible(true);
	}
	
	/**
	 * Runs whenever an action is performed, to trigger the appropriate action to things like button presses
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		AbstractButton eventSource = (AbstractButton)e.getSource();
		if(eventSource == loadButton){
			try{
				fileChooser.showOpenDialog(this);
				File file = fileChooser.getSelectedFile();
				String filePath = file.getPath();
				sudokuCanvas.importFile(filePath);
			}
			catch(NullPointerException NPE){
				System.out.println("No Path Selected");
			}
		}
		else if(eventSource == saveButton){
			fileChooser.showSaveDialog(this);
			File file = fileChooser.getSelectedFile();
			String filePath = file.getPath();
			sudokuCanvas.saveCurrentState(filePath);
		}
		else if(eventSource == solveButton){
			sudokuCanvas.startSolver();
		}
	}
}