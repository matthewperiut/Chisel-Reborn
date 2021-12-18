from addBlocks import from_settings
from addBlocks import reload_all

print('type "settings" to load settings file')
print('type "all" to load all full names in "full_names.txt"')
response = input()

if response == "settings":
    from_settings()
elif response == "all":
    reload_all()