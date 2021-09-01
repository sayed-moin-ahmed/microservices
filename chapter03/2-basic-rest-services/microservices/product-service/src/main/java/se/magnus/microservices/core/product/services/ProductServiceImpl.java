package se.magnus.microservices.core.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import se.magnus.api.core.product.Product;
import se.magnus.api.core.product.ProductService;
import se.magnus.util.http.ServiceUtil;

public class ProductServiceImpl implements ProductService {
    @Override
    public Product getProduct(int productId) {
        return new Product(123,"computer",10,"Home");
    }

    private final ServiceUtil serviceUtil;
    @Autowired
    public ProductServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }
}
