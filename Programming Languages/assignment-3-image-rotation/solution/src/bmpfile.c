#include "bmpfile.h"


enum read_status from_file(char* file, struct image* img ){
    FILE *in = fopen(file, "rb");
    if (!in)
        return READ_INVALID_FILE;
    enum read_status result = from_bmp(in, img);
    fclose(in);
    return result;

}

enum write_status to_file(char* file, struct image const* img ){
    FILE *out = fopen(file, "wb");
    if(!out)
        return WRITE_ERROR;
    enum write_status result = to_bmp(out, img);
    fclose(out);
    return result;
}
