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
    endend = '"\n'

    for x in range(0,len(full_names)):
        if(full_names[x] == ''):
            f.write('\n')
            continue

        if(x != len(full_names)-1):
            f.write(stub + full_names[x] + end)
        else:
            f.write(stub + full_names[x] + endend)

    f.write('  ]\n')
    f.write('}')
    f.close()

def create_ctm_dir(block_type, block):
    create_dir("assets/chisel/optifine/ctm/" + block_type + "/" + block)

def refresh_names():
    full_names = []
    with open('full_names.txt') as my_file:
        full_names = my_file.readlines()

    for x in range(len(full_names)):
        if('\n' in full_names[x]):
            full_names[x] = full_names[x][0:-1]

    full_names.sort()
    full_names = [i for n, i in enumerate(full_names) if i not in full_names[:n]]

    #for x in full_names:
    #    write_blockstate(x)
    #    write_item_model(x)
    #    write_block_model(x)
    #    write_loot_table(x)

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

    settings_f = open("settings.txt", "r")
    settings = settings_f.readlines()
    for i in range(len(settings)):
        settings[i] = settings[i].replace("\n","")

    f = open('full_names.txt', 'a')
    for i in range(1, len(settings)):
        first = settings[0]
        second = settings[i]

        if first == "":
            first = settings[i]
            second = settings[1]
            if i == 1:
                continue

        full_name = first + '/' + second
        f.write('\n' + full_name)
        create_ctm_dir(first, second)
        write_blockstate(full_name)
        write_item_model(full_name)
        write_block_model(full_name)
        write_loot_table(full_name)
    f.close()

def reload_all():
    all_names_f = open("full_names.txt", "r")
    all_names = all_names_f.readlines()
    all_names_f.close()
    for i in range(len(all_names)):
        all_names[i] = all_names[i].replace("\n","")
        write_blockstate(all_names[i])
        write_item_model(all_names[i])
        write_block_model(all_names[i])
        write_loot_table(all_names[i])
    check_lang(all_names)

def check_lang_from_settings():
    settings_f = open("settings.txt", "r")
    settings = settings_f.readlines()
    for i in range(len(settings)):
        settings[i] = settings[i].replace("\n","")

    list_of_full_names = []
    for i in range(1, len(settings)):
        first = settings[0]
        second = settings[i]

        if first == "":
            first = settings[i]
            second = settings[1]
            if i == 1:
                continue

        list_of_full_names.append(first + "/" + second)

    check_lang(list_of_full_names)

def check_lang(list_of_full_names):
    with open('assets/chisel/lang/en_us.json') as json_file:
        data = json.load(json_file)

        for name in list_of_full_names:
            temp = name.split('/')
            first = temp[0]
            second = temp[1]

            key = "block.chisel." + first + "." + second
            if not key in data.keys():
                value = input('Input English for "' + key + '":\n e.g.: ' + data["block.chisel." + first + ".stone"] + '\n' + 'yours: ')
                data[key] = value

        with open('assets/chisel/lang/en_us.json', 'w') as outfile:
                json.dump(data, outfile, separators=(',\n', ':'), sort_keys=True)



#from_settings()
#check_lang_from_settings()
reload_all()
#refresh_names()