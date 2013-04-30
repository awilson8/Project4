import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.*;
import java.net.URI;
/**
 * View class for Project4.
 * 
 * @author Drew Wilson
 * @version 1.0
 */
public class View extends JPanel {
	Reader vReader = new Reader();
	JFrame frame = new JFrame("Drew Wilson - Project 4");
	public static final int FRAMEHEIGHT = 345;
	public static final int FRAMEWIDTH = 705;
	private Image background;
	private JTextField artist = new JTextField("Ex: Celine Dion");
	String[] stats = { "All", "Bio", "Top Tracks", "Listeners", "Top Albums" };
	JComboBox selectStat = new JComboBox(stats);
	JButton go = new JButton("Go!");
	private JEditorPane messageCenter = new JEditorPane("text/html","");
	JScrollPane sp = new JScrollPane(messageCenter);
	Runtime rt = Runtime.getRuntime();
	
	/**
	 * Create the panel.
	 */
	public View() {
		setLayout(null);
		background = new ImageIcon("Project4.png").getImage();
		setSize(FRAMEWIDTH,FRAMEHEIGHT);
		artist.setLocation(25, 50);
		artist.setSize(150, 20);
		add(artist);
		selectStat.setLocation(25, 115);
		selectStat.setSize(150, 20);
		add(selectStat);
		go.setLocation(65, 150);
		go.setSize(70, 20);
		go.addActionListener(new GoClickHandler());
		add(go);
		messageCenter.setSize(490,140);
		messageCenter.addHyperlinkListener(new HyperLinkHandler());
		messageCenter.setEditable(false);
		sp.setSize(490, 140);
		sp.setLocation(190, 20);
		add(sp);
	}
	
	/**
	 * sets the background
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, FRAMEWIDTH, FRAMEHEIGHT, null);
	}
	
	/**
	 * Displays the Frame
	 */
	public void display() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setContentPane(this);
	    frame.setSize(FRAMEWIDTH,366);
	    frame.setVisible(true);
	    frame.setLocationRelativeTo(null);
	}
	
	/**
	 * GoClickHandler provides the action listener for the go button
	 */
	private class GoClickHandler implements ActionListener{

		/**
		 * This method handles the tasks of loading the statistics
		 *
		 * @param e the action event handled by this method
		 */
		
		public void actionPerformed(ActionEvent e){	
			Scanner scan = new Scanner(artist.getText());
			String art = scan.next();
			try {
				while (scan.hasNext()) {
					String temp = scan.next();
					if (temp.equals("&") || temp.equals("-") || temp.equals("/")) {
						art += temp;
					}
					else {
						art += "+" + temp;
					}
				}
			}
			catch (Exception ee) {
				messageCenter.setText("Invalid Artist");
			}
			String stat = (selectStat.getSelectedItem()).toString();
			messageCenter.setText(vReader.getStats(art, stat));
			messageCenter.setCaretPosition(0);
		}
	}
	
	/**
	 * HyperLinkHandler provides the action listener for the hyperlink
	 */
	private class HyperLinkHandler implements HyperlinkListener{

		/**
		 * This method handles the tasks of loading the hyperlink
		 *
		 * @param e the action event handled by this method
		 */
		
		public void hyperlinkUpdate(HyperlinkEvent e){
			try{
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					if(Desktop.isDesktopSupported()) {
						Desktop.getDesktop().browse(e.getURL().toURI());
					}
				}
			}
			catch (Exception ee) {
				System.out.println(ee);
			}
		}
	}
}