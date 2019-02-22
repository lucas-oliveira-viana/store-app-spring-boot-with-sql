package com.lucas.lojasql.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.lucas.lojasql.exception.db.DBException;

public class DB {

	private static final String DBURL_ARQUIVOPROPERTIES = "dburl";

	private static Connection conn = null;

	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = lerArquivoProperties();
				String url = props.getProperty(DBURL_ARQUIVOPROPERTIES);
				conn = DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
		return conn;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}

	private static Properties lerArquivoProperties() {
		try (FileInputStream is = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(is);
			return props;
		} catch (IOException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	
	public static void fecharConexoes(PreparedStatement ps, ResultSet rs) {
		DB.closeStatement(ps);
		DB.closeResultSet(rs);
	}
}
