
def get_list(resource):
    f = open("resources/" + resource + ".txt", "r")
    out = f.readlines()
    for i in range(len(out)):
            out[i] = out[i].replace("\n","")
    f.close()
    return out

def sorted_full_names():
    output = []
    blocks = get_list("blocks")
    full_names = get_list("full_names")

    for i in range(len(blocks)):
        for j in range(len(full_names)):
            name = full_names[j].split('/')
            if name[1] == blocks[i]:
                output.append(full_names[j])

    return output

def get_block_id(name):
    output = name
    if name == "nether_brick":
        output = "nether_bricks"
    if name == "purpur":
        output = "purpur_block"
    if name == "quartz":
        output = "quartz_block"
    return output.upper()

def write_generated_register(register_dir):
    f = open(register_dir + ".txt", "r")
    lines = f.readlines()
    f.close()

    full_names = sorted_full_names()

    i = 12
    for name in full_names:
        semi = name.split('/')
        inserted_line = "   public static final Block " + name.replace('/','_').upper() + " = new Block(FabricBlockSettings.copyOf(Blocks." + get_block_id(semi[1]) + "));"
        if "pillar" in name or "twist" in name:
            inserted_line = "   public static final PillarBlock " + name.replace('/','_').upper() + " = new PillarBlock(FabricBlockSettings.copyOf(Blocks." + get_block_id(semi[1]) + "));"
        lines.insert(i,inserted_line+'\n')
        i += 1

    i += 3

    for name in full_names:
        semi = name.split('/')
        lines.insert(i, '        Reg("' + semi[1] + '", "' + name + '", ' + name.replace('/','_').upper() + ');\n')
        i += 1

    final_file = open(register_dir + ".java", "w")
    for j in lines:
        final_file.write(j)
    final_file.close()