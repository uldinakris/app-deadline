package ru.netology.data;

import lombok.Value;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getWrongAuthInfo() {
        return new AuthInfo("vasya", "wrongpass");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode(AuthInfo authInfo) {
        var userSQL = "SELECT id FROM users WHERE login = ? LIMIT 1;";
        var codeSQL = "SELECT code FROM auth_codes WHERE user_id = ? ORDER BY created DESC LIMIT 1;";
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                var userStmt = conn.prepareStatement(userSQL);
                var codeStmt = conn.prepareStatement(codeSQL);
        ) {
            userStmt.setString(1, "vasya");
            try (var rs = userStmt.executeQuery()) {
                if (rs.next()) {
                    var userId = rs.getString(1);
                    codeStmt.setString(1, userId);
                    try (var rs2 = codeStmt.executeQuery()) {
                        if (rs2.next()) {
                            var code = rs2.getString(1);
                            return new VerificationCode(code);
                        }
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new Error("cant get code");
    }
}
