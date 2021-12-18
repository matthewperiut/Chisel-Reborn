import os
import math
import shutil
from PIL import Image
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
