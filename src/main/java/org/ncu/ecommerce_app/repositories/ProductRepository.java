package org.ncu.ecommerce_app.repositories;

import java.util.List;
import org.ncu.ecommerce_app.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// Add a new product to the database
	public void addProduct(Product product) {
		String queryString = "INSERT INTO product (productID, productName, productDesc, available, productPrice) VALUES (?, ?, ?, ?, ?)";
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
		return jdbcTemplate.query(queryString, new BeanPropertyRowMapper<>(Product.class));
	}

	// Update an existing product in the database
	public void updateProduct(Product product) {
		String queryString = "UPDATE product SET productName = ?, productDesc = ?, available = ?, productPrice = ? WHERE productID = ?";

		// Assuming the productID is an Integer and others are String, Boolean, and Double respectively
		Object[] argsObject = {
				product.getProductName(),
				product.getProductDesc(),
				product.isAvailable(),
				product.getProductPrice(),
				product.getProductID()
		};

		// Ensure the query is valid and argsObject matches the expected parameter types
		try {
			jdbcTemplate.update(queryString, argsObject);
		} catch (DataAccessException e) {
			// Handle the exception if an error occurs
			System.out.println("Error executing update: " + e.getMessage());
		}
	}


	// Delete an existing product by its ID
	public boolean deleteProduct(int id) {
		String queryString = "DELETE FROM product WHERE productID = ?";
		int rowsAffected = jdbcTemplate.update(queryString, id);
		return rowsAffected > 0;
	}
}