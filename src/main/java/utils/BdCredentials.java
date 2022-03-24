package utils;

public class BdCredentials {
    private static final String bdLogin = "postgres";
    private static final String bdPassword = "admin";
    private static final String bdName = "atm_service";
    private static final String bdHost = "localhost";

    public static String getBdLogin() {
        return bdLogin;
    }

    public static String getBdPassword() {
        return bdPassword;
    }

    public static String getBdName() {
        return bdName;
    }

    public static String getBdHost() {
        return bdHost;
    }
}
