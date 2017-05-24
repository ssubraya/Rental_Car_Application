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
import com.RentalCar.model.ReportModel;
import com.sun.rowset.CachedRowSetImpl;

public class ReportFrame extends JFrame implements RowSetListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection connection;
	JTable table; // The table for displaying data

	JLabel labelReportID;	
	JLabel labelReportType;
	JLabel labelReportDetails;
	
	JTextField textFieldReportID;
	JTextField textFieldReportType;
	JTextField textFieldReportDetails;

	JButton button_ADD_ROW;
	JButton button_UPDATE_DATABASE;
	JButton button_DISCARD_CHANGES;
	JButton button_DELETE_CHANGES;
	ReportModel reportModel;

	public ReportFrame() throws SQLException {

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
		CachedRowSet myCachedRowSet = getContentsOfReportTable();
		reportModel = new ReportModel(myCachedRowSet);
		reportModel.addEventHandlersToRowSet(this);

		table = new JTable(); // Displays the table
		table.setModel(reportModel);
		
		labelReportID= new JLabel();
		labelReportType= new JLabel();
		labelReportDetails= new JLabel();
		

		textFieldReportID= new JTextField(10);
		textFieldReportType= new JTextField(10);
		textFieldReportDetails= new JTextField(10);

		button_ADD_ROW = new JButton();
		button_UPDATE_DATABASE = new JButton();
		button_DISCARD_CHANGES = new JButton();
		button_DELETE_CHANGES=new JButton();
		
		labelReportID.setText("Report ID:");		
		labelReportType.setText("Report Type : ");
		labelReportDetails.setText("Report Details : ");
		
		textFieldReportID.setText("Please enter the Report ID which needs to be updated");
		textFieldReportType.setText("Enter Report type");
		textFieldReportDetails.setText("Enter Report Details");

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
		contentPane.add(labelReportID, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		contentPane.add(textFieldReportID, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		contentPane.add(labelReportType, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		contentPane.add(textFieldReportType, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		contentPane.add(labelReportDetails, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		contentPane.add(textFieldReportDetails, c);

		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		contentPane.add(button_ADD_ROW, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		contentPane.add(button_UPDATE_DATABASE, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		contentPane.add(button_DELETE_CHANGES, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		contentPane.add(button_DISCARD_CHANGES, c);		

		button_ADD_ROW.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(ReportFrame.this,
						new String[] { "Adding the following row:",
								"Report ID: [" + textFieldReportID.getText() + "]",
								"Report Type: [" + textFieldReportType.getText() + "]",
								"Report Detail: [" + textFieldReportDetails.getText() + "]"
								});

				try {

					insertTable(
							textFieldReportType.getText().trim(),textFieldReportDetails.getText().trim());
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
					reportModel.reportRowSet.acceptChanges();
					
					
					updateTable(textFieldReportID.getText().trim(),
							textFieldReportType.getText().trim(),textFieldReportDetails.getText().trim());
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
					
					deleteTable(textFieldReportID.getText().trim());
					createNewTableModel();
					clearField();
				
				} catch (SQLException sqle) {
					displaySQLExceptionDialog(sqle);
				}
			}
		});

	}

	private void createNewTableModel() throws SQLException {
		reportModel = new ReportModel(getContentsOfReportTable());
		reportModel.addEventHandlersToRowSet(this);
		table.setModel(reportModel);
	}

	private void displaySQLExceptionDialog(SQLException e) {

		// Display the SQLException in a dialog box
		JOptionPane.showMessageDialog(ReportFrame.this,
				new String[] { e.getClass().getName() + ": ", e.getMessage() });
	}

	private CachedRowSet getContentsOfReportTable() {

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

			crs.setCommand("select reportid, reporttype, reservationdetail from reports");
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
	
	private void updateTable(String reportID,String reportType,String reportDetails) {

		String insertIntoCustomerReservationQuery = "UPDATE `reports` SET reporttype=?, reservationdetail=? "
				
				+ " WHERE `reportid`=? ";
		try {
			PreparedStatement pst = connection.prepareStatement(insertIntoCustomerReservationQuery);
			pst.setString(1, reportType);
			pst.setString(2, reportDetails);
			pst.setString(3, reportID);
			
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void deleteTable(String planid) {

		
		String insertIntoCustomerReservationQuery = "DELETE FROM reports "
				+ "WHERE `reportid`=? ";
		try {
			PreparedStatement pst = connection.prepareStatement(insertIntoCustomerReservationQuery);
			
			pst.setString(1, planid);
			
			
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void insertTable(String reportType,String reportDetails) {

		DateFormat dateFormat = new SimpleDateFormat("MMddyyyyHHmmss");
		Date date = new Date();
		String reportID= "RPT"+dateFormat.format(date);	
		
		String insertIntoCustomerReservationQuery = "INSERT INTO `reports` ( reportid,reporttype, reservationdetail)"
				+ "VALUES (?,?,?);";
		try {
			PreparedStatement pst = connection.prepareStatement(insertIntoCustomerReservationQuery);
			pst.setString(1, reportID);
			pst.setString(2, reportType);
			pst.setString(3, reportDetails);
			
			
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void clearField(){
		textFieldReportID.setText("Please enter the Report ID which needs to be updated");
		textFieldReportType.setText("Enter Report type");
		textFieldReportDetails.setText("Enter Report Details");
	}
	
	
	
	public static void main(String[] args) {
		try {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			
			ReportFrame customerFrame = new ReportFrame();
			customerFrame.setTitle("Report");
			customerFrame.setSize(screenSize.width, screenSize.height);
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

		CachedRowSet currentRowSet = this.reportModel.reportRowSet;

		try {
			currentRowSet.moveToCurrentRow();
			reportModel = new ReportModel(reportModel.getCoffeesRowSet());
			table.setModel(reportModel);

		} catch (SQLException ex) {

			printSQLException(ex);

			// Display the error in a dialog box.

			JOptionPane.showMessageDialog(ReportFrame.this, new String[] { // Display
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
