 
#"chisel/blockstates/"
#folder full of folders that contains json files.
#create a for loop in python3 that goes through every json
#e.g. "chisel/blockstates/array/block.json"

#capture the filename before .json, and setup a way to put text in the
#file replacing the current contents

import json
import os

# Specify the directory to search
directory = 'chisel/blockstates/'

# Define the new content to replace in each JSON file

ctm_compact = {
    "variants": {
        "": {"model": "chisel:block/chisel_style/block_name"}
    },
    "athena:loader": "athena:ctm",
    "ctm_textures": {
        "center": "chisel:block/ctm/chisel_style/block_name/4",
        "empty": "chisel:block/ctm/chisel_style/block_name/1",
        "horizontal": "chisel:block/ctm/chisel_style/block_name/3",
        "particle": "chisel:block/ctm/chisel_style/block_name/0",
        "vertical": "chisel:block/ctm/chisel_style/block_name/2"
    }
}

ctm_repeat = {
    "variants": {
        "": {"model": "chisel:block/chisel_style/block_name"}
    },
    "athena:loader": "athena:giant",
    "ctm_textures": {
        "1": "chisel:block/ctm/chisel_style/block_name/0",
        "2": "chisel:block/ctm/chisel_style/block_name/1",
        "3": "chisel:block/ctm/chisel_style/block_name/2",
        "4": "chisel:block/ctm/chisel_style/block_name/3",
        "particle": "chisel:block/chisel_style/block_name"
    },
    "height": 2,
    "width": 2
}

# Convert the dictionary to a JSON string to write to the files
ctm_compact_json = json.dumps(ctm_compact, indent=4)
ctm_repeat_json = json.dumps(ctm_repeat, indent=4)

def create_blockstate_json(chisel_style, block_name, num_files):
    return {
        "variants": {
            "": [
                {"model": f"chisel:block/{chisel_style}/{block_name}_{i}", "weight": 1} for i in range(0, num_files)
            ]
        }
    }

def create_model_json(chisel_style, block_name, index):
    return {
        "parent": "block/cube_all",
        "textures": {
            "all": f"chisel:block/ctm/{chisel_style}/{block_name}/{index}",
            "particle": f"chisel:block/{chisel_style}/{block_name}"
        }
    }

def populate_random(chisel_style, block_name, count):
    blockstates_path = f'chisel/blockstates/{chisel_style}/'
    models_path = f'chisel/models/block/{chisel_style}/'

    # just in case
    os.makedirs(blockstates_path, exist_ok=True)
    os.makedirs(models_path, exist_ok=True)

    with open(f'{blockstates_path}/{block_name}.json', 'w') as f:
        json.dump(create_blockstate_json(chisel_style, block_name, count), f, indent=4)

    for i in range(0, count):
        with open(f'{models_path}/{block_name}_{i}.json', 'w') as f:
            json.dump(create_model_json(chisel_style, block_name, i), f, indent=4)
    pass

# Walk through the directory
for root, dirs, files in os.walk(directory):
    for file in files:
        if file.endswith('.json'):
            full_path = os.path.join(root, file)
            filename = os.path.splitext(file)[0]
            foldername = root.split('/')[2];
            properties_path = f"chisel/optifine/ctm/{foldername}/{filename}/{foldername}_{filename}.properties"
            if os.path.isfile(properties_path):
                with open(properties_path, 'r') as properties:
                    contents = properties.read()
                    method = contents.split('\n')[1].split('=')[1]
                    if method == "ctm_compact":
                        put_me_in = ctm_compact_json.replace("chisel_style", foldername).replace("block_name", filename)
                        with open(full_path, 'w') as json_file:
                            json_file.write(put_me_in)
                    elif method == "random":
                        block_name = filename
                        chisel_style = foldername
                        num = int(contents.split('\n')[2].split('-')[1]) + 1
                        populate_random(chisel_style, block_name, num)
                        #print(str(num))
                    elif method == "repeat":
                        put_me_in = ctm_repeat_json.replace("chisel_style", foldername).replace("block_name", filename)
                        with open(full_path, 'w') as json_file:
                            json_file.write(put_me_in)
            pass
#
#             # Print file and directory information
#             #print(f"Processing file: {file} in directory: {foldername}")
#             properties_path = f"chisel/optifine/ctm/{foldername}/{filename}/{foldername}_{filename}.properties"
#             method = "null"
#             size = -1
#             if os.path.isfile(properties_path):
#                 with open(properties_path, 'r') as properties:
#                     contents = properties.read()
#                     if not contents.isspace():
#                         #print(contents)
#                         method = contents.split('\n')[1].split('=')[1]
#                         #size = int(contents.split('\n')[4].split('-')[1])
#                         if "ctm_compact" in contents:
#                             if ("tiles" in contents):
#                                 if (not "0-4" in contents):
#                                     #print(contents)
#                                     pass


