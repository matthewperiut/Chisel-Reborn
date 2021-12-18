import math
from helpful import get_settings
from helpful import open_file
from helpful import cutter

def make_random_properties(file_path, block_type, block, size):
    file_name = block_type + "_" + block
    file_path = file_path + "/" + file_name + ".properties"

    f = open_file(file_path)

    f.write("matchBlocks=chisel:" + block_type + "/" + block + "\n")
    f.write("method=random\n")
    f.write("tiles=0-" + str(int(math.pow(size,2)-1)))

    f.close()

def rando(first, second, num):
    file_path = first + '/' + second
    cutter(file_path, first, num)
    make_random_properties(file_path, first, second, num)