package com.isima.F2.Tp2_java_pro.productExceptions;

public class ProductBadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProductBadRequestException() {
        super("The id could not be null !");
    }
}