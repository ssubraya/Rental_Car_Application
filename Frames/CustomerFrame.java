package com.RentalCar.Frames;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.RentalCar.dbconnection.MakeConnection;
import com.RentalCar.model.CustomerModel;
import com.sun.rowset.CachedRowSetImpl;

public class CustomerFrame extends JFrame implements RowSetListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection connection;
	JTable table; // The table for displaying data

	JLabel labelCustomerID;
	JLabel labelFirstName;
	JLabel labelLastName;
	JLabel labelEmail;	
	JLabel labelPassword;
	JLabel labelAddres;
	JLabel labelCreditCard;
	JLabel labelBookingDetails;
	
	JTextField textFieldCustomerID;
	JTextField textFieldFirstName;
	JTextField textFieldLastName;
	JTextField textFieldEmail;	
	JTextField textFieldPassword;
	JTextField textFieldAddres;
	JTextField textFieldCreditCard;
	JTextField textFieldBookingDetails;

	JButton button_ADD_ROW;
	JButton button_UPDATE_DATABASE;
	JButton button_DISCARD_CHANGES;
	JButton button_DELETE_CHANGES;
	CustomerModel customerModel;

	public CustomerFrame() throws SQLException {

		// get the connection
		connection = MakeConnection.getConnection("cs521");

		// event listen when window is closed
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("Window is closed..");
				try {
					connection.close();
				} catch (SQLException ex) {
					printSQLException(ex);
				}
				System.exit(0);
			}
		});

		// create the frame components
		CachedRowSet myCachedRowSet = getContentsOfCustomerTable();
		customerModel = new CustomerModel(myCachedRowSet);
		customerModel.addEventHandlersToRowSet(this);

		table = new JTable(); // Displays the table
		table.setModel(customerModel);
		
		labelCustomerID= new JLabel();
		labelFirstName= new JLabel();
		labelLastName= new JLabel();
		labelEmail= new JLabel();
		labelPassword= new JLabel();
		labelAddres= new JLabel();
		labelCreditCard= new JLabel();
		labelBookingDetails= new JLabel();

		textFieldCustomerID= new JTextField(10);		
		textFieldFirstName= new JTextField(10);
		textFieldLastName= new JTextField(10);
		textFieldEmail= new JTextField(10);	
		textFieldPassword= new JTextField(10);
		textFieldAddres= new JTextField(10);
		textFieldCreditCard= new JTextField(10);
		textFieldBookingDetails= new JTextField(10);

		button_ADD_ROW = new JButton();
		button_UPDATE_DATABASE = new JButton();
		button_DISCARD_CHANGES = new JButton();
		button_DELETE_CHANGES=new JButton();
		
		labelCustomerID.setText("Customer ID:");
		labelFirstName.setText("First Name: ");
		labelLastName.setText("Last Name: ");
		labelEmail.setText("Email: ");
		labelPassword.setText("Password: ");
		labelAddres.setText("Address");
		labelCreditCard.setText("Credit Card");
		labelBookingDetails.setText("Booking Details");
		
		textFieldCustomerID.setText("Please enter the customer ID which needs to be updated");
		textFieldFirstName.setText("Enter First Name here");
		textFieldLastName.setText("Enter Last Name here");
		textFieldEmail.setText("Enter email id here");	
		textFieldPassword.setText("Enter Password here");
		textFieldAddres.setText("Enter Address here");
		textFieldCreditCard.setText("Enter Credit card here");
		textFieldBookingDetails.setText("Enter BookingDetails here");

		button_ADD_ROW.setText("Add row to table");
		button_UPDATE_DATABASE.setText("Update database");
		button_DISCARD_CHANGES.setText("Discard changes");
		button_DELETE_CHANGES.setText("DELETE Record");

		Container contentPane = getContentPane();
		contentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.5;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		contentPane.add(new JScrollPane(table), c);		
	
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.25;
		c.weighty = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		contentPane.add(labelCustomerID, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		contentPane.add(textFieldCustomerID, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.25;
		c.weighty = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		contentPane.add(labelFirstName, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		contentPane.add(textFieldFirstName, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		contentPane.add(labelLastName, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		contentPane.add(textFieldLastName, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		contentPane.add(labelEmail, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		contentPane.add(textFieldEmail, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		contentPane.add(labelPassword, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		contentPane.add(textFieldPassword, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		contentPane.add(labelAddres, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		contentPane.add(textFieldAddres, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 1;
		contentPane.add(labelCreditCard, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 7;
		c.gridwidth = 1;
		contentPane.add(textFieldCreditCard, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 1;
		contentPane.add(labelBookingDetails, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 8;
		c.gridwidth = 1;
		contentPane.add(textFieldBookingDetails, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 1;
		contentPane.add(button_ADD_ROW, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 9;
		c.gridwidth = 1;
		contentPane.add(button_UPDATE_DATABASE, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 10;
		c.gridwidth = 1;
		contentPane.add(button_DELETE_CHANGES, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 10;
		c.gridwidth = 1;
		contentPane.add(button_DISCARD_CHANGES, c);		

		button_ADD_ROW.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(CustomerFrame.this,
						new String[] { "Adding the following row:",
								"First Name: [" + textFieldFirstName.getText() + "]",
								"Last Name: [" + textFieldLastName.getText() + "]",
								"Email: [" + textFieldEmail.getText() + "]",
								"password: [" + textFieldPassword.getText() + "]",
								"Address: [" + textFieldAddres.getText() + "]",
								"Credit Card: [" + textFieldCreditCard.getText() + "]",
								"Booking Details: [" + textFieldBookingDetails.getText() + "]" });

				try {

					insertCustomerTable(
							textFieldFirstName.getText().trim(),
							textFieldLastName.getText().trim(),
							textFieldEmail.getText().trim(),
							textFieldPassword.getText().trim(),
							textFieldAddres.getText().trim(),
							Integer.parseInt(textFieldCreditCard.getText().trim()),
							textFieldBookingDetails.getText().trim());
					createNewTableModel();
					clearField();
				} catch (Exception sqle) {
					sqle.printStackTrace();
				}
			}

		});

		button_UPDATE_DATABASE.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					customerModel.customerRowSet.acceptChanges();
					
					
					updateCustomerTable(
							textFieldFirstName.getText().trim(),
							textFieldLastName.getText().trim(),
							textFieldEmail.getText().trim(),
							textFieldPassword.getText().trim(),
							textFieldAddres.getText().trim(),
							Integer.parseInt(textFieldCreditCard.getText().trim()),
							textFieldBookingDetails.getText().trim(),
							Integer.parseInt(textFieldCustomerID.getText().trim()));
					createNewTableModel();
					clearField();
					
				} catch (SQLException sqle) {
					displaySQLExceptionDialog(sqle);
					// Now revert back changes
					try {
						createNewTableModel();
					} catch (SQLException sqle2) {
						displaySQLExceptionDialog(sqle2);
					}
				}
			}

			
		});

		button_DISCARD_CHANGES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clearField();
				} catch (Exception sqle) {
					sqle.printStackTrace();
				}
			}
		});
		button_DELETE_CHANGES.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					
					deleteCustomerTable(
							Integer.parseInt(textFieldCustomerID.getText().trim()));
					createNewTableModel();
					clearField();
				
				} catch (SQLException sqle) {
					displaySQLExceptionDialog(sqle);
				}
			}
		});

	}

	private void createNewTableModel() throws SQLException {
		customerModel = new CustomerModel(getContentsOfCustomerTable());
		customerModel.addEventHandlersToRowSet(this);
		table.setModel(customerModel);
	}

	private void displaySQLExceptionDialog(SQLException e) {

		// Display the SQLException in a dialog box
		JOptionPane.showMessageDialog(CustomerFrame.this,
				new String[] { e.getClass().getName() + ": ", e.getMessage() });
	}

	private CachedRowSet getContentsOfCustomerTable() {

		// write the codd to fetch the records form reservation table
		// setting for scroll option

		CachedRowSet crs = null;
		try {
			connection = MakeConnection.getConnection("cs521");
			crs = new CachedRowSetImpl();
			crs.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
			crs.setConcurrency(ResultSet.CONCUR_UPDATABLE);
			crs.setUsername("root");
			crs.setPassword("password");

			// In MySQL, to disable auto-commit, set the property
			// relaxAutoCommit to
			// true in the connection URL.

			crs.setUrl("jdbc:mysql://localhost:3306/cs521?relaxAutoCommit=true");

			// Regardless of the query, fetch the contents of COFFEES

			crs.setCommand("select customerid, firstname, lastname, email, password, address, creditcard, bookingdetails from customer");
			crs.execute();

		} catch (SQLException e) {
			printSQLException(e);
		}
		return crs;

	}

	private void printSQLException(SQLException ex) {
		// TODO Auto-generated method stub
		for (Throwable e : ex) {
		      if (e instanceof SQLException) {
		        if (ignoreSQLException(((SQLException)e).getSQLState()) == false) {
		          e.printStackTrace(System.err);
		          System.err.println("SQLState: " + ((SQLException)e).getSQLState());
		          System.err.println("Error Code: " + ((SQLException)e).getErrorCode());
		          System.err.println("Message: " + e.getMessage());
		          Throwable t = ex.getCause();
		          while (t != null) {
		            System.out.println("Cause: " + t);
		            t = t.getCause();
		          }
		        }
		      }
		    }
	}

	private static boolean ignoreSQLException(String sqlState) {
	    if (sqlState == null) {
	      System.out.println("The SQL state is not defined!");
	      return false;
	    }
	    // X0Y32: Jar file already exists in schema
	    if (sqlState.equalsIgnoreCase("X0Y32"))
	      return true;
	    // 42Y55: Table already exists in schema
	    if (sqlState.equalsIgnoreCase("42Y55"))
	      return true;
	    return false;
	  }
	
	private void updateCustomerTable(String firstname,String lastname,String email,
			String password,String address,int creditcard,String bookingdetails,int customerid) {

		String insertIntoCustomerReservationQuery = "UPDATE `customer` SET `firstname`=?,"
				+ " `lastname`=?, `email`=?, `password`=?, `address`=?, `creditcard`=?, "
				+ "`bookingdetails`=? WHERE `customerid`=? ";
		try {
			PreparedStatement pst = connection.prepareStatement(insertIntoCustomerReservationQuery);
			pst.setString(1, firstname);
			pst.setString(2, lastname);
			pst.setString(3, email);
			pst.setString(4, password);
			pst.setString(5, address);
			pst.setInt(6, creditcard);
			pst.setString(7, bookingdetails);
			pst.setInt(8, customerid);
			
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void deleteCustomerTable(int customerID) {

		
		String insertIntoCustomerReservationQuery = "DELETE FROM customer "
				+ "WHERE `customerid`=? ";
		try {
			PreparedStatement pst = connection.prepareStatement(insertIntoCustomerReservationQuery);
			
			pst.setInt(1, customerID);
			
			
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void insertCustomerTable(String firstname,String lastname,String email,
			String password,String address,int creditcard,String bookingdetails) {

		
		String insertIntoCustomerReservationQuery = "INSERT INTO `customer` ( `firstname`, `lastname`, `email`, `password`, `address`, `creditcard`, `bookingdetails`) "
				+ "VALUES (?,?,?,?,?,?,?);";
		try {
			PreparedStatement pst = connection.prepareStatement(insertIntoCustomerReservationQuery);
			pst.setString(1, firstname);
			pst.setString(2, lastname);
			pst.setString(3, email);
			pst.setString(4, password);
			pst.setString(5, address);
			pst.setInt(6, creditcard);
			pst.setString(7, bookingdetails);
			
			
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void clearField(){
		textFieldCustomerID.setText("Please enter the customer ID which needs to be updated");
		textFieldFirstName.setText("Enter First Name here");
		textFieldLastName.setText("Enter Last Name here");
		textFieldEmail.setText("Enter email id here");	
		textFieldPassword.setText("Enter Password here");
		textFieldAddres.setText("Enter Address here");
		textFieldCreditCard.setText("Enter Credit card here");
		textFieldBookingDetails.setText("Enter BookingDetails here");
	}
	
	
	
	public static void main(String[] args) {
		try {
			CustomerFrame customerFrame = new CustomerFrame();
			customerFrame.setTitle("Create Customer");
			customerFrame.setSize(600, 600);
			customerFrame.setVisible(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void rowSetChanged(RowSetEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rowChanged(RowSetEvent event) {

		CachedRowSet currentRowSet = this.customerModel.customerRowSet;

		try {
			currentRowSet.moveToCurrentRow();
			customerModel = new CustomerModel(customerModel.getCoffeesRowSet());
			table.setModel(customerModel);

		} catch (SQLException ex) {

			printSQLException(ex);

			// Display the error in a dialog box.

			JOptionPane.showMessageDialog(CustomerFrame.this, new String[] { // Display
																				// a
																				// 2-line
																				// message
					ex.getClass().getName() + ": ", ex.getMessage() });
		}
	}

	@Override
	public void cursorMoved(RowSetEvent event) {

	}

}
