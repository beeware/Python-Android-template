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

3. Do something...

If you've done this correctly, a project called ``myproject`` should have a
directory structure that looks something like::

    android/
        app_packages/
        src/

You're now ready to build and run your project!

Next steps
----------

Of course, just running Python code isn't very interesting by itself - you'll
be able to output to the console, but if you tap the icon on your phone, you
won't see anything - because there isn't a visible console on an Android.

To do something interesting, you'll need to work with the native Android system
libraries to draw widgets and respond to screen taps.

Or, you could use a library like `toga`_ that provides a cross-platform widget
toolkit that supports Android.

If you have any external library dependencies (like `toga`, or anything other
third-party library), you should install the library code into the
``app_packages`` directory. This directory is the same as a  ``site_packages``
directory on a desktop Python install.

It's also worth noting that the ``src`` and ``app_packages`` code don't need
to contain the **actual** code. If it's more convenient to keep the code
somewhere else, you can symlink to the actual code inside the ``src`` or
``app_packages`` directory. At compile time, the symlink will be resolved and
copied to the app bundle, but during development, you can avoid having copies
of code in your source repositories.

One pattern for doing this is to have a top level project directory that
contains the source module, and an iOS directory at the same level that
links in the project source::

    myproject/
        android/
           src/
               myproject -> ../../myproject
        myproject/
            __init__.py
            main.py
            other.py
        setup.py

.. _cookiecutter: http://github.com/audreyr/cookiecutter
.. _toga: http://pybee.org/toga
