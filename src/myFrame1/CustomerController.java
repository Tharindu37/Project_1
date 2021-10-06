package myFrame1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerController {
    //public static boolean addCustomer(String id,String name,String address,double salary) throws ClassNotFoundException, SQLException{
    public static boolean addCustomer(Customer customer) throws ClassNotFoundException, SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("INSERT INTO Customer VALUES(?,?,?,?)");
        stm.setObject(1, customer.getId());
        stm.setObject(2, customer.getName());
        stm.setObject(3, customer.getAddress());
        stm.setObject(4, customer.getSalary());
        return stm.executeUpdate()>0;
    }

    public static Customer searchCustomer(String id) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        String SQL="SELECT * FROM Customer WHERE id='"+id+"'";
        ResultSet rst = stm.executeQuery(SQL);
        if(rst.next()){
            return new Customer(id,rst.getString("name"),rst.getString("address"),rst.getDouble("salary"));
        }
        return null;
    }
    
    public static boolean deleteCustomer(String id) throws ClassNotFoundException, SQLException{
       return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Customer WHERE id='"+id+"'")>0;   
    }
    
    public static boolean upadateCustomer(Customer customer) throws ClassNotFoundException, SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("UPDATE Customer SET name=?,address=?,salary=? WHERE id=?");
        stm.setObject(1, customer.getName());
        stm.setObject(2, customer.getAddress());
        stm.setObject(3, customer.getSalary());
        stm.setObject(4, customer.getId());
        return stm.executeUpdate()>0;
    }
    
    public static ArrayList<Customer> getAllCustomers() throws ClassNotFoundException, SQLException{
        ArrayList<Customer> cusomerList=new ArrayList();
        ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM Customer");
        while (rst.next()) {
            cusomerList.add(new Customer(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getDouble("salary"))); 
        }
        return cusomerList;
    }
}
