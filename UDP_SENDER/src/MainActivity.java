import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import components.FancyRoundButton;
import java.awt.Container;

public class MainActivity {
	public static void main(String[] args) {
		
		GridBagConstraints gc = new GridBagConstraints();

		
		
		
		System.out.println("Hello World");
		JFrame frame = new JFrame("UDP Transit");
        frame.setSize(859, 562);
		frame.setVisible(true);
		
        Container contentPane = frame.getContentPane();
        contentPane.setBackground(Color.white);
        contentPane.setLayout(new GridBagLayout());
        
        
		FancyRoundButton sendButton = new FancyRoundButton();
		sendButton.SetParameters(141, 141, Color.decode("#B73737"),"SEND",40,75);

        
        
		gc.gridx  = 0;
		gc.gridy = 0;
		gc.insets = new Insets(0, 0, 0, 20);
		contentPane.add(sendButton,gc);

		FancyRoundButton recieveButton = new FancyRoundButton();
		recieveButton.SetParameters(141, 141, Color.decode("#4464AD"),"RECEIEVE",20,75);
		recieveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Recieve");
				
			}
		});
		
		
		gc.gridx=1;
		gc.gridy=0;
		gc.insets = new Insets(0, 20, 0, 0);
		
		contentPane.add(recieveButton,gc);
		
		
		
		
		

		sendButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Send");
			}
		});
		
		
		
		
		
		

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	
	}
	

    
}
