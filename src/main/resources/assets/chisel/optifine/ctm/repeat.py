from compact import image_manipulation
from compact import open_file
from compact import get_settings

def make_repeat_properties(file_path, block_type, block):
    file_name = block_type + "_" + block
    file_path = file_path + "/" + file_name + ".properties"

    f = open_file(file_path)

    f.write("matchBlocks=chisel:" + block_type + "/" + block + "\n")
    f.write("method=repeat\n")
    f.write("width=2\n")
    f.write("height=2\n")
    f.write("tiles=1-4\n")
    f.write("symmetry=opposite\n")

    f.close()

settings = get_settings()
for i in range(1, len(settings)):
    file_path = settings[0] + '/' + settings[i]
    image_manipulation(file_path, settings[0])
    make_repeat_properties(file_path, settings[0], settings[i])