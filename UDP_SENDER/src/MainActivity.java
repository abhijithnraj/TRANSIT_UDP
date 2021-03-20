import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import components.FancyRoundButton;
import java.awt.Container;


public class MainActivity {
	public static void buttonAction(FancyRoundButton buttonToDelete,Thread thread) {
		buttonToDelete.setVisible(false);
		Boolean threadAlive =thread.isAlive();
		System.out.println("ListenThread: " + threadAlive);
		if(!threadAlive){
			thread.start();
		}
	}
	public static void showAgainMessageAction(JFrame frame) {

		JCheckBox  doNotShowAgain = new JCheckBox("Do not show this message again.");
		
		Object[] options = {doNotShowAgain,"OK"};
		int n = JOptionPane.showOptionDialog(frame,
		    "Click on Receive button the Receiver side. Scanning will start while you select the File",
		    "Action Required",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[1]);
			
		if(doNotShowAgain.isSelected()) {
			System.out.println("Do not Show again is selected");
		}
		
	}
	
	private static File chooseFile(JFrame frame) {
		JFileChooser fileChooser = new JFileChooser(); 
		int returnVal = fileChooser.showOpenDialog(frame);
		File f = fileChooser.getSelectedFile();
		return f;
	}
	
	public static void setGridBagLayoutButtons(Container contentPane,GridBagConstraints gc, FancyRoundButton sendButton,FancyRoundButton receiveButton) {
        contentPane.setLayout(new GridBagLayout());
        
    	sendButton.SetParameters(141, 141, Color.decode("#B73737"),"SEND",40,75);
		gc.gridx  = 0;
		gc.gridy = 0;
		gc.insets = new Insets(0, 0, 0, 20);
		contentPane.add(sendButton,gc);
		
		receiveButton.SetParameters(141, 141, Color.decode("#4464AD"),"RECEIEVE",20,75);
		gc.gridx=1;
		gc.gridy=0;
		gc.insets = new Insets(0, 20, 0, 0);
		contentPane.add(receiveButton,gc);
		
		
		
	}
	
	public static void main(String[] args) {
		
		/** Broadcast Discover initialization **/
		BroadcastDiscover bd = new BroadcastDiscover();
		bd.disableDebug(); // Now in production environment, comment this line to shush.
		
		JFrame frame = new JFrame("UDP Transit");
        frame.setSize(859, 562);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        Container contentPane = frame.getContentPane();
        contentPane.setBackground(Color.white);
        
		FancyRoundButton sendButton = new FancyRoundButton();
		FancyRoundButton recieveButton = new FancyRoundButton();
		GridBagConstraints gc = new GridBagConstraints();
		setGridBagLayoutButtons(contentPane, gc, sendButton, recieveButton);
		
		recieveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendButton.setVisible(false);
				System.out.println("Receive Button Clicked");
				buttonAction(sendButton, bd.announceThread);
			}
		});
		
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Send button clicked");
				buttonAction(recieveButton,bd.listenThread);
				ArrayList<String> ipList = bd.getDiscovered();
				showAgainMessageAction(frame);
				File f = chooseFile(frame);
				if(f!=null) {
					System.out.println(f.toString());
				}
				else {
					JOptionPane.showMessageDialog(frame, "File not Correctly chosen", "Error", JOptionPane.ERROR_MESSAGE);
					//TODO Reset the flow here.
				}						
				System.out.println(ipList.toString());
			}
		});
		
	}
	
}
