from shutil import copyfile
import glob
import os
import jinja2

for src in glob.glob('../libs/*.jar'):
    dest = os.path.join('libs', os.path.basename(src))
    copyfile(src, dest)

print(' * All libs are already copied')

base_path = os.path.split(os.getcwd())[0]
manifest = 'AndroidManifest.xml'
manifest_path = os.path.join(base_path, manifest)
if os.path.isfile(manifest_path):
    env = jinja2.Environment(loader=jinja2.FileSystemLoader(base_path))
    content = {}
    content['cookiecutter'] = {{ cookiecutter }}
    output_manifest = env.get_template(manifest).render(content)
    with open(manifest, 'w') as f:
        f.write(output_manifest)
