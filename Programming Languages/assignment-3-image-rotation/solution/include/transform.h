#ifndef TRANSFORM_H
#define TRANSFORM_H

#include "image.h"

struct image rotate( struct image const source );

struct image rotate_180( struct image const source );

struct image rotate_270( struct image const source );

struct image rotate_0(struct image const source);

#endif