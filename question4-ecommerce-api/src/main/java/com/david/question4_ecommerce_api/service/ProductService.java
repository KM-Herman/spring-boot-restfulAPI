package com.david.question4_ecommerce_api.service;

import com.david.question4_ecommerce_api.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private List<Product> products = new ArrayList<>();
    private long currentId = 1;

    public List<Product> getAllProducts(Integer page, Integer limit) {
        if (page != null && limit != null && page > 0 && limit > 0) {
            int skip = (page - 1) * limit;
            return products.stream().skip(skip).limit(limit).collect(Collectors.toList());
        }
        return new ArrayList<>(products);
    }

    public Optional<Product> getProductById(Long id) {
        return products.stream().filter(p -> p.getProductId().equals(id)).findFirst();
    }

    public List<Product> getProductsByCategory(String category) {
        return products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByBrand(String brand) {
        return products.stream()
                .filter(p -> p.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

    public List<Product> searchProducts(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerKeyword) ||
                        p.getDescription().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByPriceRange(Double min, Double max) {
        return products.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }

    public List<Product> getProductsInStock() {
        return products.stream()
                .filter(p -> p.getStockQuantity() > 0)
                .collect(Collectors.toList());
    }

    public Product addProduct(Product product) {
        product.setProductId(currentId++);
        products.add(product);
        return product;
    }

    public Optional<Product> updateProduct(Long id, Product productDetails) {
        return getProductById(id).map(existingProduct -> {
            existingProduct.setName(productDetails.getName());
            existingProduct.setDescription(productDetails.getDescription());
            existingProduct.setPrice(productDetails.getPrice());
            existingProduct.setCategory(productDetails.getCategory());
            existingProduct.setStockQuantity(productDetails.getStockQuantity());
            existingProduct.setBrand(productDetails.getBrand());
            return existingProduct;
        });
    }

    public Optional<Product> updateStock(Long id, int quantity) {
        return getProductById(id).map(existingProduct -> {
            existingProduct.setStockQuantity(quantity);
            return existingProduct;
        });
    }

    public boolean deleteProduct(Long id) {
        return products.removeIf(p -> p.getProductId().equals(id));
    }
}
