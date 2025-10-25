package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql="CREATE TABLE IF NOT EXISTS users ("
                + "id SERIAL PRIMARY KEY ,"
                + "username VARCHAR(255) NOT NULL,"
                + "lastname VARCHAR(255) NOT NULL,"+
        "age SMALLINT NOT NULL)";
        try(Connection conn = Util.connectDatabase();
            Statement stmt = conn.createStatement()){
            conn.setAutoCommit(true);
                stmt.execute(sql);
            System.out.println("✅ Таблица 'users' создана или уже существует!");
            Util.closeConnection(conn);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Connection connection=Util.connectDatabase();
        Statement stmt = connection.createStatement()){
            stmt.execute("DROP TABLE IF EXISTS users");
            Util.closeConnection(connection);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql="INSERT INTO users (username, lastname, age) VALUES ( ?, ?, ?)";
        try(Connection conn = Util.connectDatabase();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            conn.setAutoCommit(true);
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
           // stmt.execute(sql);
            System.out.println("user с именем "+name+" добавлен в таблицу");
            Util.closeConnection(conn);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Connection connection=Util.connectDatabase();
            Statement stmt = connection.createStatement()){
            stmt.execute("Delete from users where id = " + id);
            Util.closeConnection(connection);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> users=new ArrayList<User>();
        String sql="SELECT * FROM users";
        try(Connection connection=Util.connectDatabase();
            Statement stmt = connection.createStatement()){
            stmt.execute(sql);
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()){
                User user=new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("username"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Connection connection=Util.connectDatabase();
            Statement stmt = connection.createStatement()){
            stmt.execute("truncate users");
            System.out.println("Очистка произведена!");
            Util.closeConnection(connection);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
