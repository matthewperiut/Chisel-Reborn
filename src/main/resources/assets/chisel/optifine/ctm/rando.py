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

cut_size = int(input("Number in ?x?: "))

settings = get_settings()
for i in range(1, len(settings)):
    file_path = settings[0] + '/' + settings[i]
    cutter(file_path, settings[0], cut_size)
    make_random_properties(file_path, settings[0], settings[i], cut_size)