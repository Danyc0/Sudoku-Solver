import javax.swing.JFrame;


/**
 * A very simple frame to build other frames off of
 * @author Daniel Clark (dac46@aber.ac.uk)
 * @version 2.0
 *
 */
public abstract class MyFrame extends JFrame {
	
	/**
	 * Constructs a very basic frame on which the GUI is based
	 */
	MyFrame() {
		this.setLocation(200,200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}