package infobiszt;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class CreatePage {
	private static JLabel lblYours;
	private static JFrame frmCreate;
	private static JPanel panel;
	private static JTextField txtFirstPsw;
	private static JTextField txtSecondPsw;

	public CreatePage() {
		frmCreate = new JFrame();
		panel = new JPanel();
		panel.setBounds(0, 0, 342, 353);

		frmCreate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCreate.setTitle("Create");
		frmCreate.setSize(357, 405);
		frmCreate.getContentPane().setLayout(null);
		frmCreate.getContentPane().add(panel);
		frmCreate.setVisible(true);
		frmCreate.setLocationRelativeTo(null);
		panel.setLayout(null);
		

		lblYours = new JLabel("New master password!");
		lblYours.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblYours.setHorizontalAlignment(SwingConstants.CENTER);
		lblYours.setBounds(0, 11, 345, 39);
		panel.add(lblYours);

		txtFirstPsw = new JTextField();
		txtFirstPsw.setBounds(0, 86, 345, 39);
		panel.add(txtFirstPsw);

		txtSecondPsw = new JTextField();
		txtSecondPsw.setBounds(0, 176, 345, 39);
		panel.add(txtSecondPsw);

		JLabel lblPleaseTypeYour = new JLabel("Please type your password");
		lblPleaseTypeYour.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseTypeYour.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPleaseTypeYour.setBounds(0, 46, 345, 39);
		panel.add(lblPleaseTypeYour);

		JLabel lblAgain = new JLabel("Please type your password again");
		lblAgain.setHorizontalAlignment(SwingConstants.CENTER);
		lblAgain.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblAgain.setBounds(0, 126, 345, 39);
		panel.add(lblAgain);

		JLabel lblNewLabel = new JLabel(
				"<html>If you lost, or forget this password,<br> you lost all of your passwords there is no option for password request</html>");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 226, 322, 70);
		panel.add(lblNewLabel);

		JButton btnCancel = new JButton(new AbstractAction("Cancel") {

			@Override
			public void actionPerformed(ActionEvent e) {
				frmCreate.dispose();
			}
		});
		btnCancel.setBounds(10, 319, 89, 23);
		panel.add(btnCancel);

		JButton btnCreate = new JButton(new AbstractAction("Create") {
			@Override
			public void actionPerformed(ActionEvent e) {
				String psw1 = txtFirstPsw.getText();
				String psw2 = txtSecondPsw.getText();
				if (psw1.isEmpty() || psw2.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Fill both!");
				} else {
					if (psw1.equals(psw2)) {
						try {
							BufferedWriter writer = new BufferedWriter(new FileWriter("shadow.txt", true));
							String shpsw1 = PassT.toHexString(PassT.getSHA(psw1));

							writer.append(shpsw1);
							writer.close();
							JOptionPane.showMessageDialog(null, "You have created a new masster password");
							new LoginPage();
							frmCreate.dispose();

						} catch (IOException | NoSuchAlgorithmException e2) {
							System.out.println("file write error in create  " + e2);

						}
					} else {
						JOptionPane.showMessageDialog(null, "Use the same passwords!");
					}
				}
			}
		});
		btnCreate.setBounds(243, 319, 89, 23);
		panel.add(btnCreate);
	}
}
