package org.ncu.ecommerce_app.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.ncu.ecommerce_app.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// Add a new product to the database
	public void addProduct(Product product) {
		String queryString = "INSERT INTO product (product_id, product_name, product_desc, is_available, product_price) VALUES (?, ?, ?, ?, ?)";
		Object[] argsObject = {
				product.getProductID(),
				product.getProductName(),
				product.getProductDesc(),
				product.isAvailable(),
				product.getProductPrice()
		};
		jdbcTemplate.update(queryString, argsObject);
	}

	// Get all products from the database
	public List<Product> getAllProducts() {
		String queryString = "SELECT * FROM product";
		return jdbcTemplate.query(queryString, new BeanPropertyRowMapper<Product>(Product.class));
	}

	// Get product by ID
	public Product getProductById(int id) {
		String queryString = "SELECT * FROM product WHERE product_id = ?";
		try {
			return jdbcTemplate.queryForObject(queryString,
					new BeanPropertyRowMapper<Product>(Product.class), id);
		} catch (DataAccessException e) {
			return null; // Return null if product not found
		}
	}

	// Update a product
	public void updateProduct(Product product) {
		String queryString = "UPDATE product SET product_name = ?, product_desc = ?, is_available = ?, product_price = ? WHERE product_id = ?";

		Object[] argsObject = {
				product.getProductName(),
				product.getProductDesc(),
				product.isAvailable(),
				product.getProductPrice(),
				product.getProductID()
		};

		try {
			jdbcTemplate.update(queryString, argsObject);
		} catch (DataAccessException e) {
			// Log the error for debugging
			System.out.println("Error executing update: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Delete a product
	public boolean deleteProduct(int id) {
		String queryString = "DELETE FROM product WHERE product_id = ?";
		int rowsAffected = jdbcTemplate.update(queryString, id);
		return rowsAffected > 0;
	}

	// Batch insert products
	public void batchInsertProducts(List<Product> products) {
		String queryString = "INSERT INTO product (product_id, product_name, product_desc, is_available, product_price) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.batchUpdate(queryString, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Product product = products.get(i);
				ps.setInt(1, product.getProductID());
				ps.setString(2, product.getProductName());
				ps.setString(3, product.getProductDesc());
				ps.setBoolean(4, product.isAvailable());
				ps.setDouble(5, product.getProductPrice());
			}

			@Override
			public int getBatchSize() {
				return products.size();
			}
		});
	}

	// Batch update products
	public void batchUpdateProducts(List<Product> products) {
		String queryString = "UPDATE product SET product_name = ?, product_desc = ?, is_available = ?, product_price = ? WHERE product_id = ?";
		jdbcTemplate.batchUpdate(queryString, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Product product = products.get(i);
				ps.setString(1, product.getProductName());
				ps.setString(2, product.getProductDesc());
				ps.setBoolean(3, product.isAvailable());
				ps.setDouble(4, product.getProductPrice());
				ps.setInt(5, product.getProductID());
			}

			@Override
			public int getBatchSize() {
				return products.size();
			}
		});
	}

	// Batch delete products
	public void batchDeleteProducts(List<Integer> productIds) {
		String queryString = "DELETE FROM product WHERE product_id = ?";
		jdbcTemplate.batchUpdate(queryString, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, productIds.get(i));
			}

			@Override
			public int getBatchSize() {
				return productIds.size();
			}
		});
	}
}
