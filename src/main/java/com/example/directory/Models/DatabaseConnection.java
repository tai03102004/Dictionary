package com.example.directory.Models;

import java.sql.*;

public class DatabaseConnection {
    public static Connection conn = null;

    // Tạo kết nối
    public static Connection getConnection() throws SQLException {

        if (conn == null || conn.isClosed()) {
            String connectionString = "jdbc:mysql://localhost:3306/Dictionary";
            conn = DriverManager.getConnection(connectionString,"root","tai03102004");
        }

        return  conn;

    }

    // Đóng kết nối
    public static void closeConnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Thực hiện các truy vấn
    public static ResultSet execQuerry(String query, Object... parameters) throws SQLException {
        ResultSet rs = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Thiết lập các tham số cho truy vấn Prepared Statement (nếu có)
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }

            // Thực hiện truy vấn
            rs = preparedStatement.executeQuery();
        }
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

    public static boolean registerUser(String fullName, String userName, String email, String phone, String password, String question, String answer, Timestamp date) {
        try (Connection connectionDB = getConnection()) {
            if (connectionDB != null) {
                String checkQuery = "SELECT * FROM Dictionary.Clients WHERE UserName = ? OR Email = ? OR phone = ?";
                try (PreparedStatement checkStatement = connectionDB.prepareStatement(checkQuery)) {
                    checkStatement.setString(1, userName);
                    checkStatement.setString(2, email);
                    checkStatement.setString(3, phone);
                    try (ResultSet resultSet = checkStatement.executeQuery()) {
                        if (resultSet.next()) {
                            // User with the same username, email, or phone already exists.
                            return false;
                        } else {
                            String insertQuery = "INSERT INTO Dictionary.Clients (FullName, UserName, Email, Phone, Password, Question, Answer, Date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement insertStatement = connectionDB.prepareStatement(insertQuery)) {
                                insertStatement.setString(1, fullName);
                                insertStatement.setString(2, userName);
                                insertStatement.setString(3, email);
                                insertStatement.setString(4, phone);
                                insertStatement.setString(5, password);
                                insertStatement.setString(6, question);
                                insertStatement.setString(7, answer);


                                insertStatement.setTimestamp(8, date);


                                int rowsAffected = insertStatement.executeUpdate();
                                return rowsAffected > 0;
                            }
                        }
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateClient(String userName, String fullName, String email, String phone) {
        try {
            PreparedStatement statement = this.conn.prepareStatement(
                    "UPDATE Dictionary.Clients SET FullName = ?, Email = ?, Phone = ? WHERE UserName = ?"
            );
            statement.setString(1, fullName);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, userName);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword (String userName,String password) {
        try {
            PreparedStatement statement = this.conn.prepareStatement(
                    "UPDATE Dictionary.Clients SET Password = ? WHERE UserName = ?"
            );
            statement.setString(1, password);
            statement.setString(2, userName);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean reportClient(Double start, String title, String comment, String userName) {
        try {
            PreparedStatement statement = this.conn.prepareStatement(
                    "INSERT INTO Dictionary.Report (Start, Title, Comment, userName) VALUES (?, ?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE Start = VALUES(Start), Title = VALUES(Title), Comment = VALUES(Comment)"
            );

            statement.setDouble(1, start);
            statement.setString(2, title);
            statement.setString(3, comment);
            statement.setString(4, userName);

            int cnt = statement.executeUpdate();

            // Kiểm tra xem có bản ghi nào được trả về hay không
            if (cnt > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Reviewable getLastReview() {
        Reviewable lastReview = null;

        try {
            PreparedStatement statement = this.conn.prepareStatement(
                    "SELECT * FROM Dictionary.Report LIMIT 1"
            );

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                double start = resultSet.getDouble("Start");
                String title = resultSet.getString("Title");
                String comment = resultSet.getString("Comment");

                lastReview = new Review(start, title, comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastReview;
    }


    public boolean forgotPassword(String userName, String question, String answer) {
        try {
            PreparedStatement statement = this.conn.prepareStatement(
                    "SELECT UserName, Question, Answer FROM Dictionary.Clients WHERE UserName = ? AND Question = ? AND Answer = ?"
            );

            statement.setString(1, userName);
            statement.setString(2, question);
            statement.setString(3, answer);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changePassword(String userName,String password,Timestamp update_date) {
        try {
            PreparedStatement statement = this.conn.prepareStatement(
                    "UPDATE Dictionary.Clients SET Password = ?, updateTime = ? "
                            + "WHERE UserName = '" + userName + "'"
            );

            statement.setString(1, password);
            statement.setString(2, String.valueOf(update_date));


            int rowsAffected = statement.executeUpdate();


            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public ResultSet infoClient(String userName) {
        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Dictionary.Clients WHERE UserName = '"+userName+"';");
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

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Xử lý ngoại lệ, ví dụ: ghi log
                e.printStackTrace();
            }
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // Xử lý ngoại lệ
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                // Xử lý ngoại lệ
                e.printStackTrace();
            }
        }
    }
}
