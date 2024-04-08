#include "image.h"



struct image init_image(const uint64_t width, const uint64_t height){
    struct image img = (struct image) {
            .width = width,
            .height = height,
            .data = malloc(sizeof(struct pixel) *width * height)};
    return img;        
}


void free_image(struct image img){
    if (img.data == NULL){
        return;
    }
    free(img.data);
    img.data = NULL;    
}