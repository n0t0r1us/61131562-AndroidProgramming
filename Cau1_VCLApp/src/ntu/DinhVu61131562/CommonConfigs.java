package ntu.DinhVu61131562;

import java.awt.*;
public class CommonConfigs {
	
	public static final Dimension GUI_SIZE = new Dimension(540, 660);
	
	public static final Dimension BANNER_SIZE = new Dimension(GUI_SIZE.width, 50);	
	
	public static final Dimension TASKPANEL_SIZE = new Dimension(GUI_SIZE.width - 30, GUI_SIZE.height - 175);
	
	public static final Dimension ADDTASKBUTTON_SIZE = new Dimension (GUI_SIZE.width, 50);
	
	public static final Dimension TASKFIELD_SIZE = new Dimension((int)(TASKPANEL_SIZE.width * 0.80), 50);
	
	public static final Dimension CHECKBOX_SIZE = new Dimension((int)(TASKFIELD_SIZE.width * 0.05), 50);
	
	public static final Dimension DELETE_BUTTON_SIZE = new Dimension((int)(TASKFIELD_SIZE.width * 0.12), 50);
	

}
