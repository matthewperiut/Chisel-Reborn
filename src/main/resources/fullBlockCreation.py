from assets.chisel.optifine.ctm.auto import auto
from writeCode import write_generated_register

from addBlocks import add_blocks_from_list
from pathlib import Path
import shutil
import os

def check_img(path_str):
    if ".png" in str(path_str):
        return True
    print("This is not an image.")
    return False

def move_png(p1,p2):
    try:
        shutil.copy(str(p1), str(p2))
        return True
    except Exception as err:
        print(str(err))
        return False

def create_regular_texture(full_name):
    if "pillar" in full_name or "twist" in full_name:
        if not "top" in full_name:
            print("For the top, ",end="")
            if not create_regular_texture(full_name + "_top"):
                print("Your top texture is incorrect, cannot continue\nquitting...")
                exit()
            print("For the side, ",end="")

    regular_texture_output_dir = Path("assets/chisel/textures/block/" + full_name + ".png")
    if(regular_texture_output_dir.is_file()):
        return False

    regular_texture_path = Path(input("Input regular texture path: \n").rstrip().lstrip())
    if not check_img(regular_texture_path):
        return False

    return move_png(regular_texture_path, regular_texture_output_dir)

def write_french_properties(full_name, count):
    f = open("assets/chisel/optifine/ctm/" + full_name + "/" + full_name.replace('/','_') + ".properties", "w")
    f.write('matchBlocks=chisel:' + full_name + '\n')
    f.write('method=random\n')
    f.write('tiles=0-' + str(count-1))
    f.close()

def create_ctm_texture(full_name, french_override=False):
    must_include = ['ctm','2x2','3x3','4x4']
    semi = full_name.split('/')

    if "french" in full_name or french_override:
        french = []
        while True:
            print(str(len(french)) + " img path:")
            temp = input()
            if (temp == ""):
                break
            french.append(temp.rstrip().lstrip())

        for i in range(len(french)):
            if not move_png(french[i], "assets/chisel/optifine/ctm/" + full_name + "/" + str(i) + ".png"):
                print("Failure to copy file " + str(i))
                return False

        write_french_properties(full_name, len(french))
        return True

    for include in must_include:
        potential_path = Path("assets/chisel/optifine/ctm/" + full_name + "/" + semi[0] + "-" + include + ".png")
        if potential_path.is_file():
            return False

    ctm_path = Path(input("Input ctm path: \n"))
    if ctm_path == "":
        return False
    elif "multi" in str(ctm_path).lower():
        return create_ctm_texture(full_name, True)
    ctm_path = str(ctm_path).rstrip().lstrip()

    if not check_img(ctm_path):
        return False

    my_id = ''
    for include in must_include:
        if include in str(ctm_path):
            my_id = include

    if my_id == '':
        print("This is an invalid CTM texture")
        return False

    ctm_path_output = Path("assets/chisel/optifine/ctm/" + full_name + "/" + semi[0] + "-" + my_id + ".png")
    if(ctm_path_output.is_file()):
        return False

    return move_png(ctm_path, ctm_path_output)

def convert(name):
    name = name.replace("tiles_large", "large_tile")
    name = name.replace("layers", "layer")
    name = name.replace("cuts", "cut")
    name = name.replace("slanted", "slant")
    name = name.replace("twisted", "twist")
    return name

block_list = [input("Full Block Name\n e.g.: pillar/stone\nyours: ")]
block_list[0] = convert(block_list[0])
add_blocks_from_list(block_list)
create_regular_texture(block_list[0])
if create_ctm_texture(block_list[0]):
    os.chdir("assets/chisel/optifine/ctm")
    auto(block_list)
    os.chdir("../../../../..")
else:
    os.chdir("..")
print(os.getcwd())
register_dir = "java/com/matthewperiut/chisel/block/GeneratedRegister"
write_generated_register(register_dir)