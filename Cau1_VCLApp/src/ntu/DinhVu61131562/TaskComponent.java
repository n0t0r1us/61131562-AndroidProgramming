package ntu.DinhVu61131562;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class TaskComponent extends JPanel {

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
		taskField.setPreferredSize(CommonConfigs.TASKFIELD_SIZE);
		taskField.setContentType("text/html");
		
		checkBox = new JCheckBox();
		checkBox.setPreferredSize(CommonConfigs.CHECKBOX_SIZE);
		
		deleteButton = new JButton("X");
		deleteButton.setPreferredSize(CommonConfigs.DELETE_BUTTON_SIZE);
		
		
		add(taskField);
		add(checkBox);
		add(deleteButton);

	}

}
