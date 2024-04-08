#include <string.h>

#include "bmp.h"
#include "bmpfile.h"
#include "image.h"
#include "transform.h"

int validate_degrees(char* arg3);


int main( int argc, char** argv ) {
    if(argc != 4){
        printf("The numbers of arguments must be 4!");
        return 0;
    }
    if(!validate_degrees(argv[3])){
        printf("The angle must take values strictly from the list: 0, 90, -90, 180, -180, 270, -270");
        return 0;
    }
    
    struct image img;
    switch (from_file(argv[1], &img))
    {
    case READ_OK:
        break;
    case READ_INVALID_FILE:
        printf("Cannot open the given file");
        free_image(img);
        return 0;
    case READ_INVALID_BITS:
        printf("The given BMP file contains invalid bits");
        free_image(img);
        return 0;  
    case READ_INVALID_HEADER:
        printf("The given BMP file contains an invalid header");
        free_image(img);
        return 0;
    case READ_INVALID_SIGNATURE:
        printf("The given BMP file contains an invalid signature");
        free_image(img);
        return 0;
    default:
        printf("An error occurred while reading the file");
        free_image(img);
        return 0;
    }

    struct image newImage;
    uint32_t angle = atoi(argv[3]) < 0 ? atoi(argv[3])+360 : atoi(argv[3]);
    switch (angle)
    {
    case 0:
        newImage = rotate_0(img);
        break;
    case 90:
        newImage = rotate(img);
        free_image(img);
        break;
    case 180:
        newImage = rotate_180(img);
        free_image(img);
        break;
    case 270:
        newImage = rotate_270(img);
        free_image(img);
        break;  
    }

    switch (to_file(argv[2], &newImage))
    {
    case WRITE_OK:
        break;
    case WRITE_ERROR:
        printf("An error occurred while writing to the file");
        break;
    }
    free_image(newImage);
    return 0;
}


int validate_degrees(char* arg3){
    if(strcmp("90", arg3)  ||
       strcmp("180", arg3) ||
       strcmp("270", arg3) ||
       strcmp("-90", arg3) ||
       strcmp("-180", arg3)||
       strcmp("-270", arg3)||
       strcmp("0", arg3)){
        return 1;
    }
    return 0;
}
