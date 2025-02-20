package org.ncu.ecommerce_app.repositories;


import org.ncu.ecommerce_app.entities.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setProductID(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setProductDesc(rs.getString("product_desc"));
        product.setAvailable(rs.getBoolean("is_available"));
        product.setProductPrice(rs.getDouble("product_price"));
        return product;

    }
}
