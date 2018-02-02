from shutil import copyfile
import os

base_path = os.path.split(os.getcwd())[0]
base_path_libs = os.path.join(base_path, 'libs')
target_path_libs = os.path.join(os.getcwd(), 'libs')

try:
    alldir = os.listdir(base_path_libs)
    libs = filter(lambda x: x.endswith('.jar'), alldir)
except:
    libs = []

for lib in libs:
    base_path_lib = os.path.join(base_path_libs, lib)
    target_path_lib = os.path.join(target_path_libs, lib)
    copyfile(base_path_lib, target_path_lib)
    print(' * Lib {} copied to android/libs/'.format(lib))

print(' * All libs are already copied')

