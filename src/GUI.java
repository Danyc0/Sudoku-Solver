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

public class GUI extends MyFrame implements ActionListener{
	private JFileChooser fileChooser = new JFileChooser();
	private FileNameExtensionFilter fileExtentionFilter = new FileNameExtensionFilter("Sudoku Files Only", "sud");
	private SudokuCanvas sudokuCanvas;
	private JButton loadButton, saveButton, solveButton;
	private JPanel buttonPanel;
	
	GUI(){
		buildViewerPanel();
	}
	
	public void buildViewerPanel(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			System.out.println("SystemLookAndFeel not found");
		}
		this.setTitle("Sudoku Solver");
		fileChooser.setFileFilter(fileExtentionFilter);
		
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
		
		setLayout(new BorderLayout());
		add(buttonPanel, BorderLayout.SOUTH);
		
		int gridSize = 9;
		Dimension minimumCanvasSize = new Dimension(((gridSize*45) + 15),((gridSize*45) + 15));
		sudokuCanvas = new SudokuCanvas();
		sudokuCanvas.setPreferredSize(minimumCanvasSize);
		sudokuCanvas.setBorder(BorderFactory.createLineBorder(Color.black,5));
		add(sudokuCanvas, BorderLayout.CENTER);
		this.pack();
		setVisible(true);
	}
	
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
