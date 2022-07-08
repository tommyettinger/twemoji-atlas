# TwemojiAtlas

Takes [twemoji](https://github.com/twitter/twemoji) icons,
uses [emoji.json](https://github.com/GreatWizard/emoji.json/tree/feat/14.0) to name them,
and outputs a [libGDX](https://libgdx.com/) atlas.

There are atlases available in three sizes:

  - 72x72 icons (original size) are in `atlas/`, with five 2048x2048 texture pages.
  - 32x32 icons (mid-size) are in `atlas-mid/`, with one 2048x2048 texture page using whitespace stripping.
  - 24x24 icons (small size) are in `atlas-small/`, with one 2048x2048 texture page without whitespace stripping.

The base images used to make these are in `renamed/`, `renamed-mid/`, and `renamed-small/`. You can pack those
into your own atlas, or use a subset of them, if you want.

## License

The atlases and all image assets are licensed under CC-BY 4.0, with the same
[permissions granted for twemoji here](https://github.com/twitter/twemoji#attribution-requirements).

The code is licensed under the MIT License. It includes emoji.json, which is also MIT-licensed.