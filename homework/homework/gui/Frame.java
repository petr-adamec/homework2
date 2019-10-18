package homework.homework.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import homework.homework.dto.PhoneBookDto;
import homework.homework.service.PhoneBookService;
import homework.homework.serviceImpl.PhoneBookServiceImpl;

public class Frame {

	private JFrame jframe;
	PhoneBookService phoneBookService = new PhoneBookServiceImpl();

	public static final String[] columnNames = { "id", "name", "phone" };

	List<PhoneBookDto> data;
	private JTable table;
	AbstractTableModel model;

	public PhoneBookService getPhoneBookService() {
		return phoneBookService;
	}

	public void setPhoneBookService(PhoneBookService phoneBookService) {
		this.phoneBookService = phoneBookService;
	}

	public void init() {

		this.data = phoneBookService.list();

		this.jframe = new JFrame("PhoneBook");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(1024, 768);
		Container content = jframe.getContentPane();
		jframe.setLayout(new BorderLayout());
		JPanel topPanel = getTopPanel();
		content.add(topPanel, BorderLayout.NORTH);
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridLayout(1, 1));
		content.add(middlePanel, BorderLayout.CENTER);
		model = getModel();
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(table);
		middlePanel.add(scroll);

		jframe.setVisible(true);
	}

	private JPanel getTopPanel() {
		// TODO Auto-generated method stub
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(1, 3, 10, 10));
		top.add(getAddButton());
		top.add(getRemoveButton());
		top.add(getSearchPanel());
		return top;
	}

	private Component getSearchPanel() {
		// TODO Auto-generated method stub
		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(1, 2, 5, 5));
		final JTextField input = new JTextField("search by name here");
		input.setMinimumSize(new Dimension(50, 10));
		pan.add(input);
		JButton search = new JButton("search");

		pan.add(search);
		search.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String name = input.getText();
				if (name == null || name.equals("")) {
					data = phoneBookService.list();
				} else {
					data = phoneBookService.findByName(name);
				}

				model.fireTableDataChanged();

			}
		});
		return pan;
	}

	private Component getRemoveButton() {
		JButton remove = new JButton("remove");
		remove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				Object rowNumber = table.getValueAt(row, 0);
				if (rowNumber == null) {
					JOptionPane.showMessageDialog(jframe, "select an entry first");
				} else {
					phoneBookService.removeEntry(data.get(row));
					data = phoneBookService.list();
					model.fireTableDataChanged();
				}

			}
		});

		return remove;
	}

	private Component getAddButton() {
		JButton add = new JButton("add");
		add.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog("Enter the name", "the name");
				if (name == null || !name.matches("^\\w+$")) {
					JOptionPane.showMessageDialog(jframe, "invalid name");
				} else {
					String phone = JOptionPane.showInputDialog("Enter phone", "some phone");
					if (phone == null || !phone.matches("^\\d+$")) {
						JOptionPane.showMessageDialog(jframe, "invalid phone - use only numbers");
					} else {
						PhoneBookDto dto = new PhoneBookDto(name, phone);
						if(phoneBookService.findByPhone(phone) != null) {
							JOptionPane.showMessageDialog(jframe, "The phone already exists");
						}else {
							phoneBookService.addEntry(dto);
							data = phoneBookService.list();
							model.fireTableDataChanged();
						}
					}
				}
			}
		});
		return add;
	}

	private AbstractTableModel getModel() {
		AbstractTableModel model = new AbstractTableModel() {
			public String getColumnName(int col) {
				return columnNames[col].toString();
			}

			public int getRowCount() {
				return data.size();
			}

			public int getColumnCount() {
				return columnNames.length;
			}

			public Object getValueAt(int row, int col) {
				PhoneBookDto r = data.get(row);
				switch (col) {
				case 0:
					return r.getId();
				case 1:
					return r.getName();
				case 2:
					return r.getPhone();
				}
				return null;
			}

			public boolean isCellEditable(int row, int col) {
				return col > 0;
			}

			public void setValueAt(Object value, int row, int col) {
				PhoneBookDto r = data.get(row);
				switch (col) {
				case 0:
					r.setId((Integer) value);
				case 1:
					r.setName((String) value);
				case 2:
					r.setPhone((String) value);
				}
				fireTableCellUpdated(row, col);
			}
		};

		return model;
	}

}
