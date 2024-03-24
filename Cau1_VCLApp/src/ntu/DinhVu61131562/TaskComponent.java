package ntu.DinhVu61131562;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class TaskComponent extends JPanel implements ActionListener {

	private JCheckBox checkBox;
	private JTextPane taskField;
	private JButton deleteButton;
	private JPanel parentPanel;
	
	public JTextPane getTaskField() {
		return taskField;
	}


	/**
	 * Create the panel.
	 */
	public TaskComponent(JPanel parentPanel) {
		this.parentPanel = parentPanel;
		
		taskField = new JTextPane();
		taskField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		taskField.setPreferredSize(CommonConfigs.TASKFIELD_SIZE);
		taskField.setContentType("text/html");
		taskField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				taskField.setBackground(null);
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				taskField.setBackground(Color.WHITE);
			}
		});
		
		checkBox = new JCheckBox();
		checkBox.setPreferredSize(CommonConfigs.CHECKBOX_SIZE);
		checkBox.addActionListener(this);
		
		deleteButton = new JButton("X");
		deleteButton.setPreferredSize(CommonConfigs.DELETE_BUTTON_SIZE);
		deleteButton.addActionListener(this);
		
		add(checkBox);
		add(taskField);
		add(deleteButton);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(checkBox.isSelected()) {
			String taskText = taskField.getText().replaceAll("<[^>]*>","");
			taskField.setText("<html><s>" + taskText + "</s></html>");
			
		}else if (!checkBox.isSelected()) {
			String taskText = taskField.getText().replaceAll("<[^>]*>","");
			taskField.setText(taskText);
			
		}
		
		if(e.getActionCommand().equalsIgnoreCase("X")) {
			parentPanel.remove(this);
			parentPanel.repaint();
			parentPanel.revalidate();
		}
		
	}

}
