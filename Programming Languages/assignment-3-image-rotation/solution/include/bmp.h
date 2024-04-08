#ifndef BMP_H
#define BMP_H
#include "image.h"
#include <stdio.h>

#define TYPE 19778
#define RESERVED 0
#define OFF_BITS 54
#define DIB_HEADER_SIZE 40
#define PLANES 1
#define BYTES_COUNT 3
#define COMPRESSION 0
#define X_PELS_PER_METER 0
#define Y_PELS_PER_METER 0
#define CLR_USED 0
#define CLR_IMPORTANT 0

#if defined _MSC_VER
#define __attribute__()
#endif

#pragma pack(push, 1)
struct __attribute__((packed)) bmp_header 
{
        uint16_t bfType;
        uint32_t bfileSize;
        uint32_t bfReserved;
        uint32_t bOffBits;
        uint32_t biSize;
        uint32_t biWidth;
        uint32_t biHeight;
        uint16_t biPlanes;
        uint16_t biBitCount;
        uint32_t biCompression;
        uint32_t biSizeImage;
        uint32_t biXPelsPerMeter;
        uint32_t biYPelsPerMeter;
        uint32_t biClrUsed;
        uint32_t biClrImportant;
};
#pragma pack(pop)


/*  deserializer   */
enum read_status  {
  READ_OK = 0,
  READ_INVALID_SIGNATURE,
  READ_INVALID_BITS,
  READ_INVALID_HEADER,
  READ_INVALID_FILE
  /* коды других ошибок  */
  };

/*  serializer   */
enum write_status  {
  WRITE_OK = 0,
  WRITE_ERROR = 7
  /* коды других ошибок  */
};


enum write_status to_bmp(FILE* out, struct image const* img );

enum read_status from_bmp(FILE* in, struct image* img );

struct image create_image_from_bmp_bytes(const uint64_t width, const uint64_t height, char* bytes);

char* write_image_to_bmp_bytes(struct image const* img, char* bytes );

size_t row_padding(uint64_t width);

#endif
