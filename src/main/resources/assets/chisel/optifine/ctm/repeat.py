from helpful import get_settings
from helpful import open_file
from helpful import cutter

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

def repeat(first, second, num):
    file_path = first + '/' + second
    cutter(file_path, first, num)
    make_repeat_properties(file_path, first, second)