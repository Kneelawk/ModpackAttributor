# ModpackAttributor
Scans a Curse modpack and generates a markdown list of all the mods contained for use in modpack attribution pages.

## Installation Instructions
You must have Java 14 or later installed in order for this project to build correctly.

In order to build a redistributable package you must use the `jpackage` gradle task.

On Mac and Linux, this would look like:
```bash
./gradlew jpackage
```

On Windows, this would look like:
```bat
gradlew.bat jpackage
```

Once this has finished, you should find the application bundle in the `build/jpackage` directory.

## Screenshots
![Application Screenshot](https://raw.githubusercontent.com/Kneelawk/ModpackAttributor/master/images/Screenshot.png)
