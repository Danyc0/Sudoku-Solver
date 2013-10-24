package uk.ac.aber.dcs.cs21120.sudoku;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author Daniel Clark (dac46@aber.ac.uk)
 * The class that displays the data in the JFrame
 */

public class SudokuCanvas extends JPanel {
	protected Solver solver;
	
	/**
	 * Starts the solver when using the program in GUI mode
	 */
	SudokuCanvas(){
		setBackground(Color.white);
		solver = new Solver();
	}
	
	/**
	 * Called whenever repaint() is called, it redraws the grid
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGrid(g);
	}
	
	/**
	 * This method outputs the grid to the user
	 * @param g
	 */
	public void drawGrid(Graphics g) {
		for (int i = 0; i<9; i++){
			for (int j = 0; j<9; j++){
				Cell currentCell = solver.getRow(i)[j];
				int currentValue = currentCell.getValue();
				int[] currentPensilMarks = currentCell.getPencilMarks();
				g.setColor(new Color(0, 255, 0));
				g.setFont(new Font("SolvedFont", 10, 25));
				//Draw a small square to put the number in
				g.fillRect(5+j*45,5+i*45,40,40);
				if(currentValue != 0){
					//Write in the completed value of that cell
					g.setColor(new Color(255, 0, 0));
					g.drawString(Integer.toString(currentValue), 15+j*45,35+i*45);
				}
				else{
					//Write in all the pencil marks for that cell
					g.setColor(new Color(0, 0, 255));
					g.setFont(new Font("PensilFont", 10, 10));
					for(int k = 0; k<9; k++){
						if(currentPensilMarks[k] != 0){
							int x = 0, y = 0;
							switch(currentPensilMarks[k]){
								case 1 : x = 10+j*45; y = 20+i*45; break;
								case 2 : x = 20+j*45; y = 20+i*45; break;
								case 3 : x = 30+j*45; y = 20+i*45; break;
								case 4 : x = 10+j*45; y = 30+i*45; break;
								case 5 : x = 20+j*45; y = 30+i*45; break;
								case 6 : x = 30+j*45; y = 30+i*45; break;
								case 7 : x = 10+j*45; y = 40+i*45; break;
								case 8 : x = 20+j*45; y = 40+i*45; break;
								case 9 : x = 30+j*45; y = 40+i*45; break;
							}
							g.drawString(Integer.toString(currentPensilMarks[k]), x,y);
						}
					}
				}
			}
		}
		//Draw the dividing lines between the squares
		g.setColor(new Color(0, 0, 0));
		g.fillRect((3*45), 0, 5, ((9*45)+15));
		g.fillRect((6*45), 0, 5, ((9*45)+15));
		g.fillRect(0, (3*45), ((9*45)+15), 5);
		g.fillRect(0, (6*45), ((9*45)+15), 5);
	}
	
	/**
	 * Imports a file into the solver from the filePath
	 * @param filePath
	 */
	public void importFile(String filePath){
		solver.importFile(filePath);
		repaint();
	}

	/**
	 * Saves the current state of the puzzle in the filePath
	 * @param filePath
	 */
	public void saveCurrentState(String filePath) {
		solver.saveCurrentState(filePath);
	}

	/**
	 * This starts the solver, it is called when the user pushes solve
	 */
	public void startSolver() {
		solver.start();
		//After finished solving, display the current state of the grid
		repaint();
	}
}
