package myFrame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemController {
    
    public static boolean addItem(Item item) throws ClassNotFoundException, SQLException{
        PreparedStatement stm = DBConnection.getInstance().getConnection()
                .prepareStatement("INSERT INTO item VALUES(?,?,?,?)");
        stm.setObject(1, item.getCode());
        stm.setObject(2, item.getDescription());
        stm.setObject(3, item.getUnitPrice());
        stm.setObject(4, item.getQtyOnHand());
        return stm.executeUpdate()>0;
    }
    
    public static Item searchItem(String code) throws ClassNotFoundException, SQLException{
        ResultSet rst = DBConnection.getInstance().getConnection()
                .createStatement().executeQuery("SELECT * FROM item WHERE code='"+code+"'");
        while(rst.next()){
            return new Item(code,rst.getString("description"),rst.getDouble("unitPrice"),rst.getInt("qtyOnHand"));
        }
        return null;
    }
    
    public static boolean deleteItem(String code) throws ClassNotFoundException, SQLException{
        return DBConnection.getInstance().getConnection().createStatement()
                .executeUpdate("DELETE FROM item WHERE code='"+code+"'")>0;
    }
    
    public static boolean updateItem(Item item) throws SQLException, ClassNotFoundException{
        PreparedStatement stm = DBConnection.getInstance().getConnection()
                .prepareStatement("UPDATE item SET description=?,unitPrice=?,qtyOnHand=? WHERE code=?");
        stm.setObject(1, item.getDescription());
        stm.setObject(2, item.getUnitPrice());
        stm.setObject(3, item.getQtyOnHand());
        stm.setObject(4, item.getCode());
        return stm.executeUpdate()>0;
    }
    
    public static ArrayList<Item> getAllItem() throws ClassNotFoundException, SQLException{
        ResultSet rst = DBConnection.getInstance().getConnection()
                .createStatement().executeQuery("SELECT * FROM item");
        ArrayList<Item> itemList=new ArrayList<>();
        while(rst.next()){
           itemList.add(new Item(rst.getString("code"),rst.getString("description"),rst.getDouble("unitPrice"),rst.getInt("qtyOnHand")));
        }
        return itemList;
    }
}
