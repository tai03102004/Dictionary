package com.example.directory.Models;

import java.sql.*;

public class DatabaseConnection {
    public static Connection conn = null;

    // Tạo kết nối
    public static Connection getConnection() throws SQLException {
        if (conn == null) {
            String connectionString = "jdbc:mysql://localhost:3306/Dictionary";
            conn = DriverManager.getConnection(connectionString,"root","tai03102004");
        }

        return  conn;

    }

    // Đóng kết nối
    public static void closeConnect() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    // Thực hiện các truy vấn
    public static ResultSet execQuerry(String querry) throws SQLException {

        ResultSet rs = null;

        PreparedStatement connPreparedStatement = conn.prepareStatement(querry);
        rs = connPreparedStatement.executeQuery();

        return rs;

    }

    /**
     * CLient Section
     */

    public ResultSet getClientData (String UserName,String password) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Dictionary.Clients WHERE UserName = '"+UserName+"' " +
                    " AND Password = '"+password+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    /**
     * Admin Section
     */

    public ResultSet getAdminData(String UserName, String password) {
        Statement statement;
        ResultSet resultSet = null;
        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Dictionary.Admins WHERE UserName = '"+UserName+"' " +
                    " AND Password = '"+password+"';");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static boolean registerUser(String fullName, String userName, String password, String email, String phone) {
        try (Connection connectionDB = getConnection()) {
            if (connectionDB != null) { // Kiểm tra xem kết nối đã mở thành công hay chưa
                String checkQuery = "SELECT * FROM Dictionary.new_table WHERE UserName = ? OR Email = ? OR phone = ?";
                try (PreparedStatement checkStatement = connectionDB.prepareStatement(checkQuery)) {
                    checkStatement.setString(1, userName);
                    checkStatement.setString(2, email);
                    checkStatement.setString(3, phone);
                    try (ResultSet resultSet = checkStatement.executeQuery()) {
                        if (resultSet.next()) {
                            // User with the same username, email, or phone already exists.
                            return false;
                        } else {
                            String insertQuery = "INSERT INTO Dictionary.new_table (Name, UserName, Password, Email, phone) VALUES (?, ?, ?, ?, ?)";
                            try (PreparedStatement insertStatement = connectionDB.prepareStatement(insertQuery)) {
                                insertStatement.setString(1, fullName);
                                insertStatement.setString(2, userName);
                                insertStatement.setString(3, password);
                                insertStatement.setString(4, email);
                                insertStatement.setString(5, phone);
                                int rowsAffected = insertStatement.executeUpdate();
                                return rowsAffected > 0;
                            }
                        }
                    }
                }
            }
            return false; // Trả về false nếu kết nối không mở thành công
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra màn hình để kiểm tra lỗi
            return false;
        }
    }

    public void updateClient(String fullName,String userName,String email,String phone,String sex) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("UPDATE Dictionary.Clients SET FullName = '" + fullName + "', UserName = '" + userName + "', Email = '" + email + "', Phone = '" + phone + "', Sex = '" + sex + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet infoClient(String userName) {
        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Dictionary.Admins WHERE UserName = '"+userName+"' " +";");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getAllClientsData () {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement =this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients;");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Utility Methods .
     */

    public int getLastClientsId() {
        Statement statement;
        ResultSet resultSet;
        int id = 0;

        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM sqlite_sequence WHERE name = 'Clients';");
            id = resultSet.getInt("seq");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

}
