package com.RentalCar.model;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


public class RentalCarModel implements TableModel {

	public CachedRowSet rentalCarRowSet; // The ResultSet to interpret
	ResultSetMetaData metadata; // Additional information about the results
	int numcols, numrows; // How many rows and columns in the table

	public CachedRowSet getCoffeesRowSet() {
		return rentalCarRowSet;
	}

	public RentalCarModel(CachedRowSet myCachedRowSet) throws SQLException {
		this.rentalCarRowSet = myCachedRowSet;
		this.metadata = this.rentalCarRowSet.getMetaData();
		numcols = metadata.getColumnCount();

		// Retrieve the number of rows.
		this.rentalCarRowSet.beforeFirst();
		this.numrows = 0;
		while (this.rentalCarRowSet.next()) {
			this.numrows++;
		}
		this.rentalCarRowSet.beforeFirst();
	}

	public void addEventHandlersToRowSet(RowSetListener listener) {
		this.rentalCarRowSet.addRowSetListener(listener);
	}

	public void close() {
		try {
			rentalCarRowSet.getStatement().close();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	private void printSQLException(SQLException ex) {
		// TODO Auto-generated method stub
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				if (ignoreSQLException(((SQLException) e).getSQLState()) == false) {
					e.printStackTrace(System.err);
					System.err.println("SQLState: " + ((SQLException) e).getSQLState());
					System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
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

	private boolean ignoreSQLException(String sqlState) {
		// TODO Auto-generated method stub
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


	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return numrows;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return numcols;
	}

	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		try {
			return this.metadata.getColumnLabel(columnIndex + 1);
		} catch (SQLException e) {
			return e.toString();
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		try {
			this.rentalCarRowSet.absolute(rowIndex + 1);
			Object o = this.rentalCarRowSet.getObject(columnIndex + 1);
			if (o == null)
				return null;
			else
				return o.toString();
		} catch (SQLException e) {
			return e.toString();
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		System.out.println("Calling setValueAt row " + rowIndex + ", column " + columnIndex);

	}

	@Override
	public void addTableModelListener(TableModelListener l) {

	}

	@Override
	public void removeTableModelListener(TableModelListener l) {

	}

}
