Python Android Template
=======================

A template for building Python apps that will run under Android.

**This repository branch contains a template for Python 3.5**.
Other Python versions are available by cloning other branches of repository.

Using this template
-------------------

The easiest way to use this project is to not use it at all - at least,
not directly. `Briefcase <https://github.com/pybee/briefcase/>`__ is a
tool that uses this template, rolling it out using data extracted from
your ``setup.py``.

However, if you *do* want use this template directly...

1. Install `cookiecutter`_. This is a tool used to bootstrap complex project
   templates::

    $ pip install cookiecutter

2. Run ``cookiecutter`` on the Python-Android template::

    $ cookiecutter https://github.com/pybee/Python-Android-template --checkout 3.5

3. `Download the Python Android support package`_. This JAR file should
   be placed in the ``libs`` directory.

   Alternatively, you can download the full `VOC`_ project, and build your own
   versions of these libraries.

4. Add your code to the template. At the very minimum, you need to have an
   ``app/<app name>/app.py`` file that defines a subclass of
   ``android.app.Activity`` whose name is the same as the project's formal name,
   but with non-alphanumeric characters removed. For example, a project with a
   formal name of ``My Super-Project`` requires an ``Activity`` subclass
   called ``MySuperProject``. If ``<app name>`` contains a dash, it will be
   converted to an underscore in the expected package name

   If your code has any dependencies, they should be installed under the
   ``app_packages`` directory.

5. Install `voc`, the Python to Java bytecode transpiler::

    $ pip install voc

If you've done this correctly, a project with a formal name of ``My Project``,
with an app name of ```my-project`` should have a directory structure that
looks something like::

    android/
        app/
            my_project/
                __init__.py
                app.py
        app_packages/
            ...
        gradle/
            ...
        libs/
            python-android-support.jar
        res/
            ...
        src/
            ...
        AndroidManifest.xml
        build.gradle
        gradle.properties
        gradlew
        gradlew.bat
        LICENSE

The contents of `app.py` needs to do two things:

1. Construct an instance of a class that can be notified when key events
   in the Android activity lifecycle occur::

   * ``onStart()``
   * ``onResume()``
   * ``onPause()``
   * ``onStop()``
   * ``onDestroy()``
   * ``onRestart()``

2. Register this instance with the ``PythonActivity`` by calling
   ``PythonActivity.setListener()``. This method will return the
   Activity instance for the app.

The following would be a simple example of an ``app.py`` that could be used::

    from android import PythonActivity


    class MyApplication:
        def onStart(self):
            print("Application starting up")


    app = MyApplication()

    activity = PythonActivity.setListener(app)


You're now ready to build and run your project!

  $ ./gradlew build

in the top level project directory (``android`` by default).

To run the app on your phone, you'll need to plug it in to a USB port,
and configure your phone for development. The Android developer documentation
has a full set of instructions:

    http://developer.android.com/tools/device.html#setting-up

Then, run the following::

  $ ./gradlew run

This will compile, install and run your new Android project on your device.

If you want to see the logs produced by this code while it runs, use::

  $ adb logcat Python:* *:E DEBUG:*

This will output:

* All the log messages produced by Python code, including all content printed
  to stdout and stderr

* All ``ERROR`` level log messages

* Any message produced by the ``DEBUG`` crash reporting system.

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

.. _cookiecutter: https://github.com/audreyr/cookiecutter
.. _Download the Python Android support package: https://github.com/pybee/voc/releases/download/3.5.2-b3/Python-3.5-Android-support.b1.tar.gz
.. _VOC: http://pybee.org/project/projects/bridges/voc
.. _toga: http://pybee.org/project/projects/libraries/toga
