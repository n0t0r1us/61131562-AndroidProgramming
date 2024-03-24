package ntu.DinhVu61131562;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class VCLGui extends JFrame {
	/**
	 * Create the frame.
	 */
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
		
	}
}
