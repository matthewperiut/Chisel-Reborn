import os
import errno

def open_file(file_name):
    if not os.path.exists(os.path.dirname(file_name)):
        try:
            os.makedirs(os.path.dirname(file_name))
        except OSError as exc: # Guard against race condition
            if exc.errno != errno.EEXIST:
                raise

    f = open(file_name, "w")
    return f

def write_blockstate(full_name):
    file_name = "assets/chisel/blockstates/" + full_name + ".json"

    f = open_file(file_name)

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

block_type = input("blocktype? ")
block = input("block? ")

full_name = block_type + '/' + block
write_blockstate(full_name)
write_item_model(full_name)
write_block_model(full_name)
write_loot_table(full_name)

f = open('full_names.txt', 'a')
f.write('\n' + full_name)
f.close()

full_names = []
with open('full_names.txt') as my_file:
    full_names = my_file.readlines()

for x in range(len(full_names)):
    if('\n' in full_names[x]):
        full_names[x] = full_names[x][0:-1]

#for x in full_names:
#    write_blockstate(x)
#    write_item_model(x)
#    write_block_model(x)
#    write_loot_table(x)

write_tags("data/minecraft/tags/blocks/mineable/pickaxe.json", full_names)
write_tags("data/minecraft/tags/blocks/needs_stone_tool.json", full_names)