package org.ncu.ecommerce_app.repositories;

import java.util.ArrayList;
import java.util.List;
import org.ncu.ecommerce_app.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
	private static List<Product> products = new ArrayList<Product>();

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// Add a new product to the database
	public void addProduct(Product product) {
		String queryString = "insert into product values (?,?,?,?,?)";
		Object[] argsObject = {
				product.getProductID(),
				product.getProductName(),
				product.getProductDesc(),
				product.isAvailable(),
				product.getProductPrice()
		};
		jdbcTemplate.update(queryString, argsObject);
	}

	// Get all products (example placeholder - typically this should fetch from DB)
	public List<Product> getAllProducts() {
		return products;
	}

	// Update an existing product in the database
	public void updateProduct(Product product) {
		String queryString = "UPDATE product SET productName = ?, productDesc = ?, available = ?, productPrice = ? WHERE productID = ?";
		Object[] argsObject = {
				product.getProductName(),
				product.getProductDesc(),
				product.isAvailable(),
				product.getProductPrice(),
				product.getProductID()
		};
		jdbcTemplate.update(queryString, argsObject);
	}

	// Delete an existing product by its ID
	public boolean deleteProduct(int id) {
		boolean isDeleted = false;
		for (Product product : products) {
			if (product.getProductID() == id) {
				isDeleted = products.remove(product);
				break;
			}
		}
		return isDeleted;
	}
}
