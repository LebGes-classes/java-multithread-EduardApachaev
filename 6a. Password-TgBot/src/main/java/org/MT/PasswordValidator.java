package org.MT;

public class PasswordValidator {
    public static boolean isPasswordStrong(String password, boolean requireSpecialChar) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasDigit = false;
        boolean hasUpper = false;
        boolean hasSpecial = !requireSpecialChar; // Если спецсимволы не требуются, считаем условие выполненным

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if ("!@#$%^&*()_+-=[]{}|;:,.<>?".indexOf(c) >= 0) {
                hasSpecial = true;
            }
        }

        return hasDigit && hasUpper && hasSpecial;
    }
}
