#ifndef BMPFILE_H
#define BMPFILE_H

#include "bmp.h"

enum read_status from_file(char* file, struct image* img );
enum write_status to_file(char* file, struct image const* img );

#endif
