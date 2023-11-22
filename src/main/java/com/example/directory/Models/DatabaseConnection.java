package com.example.directory.Models;

import com.example.directory.Controllers.client.WordItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DatabaseConnection {
    public static Connection conn = null;

    // Tạo kết nối : connect database
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

            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }

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
     * Words
     */

    // Report Admin
    public Boolean getReportAdmin (String text,String userName) {
        try (PreparedStatement insertStatement = this.conn.prepareStatement(
                "INSERT INTO Dictionary.ReportAdmin (Text, UserNameClient) VALUES (?, ?)"
        )) {
            insertStatement.setString(1, text);
            insertStatement.setString(2, userName);

            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                Model.getInstance().notifyDatabaseChange();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ResultSet getAllClientsDataReport () {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement =this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Dictionary.ReportAdmin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public SearchResult getWordClient(String word) {
        try {
            String sql = "SELECT id, definition FROM Dictionary.Words WHERE target = ? AND deleted = false";
            try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
                preparedStatement.setString(1,   word );
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String definition = resultSet.getString("definition");
                        return new SearchResult(true, definition);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new SearchResult(false, null);
    }

    public List<String> getSuggestedWords(String partialKeyword) {
        List<String> suggestedWords = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT target FROM Dictionary.Words WHERE target LIKE ? AND deleted = false LIMIT 8";
            try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
                preparedStatement.setString(1,  partialKeyword + "%");
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String suggestedWord = resultSet.getString("target");
                        suggestedWords.add(suggestedWord);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return suggestedWords;
    }

    public List<WordsDataBase> getTargetAndDefinition() {
        List<WordsDataBase> targetAndDefinitions = new ArrayList<>();
        try {
            String sql = "SELECT target, definition FROM Dictionary.Words where deleted = false";
            try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String target = resultSet.getString("target");
                        String definition = resultSet.getString("definition");
                        WordsDataBase wordData = new WordsDataBase(target, definition);
                        targetAndDefinitions.add(wordData);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return targetAndDefinitions;
    }


    // End words


    //  Insert Admin
    public boolean insertWord(String Target, String Definition,boolean Deleted) {
        try {

            PreparedStatement insertStatement = this.conn.prepareStatement(
                    "INSERT INTO Dictionary.Words (target, definition,deleted) VALUES (?, ?,?)"
            );
            insertStatement.setString(1, Target);
            insertStatement.setString(2, Definition);
            insertStatement.setBoolean(3,Deleted);

            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                Model.getInstance().notifyDatabaseChange();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Select word (Update Admin)
    public String getDefinitionForTarget(String target) {
        String definition = null;
        try {
            String sql = "SELECT definition FROM Dictionary.Words WHERE target = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, target);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        definition = resultSet.getString("definition");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return definition;
    }

    // update Admin
    public boolean updateWord(String target, String definition) {
        // Câu lệnh SQL UPDATE
        String sql = "UPDATE Dictionary.Words SET Definition = ? WHERE Target = ?";

        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            // Thiết lập các tham số trong câu lệnh SQL
            preparedStatement.setString(1, definition);
            preparedStatement.setString(2, target);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean checkIfTargetExists(String target) {
        String sql = "SELECT COUNT(*) FROM Dictionary.Words WHERE target = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, target);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public void updateDeletedStatus(String target, boolean deletedStatus) {
        String sql = "UPDATE Dictionary.Words SET deleted = ? WHERE target = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, deletedStatus);
            preparedStatement.setString(2, target);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean selectStatusDeleted(String target) {
        String sql = "SELECT deleted FROM Dictionary.Words WHERE target = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, target);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("deleted");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    // deleted Admin (deleted => true)
    public boolean markAsDeleted(String target) {
        try {
            String sql = "UPDATE Dictionary.Words SET deleted = true WHERE target = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, target);
                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // History Word
    public boolean saveWordToHistory(String Target, String Definition, String userName) {
        try (PreparedStatement insertStatement = this.conn.prepareStatement(
                "INSERT IGNORE Dictionary.HistoryWord (target, definition, UserName) VALUES (?, ?, ?)"
        )) {
            insertStatement.setString(1, Target);
            insertStatement.setString(2, Definition);
            insertStatement.setString(3, userName);

            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Thông báo sự thay đổi trong cơ sở dữ liệu
                Model.getInstance().notifyDatabaseChange();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isWordInHistory(String word, String userName) {
        try (PreparedStatement statement = this.conn.prepareStatement(
                "SELECT COUNT(*) FROM Dictionary.HistoryWord WHERE target = ? AND UserName = ?"
        )) {
            statement.setString(1, word);
            statement.setString(2, userName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // End History Word

    // Save words (Client)
    public boolean saveWord(String Target, String Definition,String userName) {
        try {

            PreparedStatement insertStatement = this.conn.prepareStatement(
                    "INSERT IGNORE INTO Dictionary.SaveWord (target, definition,UserName) VALUES (?, ?,?)"
            );
            insertStatement.setString(1, Target);
            insertStatement.setString(2, Definition);
            insertStatement.setString(3, userName);

            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                Model.getInstance().notifyDatabaseChange();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<WordItem> accountSaveWordList(String userName) {
        List<WordItem> wordItemList = new ArrayList<>();

        try {
            PreparedStatement selectStatement = this.conn.prepareStatement(
                    "SELECT target, definition FROM Dictionary.SaveWord WHERE UserName = ?"
            );
            selectStatement.setString(1, userName);

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    String target = resultSet.getString("target");
                    String definition = resultSet.getString("definition");
                    wordHtml.add(new WordItem(target, definition));
                    // Chuẩn hóa nội dung HTML bằng JSoup
                    definition = normalizeHTML(definition);
                    wordItemList.add(new WordItem(target, definition));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wordItemList;
    }
    public boolean deleteWordItem(String Target) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(
                "DELETE FROM Dictionary.SaveWord WHERE target = ?"
        )) {
            preparedStatement.setString(1, Target);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isWordSaved(String searchTerm) {
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(
                "SELECT COUNT(*) FROM Dictionary.SaveWord WHERE target = ?"
        )) {
            preparedStatement.setString(1, searchTerm);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<WordItem> getWordItemsFromDatabase(String userName) {
        List<WordItem> wordItems = new ArrayList<>();

        try {
            PreparedStatement selectStatement = this.conn.prepareStatement(
                    "SELECT target, definition FROM Dictionary.SaveWord where UserName = ?"
            );
            selectStatement.setString(1, userName);
            ResultSet selectResultSet = selectStatement.executeQuery();

            while (selectResultSet.next()) {
                String retrievedTarget = selectResultSet.getString("target");
                String retrievedDefinition = selectResultSet.getString("definition");

                // Chuẩn hóa nội dung HTML bằng JSoup
                retrievedDefinition = normalizeHTML(retrievedDefinition);

                // Thêm từ đã chuẩn hóa vào danh sách
                wordItems.add(new WordItem(retrievedTarget, retrievedDefinition));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wordItems;

    }

    public static List<WordItem> getWordHtml() {
        return wordHtml;
    }

    // Hàm chuẩn hóa nội dung HTML sử dụng JSoup
    private String normalizeHTML(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        String textContent = doc.text();
        return textContent;
    }

    // End save word

    // delete : word

    // History Word
    public boolean deleteWordItemHistory(String Target) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(
                "DELETE FROM Dictionary.HistoryWord WHERE target = ?"
        )) {
            preparedStatement.setString(1, Target);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<WordItem> accountHistoryWordList(String userName) {
        List<WordItem> wordItemList = new ArrayList<>();

        try {
            PreparedStatement selectStatement = this.conn.prepareStatement(
                    "SELECT target, definition FROM Dictionary.HistoryWord WHERE UserName = ?"
            );
            selectStatement.setString(1, userName);

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    String target = resultSet.getString("target");
                    String definition = resultSet.getString("definition");
                    wordHtml.add(new WordItem(target, definition));
                    // Chuẩn hóa nội dung HTML bằng JSoup
                    definition = normalizeHTML(definition);
                    wordItemList.add(new WordItem(target, definition));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wordItemList;
    }
    public List<WordItem> getWordItemsHistoryFromDatabase(String userName) {
        List<WordItem> wordItems = new ArrayList<>();

        try {
            PreparedStatement selectStatement = this.conn.prepareStatement(
                    "SELECT target, definition FROM Dictionary.HistoryWord where UserName = ?"
            );
            selectStatement.setString(1, userName);
            ResultSet selectResultSet = selectStatement.executeQuery();

            while (selectResultSet.next()) {
                String retrievedTarget = selectResultSet.getString("target");
                String retrievedDefinition = selectResultSet.getString("definition");
                wordHtml.add(new WordItem(retrievedTarget, retrievedDefinition));
                // Chuẩn hóa nội dung HTML bằng JSoup
                retrievedDefinition = normalizeHTML(retrievedDefinition);
                wordItems.add(new WordItem(retrievedTarget, retrievedDefinition));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wordItems;
    }

    // End History Word
    public static List<WordItem> wordHtml = new ArrayList<>();
    public static class SearchResult {
        private final boolean found;
        private final String definition;

        public SearchResult(boolean found, String definition) {
            this.found = found;
            this.definition = definition;
        }

        public boolean isFound() {
            return found;
        }

        public String getDefinition() {
            return definition;
        }
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

    // Thêm các đánh giá khác nhau trong CLient
    public boolean reportClient(Double start, String title, String comment, String userName) {
        try {
            PreparedStatement selectStatement = this.conn.prepareStatement(
                    "SELECT * FROM Dictionary.Report WHERE userName = ?"
            );
            selectStatement.setString(1, userName);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Người dùng đã có đánh giá, cập nhật nó
                PreparedStatement updateStatement = this.conn.prepareStatement(
                        "UPDATE Dictionary.Report SET Start = ?, Title = ?, Comment = ? WHERE userName = ?"
                );
                updateStatement.setDouble(1, start);
                updateStatement.setString(2, title);
                updateStatement.setString(3, comment);
                updateStatement.setString(4, userName);
                int updateCount = updateStatement.executeUpdate();

                return updateCount > 0;
            } else {
                // Người dùng chưa có đánh giá, thêm mới
                PreparedStatement insertStatement = this.conn.prepareStatement(
                        "INSERT INTO Dictionary.Report (Start, Title, Comment, userName) VALUES (?, ?, ?, ?)"
                );
                insertStatement.setDouble(1, start);
                insertStatement.setString(2, title);
                insertStatement.setString(3, comment);
                insertStatement.setString(4, userName);

                int insertCount = insertStatement.executeUpdate();
                return insertCount > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean hasExistingReview(String userName) {
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(
                "SELECT COUNT(*) FROM Dictionary.Report WHERE userName = ?"
        )) {
            preparedStatement.setString(1, userName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<String> userNameReport(String userName) {
        List<String> userNames = new ArrayList<>();

        try (PreparedStatement preparedStatement = this.conn.prepareStatement(
                "SELECT userName FROM Dictionary.Report WHERE userName = ?"
        )) {
            preparedStatement.setString(1, userName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String foundUserName = resultSet.getString("userName");
                    userNames.add(foundUserName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userNames;
    }


    public boolean updateReview(double start, String title, String comment, String userName) {
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(
                "UPDATE Dictionary.Report SET Start = ?, Title = ?, Comment = ? WHERE userName = ?"
        )) {
            preparedStatement.setDouble(1, start);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, comment);
            preparedStatement.setString(4, userName);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // In hoặc ghi log lỗi để theo dõi vấn đề
        }
        return false;
    }

    // Game

    public void saveScore(String username, int score) {
        try {
            String sql = "INSERT INTO Dictionary.RankUser (UserName, Point) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setInt(2, score);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ScoreEntry> getScoreboard() {
        List<ScoreEntry> scoreboard = new ArrayList<>();

        try {
            String sql = "SELECT UserName, Point FROM Dictionary.RankUser ORDER BY Point DESC limit 7";
            try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String username = resultSet.getString("UserName");
                        int score = resultSet.getInt("Point");

                        ScoreEntry entry = new ScoreEntry(username, score);
                        scoreboard.add(entry);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scoreboard;
    }

    // End game

    // review Client
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

    // Hết đánh giá trong Client

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
            resultSet = statement.executeQuery("SELECT * FROM Dictionary.Clients");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // close
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
