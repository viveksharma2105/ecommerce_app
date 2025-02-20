package org.ncu.ecommerce_app.repositories;

import org.ncu.ecommerce_app.entities.Product;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductResultSetExtractor implements ResultSetExtractor<List<Product>> {
    @Override
    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Product> products = new ArrayList<>();
        while (rs.next()){
            Product tempProduct = new Product();
            tempProduct.setProductID(rs.getInt("product_id"));
            tempProduct.setProductName(rs.getString("product_name"));
            tempProduct.setProductDesc(rs.getString("product_desc"));
            tempProduct.setAvailable(rs.getBoolean("is_available"));
            tempProduct.setProductPrice(rs.getDouble("product_price"));
            products.add(tempProduct);
        }
        return products;
    }
}
