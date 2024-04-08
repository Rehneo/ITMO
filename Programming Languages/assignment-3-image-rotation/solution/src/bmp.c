#include "bmp.h"


enum read_status from_bmp(FILE* in, struct image* img ){
    struct bmp_header header;
    if (fread(&header, sizeof(struct bmp_header), 1, in) < 1) {
        return READ_INVALID_HEADER;
    }

    if(
        header.bfType != TYPE ||
        header.bfReserved != RESERVED ||
        header.bOffBits != OFF_BITS ||
        header.biPlanes != PLANES ||
        header.biSize != DIB_HEADER_SIZE ||
        header.biWidth < 1 ||
        header.biHeight < 1 ||
        header.biBitCount != BYTES_COUNT*8 ||
        header.biCompression != COMPRESSION
    ){
        return READ_INVALID_BITS;
    }

    char* bytes = malloc(header.biSizeImage);
    if (fread(bytes, header.biSizeImage, 1, in) < 1) {
        free(bytes);
        return READ_INVALID_SIGNATURE;
    }

    *img = create_image_from_bmp_bytes(header.biWidth, header.biHeight, bytes);
    return READ_OK;
}


struct image create_image_from_bmp_bytes(const uint64_t width, const uint64_t height, char* bytes){
    struct image img = init_image(width, height);
    size_t padding = row_padding(width);
    size_t bytesPerRow = width * BYTES_COUNT + padding;
    for(uint64_t i = 0; i < height; i++){
        for(uint64_t j = 0; j < width; j++){
            img.data[i * width + j] = (struct pixel){
                bytes[bytesPerRow * i + j * BYTES_COUNT],
                bytes[bytesPerRow * i + j * BYTES_COUNT + 1],
                bytes[bytesPerRow * i + j * BYTES_COUNT + 2]
            };
        }
    }
    free(bytes);
    return img;
}

size_t row_padding(uint64_t width){
    return (4 - ( width*BYTES_COUNT ) % 4) % 4;
}

struct bmp_header createOutHeader(uint32_t width, uint32_t height){
    uint32_t biSizeImage = ( (uint32_t) (width*sizeof(struct pixel)+row_padding(width)) )*height;
    return (const struct bmp_header){
    .bfType = TYPE,
    .bfReserved = RESERVED,
    .bfileSize = biSizeImage + OFF_BITS,
    .bOffBits = OFF_BITS,
    .biSize = DIB_HEADER_SIZE,
    .biWidth = width,
    .biHeight = height,
    .biPlanes = PLANES,
    .biBitCount = BYTES_COUNT*8,
    .biCompression = COMPRESSION,
    .biSizeImage = biSizeImage,
    .biXPelsPerMeter = X_PELS_PER_METER,
    .biYPelsPerMeter = Y_PELS_PER_METER,
    .biClrUsed = CLR_USED,
    .biClrImportant = CLR_IMPORTANT
    };
}

enum write_status to_bmp(FILE* out, struct image const* img ){
    struct bmp_header header = createOutHeader((uint32_t)img->width,(uint32_t)img->height);
    if (fwrite(&header, sizeof(header), 1, out) < 1) {
        return WRITE_ERROR;
    }
    char *bytes = malloc(header.biSizeImage);
    bytes = write_image_to_bmp_bytes(img, bytes);
    if (fwrite(bytes, 1, header.biSizeImage, out) < header.biSizeImage) {
        free(bytes);
        return WRITE_ERROR;
    }
    free(bytes);
    return WRITE_OK;
}

char* write_image_to_bmp_bytes(struct image const* img, char* bytes ){
    uint64_t width = img->width;
    uint64_t height = img->height;
    size_t padding = row_padding(width);
    size_t bytesPerRow = width* BYTES_COUNT + padding;
    for(uint64_t i = 0; i < height; i++){
        for(uint64_t j = 0; j < width; j++){
            bytes[bytesPerRow * i + j * BYTES_COUNT] = img->data[width*i+j].b;
            bytes[bytesPerRow * i + j * BYTES_COUNT + 1] = img->data[width*i+j].g;
            bytes[bytesPerRow * i + j * BYTES_COUNT + 2] = img->data[width*i+j].r;
        }
        for(uint64_t j = 0; j < padding;j++){ //add additional zeroes;
            bytes[bytesPerRow * i + width * BYTES_COUNT + j] = 0;
        }
    }
    return bytes;
}

