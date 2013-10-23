import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SudokuCanvas extends JPanel {
	protected Solver solver;
	SudokuCanvas(){
		setBackground(Color.white);
		solver = new Solver();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGrid(g);
	}
	
	public void drawGrid(Graphics g) {
		for (int i = 0; i<9; i++){
			for (int j = 0; j<9; j++){
				Cell currentCell = solver.getRow(i)[j];
				int currentValue = currentCell.getValue();
				int[] currentPensilMarks = currentCell.getPensilMarks();
				g.setColor(new Color(0, 255, 0));
				g.setFont(new Font("SolvedFont", 10, 25));
				g.fillRect(10+j*45,10+i*45,40,40);
				if(currentValue != 0){
					g.setColor(new Color(255, 0, 0));
					g.drawString(Integer.toString(currentValue), 20+j*45,40+i*45);
				}
				else{
					g.setColor(new Color(0, 0, 255));
					g.setFont(new Font("PensilFont", 10, 10));
					for(int k = 0; k<9; k++){
						if(currentPensilMarks[k] != 0){
							int x = 0, y = 0;
							switch(k){
								case 1 : x = 15+j*45; y = 35+i*45; break;
								case 2 : x = 20+j*45; y = 35+i*45; break;
								case 3 : x = 25+j*45; y = 35+i*45; break;
								case 4 : x = 15+j*45; y = 40+i*45; break;
								case 5 : x = 20+j*45; y = 40+i*45; break;
								case 6 : x = 25+j*45; y = 40+i*45; break;
								case 7 : x = 15+j*45; y = 45+i*45; break;
								case 8 : x = 20+j*45; y = 45+i*45; break;
								case 9 : x = 25+j*45; y = 45+i*45; break;
							}
							g.drawString(Integer.toString(currentPensilMarks[k]), x,y);
						}
					}
				}
			}
		}
	}
	
	public void importFile(String filePath){
		solver.importFile(filePath);
		repaint();
	}

	public void saveCurrentState(String filePath) {
		solver.saveCurrentState(filePath);
	}

	public void startSolver() {
		solver.start();
		repaint();
	}
}
