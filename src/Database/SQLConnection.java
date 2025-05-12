package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLConnection {

    protected static Connection koneksi;
    private static Statement stmt;
    private static ResultSet result;
    private static final Config c = new Config();

    public SQLConnection() {

    }

    public static Connection getConnection() throws SQLException {
        try {
            // Periksa apakah koneksi sudah ada dan masih valid
            if (koneksi != null && !koneksi.isClosed()) {
                return koneksi; // Gunakan koneksi yang sudah ada
            }

            // Memuat driver MySQL
            Class.forName(Config.DB_DRIVER_LIB);

            // Membuat koneksi baru
            koneksi = DriverManager.getConnection(
                    "jdbc:" + Config.DB_DRIVER_NAME + "://" + Config.DB_HOST + ":" + Config.DB_PORT + "/" + Config.DB_NAME,
                    Config.DB_USER,
                    Config.DB_PASS
            );

            System.out.println("Koneksi berhasil!");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver tidak ditemukan: " + e.getMessage());
            throw new SQLException("Driver tidak ditemukan", e);
        } catch (SQLException e) {
            System.err.println("Koneksi gagal: " + e.getMessage());
            throw e; // Lempar ulang exception agar bisa ditangani di tempat lain
        }
        return koneksi;
    }

    public static ResultSet doQuery(String query, Object... parameters) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);

            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }

            return pstmt.executeQuery(); // Jangan tutup koneksi di sini
        } catch (SQLException e) {
            throw new SQLException("Error during query execution: " + e.getMessage(), e);
        }
    }

    public static ResultSet doPreparedQuery(String query, Object... parameters) throws SQLException {
        // Validasi input
        if (query == null || query.trim().isEmpty()) {
            throw new SQLException("Query tidak boleh null atau kosong.");
        }

        Connection connection = getConnection(); // Mendapatkan koneksi ke database
        PreparedStatement pstmt = connection.prepareStatement(query);

        // Atur parameter query
        for (int i = 0; i < parameters.length; i++) {
            pstmt.setObject(i + 1, parameters[i]);
        }

        // Eksekusi query dan kembalikan ResultSet
        return pstmt.executeQuery();
    }

    public static boolean doPreparedUpdate(String query, Object... parameters) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement pstmt = null;

            pstmt = connection.prepareStatement(query);
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public static void closeConnection() {
        try {
            if (koneksi != null && !koneksi.isClosed()) {
                koneksi.close();
                koneksi = null; // Set ke null setelah ditutup
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeResources() {
        try {
            if (result != null && !result.isClosed()) {
                result.close();
            }
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
            if (koneksi != null && !koneksi.isClosed()) {
                koneksi.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
