package ntu.DinhVu61131562;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class VCLGui extends JFrame {
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
		scrollPane.setMaximumSize(CommonConfigs.TASKPANEL_SIZE);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.getContentPane().add(bannerLabel);
		this.getContentPane().add(scrollPane);
		
	}
	
}
