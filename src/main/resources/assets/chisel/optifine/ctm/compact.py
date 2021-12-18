import os
import errno
from shutil import copyfile
from pathlib import Path
import cv2

from helpful import open_file
from helpful import cutter
from helpful import get_settings

def make_compact_properties(file_path, block_type, block):
    file_name = block_type + "_" + block
    file_path = file_path + "/" + file_name + ".properties"

    f = open_file(file_path)

    f.write("matchBlocks=chisel:" + block_type + "/" + block + "\n")
    f.write("method=ctm_compact\n")
    f.write("innerSeams=true\n")
    f.write("tiles=0-4")

    f.close()

def get_zero(file_path):
    path = Path(os.getcwd()).parent.parent.parent
    new_path = str(path) + "/chisel/textures/block/" + file_path + ".png"
    copyfile(new_path, file_path+"/0.png")

def compact(first, second, num):
    file_path = first + '/' + second
    cutter(file_path, first, num, 1)
    make_compact_properties(file_path, first, second)
    get_zero(file_path)