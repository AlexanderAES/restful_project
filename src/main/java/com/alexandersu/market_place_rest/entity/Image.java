package com.alexandersu.market_place_rest.entity;

public class Image {

    private Long id;
    private String name;
    private String originalFileName;
    private Long size;
    private String contentType;
    private boolean isPreviewImage;
    private byte[] bytes;
    private Product product;

}
