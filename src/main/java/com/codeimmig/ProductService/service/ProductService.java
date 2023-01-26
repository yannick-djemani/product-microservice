package com.codeimmig.ProductService.service;

import com.codeimmig.ProductService.model.ProductRequest;
import com.codeimmig.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long productId);

    void reduceQuantity(long productId, long quantity);
}
