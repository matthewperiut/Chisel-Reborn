import os
import math
import shutil
import errno

from PIL import Image
from shutil import copyfile
from pathlib import Path

def get_settings():
    path = Path(os.getcwd()).parent.parent.parent.parent
    new_path = str(path) + "/settings.txt"

    settings_f = open(new_path)
    settings = settings_f.readlines()
    for i in range(len(settings)):
        settings[i] = settings[i].replace("\n","")
    return settings

def open_file(file_name):
    if not os.path.exists(os.path.dirname(file_name)):
        try:
            os.makedirs(os.path.dirname(file_name))
        except OSError as exc: # Guard against race condition
            if exc.errno != errno.EEXIST:
                raise

    f = open(file_name, "w")
    return f

def cutter(path, modifier, size, start_ct=0):
    override = False
    if (size == 0):
            override = True

    file_path = path + '/' + modifier + '-'
    if(override == False):
        file_path += str(size) + 'x' + str(size) + '.png'
    else:
        file_path += "ctm.png"
        size = 2

    file_path.replace("cut","cuts",1)
    file_path.replace("large_tile","tiles_large",1)
    file_path.replace("slant","slanted",1)

    sheet = Image.open(file_path)

    count = start_ct

    for y in range(size):
        for x in range(size):
            a = (x + 1) * 16
            b = (y + 1) * 16
            icon = sheet.crop((a - 16, b - 16, a, b))
            icon.save(path + "/{}.png".format(count))
            count += 1

    if ("zag" in file_path):
        os.remove(path + "/2.png")
        os.remove(path + "/3.png")
        shutil.copyfile(path + "/0.png", path + "/3.png")
        shutil.copyfile(path + "/1.png", path + "/2.png")

def get_zero(file_path):
    path = Path(os.getcwd()).parent.parent.parent
    new_path = str(path) + "/chisel/textures/block/" + file_path + ".png"
    copyfile(new_path, file_path+"/0.png")

def make_compact_properties(file_path, block_type, block):
    file_name = block_type + "_" + block
    file_path = file_path + "/" + file_name + ".properties"

    f = open_file(file_path)
    f.write("matchBlocks=chisel:" + block_type + "/" + block + "\n")
    f.write("method=ctm_compact\n")
    f.write("innerSeams=true\n")
    f.write("tiles=0-4")
    f.close()

def make_random_properties(file_path, block_type, block, size):
    file_name = block_type + "_" + block
    file_path = file_path + "/" + file_name + ".properties"

    f = open_file(file_path)

    f.write("matchBlocks=chisel:" + block_type + "/" + block + "\n")
    f.write("method=random\n")
    f.write("tiles=0-" + str(int(math.pow(size,2)-1)))

    f.close()

def make_repeat_properties(file_path, block_type, block):
    file_name = block_type + "_" + block
    file_path = file_path + "/" + file_name + ".properties"

    f = open_file(file_path)

    f.write("matchBlocks=chisel:" + block_type + "/" + block + "\n")
    f.write("method=repeat\n")
    f.write("width=2\n")
    f.write("height=2\n")
    f.write("tiles=0-3\n")
    f.write("symmetry=none\n")

    f.close()

def compact(first, second, num):
    file_path = first + '/' + second
    cutter(file_path, first, num, 1)
    make_compact_properties(file_path, first, second)
    get_zero(file_path)

def rando(first, second, num):
    file_path = first + '/' + second
    cutter(file_path, first, num)
    make_random_properties(file_path, first, second, num)

def repeat(first, second, num):
    file_path = first + '/' + second
    cutter(file_path, first, num)
    make_repeat_properties(file_path, first, second)

def auto(names_list):
    for name in names_list:
        temp = name.split('/')
        first = temp[0]
        second = temp[1]

    files = os.scandir(first + "/" + second)
    good = False
    for i in files:
        file_name = str(i)
        if "ctm" in file_name:
            if "zag" in first:
                repeat(first, second, 0)
            else:
                compact(first, second, 0)
            good = True
            continue
        if "2x2" in file_name:
            repeat(first, second, 2)
            good = True
            continue
        if "3x3" in file_name:
            rando(first, second, 3)
            good = True
            continue
        if "4x4" in file_name:
            rando(first, second, 4)
            good = True
            continue

    if not good:
        print(first + '/' + second + ' has no ctm file')

def get_settings_names():
    settings = get_settings()
    names_list = []
    for i in range(1, len(settings)):
        first = settings[0]
        second = settings[i]

        if first == "":
            first = settings[i]
            second = settings[1]
            if i == 1:
                continue

        names_list.append(first + '/' + last)

    return names_list