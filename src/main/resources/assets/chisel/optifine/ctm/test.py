import os
import errno
from shutil import copyfile
from pathlib import Path
import cv2

def open_file(file_name):
    if not os.path.exists(os.path.dirname(file_name)):
        try:
            os.makedirs(os.path.dirname(file_name))
        except OSError as exc: # Guard against race condition
            if exc.errno != errno.EEXIST:
                raise

    f = open(file_name, "w")
    return f

def image_manipulation(path, modifier):
    # load image
    img = cv2.imread(path + '/' + modifier + '-ctm.png')
    ##########################################
    # At first vertical devide image         #
    ##########################################
    # start vertical devide image
    height = img.shape[0]
    width = img.shape[1]
    # Cut the image in half
    width_cutoff = width // 2
    left1 = img[:, :width_cutoff]
    right1 = img[:, width_cutoff:]
    # finish vertical devide image
    ##########################################
    # At first Horizontal devide left1 image #
    ##########################################
    #rotate image LEFT1 to 90 CLOCKWISE
    img = cv2.rotate(left1, cv2.ROTATE_90_CLOCKWISE)
    # start vertical devide image
    height = img.shape[0]
    width = img.shape[1]
    # Cut the image in half
    width_cutoff = width // 2
    l1 = img[:, :width_cutoff]
    l2 = img[:, width_cutoff:]
    # finish vertical devide image
    #rotate image to 90 COUNTERCLOCKWISE
    l1 = cv2.rotate(l1, cv2.ROTATE_90_COUNTERCLOCKWISE)
    #save
    cv2.imwrite(path + "/3.png", l1)
    #rotate image to 90 COUNTERCLOCKWISE
    l2 = cv2.rotate(l2, cv2.ROTATE_90_COUNTERCLOCKWISE)
    #save
    cv2.imwrite(path + "/1.png", l2)
    ##########################################
    # At first Horizontal devide right1 image#
    ##########################################
    #rotate image RIGHT1 to 90 CLOCKWISE
    img = cv2.rotate(right1, cv2.ROTATE_90_CLOCKWISE)
    # start vertical devide image
    height = img.shape[0]
    width = img.shape[1]
    # Cut the image in half
    width_cutoff = width // 2
    r1 = img[:, :width_cutoff]
    r2 = img[:, width_cutoff:]
    # finish vertical devide image
    #rotate image to 90 COUNTERCLOCKWISE
    r1 = cv2.rotate(r1, cv2.ROTATE_90_COUNTERCLOCKWISE)
    #save
    cv2.imwrite(path + "/4.png", r1)
    #rotate image to 90 COUNTERCLOCKWISE
    r2 = cv2.rotate(r2, cv2.ROTATE_90_COUNTERCLOCKWISE)
    #save
    cv2.imwrite(path + "/2.png", r2)

def make_compact_properties(file_path, file_name):
    file_path = file_path + "/" + file_name + ".properties"

    print(file_path)

    f = open_file(file_path)

    f.write("matchBlocks=chisel:" + file_name.replace('_','/') + "\n")
    f.write("method=ctm_compact\n")
    f.write("innerSeams=true\n")
    f.write("tiles=0-4")

    f.close()

def get_zero(file_path):
    path = Path(os.getcwd()).parent.parent.parent
    print(path)
    new_path = str(path) + "/chisel/textures/block/" + file_path + ".png"
    copyfile(new_path, file_path+"/0.png")


block_type = input("block type: ")
block = input("block: ")

file_path = block_type + "/" + block
image_manipulation(file_path, block_type)
make_compact_properties(file_path, block_type + "_" + block)
get_zero(file_path)