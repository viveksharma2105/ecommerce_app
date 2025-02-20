package org.ncu.ecommerce_app.repositories;

import java.util.List;
import org.ncu.ecommerce_app.entities.Product;
import org.ncu.ecommerce_app.repositories.ProductRowMapper;
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
		return jdbcTemplate.query(queryString, new ProductRowMapper());
	}


	public void updateProduct(Product product) {
		String queryString = "UPDATE product SET product_name = ?, product_desc = ?, is_available = ?, product_price = ? WHERE product_id = ?";


		Object[] argsObject = {
				product.getProductName(),
				product.getProductDesc(),
				product.isAvailable() ? 1 : 0,
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




	public boolean deleteProduct(int id) {
		String queryString = "DELETE FROM product WHERE productID = ?";
		int rowsAffected = jdbcTemplate.update(queryString, id);
		return rowsAffected > 0;
	}
}