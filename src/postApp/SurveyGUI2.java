package postApp;

import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public class SurveyGUI2 extends JFrame implements ActionListener{
	//Create the fields
	private JFrame frame;
	private JPanel screen;
	private JLabel surveyScreen;
	
	private final int CONSTANT=5;//The value of the previous question 
	
	private int colourCodes[][]= {{232,17,35},{247,99,12},{255,185,0},{73,130,5},{16,124,16}};

	private JButton backButton;
	private JButton continueButton;
	

	/**
	 * Create the application.
	 */
	public SurveyGUI2() {
		frameSetup();
		panelDesign();
	}
	//setup the JFrame
	private void frameSetup() {
		frame = new JFrame();
		screen = new JPanel();
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //program will end when exited
		frame.setSize(Initialize.WIDTH,Initialize.HEIGHT); // sets the size of the frame
		frame.setTitle("Engineering Program Finder");
		frame.setBounds(0,0,Initialize.WIDTH+5,Initialize.HEIGHT+29);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false); // can't resize
		frame.getContentPane().add(screen); // add panel to the frame
		frame.validate();
		frame.repaint();
		frame.setVisible(true); 
		
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void panelDesign() {
		
		screen.setBorder(null);
		screen.setBounds(0,0,Initialize.WIDTH,Initialize.HEIGHT);
		screen.setLayout(null);

		// gives each block their label, and image
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				SurveyGUI1.answerButtons[i+CONSTANT][j] = new JButton();
				SurveyGUI1.answerButtons[i+CONSTANT][j].addActionListener(this);
				SurveyGUI1.answerButtons[i+CONSTANT][j].setBounds(500 + 95 * j, 170 + 60 * i, 60, 20); // location moves so labels don't overlap
				// the imagesArr index corresponds with the value on the board
				
				SurveyGUI1.answerButtons[i+CONSTANT][j].setBackground(new java.awt.Color(colourCodes[j][0], colourCodes[j][1], colourCodes[j][2]));
				
				
				
				// creates a border around the buttons.
				if(Initialize.surveyAnswers[i+CONSTANT]==j)
					SurveyGUI1.answerButtons[i+CONSTANT][j].setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 120, 215), 3));

				else
					SurveyGUI1.answerButtons[i+CONSTANT][j].setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

				screen.add(SurveyGUI1.answerButtons[i+CONSTANT][j]);
			}
		}

		// JButton Login Instead
		// Exits Sign-up screen and returns to login screen
		backButton = new JButton();
		backButton.addActionListener(this);
		backButton.setBounds(250, 465, 140, 30);
		backButton.setOpaque(false); 
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		screen.add(backButton);

		// JButton Continue
		// Continues to the next sign up step if all entered information is valid
		continueButton = new JButton();
		continueButton.addActionListener(this);
		continueButton.setBounds(590, 465, 100, 30);
		continueButton.setOpaque(false); 
		continueButton.setContentAreaFilled(false);
		continueButton.setBorderPainted(false);
		screen.add(continueButton);
		
		
		surveyScreen=new JLabel();
		surveyScreen.setBounds(0, 0,Initialize.WIDTH,Initialize.HEIGHT);
		surveyScreen.setIcon(LoadImages.surveyGUIImages[2]);
		surveyScreen.setVisible(true);
		screen.add(surveyScreen);
		
		frame.repaint();
	}
	//Update the panel after changes 
	private void panelUpdate(boolean flag) {
		
		//show selected buttons
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				// creates a border around the buttons.
				if(Initialize.surveyAnswers[i+CONSTANT]==j)
					SurveyGUI1.answerButtons[i+CONSTANT][j].setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 120, 215), 3));

				else
					SurveyGUI1.answerButtons[i+CONSTANT][j].setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

			}
		}
		
		if(flag) {
			//change screen (show/hide error)
			if (!checkEmptyInfo())
				surveyScreen.setIcon(LoadImages.surveyGUIImages[2]);
			
			else 
				surveyScreen.setIcon(LoadImages.surveyGUIImages[3]);
		}
		
		frame.repaint();
	}
	

	

	// Checks if any of the text fields are blank
	// Displays an error message beside blank fields by enabling and/or setting JLabels
	// Returns boolean
	private boolean checkEmptyInfo() {
		// Default set to false
		// If any of the following fields are empty, anyEmpty set to true
		boolean anyEmpty = false;
		
		for(int i =CONSTANT;i<5+CONSTANT;i++) {
			if (Initialize.surveyAnswers[i]==-1)
				anyEmpty = true;
		}
		
		// Return boolean
		return anyEmpty;
	}



	
	// ActionPerformed - when user clicks any button
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == backButton) { // If user clicked SurveyGUI2
			new SurveyGUI1(); // Create new SurveyGUI (redirect back to SurveyGUI1)
			frame.setVisible(false); // Make current screen invisible
			
		} 
		else if (event.getSource() == continueButton) { // if user clicked continueButton
			panelUpdate(true);
			if (! checkEmptyInfo()) { // Calls validation method to check if all information entered is valid
				new SurveyGUI3();
				frame.setVisible(false);

			}
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (event.getSource() == SurveyGUI1.answerButtons[i+CONSTANT][j])
					Initialize.surveyAnswers[i+CONSTANT]=j;
			}
		}
		
		panelUpdate(false);
		repaint();

	}

}
