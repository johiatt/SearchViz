package playgroundTest;

import java.io.FileNotFoundException;

import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
//	       SwingUtilities.invokeLater(new Runnable() {
//		   		public void run() {
//		   			//can i reference gui from anywhere like this?
//		   		    new GUI();
//				}
//   	    	});
		new GUI();
	}
}
