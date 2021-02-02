package infobiszt;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.AbstractAction;
import javax.swing.JButton;

public class DeletePage {
	private static JFrame frmDelete;
	private static JPanel panel;

	public DeletePage() {
		frmDelete = new JFrame();
		panel = new JPanel();
		panel.setBounds(0, 0, 866, 571);

		frmDelete.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDelete.setTitle("Delete");
		frmDelete.setSize(882, 610);
		frmDelete.setVisible(true);
		frmDelete.getContentPane().setLayout(null);
		frmDelete.setLocationRelativeTo(null);
		frmDelete.getContentPane().add(panel);
		try {
			BufferedReader reader = new BufferedReader(new FileReader("shadow.txt"));
			int lines = 0;
			while (reader.readLine() != null)
				lines++;
			reader.close();

			ArrayList<Data> dataList = new ArrayList<Data>();
			BufferedReader reader1;
			String[] tomb = new String[(lines * 3) - 3];

			try {
				reader1 = new BufferedReader(new FileReader("shadow.txt"));
				reader1.readLine();
				String line = reader1.readLine();

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
						line = reader1.readLine();
					}

				} catch (Exception e) {

				}
				reader1.close();
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

			String[] columns = new String[] { "WeB Name", "Web URL", "Web Password" };
			final Class[] columnClass = new Class[] { String.class, String.class, String.class };

			DefaultTableModel model = new DefaultTableModel(MainPage.Read(), columns) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}

				@Override
				public Class<?> getColumnClass(int columnIndex) {
					return columnClass[columnIndex];
				}
			};
			panel.setLayout(null);

			JTable table_2 = new JTable(model);
			table_2.setShowGrid(false);
			table_2.setShowVerticalLines(false);
			table_2.setBackground(Color.LIGHT_GRAY);
			table_2.setForeground(SystemColor.menuText);
			table_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table_2.setFont(new Font("Times New Roman", Font.BOLD, 20));

			JScrollPane scrollPane = new JScrollPane(table_2);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setBounds(10, 55, 846, 444);
			panel.add(scrollPane);
			scrollPane.setViewportBorder(UIManager.getBorder("ComboBox.border"));

			JButton btnClose = new JButton(new AbstractAction("CLOSE") {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						new MainPage();
						frmDelete.dispose();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			});
			btnClose.setBounds(10, 510, 138, 50);
			panel.add(btnClose);

			JButton btnDelete = new JButton(new AbstractAction("DELETE") {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						int n = table_2.getSelectedRow();
						if (n < 0) {
							JOptionPane.showMessageDialog(null, "Choose first!");
						} else {
							Delete(n);
							new MainPage();
							frmDelete.dispose();
						}
					} catch (Exception e1) {

						e1.printStackTrace();
					}
				}

				private void Delete(int n) {
					try {
						ArrayList<String> result = new ArrayList<>();

						BufferedReader br = new BufferedReader(new FileReader("shadow.txt"));
						while (br.ready()) {
							result.add(br.readLine());
						}
						
						br.close();
						result.remove(n + 1);

						FileWriter writer = new FileWriter("shadow.txt");
						int counter = 0;
						for (String str : result) {
							
							if (counter == result.size()-1) {
								writer.write(str);
								
							} else {
								writer.write(str + System.lineSeparator());
								counter++;
								
							}
						}
						writer.close();

					} catch (Exception asd) {
						System.out.println("delet error");
					}
				}

			});
			btnDelete.setBounds(718, 510, 138, 50);
			panel.add(btnDelete);

			JLabel lblNewLabel = new JLabel("Choose Row!");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 31));
			lblNewLabel.setBounds(10, 11, 846, 33);
			panel.add(lblNewLabel);

		} catch (Exception e) {
			System.out.println(e + " Delet read ERROR");
		}

	}
}
