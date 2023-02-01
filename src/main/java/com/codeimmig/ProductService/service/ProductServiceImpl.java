package com.codeimmig.ProductService.service;

import com.codeimmig.ProductService.entity.Product;
import com.codeimmig.ProductService.exception.ProductServiceCustomException;
import com.codeimmig.ProductService.model.ProductRequest;
import com.codeimmig.ProductService.model.ProductResponse;
import com.codeimmig.ProductService.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl  implements  ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding product ......");
        Product product=Product
                .builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product created ");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("Get the Product for productId: {}",productId);
        Product product=productRepository.findById(productId).orElseThrow(()->new ProductServiceCustomException("Product with given id not found","NOT FOUND"));
        ProductResponse productResponse=new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce quantity for id : {}", quantity, productId);
        Product product=productRepository.findById(productId).orElseThrow(()->new ProductServiceCustomException("Product with given id not found ", "PRODUCT NOT FOUND"));
        if (product.getQuantity()<quantity){
            throw  new ProductServiceCustomException("Product doesn't have sufficient Quantity", "INSUFFICIANT_QUANTITY");

        }
        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
        log.info("Product Quantity update Successfully");

    }
}
