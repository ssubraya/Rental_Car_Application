package com.RentalCar.Frames;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
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

public class IncidentFrame extends JFrame implements RowSetListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection connection;
	JTable table; // The table for displaying data

	JLabel labelIncidentID;
	JLabel labelcustomerid;	
	JLabel labelemployeeid;	
	JLabel labelmechanicid;
	JLabel labelincidentdetails;
	
	JTextField textFieldincidentid;
	JTextField textFieldcustomerid;
	JTextField textFieldemployeeid;
	JTextField textFieldmechanicid;
	JTextField textFieldincidentdetails;

	JButton button_ADD_ROW;
	JButton button_UPDATE_DATABASE;
	JButton button_DISCARD_CHANGES;
	JButton button_DELETE_CHANGES;
	CustomerModel customerModel;

	public IncidentFrame() throws SQLException {

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
		CachedRowSet myCachedRowSet = getContentsOfIncidentTable();
		customerModel = new CustomerModel(myCachedRowSet);
		customerModel.addEventHandlersToRowSet(this);

		table = new JTable(); // Displays the table
		table.setModel(customerModel);
		
		labelIncidentID= new JLabel();
		labelemployeeid= new JLabel();
		labelcustomerid= new JLabel();
		labelmechanicid= new JLabel();
		labelincidentdetails= new JLabel();

		textFieldincidentid= new JTextField(10);
		textFieldemployeeid= new JTextField(10);
		textFieldcustomerid= new JTextField(10);
		textFieldmechanicid= new JTextField(10);
		textFieldincidentdetails= new JTextField(10);

		button_ADD_ROW = new JButton();
		button_UPDATE_DATABASE = new JButton();
		button_DISCARD_CHANGES = new JButton();
		button_DELETE_CHANGES=new JButton();
		
		labelIncidentID.setText("Incident ID:");
		labelcustomerid.setText("Cutomer ID:");
		labelmechanicid.setText("Mechanic ID:");	
		labelincidentdetails.setText("Incident Detail: ");
		
		textFieldincidentid.setText("Please enter the Payment ID which needs to be updated");
		textFieldcustomerid.setText("Enter Customer ID");
		textFieldmechanicid.setText("Enter Mechanic Id");
		textFieldincidentdetails.setText("Enter Incident details");
		
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
		contentPane.add(labelIncidentID, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		contentPane.add(textFieldincidentid, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		contentPane.add(labelcustomerid, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		contentPane.add(textFieldcustomerid, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		contentPane.add(labelmechanicid, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		contentPane.add(textFieldmechanicid, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		contentPane.add(labelincidentdetails, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		contentPane.add(textFieldincidentdetails, c);
		
		
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

				JOptionPane.showMessageDialog(IncidentFrame.this,
						new String[] { "Adding the following row:",								
								"Incident Detail: [" + textFieldincidentdetails.getText() + "]"});

				try {

					insertIncidentTable(
							textFieldincidentdetails.getText().trim());
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
					
					
					updateIncidentTable(
							textFieldincidentid.getText().trim(),
							Integer.parseInt(textFieldcustomerid.getText().trim()),
							textFieldmechanicid.getText().trim(),
							textFieldincidentdetails.getText().trim());
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
					
					deleteIncidentTable(
							textFieldincidentid.getText().trim());
					createNewTableModel();
					clearField();
				
				} catch (SQLException sqle) {
					displaySQLExceptionDialog(sqle);
				}
			}
		});

	}

	private void createNewTableModel() throws SQLException {
		customerModel = new CustomerModel(getContentsOfIncidentTable());
		customerModel.addEventHandlersToRowSet(this);
		table.setModel(customerModel);
	}

	private void displaySQLExceptionDialog(SQLException e) {

		// Display the SQLException in a dialog box
		JOptionPane.showMessageDialog(IncidentFrame.this,
				new String[] { e.getClass().getName() + ": ", e.getMessage() });
	}

	private CachedRowSet getContentsOfIncidentTable() {

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

			crs.setCommand("select incidentid, customerid, employeeid, mechanicid, incidentdetails from incident");
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
	
	private void updateIncidentTable(String incidentid,int customerid,String mechanicid,String incidentdetails) {

		String updateQuery = "UPDATE `incident` SET  customerid=?, "
				+ " mechanicid=?, incidentdetails=? "
				+ " WHERE `incidentid`=? ";
		try {
			PreparedStatement pst = connection.prepareStatement(updateQuery);
			pst.setInt(1, customerid);
			pst.setString(2, mechanicid);
			pst.setString(3, incidentdetails);
			pst.setString(4, incidentid);
			
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void deleteIncidentTable(String incidentid) {

		
		String deleteQuery = "DELETE FROM incident "
				+ "WHERE `incidentid`=? ";
		try {
			PreparedStatement pst = connection.prepareStatement(deleteQuery);
			
			pst.setString(1, incidentid);
			
			
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void insertIncidentTable(String incidentdetails) {

		DateFormat dateFormat = new SimpleDateFormat("MMddyyyyHHmmss");
		Date date = new Date();
		String incidentID= "INC"+dateFormat.format(date);	
		
		String insertIntoCustomerReservationQuery = "INSERT INTO `incident` (incidentid, customerid, employeeid, mechanicid, incidentdetails) "
				+ "VALUES (?,?,?,?,?);";
		try {
			PreparedStatement pst = connection.prepareStatement(insertIntoCustomerReservationQuery);
			pst.setString(1, incidentID);
			pst.setInt(2, 1);
			pst.setString(3, "Tom06292016113838");
			pst.setString(4, "Gibson06292016113805");
			pst.setString(5, incidentdetails);
			
			
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void clearField(){
		textFieldincidentid.setText("Please enter the Payment ID which needs to be updated");
		textFieldcustomerid.setText("Enter Customer ID");
		textFieldmechanicid.setText("Enter Mechanic Id");
		textFieldincidentdetails.setText("Enter Incident details");
	}
	
	
	
	public static void main(String[] args) {
		try {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			IncidentFrame incidentFrame = new IncidentFrame();
			incidentFrame.setTitle("Incident");
			incidentFrame.setSize(screenSize.width, screenSize.height);
			incidentFrame.setVisible(true);
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

			JOptionPane.showMessageDialog(IncidentFrame.this, new String[] { // Display
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
