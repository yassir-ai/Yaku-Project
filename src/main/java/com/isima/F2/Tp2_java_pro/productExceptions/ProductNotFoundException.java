package com.isima.F2.Tp2_java_pro.productExceptions;

public class ProductNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(String id) {
        super("The product with " + id + " cannot be found !");
    }
}
