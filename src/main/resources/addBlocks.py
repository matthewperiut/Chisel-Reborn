import os
import json
import errno

def create_dir(dirname):
    if not os.path.exists(dirname):
            try:
                os.makedirs(dirname)
            except OSError as exc: # Guard against race condition
                if exc.errno != errno.EEXIST:
                    raise

def open_file(file_name):
    create_dir((os.path.dirname(file_name)))
    f = open(file_name, "w")
    return f

def write_blockstate(full_name):
    file_name = "assets/chisel/blockstates/" + full_name + ".json"

    f = open_file(file_name)

    if "pillar" in full_name or "twist" in full_name:
        blockstate = \
        [\
        '{\n',\
        '  "variants": {\n',\
        '    "axis=x": {\n',\
        '      "model": "chisel:block/' + full_name + '_horizontal",\n',\
        '      "x": 90,\n',\
        '      "y": 90\n',\
        '    },\n',\
        '    "axis=y": {\n',\
        '      "model": "chisel:block/' + full_name + '"\n',\
        '    },\n',\
        '    "axis=z": {\n',\
        '      "model": "chisel:block/' + full_name + '_horizontal",\n',\
        '      "x": 90\n',\
        '    }\n',\
        '  }\n',\
        '}',\
        ]

        for x in blockstate:
                f.write(x)
        return

    # blockstate[4] needs replacing
    # e.g. blockstate[4] = "cut/cobblestone"
    blockstate = \
    [\
    '{\n',\
    '  "variants": {\n',\
    '    "": { "model": "chisel:block/', full_name, '" }\n',\
    '  }\n',\
    '}'\
    ]

    # replace
    blockstate[3] = full_name

    # write to file
    for x in blockstate:
        f.write(x)

def write_item_model(full_name):
    file_name = "assets/chisel/models/item/" + full_name + ".json"

    f = open_file(file_name)

    item_model = \
    [\
    '{\n',\
    '  "parent": "chisel:block/', full_name, '"\n',\
    "}"\
    ]

    for x in item_model:
            f.write(x)

def write_block_model(full_name):
    file_name = "assets/chisel/models/block/" + full_name + ".json"

    f = open_file(file_name)

    if "pillar" in full_name or "twist" in full_name:
        block_model =\
        [\
        '{\n',\
        '  "parent": "minecraft:block/cube_column",\n',\
        '  "textures": {\n',\
        '    "end": "chisel:block/' + full_name + '_top",\n',\
        '    "side": "chisel:block/' + full_name + '"\n',\
        '  }\n',\
        '}',\
        ]
        for x in block_model:
            f.write(x)

        hf = open_file("assets/chisel/models/block/" + full_name + "_horizontal.json")
        block_model_horizontal = block_model
        block_model_horizontal[1] = '  "parent": "minecraft:block/cube_column_horizontal",\n'

        for x in block_model_horizontal:
            hf.write(x)
        return

    block_model = \
    [\
    '{\n',\
    '  "parent": "block/cube_all",\n',\
    '  "textures": {\n',\
    '    "all": "chisel:block/', full_name,'"\n',\
    '  }\n',\
    '}',\
    ]

    for x in block_model:
        f.write(x)

    pass

def write_loot_table(full_name):
    file_name = "data/chisel/loot_tables/blocks/" + full_name + ".json"

    f = open_file(file_name)

    loot_table =\
    [\
    '{\n',\
    '  "type": "minecraft:block",\n',\
    '  "pools": [\n',\
    '    {\n',\
    '      "rolls": 1,\n',\
    '      "entries": [\n',\
    '        {\n',\
    '          "type": "minecraft:item",\n',\
    '          "name": "chisel:', full_name,'"\n',\
    '        }\n',\
    '      ],\n',\
    '      "conditions": [\n',\
    '        {\n',\
    '          "condition": "minecraft:survives_explosion"\n',\
    '        }\n',\
    '      ]\n',\
    '    }\n',\
    '  ]\n',\
    '}',\
    ]

    for x in loot_table:
            f.write(x)

