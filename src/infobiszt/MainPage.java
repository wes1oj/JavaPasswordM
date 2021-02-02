package infobiszt;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import java.awt.SystemColor;
import java.awt.Color;

public class MainPage {
	private static JLabel lblYours;
	private static JFrame frame;
	private static JPanel panel;
	private JButton btnClose;
	private JButton btnAdd;
	private JButton btnDelete;

	
	public MainPage() throws IOException {
		mainpagebuilder();
	}
		
		public void mainpagebuilder() {
		frame = new JFrame();
		panel = new JPanel();
		frame.setVisible(true);
		lblYours = new JLabel("Your passwords!");
		lblYours.setHorizontalAlignment(SwingConstants.CENTER);
		lblYours.setFont(new Font("Times New Roman", Font.BOLD, 32));
		lblYours.setBounds(40, 11, 927, 49);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Main");
		frame.setSize(1034, 541);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		panel.setLayout(null);

		panel.add(lblYours);

		try {
			printdata(Read());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		btnAdd = new JButton(new AbstractAction("ADD") {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddPage();
				frame.dispose();
			}
		});
		btnAdd.setBounds(867, 435, 100, 45);
		panel.add(btnAdd);


		btnClose = new JButton(new AbstractAction("CLOSE") {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				System.exit(0);
			}
		});
		btnClose.setBounds(40, 435, 100, 45);
		panel.add(btnClose);
		

		btnDelete = new JButton(new AbstractAction("DELETE") {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DeletePage();
				frame.dispose();
			}
		});
		btnDelete.setBounds(418, 435, 164, 45);
		panel.add(btnDelete);


		}
	

	public static String[][] Read() throws IOException {
		ArrayList<Data> dataList = new ArrayList<Data>();
		BufferedReader reader;
		String[] tomb = new String[(Count() * 3) - 3];
		try {
			reader = new BufferedReader(new FileReader("shadow.txt"));
			reader.readLine();
			String line = reader.readLine();

			try {
				int counter = 0;
				while (line != null) {
					String result, result1, result2;
					String[] arrOfStr = line.split(":", -2);
					result = PassT.Decrypt(arrOfStr[0]);
					result1 = PassT.Decrypt(arrOfStr[1]);
					result2 = PassT.Decrypt(arrOfStr[2]);
					dataList.add(new Data(arrOfStr[0], arrOfStr[1], arrOfStr[2]));
					tomb[counter] = result;
					counter++;
					tomb[counter] = result1;
					counter++;
					tomb[counter] = result2;
					counter++;
					line = reader.readLine();
				}

			} catch (Exception e) {

			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
		String[][] nagytomb = new String[dataList.size()][3];
		int b = 0;
		for (int sor = 0; sor < dataList.size(); sor++) {

			for (int oszlop = 0; oszlop < 3; oszlop++) {
				nagytomb[sor][oszlop] = tomb[b];
				b++;
			}
		}
		return nagytomb;
	}

	private static int Count() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("shadow.txt"));
		int lines = 0;
		while (reader.readLine() != null)
			lines++;
		reader.close();
		return lines;
	}

	public static void printdata(String[][] tabeldata) throws IOException {

		String[] columns = new String[] { "WeB Name", "Web URL", "Web Password" };
		final Class[] columnClass = new Class[] { String.class, String.class, String.class };

		DefaultTableModel model = new DefaultTableModel(Read(), columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnClass[columnIndex];
			}
		};
		JTable table_2 = new JTable(model);
		table_2.setBackground(Color.LIGHT_GRAY);
		table_2.setForeground(SystemColor.menuText);
		table_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		table_2.setCellSelectionEnabled(true);

		JScrollPane scrollPane = new JScrollPane(table_2);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(40, 59, 927, 365);
		panel.add(scrollPane);
		scrollPane.setViewportBorder(UIManager.getBorder("ComboBox.border"));

	}
}