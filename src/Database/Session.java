package Database;

import java.sql.ResultSet;

/**
 *
 * @author a
 */
public class Session {
    private static String userId;
    private static String role;
    private static boolean isValid = false;
    private static ResultSet appdata;
    
    private Session() {}
    
    public static String getUserId() {
        return userId;
    }
    
    public static void setUserId(String userId) {
        Session.userId = userId;
    }
    
    /**
     * @return the role
     */
    public static String getRole() {
        return role;
    }

    /**
     * @param aRole the role to set
     */
    public static void setRole(String aRole) {
        role = aRole;
    }
    
    public static boolean getValidStatus() {
        return isValid;
    }
    
    public static void setValidStatus(boolean status) {
        Session.isValid = status;
    }
    
    public static ResultSet getAppdata() {
        return appdata;
    }
    
    public static void setAppdata(ResultSet data) {
        Session.appdata = data;
    }
    
}