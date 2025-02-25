package org.ncu.ecommerce_app.controllers;

import java.util.List;

import org.ncu.ecommerce_app.entities.Product;
import org.ncu.ecommerce_app.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ecommerce-app")
public class ProductController {

	@Autowired
	private ProductService productService;

	// Get all products sorted by their IDs
	@GetMapping(value = "/products")
	public List<Product> fetchProducts() {
		return productService.getSoretedProducts();
	}

	// Get product by ID
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<?> getProductById(@PathVariable int id) {
		Product product = productService.getProductById(id);
		if (product == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(product);
	}

	// Save a new product
	@PostMapping(value = "/save")
	public String saveProduct(@RequestBody Product product) {
		productService.insertProduct(product);
		return "Product saved successfully";
	}

	// Update an existing product
	@PutMapping(value = "/update/{id}")
	public String updateProduct(@PathVariable int id, @RequestBody Product product) {
		productService.updateProductById(id, product);
		return "Product updated successfully";
	}

	// Delete a product by ID
	@DeleteMapping(value = "/delete/{id}")
	public String deleteProduct(@PathVariable int id) {
		productService.deleteProductByid(id);
		return "Product deleted successfully";
	}

	// Batch save products
	@PostMapping(value = "/save-products")
	public ResponseEntity<String> batchSaveProducts(@RequestBody List<Product> products){
		productService.batchInsert(products);
		return ResponseEntity.ok("Products added successfully");
	}

	// Batch update products
	@PutMapping(value = "/update-products")
	public ResponseEntity<String> batchUpdateProducts(@RequestBody List<Product> products){
		productService.batchUpdateById(products);
		return ResponseEntity.ok("Products updated successfully");
	}

	// Batch delete products
	@DeleteMapping(value = "/delete-products")
	public ResponseEntity<String> batchDeleteProducts(@RequestBody List<Integer> productIds){
		productService.batchDeleteById(productIds);
		return ResponseEntity.ok("Products deleted successfully");
	}
}