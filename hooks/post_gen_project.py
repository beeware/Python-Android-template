from shutil import copyfile
from os import listdir

base_path_libs = '../libs'

alldir = listdir(base_path_libs)

libs = list(filter(lambda x: x.endswith('.jar'), alldir))

for lib in libs:
    path_lib = base_path_libs +'/'+ lib
    destiny_lib = './libs/' + lib
    copyfile(path_lib, destiny_lib)
    print(' * Lib {} imported to android/libs/'.format(lib))

print(' * All libs is already imported')

