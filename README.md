# TwemojiAtlas

Takes [twemoji](https://github.com/twitter/twemoji) icons,
uses [emoji.json](https://github.com/GreatWizard/emoji.json/tree/feat/14.0) to name them,
and outputs a [libGDX](https://libgdx.com/) atlas (eventually).

## Gradle

This project uses [Gradle](http://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `headless:run`: starts the headless application. Note: if headless sources were not modified - and the application still creates `ApplicationListener` from `core` project - this task might fail due to no graphics support.
- `idea`: generates IntelliJ project data.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.