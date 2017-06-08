package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaDeConexoes {
	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "marcos", "oracle");
			if (connection.isValid(0)) {
				System.out.printf("\n%s\n", "Conexão inicializada com sucesso.");
			}
		} catch (SQLException | RuntimeException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;

	}
}
