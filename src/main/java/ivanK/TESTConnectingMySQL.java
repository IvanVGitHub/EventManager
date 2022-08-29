package ivanK;

public class TESTConnectingMySQL {
    public static void main(String[] args) {
//TEST connecting to MySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            System.out.println(":)\nConnection succesfull!");
        } catch (Exception ex) {
            System.out.println(":(\nConnection failed...");

            System.out.println(ex);
        }

        //
        //
        //
    }
}