package infobiszt;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.*;
import java.awt.Font;

public class LoginPage implements ActionListener {

	private static JLabel lblWellcome;
	private static JLabel lblPsw;
	protected static JPasswordField TxtPsw;
	private static JButton btnLogin;
	private static JButton btnClose;
	private static JFrame frame;
	private static JPanel panel;
	JMenu menu, submenu;
	JMenuItem i1;

	public LoginPage() {

		frame = new JFrame();
		panel = new JPanel();

		JMenuBar mb = new JMenuBar();
		menu = new JMenu("First time?");
		submenu = new JMenu("Sub Menu");
		i1 = new JMenuItem(new AbstractAction("Create my master password") {
			public void actionPerformed(ActionEvent e) {
				try {
					File secret = new File("shadow.txt");
					if(secret.length()==0) {
						new CreatePage();
						frame.dispose();
					}else {
						JOptionPane.showMessageDialog(null,"You already have one!");
					}
				} catch (Exception e1) {
					System.out.println(e1+" java creat menu exeption");
				}
			}
		});
		
		menu.add(i1);
		mb.add(menu);
		frame.setJMenuBar(mb);

		frame.setSize(364, 233);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Login");
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		lblWellcome = new JLabel("WELCOME!");
		lblWellcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWellcome.setFont(new Font("Times New Roman", Font.BOLD, 32));
		lblWellcome.setBounds(0, 11, 334, 49);
		panel.add(lblWellcome);

		lblPsw = new JLabel("Password:");
		lblPsw.setHorizontalAlignment(SwingConstants.CENTER);
		lblPsw.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblPsw.setBounds(0, 59, 96, 25);
		panel.add(lblPsw);

		TxtPsw = new JPasswordField();
		TxtPsw.setBounds(105, 60, 208, 25);
		panel.add(TxtPsw);

		btnLogin = new JButton(new AbstractAction("LOGIN") {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (PassT.toHexString(PassT.getSHA(TxtPsw.getText())).equals(Reader())) {
						new MainPage();
						frame.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(null, "No, its not");
					}
				} catch (HeadlessException | NoSuchAlgorithmException | IOException e1) {
					System.out.println(e1 + "login fail");
					e1.printStackTrace();
				}
			}

		});
		btnLogin.setBounds(233, 118, 80, 25);
		panel.add(btnLogin);

		btnClose = new JButton(new AbstractAction("CLOSE") {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnClose.setBounds(16, 118, 80, 25);
		panel.add(btnClose);

		frame.setVisible(true);

	}

	public static void main(String[] args) {
		new LoginPage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e + "Override button");
	}

	public static String Reader() throws IOException {
		BufferedReader reader;
		reader = new BufferedReader(new FileReader("shadow.txt"));
		String line = reader.readLine();
		reader.close();
		return line;

	}
}
