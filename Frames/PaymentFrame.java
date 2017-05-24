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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

public class PaymentFrame extends JFrame implements RowSetListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection connection;
	JTable table; // The table for displaying data

	JLabel labelPaymentID;
	JLabel labelAmount;	
	JLabel labelPaymentStatus;	
	JLabel labelPaymentType;
	
	JTextField textFieldPaymentID;
	JTextField textFieldAmount;
	JTextField textFieldPaymentStatus;
	JTextField textFieldPaymentType;

	JButton button_ADD_ROW;
	JButton button_UPDATE_DATABASE;
	JButton button_DISCARD_CHANGES;
	JButton button_DELETE_CHANGES;
	CustomerModel customerModel;

	public PaymentFrame() throws SQLException {

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
		
		labelPaymentID= new JLabel();
		labelAmount= new JLabel();
		labelPaymentStatus= new JLabel();
		labelPaymentType= new JLabel();

		textFieldPaymentID= new JTextField(10);
		textFieldAmount= new JTextField(10);
		textFieldPaymentStatus= new JTextField(10);
		textFieldPaymentType= new JTextField(10);

		button_ADD_ROW = new JButton();
		button_UPDATE_DATABASE = new JButton();
		button_DISCARD_CHANGES = new JButton();
		button_DELETE_CHANGES=new JButton();
		
		labelPaymentID.setText("Payment ID:");		
		labelAmount.setText("Amount : ");
		labelPaymentStatus.setText("Payment current Status: ");
		labelPaymentType.setText("Payment Type: ");
		
		textFieldPaymentID.setText("Please enter the Payment ID which needs to be updated");
		textFieldAmount.setText("Enter amount here");
		textFieldPaymentStatus.setText("Enter payment status here");
		textFieldPaymentType.setText("Enter payment type here");

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
		c.gridy = 2;
		c.gridwidth = 1;
		contentPane.add(labelPaymentID, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		contentPane.add(textFieldPaymentID, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		contentPane.add(labelAmount, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		contentPane.add(textFieldAmount, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		contentPane.add(labelPaymentStatus, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		contentPane.add(textFieldPaymentStatus, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		contentPane.add(labelPaymentType, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		contentPane.add(textFieldPaymentType, c);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		contentPane.add(button_ADD_ROW, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		contentPane.add(button_UPDATE_DATABASE, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 1;
		contentPane.add(button_DELETE_CHANGES, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 7;
		c.gridwidth = 1;
		contentPane.add(button_DISCARD_CHANGES, c);		

		button_ADD_ROW.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(PaymentFrame.this,
						new String[] { "Adding the following row:",
								"Payment ID: [" + textFieldPaymentID.getText() + "]",
								"Amount: [" + textFieldAmount.getText() + "]",
								"Payment Stauts: [" + textFieldPaymentStatus.getText() + "]",
								"Payment Type: [" + textFieldPaymentType.getText() + "]"});

				try {

					insertPaymentTable(
							Integer.parseInt(textFieldAmount.getText().trim()),
							textFieldPaymentStatus.getText().trim(),
							textFieldPaymentType.getText().trim());
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
					
					
					updatePaymentTable(
							textFieldPaymentID.getText().trim(),
							Integer.parseInt(textFieldAmount.getText().trim()),
							textFieldPaymentStatus.getText().trim(),
							textFieldPaymentType.getText().trim());
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
					
					deletePaymentTable(
							textFieldPaymentID.getText().trim());
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
		JOptionPane.showMessageDialog(PaymentFrame.this,
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

			crs.setCommand("select paymentid,customerid, amount, paymentstatus, paymenttype from payment");
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
	
	private void updatePaymentTable(String paymentid,int amount,String paymentStatus,String paymentType) {

		String insertIntoCustomerReservationQuery = "UPDATE `payment` SET `amount`=?,"
				+ " `paymentstatus`=?, `paymenttype`=? "
				+ " WHERE `paymentid`=? ";
		try {
			PreparedStatement pst = connection.prepareStatement(insertIntoCustomerReservationQuery);
			pst.setInt(1, amount);
			pst.setString(2, paymentStatus);
			pst.setString(3, paymentType);
			pst.setString(4, paymentid);
			
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void deletePaymentTable(String paymentid) {

		
		String insertIntoCustomerReservationQuery = "DELETE FROM payment "
				+ "WHERE `paymentid`=? ";
		try {
			PreparedStatement pst = connection.prepareStatement(insertIntoCustomerReservationQuery);
			
			pst.setString(1, paymentid);
			
			
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void insertPaymentTable(int amount,String paymentStatus,String paymentType) {

		DateFormat dateFormat = new SimpleDateFormat("MMddyyyyHHmmss");
		Date date = new Date();
		String paymentId= paymentType+dateFormat.format(date);	
		
		String insertIntoCustomerReservationQuery = "INSERT INTO `payment` ( `paymentid`, `customerid`, `amount`,"
				+ " `paymentstatus`, `paymenttype`) "
				+ "VALUES (?,?,?,?,?);";
		try {
			PreparedStatement pst = connection.prepareStatement(insertIntoCustomerReservationQuery);
			pst.setString(1, paymentId);
			pst.setString(2, "2");
			pst.setInt(3, amount);
			pst.setString(4, paymentStatus);
			pst.setString(5, paymentType);
			
			
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void clearField(){
		textFieldPaymentID.setText("Please enter the payment ID which needs to be updated");
		textFieldAmount.setText("Enter amount here");
		textFieldPaymentStatus.setText("Enter payment status here");
		textFieldPaymentType.setText("Enter payment type here");
	}
	
	
	
	public static void main(String[] args) {
		try {
			PaymentFrame customerFrame = new PaymentFrame();
			customerFrame.setTitle("Payment");
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

			JOptionPane.showMessageDialog(PaymentFrame.this, new String[] { // Display
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
