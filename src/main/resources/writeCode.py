
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

def write_accompanying_generated_client_register(register_dir,transparent,translucent):
    f = open(register_dir + ".txt", "r")
    lines = f.readlines()
    f.close()

    register_transparent = "        BlockRenderLayerMap.INSTANCE.putBlock(GeneratedRegister.NAME_HERE, RenderLayer.getCutout());\n"
    register_translucent = "        BlockRenderLayerMap.INSTANCE.putBlock(GeneratedRegister.NAME_HERE, RenderLayer.getTranslucent());\n"

    i = 9
    for block in transparent:
        lines.insert(i,register_transparent.replace("NAME_HERE",block))
        i += 1
    for block in translucent:
        lines.insert(i,register_translucent.replace("NAME_HERE",block))
        i += 1

    final_file = open(register_dir + ".java", "w")
    for j in lines:
        final_file.write(j)
    final_file.close()

def write_generated_register(register_dir,client_register_dir):
    f = open(register_dir + ".txt", "r")
    lines = f.readlines()
    f.close()

    full_names = sorted_full_names()
    translucent = []
    transparent = []

    i = 11
    for name in full_names:
        semi = name.split('/')
        block_name_but_louder = name.replace('/','_').upper()
        class_type = "Block"
        pillar = False
        if "pillar" in name or "twist" in name:
            class_type = "PillarBlock"
            pillar = True
        if "redstone" in name:
            class_type = "RedstoneBlock"
        if "redstone" in name and pillar:
            class_type = "RedstonePillarBlock"
        if "glass" in name:
            class_type = "GlassBlock"
            transparent.append(block_name_but_louder)
        if "ice" in name:
            class_type = "IceBlock"
            translucent.append(block_name_but_louder)
            if pillar:
                class_type = "IcePillarBlock"

        inserted_line = "   public static final " + class_type + " " + block_name_but_louder + " = new " + class_type + "(FabricBlockSettings.copyOf(Blocks." + get_block_id(semi[1]) + "));"
        lines.insert(i,inserted_line+'\n')
        i += 1

    i += 3

    for name in full_names:
        semi = name.split('/')
        if semi[1] == "quartz":
            semi[1] = "quartz_block"
        lines.insert(i, '        Reg("' + semi[1] + '", "' + name + '", ' + name.replace('/','_').upper() + ');\n')
        i += 1

    write_accompanying_generated_client_register(client_register_dir,transparent,translucent)

    final_file = open(register_dir + ".java", "w")
    for j in lines:
        final_file.write(j)
    final_file.close()