# -*- coding: utf-8 -*-
from PIL import Image

#竟然图片过大，666666666
Image.MAX_IMAGE_PIXELS = None
import sys
import os

#直接转int可能会越界，所以使用取整，舍去右下部分边缘
import math


my_size=256

def cut_image(image):
    width, height = image.size
    item_width =math.floor(my_size)
    item_height=math.floor(my_size)
    the_shorter=min(width,height)
    print(the_shorter)

    for i in range(0,int(the_shorter/my_size)):
        for j in range(0,int(the_shorter/my_size)):
            box = (j*item_width,i* item_height,(j+1)*item_width,(i+1)*item_height)
            temp=image.crop(box)
            temp.save(pic_name+"/"+str(i) + "x"+str(j)+'.png', 'PNG')




if __name__ == '__main__':




    pic_name="PT001_50_10"
    file_path = pic_name+".png"  #图片保存的地址

    if os.path.exists(pic_name):
        shutil.rmtree(pic_name)  # delete output folder
    os.makedirs(pic_name)  # make new output folder

    image = Image.open(file_path)
    image_new=image
    image_list = cut_image(image_new)