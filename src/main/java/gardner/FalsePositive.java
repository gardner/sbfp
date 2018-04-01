package gardner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import static org.apache.commons.dbutils.DbUtils.*;

public class FalsePositive {

    private Connection conn = null;
    private PreparedStatement stmt = null;

    public FalsePositive() throws SQLException {
        conn = DriverManager.getConnection("jdbc:derby:memory:myDB;create=true");
    }

    public void createTable() throws SQLException {
        Statement s = null;
        try {
            s = conn.createStatement();
            int rs = s.executeUpdate("CREATE TABLE atable (a INT, b LONG VARCHAR)");
            conn.commit();
        } finally {
            closeQuietly(s);
        }
    }

    public void thing1() {
        try {
            stmt = conn.prepareStatement("SELECT * FROM ? LIMIT 1");
            stmt.setString(1, "TABLE");
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        } finally {
            closeQuietly(stmt);
        }
    }

}
