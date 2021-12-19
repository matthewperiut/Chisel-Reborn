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

def create_regular_texture(full_name, input_dir="", top="", bottom=""):
    semi = full_name.split('/')
    if "pillar" in full_name or "twist" in full_name:
        if input_dir == "":
            if not "top" in full_name:
                print("For the top, ",end="")
                if not create_regular_texture(full_name + "_top"):
                    print("Your top texture is incorrect, cannot continue\nquitting...")
                    exit()
                print("For the side, ",end="")
    elif not top == "":
        if not check_img(input_dir) or not Path(input_dir).is_file():
            print(full_name + " has improper auto -side texture or missing file")
            print(input_dir)
            return False
        if not check_img(top) or not Path(top).is_file():
            print(full_name + " has improper auto -top texture or missing file")
            print(top)
            return False

        if not bottom == "":
            if not check_img(bottom) or not Path(bottom).is_file():
                print(full_name + " has improper auto -bottom texture or missing file")
                print(bottom)
                return False

        if move_png(input_dir, "assets/chisel/textures/block/" + full_name + ".png") and move_png(top, "assets/chisel/textures/block/" + full_name + "_top.png"):
            if not bottom == "":
                if move_png(bottom, "assets/chisel/textures/block/" + full_name + "_bottom.png"):
                    return True
                else:
                    return False
            return True
        else:
            return False

    regular_texture_output_dir = Path("assets/chisel/textures/block/" + full_name + ".png")
    if(regular_texture_output_dir.is_file()):
        return False

    if input_dir == "":
        input_dir = input("Input regular texture path: \n")

    regular_texture_path = Path(input_dir.rstrip().lstrip())
    if not check_img(regular_texture_path):
        return False

    return move_png(regular_texture_path, regular_texture_output_dir)

def write_french_properties(full_name, count):
    f = open("assets/chisel/optifine/ctm/" + full_name + "/" + full_name.replace('/','_') + ".properties", "w")
    f.write('matchBlocks=chisel:' + full_name + '\n')
    f.write('method=random\n')
    f.write('tiles=0-' + str(count-1))
    f.close()

def create_ctm_texture(full_name, french_override=False, input_dir=""):
    must_include = ['ctm','2x2','3x3','4x4']
    semi = full_name.split('/')

    if "french" in full_name or french_override:
        french = []
        if input_dir == "":
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
        else:
            input_dirs = input_dir.split(',')
            count = 0
            for d in input_dirs:
                move_png(d, "assets/chisel/optifine/ctm/" + full_name + "/" + str(count) + ".png")
                count += 1
            write_french_properties(full_name, len(input_dirs))
            return True

    for include in must_include:
        potential_path = Path("assets/chisel/optifine/ctm/" + full_name + "/" + semi[0] + "-" + include + ".png")
        if potential_path.is_file():
            return False

    ctm_path = ""
    if input_dir == "":
        ctm_path = Path(input("Input ctm path: \n"))
        if ctm_path == "":
            return False
        elif "multi" in str(ctm_path).lower():
            return create_ctm_texture(full_name, True)
    else:
        ctm_path = input_dir
    print(ctm_path)
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

def convert(name, reverse = False):
    conversions =\
    [\
    "tiles_large", "large_tile",\
    "layers", "layer",\
    "cuts", "cut",\
    "slanted", "slant",\
    "twisted", "twist"\
    ]

    for i in range(0,len(conversions),2):
        first = conversions[i+(1*int(reverse))]
        second = conversions[i+1+(-1*int(reverse))]

        name = name.replace(first, second)

    return name

def block_creation():
    block_list = [input("Full Block Name\n e.g.: pillar/stone\nyours: ").replace('\\','/')]
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
    client_register_dir = "java/com/matthewperiut/chisel/block/GeneratedClientRegister"
    write_generated_register(register_dir,client_register_dir)

def get_full_name_list(double_list):
    output = []
    for x in double_list:
        output.append(x[0])
    return output

