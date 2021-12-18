from assets.chisel.optifine.ctm.auto import auto
from writeCode import write_generated_register

from addBlocks import add_blocks_from_list
from pathlib import Path
import shutil
import os

def create_regular_texture(full_name):
    regular_texture_output_dir = Path("assets/chisel/textures/block/" + full_name + ".png")
    if(regular_texture_output_dir.is_file()):
        return

    regular_texture_path = Path(input("Input regular texture path: \n").rstrip().lstrip())
    if not ".png" in str(regular_texture_path):
        print("This is not an image.")
        create_regular_texture()
        return

    try:
        shutil.move(str(regular_texture_path), str(regular_texture_output_dir))
    except Exception as err:
        print(str(err))

def create_ctm_texture(full_name):
    semi = full_name.split('/')
    must_include = ['ctm','2x2','3x3','4x4']

    for include in must_include:
        potential_path = Path("assets/chisel/optifine/ctm/" + full_name + "/" + semi[0] + "-" + include + ".png")
        if potential_path.is_file():
            return

    ctm_path = Path(input("Input ctm path: \n").rstrip().lstrip())
    if ctm_path == "":
        return

    if not ".png" in str(ctm_path):
            print("This is not an image.")
            create_ctm_texture()
            return

    my_id = ''
    for include in must_include:
        if include in str(ctm_path):
            my_id = include

    if my_id == '':
        print("This is an invalid CTM texture")
        create_ctm_texture()
        return

    ctm_path_output = Path("assets/chisel/optifine/ctm/" + full_name + "/" + semi[0] + "-" + my_id + ".png")
    if(ctm_path_output.is_file()):
        return

    try:
        shutil.move(str(ctm_path), str(ctm_path_output))
    except Exception as err:
        print(str(err))


block_list = [input("Full Block Name\n e.g.: pillar/stone\nyours: ")]
add_blocks_from_list(block_list)
create_regular_texture(block_list[0])
create_ctm_texture(block_list[0])
os.chdir("assets/chisel/optifine/ctm")
auto(block_list)
os.chdir("../../../../..")
print(os.getcwd())
register_dir = "java/com/matthewperiut/chisel/block/GeneratedRegister"
write_generated_register(register_dir)