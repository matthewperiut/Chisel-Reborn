import os
from helpful import get_settings

from compact import compact
from rando import rando
from repeat import repeat

settings = get_settings()
for i in range(1, len(settings)):
    first = settings[0]
    second = settings[i]

    if first == "":
        first = settings[i]
        second = settings[1]
        if i == 1:
            continue

    files = os.scandir(first + "/" + second)
    good = False
    for i in files:
        file_name = str(i)
        if "ctm" in file_name:
            if "zag" in first:
                repeat(first, second, 0)
            else:
                compact(first, second, 0)
            good = True
            continue
        if "2x2" in file_name:
            repeat(first, second, 2)
            good = True
            continue
        if "3x3" in file_name:
            rando(first, second, 3)
            good = True
            continue
        if "4x4" in file_name:
            rando(first, second, 4)
            good = True
            continue

    if not good:
        print(first + "/" + second + " has no ctm file")