def write_tags(file_path, full_names):
    f = open_file(file_path)

    f.write('{\n')
    f.write('  "replace": false,\n')
    f.write('  "values": [\n')

    stub = '    "chisel:'
    end = '",\n'
    true_end = '"\n'

    for x in range(0,len(full_names)):
        if(full_names[x] == ''):
            f.write('\n')
            continue

        if(x != len(full_names)-1):
            f.write(stub + full_names[x] + end)
        else:
            f.write(stub + full_names[x] + true_end)

    f.write('  ]\n')
    f.write('}')
    f.close()

def create_dirs(full_name):
    semi = full_name.split('/')
    create_dir("assets/chisel/optifine/ctm/" + full_name)
    create_dir("assets/chisel/textures/block/" + semi[0])

def create_dirs_partials(block_type, block):
    create_dirs(block_type + "/" + block)

def refresh_names():
    full_names = get_full_names_from_file("full_names.txt", False)

    full_names.sort()
    full_names = [i for n, i in enumerate(full_names) if i not in full_names[:n]]

    f = open("full_names.txt", "w")
    for i in range(len(full_names)):
        f.write(full_names[i])
        if (i == len(full_names)-1):
            break
        f.write("\n")
    f.close()

    write_tags("data/minecraft/tags/blocks/mineable/pickaxe.json", full_names)
    write_tags("data/minecraft/tags/blocks/needs_stone_tool.json", full_names)

def from_settings():
    add_blocks_from_list(get_full_names_from_file("settings.txt", True, True))

def reload_all():
    add_blocks_from_list(get_full_names_from_file("full_names.txt", False, True))

def add_blocks_from_list(full_names):
    check_lang(full_names)
    f = open('full_names.txt', 'a')
    for name in full_names:
        f.write('\n' + name)
        create_dirs(name)
        write_blockstate(name)
        write_item_model(name)
        write_block_model(name)
        write_loot_table(name)
    f.close()
    refresh_names()
    generate_blocks_and_categories()

def get_full_names_from_file(file_name, partial_names_input=True, check_if_correct=False):
    names_f = open(file_name, "r")
    names = names_f.readlines()
    for i in range(len(names)):
        names[i] = names[i].replace("\n","")

    list_of_full_names = []
    if partial_names_input == True:
        for i in range(1, len(names)):
            first = names[0]
            second = names[i]

            if first == "":
                first = names[i]
                second = names[1]
                if i == 1:
                    continue

            list_of_full_names.append(first + "/" + second)
    else:
        list_of_full_names = names

    if check_if_correct:
        response = input('Does "' + list_of_full_names[0] + '" look correct? (y/n)\n')
        if(response.lower() != "y"):
            print("Cancelling...")
            exit()

    return list_of_full_names

def check_lang_from_settings():
    check_lang(get_full_names_from_file("settings.txt"))

def check_lang(list_of_full_names):
    with open('assets/chisel/lang/en_us.json') as json_file:
        data = json.load(json_file)

        for name in list_of_full_names:
            temp = name.split('/')
            first = temp[0]
            second = temp[1]

            stone_key = "block.chisel." + first + ".stone"
            if not stone_key in data.keys():
                print('"' + stone_key + '" does not exist, does this seem correct? (type "no" to exit)')
                print('If so, please input a proper name for this key, this is the e.g.')
                print(first + ".stone")
                data[stone_key] = input('yours: ')

            if (data[stone_key] == "no"):
                exit()

            key = "block.chisel." + first + "." + second
            if not key in data.keys():
                print('Input English for "' + key + '":\n e.g.: ' + data[stone_key])
                print(first + "." + second)
                data[key] = input('yours: ')

        with open('assets/chisel/lang/en_us.json', 'w') as outfile:
                json.dump(data, outfile, separators=(',\n', ':'), sort_keys=True)

def write_list_to_file(l,fp):
    f = open(fp, "w")
    for i in range(len(l)):
        f.write(l[i])
        if i != len(l)-1:
            f.write('\n')

def generate_blocks_and_categories():
    f = open("full_names.txt", "r")
    blocks = []
    categories = []
    full_names = f.readlines()
    for i in range(len(full_names)):
        full_names[i] = full_names[i].replace("\n","")
        name = full_names[i].split('/')

        if not name[0] in categories:
            categories.append(name[0])

        if not name[1] in blocks:
            blocks.append(name[1])
    f.close()

    blocks.sort()
    categories.sort()

    write_list_to_file(blocks, "blocks.txt")
    write_list_to_file(categories, "categories.txt")

#from_settings()
#reload_all()