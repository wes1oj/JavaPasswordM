package infobiszt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddPage implements ActionListener {
	private static JLabel lblYours;
	private static JFrame frame;
	private static JPanel panel;
	private JButton btnClose;
	private JButton btnAdd;
	private static JTextField txtName;
	private static JTextField txtUrl;
	private static JTextField txtPass;

	public AddPage() {
		frame = new JFrame();
		panel = new JPanel();//asd!
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("add");
		frame.setSize(324, 342);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		panel.setLayout(new GridLayout(0, 1));

		lblYours = new JLabel("New password!");
		panel.add(lblYours);

		lblYours = new JLabel("Website name!");
		panel.add(lblYours);

		txtName = new JTextField();
		panel.add(txtName);

		lblYours = new JLabel("Website Url, please dont use ':'");
		panel.add(lblYours);

		txtUrl = new JTextField();
		panel.add(txtUrl);

		lblYours = new JLabel("Password");
		panel.add(lblYours);

		txtPass = new JTextField();
		panel.add(txtPass);

		btnClose = new JButton(new AbstractAction("CLOSE") {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new MainPage();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
		panel.add(btnClose);

		btnAdd = new JButton(new AbstractAction("ADD") {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String url = txtUrl.getText();
				String pw = txtPass.getText();
				if (name.isEmpty() || url.isEmpty() || pw.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Error");
				} else {
					try {
						
						new Data(name, url, pw);
						String pwb = null;
						String urlb = null;
						String nameb = null;
						pwb = PassT.Encrypt(pw.getBytes());
						urlb = PassT.Encrypt(url.getBytes());
						nameb = PassT.Encrypt(name.getBytes());
						
						name=new String(nameb);						
						url=new String(urlb);
						pw=new String(pwb);
						writeFile(name, url, pw);
					} catch (Exception e2) {
						System.out.println("encryaddexeption" + e2);
					}
					try {
						new MainPage();
						frame.dispose();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					frame.dispose();
				}
			}
		});
		panel.add(btnAdd);
		frame.setVisible(true);
	}

	private void writeFile(String name, String url, String pw) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("shadow.txt", true));
			writer.append("\n");
			writer.append(name + ":" + url + ":" + pw);
			writer.close();
		} catch (IOException e) {
			System.out.println("File write error");
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e + "Override button");
	}
}
