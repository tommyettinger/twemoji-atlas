/*
 * Copyright (c) 2022 Tommy Ettinger.
 * Licensed under MIT.
 * https://github.com/tommyettinger/twemoji-atlas/blob/main/LICENSE
 */

package com.github.tommyettinger;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class Main extends ApplicationAdapter {
//    public static final String MODE = "FLAG";
//    public static final String MODE = "EMOJI";
    public static final String MODE = "EMOJI_HTML";
    @Override
    public void create() {
        JsonReader reader = new JsonReader();
        if("FLAG".equals(MODE)) {
            JsonValue json = reader.parse(Gdx.files.internal("emoji.json"));
            char[] buffer = new char[2];
            for (JsonValue entry = json.child; entry != null; entry = entry.next) {
                if(!"Flags (country-flag)".equals(entry.getString("category"))) continue;

                String codename = entry.getString("codes").toLowerCase().replace(' ', '-') + ".png";
                String charString = entry.getString("char") + ".png";
                String name = entry.getString("name").replace(':', ',').replace(".", "").replace("&", "and") + ".png";
                String countryUnicode = entry.getString("char");
                buffer[0] = (char)(countryUnicode.codePointAt(1) - 56806 + 'A');
                buffer[1] = (char)(countryUnicode.codePointAt(3) - 56806 + 'A');
                String countryCode = String.valueOf(buffer);
                FileHandle original = Gdx.files.local("../../scaled-tiny/" + codename);
                if (original.exists()) {
                    original.copyTo(Gdx.files.local("../../flags-tiny/emoji/" + charString));
                    original.copyTo(Gdx.files.local("../../flags-tiny/name/" + name));
                    original.copyTo(Gdx.files.local("../../flags-tiny/code/" + countryCode + ".png"));
                }
            }
        }
        else if("EMOJI".equals(MODE)) {
            JsonValue json = reader.parse(Gdx.files.internal("emoji.json"));
            for (JsonValue entry = json.child; entry != null; entry = entry.next) {
                String codename = entry.getString("codes").toLowerCase().replace(' ', '-') + ".png";
                String charString = entry.getString("char") + ".png";
                String name = entry.getString("name").replace(':', ',').replace('“', '\'').replace('”', '\'').replace('’', '\'').replace(".", "").replace("&", "and") + ".png";
                FileHandle original = Gdx.files.local("../../scaled-mid/" + codename);
                if (original.exists() && !Gdx.files.local("../../renamed-mid/emoji/" + charString).exists()) {
                    original.copyTo(Gdx.files.local("../../renamed-mid/emoji/" + charString));
                    original.copyTo(Gdx.files.local("../../renamed-mid/name/" + name));
                }
            }
        }
        else if("EMOJI_HTML".equals(MODE)) {
            JsonValue json = reader.parse(Gdx.files.internal("emoji-info.json"));
            StringBuilder sb = new StringBuilder(4096);
            sb.append("""
                    <!doctype html>
                    <html>
                    <head>
                    \t<title>Twemoji Preview</title>
                    \t<meta http-equiv="content-type" content="text/html; charset=UTF-8">
                    \t<meta id="gameViewport" name="viewport" content="width=device-width initial-scale=1">
                    \t<link href="styles.css" rel="stylesheet" type="text/css">
                    </head>
                    
                    """);
            sb.append("<body>\n");
            sb.append("<h1>Twemoji Preview</h1>\n");
            sb.append("<p>This shows all emoji supported by " +
                      "<a href=\"https://github.com/tommyettinger/twemoji-atlas\">TwemojiAtlas</a>, " +
                      "along with the two names each can be looked up by.</p>\n");
            sb.append("<p>The atlases and all image assets are licensed under CC-BY 4.0, with the same " +
                      "<a href=\"https://github.com/twitter/twemoji#attribution-requirements\">permissions granted for twemoji here</a>.</p>\n");
            sb.append("<div class=\"box\">\n");
            for (JsonValue entry = json.child; entry != null; entry = entry.next) {
                String emojiChar = entry.getString("char");
                String name = entry.getString("name");
                String emojiFile = "name/" + name + ".png";
                sb.append("\t<div class=\"item\">\n" +
                          "\t\t<img src=\"" + emojiFile + "\" alt=\"" + name + "\" />\n" +
                          "\t\t<p>" + emojiChar + "</p>\n" +
                          "\t\t<p>" + name + "</p>\n" +
                          "\t</div>\n");
            }
            sb.append("</div>\n</body>\n");
            sb.append("</html>\n");
            Gdx.files.local("index.html").writeString(sb.toString(), false, "UTF8");
        }
    }
}