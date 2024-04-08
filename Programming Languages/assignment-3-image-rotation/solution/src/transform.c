#include "transform.h"

struct image rotate(struct image const source ){
    uint64_t newHeight = source.width;
    uint64_t newWidth = source.height;
    struct image newImage = init_image(newWidth, newHeight);
    for(uint64_t i = 0; i < newHeight; i++){
        for(uint64_t j = 0; j < newWidth; j++){
            newImage.data[newWidth*i + j] = source.data[source.width * j + source.width - i - 1];
        }
    }
    return newImage;
}

struct image rotate_180(struct image const source){
    struct image newImage = rotate(source);
    struct image result = rotate(newImage);
    free_image(newImage);
    return result;
}

struct image rotate_270(struct image const source){
    struct image newImage = rotate_180(source);
    struct image result = rotate(newImage);
    free_image(newImage);
    return result;
}

struct image rotate_0(struct image const source){
    struct image newImage = init_image(source.width, source.height);
    for(uint64_t i = 0; i < source.height; i++){
        for(uint64_t j = 0; j < source.width; j++){
            newImage.data[source.width*i + j] = source.data[source.width*i + j];
        }
    }
    return newImage;
}