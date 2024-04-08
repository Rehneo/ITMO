#ifndef IMAGE_H
#define IMAGE_H

#include <stdint.h>
#include <stdlib.h>

#pragma pack(push, 1)
struct pixel {
    int8_t b, g, r;
};
#pragma pack(pop)


struct image {
    uint64_t width, height;
    struct pixel *data;
};

struct image init_image(const uint64_t width, const uint64_t height);

void free_image(struct image img);

#endif
