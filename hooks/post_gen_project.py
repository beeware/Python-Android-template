from shutil import copyfile
import glob
import os

for src in glob.glob('../libs/*.jar'):
    dest = os.path.join('libs', os.path.basename(src))
    copyfile(src, dest)

print(' * All libs are already copied')

