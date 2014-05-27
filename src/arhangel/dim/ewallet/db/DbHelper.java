package arhangel.dim.ewallet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 */
public class DbHelper {

    private Connection conn;


    public DbHelper() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");

            System.out.println("Opened database successfully");
            if (!isTablesExist()) {
                Statement stmt = conn.createStatement();
                String createSql = readResource(DbHelper.class, "create.sql");
                stmt.executeUpdate(createSql);

                String insertSql = readResource(DbHelper.class, "insert.sql");
                stmt.executeUpdate(insertSql);
                stmt.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public Connection getConn() {
        return conn;
    }

    boolean isTablesExist() throws Exception {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='USERS';");
        boolean result = true;
        int count = rs.getInt(1);
        if (count == 0) {
            result = false;
        }
        rs.close();
        stmt.close();
        return result;
    }

    String readResource(Class cpHolder, String path) throws Exception {
        java.net.URL url = cpHolder.getResource(path);
        java.nio.file.Path resPath = java.nio.file.Paths.get(url.toURI());
        return new String(java.nio.file.Files.readAllBytes(resPath), "UTF8");
    }

}