package ntu.DinhVu61131562;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class VCLGui extends JFrame implements ActionListener {
	/**
	 * Create the frame.
	 */
	
	private JPanel taskPanel, taskComponentPanel;
	
	public VCLGui() {
		super("Việc Cần Làm App");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(CommonConfigs.GUI_SIZE);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		
		addGuiComponents();
		
	}
	
	private void addGuiComponents() {
		JLabel bannerLabel = new JLabel("DANH SÁCH CÁC VIỆC CẦN LÀM");
		bannerLabel.setFont(createFont("resources/ComicHelvetic-Light.ttf",26f));
		bannerLabel.setBounds(
				(CommonConfigs.GUI_SIZE.width - bannerLabel.getPreferredSize().width)/2,
					15,
				CommonConfigs.BANNER_SIZE.width,
				CommonConfigs.BANNER_SIZE.height);
		
		taskPanel = new JPanel();
		
		taskComponentPanel = new JPanel();
		taskComponentPanel.setLayout(new BoxLayout(taskComponentPanel, BoxLayout.Y_AXIS));
		taskPanel.add(taskComponentPanel);
		
		JScrollPane scrollPane = new JScrollPane(taskPanel);
		scrollPane.setBounds(8, 70, CommonConfigs.TASKPANEL_SIZE.width, CommonConfigs.TASKPANEL_SIZE.height);
		scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
		scrollPane.setMaximumSize(CommonConfigs.TASKPANEL_SIZE);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
		verticalScrollBar.setUnitIncrement(20);
		
		JButton addTaskButton =  new JButton("Thêm Việc");
		addTaskButton.setFont(createFont("resources/ComicHelvetic-Light.ttf",18f));
		addTaskButton.setBounds(-5, CommonConfigs.GUI_SIZE.height - 88, 
				CommonConfigs.ADDTASKBUTTON_SIZE.width, CommonConfigs.ADDTASKBUTTON_SIZE.height);	
		addTaskButton.addActionListener(this);
		
		this.getContentPane().add(bannerLabel);
		this.getContentPane().add(scrollPane);
		this.getContentPane().add(addTaskButton);
		
	}

	private Font createFont(String resources, float size) {
		// TODO Auto-generated method stub
		String filePath = getClass().getClassLoader().getResource(resources).getPath();
		if(filePath.contains("%20")) {
			filePath = getClass().getClassLoader().getResource(resources).getPath()
					.replace("%20", " ");
		}
		
		try {
			File customFontFile = new File(filePath);
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, customFontFile).deriveFont(size);
			return customFont;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Lỗi: " + e);
		}
		
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		if(command.equalsIgnoreCase("Thêm Việc")) {
			TaskComponent taskComponent = new TaskComponent(taskComponentPanel);
			taskComponentPanel.add(taskComponent);
			
			taskComponent.getTaskField().requestFocus();
			repaint();
			revalidate();
		}
		
	}
	
}
