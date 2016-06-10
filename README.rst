Python Android Template
=======================

A template for building Python apps that will run under Android.

Using this template
-------------------

1. Install `cookiecutter`_. This is a tool used to bootstrap complex project
   templates::

    $ pip install cookiecutter

2. Run ``cookiecutter`` on the Python-Android template::

    $ cookiecutter https://github.com/pybee/Python-Android-template

3. `Download the Python Android support package`_. This JAR file should
   be placed in the ``libs`` directory.

   Alternatively, you can download the full `VOC`_ project, and build your own
   versions of these libraries.

If this all worked, a project called ``myproject`` should have a directory
structure that looks something like::

    android/
        app/
            ...
        app_packages/
            ...
        libs/
            python-android.jar
        res/
            ...
        src/
            ...
        AndroidManifest.xml
        ant.properties
        build.xml
        local.properties
        LICENSE
        proguard-project.txt
        project.properties

4. Install `voc`, the Python to Java bytecode transpiler::

    $ pip install voc

You're now ready to build and run your project! Define a MainActivity
class in ``app/myproject.py``, and run::

  $ ant debug

in the top level project directory (``android`` by default).

To run the app on your phone, you'll need to plug it in to a USB port,
and configure your phone for development. The Android developer documentation
has a full set of instructions:

    http://developer.android.com/tools/device.html#setting-up

Then, run the following (substituting your project name for ``myproject``)::

  $ adb install -r bin/myproject-debug.apk
  $ adb shell am start -n python.myproject/python.myproject.MainActivity

This will compile, install and run your new Android project on your device.

If you want to see the logs produced by this code while it runs, use::

  $ adb logcat VOC:* *:E DEBUG:*

This will output:

* All the log messages produced by Python code

* All ERROR level log messages

* Any message produced by the DEBUG crash reporting system.

Next steps
----------

Of course, just running Python code isn't very interesting by itself - you'll
be able to output to the console, but if you tap the icon on your phone, you
won't see anything on the screen - you'll only see content in the logs.

To do something interesting, you'll need to work with the native Android
system libraries to draw widgets and respond to screen taps.

Alternatively, you could use a library like `toga`_ that provides a cross-platform
widget toolkit that supports Android.

If you have any external library dependencies (like `toga`_, or any other
third-party library), you should install the library code into the
``app_packages`` directory. This directory is the same as a  ``site_packages``
directory on a desktop Python install.

It's also worth noting that the ``app`` and ``app_packages`` code don't need
to contain the **actual** code. If it's more convenient to keep the code
somewhere else, you can symlink to the actual code inside the ``app`` or
``app_packages`` directory. At compile time, the symlink will be resolved and
copied to the app bundle, but during development, you can avoid having copies
of code in your source repositories.

One pattern for doing this is to have a top level project directory that
contains the source module, and an ``android`` directory at the same level
that links in the project source::

    myproject/
        android/
            app/
                myproject -> ../../myproject
            ...
        myproject/
            __init__.py
            main.py
            other.py
        setup.py

.. _cookiecutter: http://github.com/audreyr/cookiecutter
.. _Download the Python Android support package: https://github.com/pybee/voc/releases/download/3.4.2-b1/Python-Android-3.4.2-b1.tar.gz
.. _VOC: https://github.com/pybee/voc
.. _toga: http://pybee.org/toga
