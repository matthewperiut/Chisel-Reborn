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

cut_size = int(input("Number in ?x?: "))
override = False

if (cut_size == 0):
    override = True

settings = get_settings()
for i in range(1, len(settings)):
    file_path = settings[0] + '/' + settings[i]
    cutter(file_path, settings[0], cut_size, override)
    make_repeat_properties(file_path, settings[0], settings[i])