def sort_from_folder(folder_path):
    files = os.listdir(folder_path)
    directories = folder_path.split('/')
    block_name = directories[-1]

    block_list = []
    french_blocks = []
    pillar_blocks = []
    crate_bottom = []
    ctm_ids = ['ctm','2x2','3x3','4x4']
    ctm_textures = []

    # sort
    for name in files:
        file_name = name
        name = convert(name)
        full_name = name.replace(".png","") + "/" + block_name

        if ".mcmeta" in name:
            continue

        leave = False
        for id in ctm_ids:
            if id in name:
                ctm_textures.append([full_name,file_name])
                leave = True
        if leave:
            continue

        if "french" in name:
            french_blocks.append([full_name,file_name])
            continue

        if "-top" in full_name or "-side" in full_name:
            full_name = full_name.replace("-top","_top")
            full_name = full_name.replace("-side","")
            pillar_blocks.append([full_name,file_name])
            continue

        if "-bottom" in full_name:
            full_name = full_name.replace("-bottom","_bottom")
            crate_bottom.append([full_name,file_name])
            continue

        block_list.append([full_name,file_name])

    # basic french include
    if len(french_blocks) > 0:
        block_full_name = french_blocks[0]
        block_list.append(["french/" + block_name,french_blocks[0][1]])

    # data files first
    #add_blocks_from_list(block_list)
    pillar_base = []
    pillar_top = []

    crate_base = []
    crate_top = []

    for crate in crate_bottom:
        semi = crate[0].split('/')
        simple_name = semi[0][:-7]
        for pillar in pillar_blocks:
            if simple_name in pillar[0]:
                if not "top" in pillar[0] and not "bottom" in pillar[0]:
                    crate_base.append(pillar)
                elif not "bottom" in pillar[0]:
                    crate_top.append(pillar)


    remove_from_pillar = crate_base + crate_top

    remove_from_pillar = [i for n, i in enumerate(remove_from_pillar) if i not in remove_from_pillar[:n]]

    for r in remove_from_pillar:
        if r in pillar_blocks:
            pillar_blocks.remove(r)

    for pillar in pillar_blocks:
        if not "top" in pillar[0]:
            pillar_base.append(pillar)
        else:
            pillar_top.append(pillar)

    combined_blocks = block_list + pillar_base + crate_base
    combined_blocks = [i for n, i in enumerate(combined_blocks) if i not in combined_blocks[:n]]

    print(block_list)
    print(pillar_base)
    print(crate_base)

    # data
    # add_blocks_from_list(get_full_name_list(combined_blocks))
    #print(get_full_name_list(combined_blocks))
    add_blocks_from_list(get_full_name_list(combined_blocks))

    # basic img
    for name in block_list:
        create_regular_texture(name[0], folder_path + "/" + name[1])

    # pillars
    for i in range(len(pillar_base)):
        if create_regular_texture(pillar_base[i][0], folder_path + "/" + pillar_base[i][1], folder_path + "/" + pillar_top[i][1]):
            print("copied " + pillar_base[i][0])

    # crates
    for i in range(len(crate_base)):
            if create_regular_texture(crate_base[i][0], folder_path + "/" + crate_base[i][1], folder_path + "/" + crate_top[i][1], folder_path + "/" + crate_bottom[i][1]):
                print("copied " + crate_base[i][0])

    #french
    if len(french_blocks) > 0:
        french_dirs = ""
        for d in french_blocks:
            french_dirs += folder_path + "/" + str(d[1]) + ","

        create_ctm_texture("french/"+block_name, True, french_dirs[0:-1])

    should_auto = []
    for name in block_list:
        for c in ctm_textures:
            if name[1][:-4] in c[1]:
                should_auto.append(name[0])
                create_ctm_texture(name[0],False,folder_path + "/" + c[1])

    os.chdir("assets/chisel/optifine/ctm")
    print(should_auto)
    auto(should_auto)

    os.chdir("../../../../..")
    print(os.getcwd())
    register_dir = "java/com/matthewperiut/chisel/block/GeneratedRegister"
    client_register_dir = "java/com/matthewperiut/chisel/block/GeneratedClientRegister"
    write_generated_register(register_dir,client_register_dir)

    #print(block_list)
    #print(pillar_blocks)
    #print(ctm_textures)

    # then basic textures
    #create_regular_texture()


#block_creation()
#sort_from_folder("C:/Users/Matthew/Desktop/wack/andesite")

sort_from_folder(input("folder: ").replace('\\','/'))