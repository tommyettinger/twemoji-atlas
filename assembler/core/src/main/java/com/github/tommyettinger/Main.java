/*
 * Copyright (c) 2022 Tommy Ettinger.
 * Licensed under MIT.
 * https://github.com/tommyettinger/twemoji-atlas/blob/main/LICENSE
 */

package com.github.tommyettinger;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;

import java.lang.StringBuilder;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class Main extends ApplicationAdapter {
//    public static final String MODE = "EMOJI_MID"; // run this first
//    public static final String MODE = "EMOJI_SMALL";
    public static final String MODE = "EMOJI_LARGE";
//    public static final String MODE = "EMOJI_HTML";
//    public static final String MODE = "FLAG";
//    public static final String MODE = "MODIFY_JSON";

    @Override
    public void create() {
        JsonReader reader = new JsonReader();
        if("MODIFY_JSON".equals(MODE)) {
            //To locate any names with non-ASCII chars in emoji.json, use this regex:
            //"name":"[^"]*[^\u0000-\u007F][^"]*",
            //To locate any names with characters that could be a problem, use this regex (may need expanding):
            //"name":"[^"]*[^0-9a-zA-Z' ,!-][^"]*",
            //Might be useful for locating intermediate things that need replacement?
            //"name":"[^"]*[^0-9a-zA-Z' ,:\(\)!-][^"]*",
            JsonValue json = reader.parse(Gdx.files.internal("emoji.json"));
            for (JsonValue entry = json.child; entry != null; entry = entry.next) {
                String name = entry.getString("name")
                        .replace(':', ',').replace('“', '\'').replace('”', '\'').replace('’', '\'')
                        .replace("ñ", "n").replace("ã", "a").replace("é", "e").replace("í", "i").replace("ü", "u")
                        .replace("ç", "c").replace("ô", "o").replace("Å", "A").replace("å", "a")
                        .replace(".", "").replace("&", "and");
                entry.get("name").set(name);
            }

            Gdx.files.local("emoji-ascii-names.json").writeString(json.toJson(JsonWriter.OutputType.json).replace("{", "\n{"), false);
        }
        else if("EMOJI_MID".equals(MODE)) {
            JsonValue json = reader.parse(Gdx.files.internal("emoji.json"));
            ObjectSet<String> used = new ObjectSet<>(json.size);
            for (JsonValue entry = json.child; entry != null; entry = entry.next) {
                String name = entry.getString("name")
//                        .replace(':', ',').replace('“', '\'').replace('”', '\'').replace('’', '\'').replace(".", "").replace("&", "and")
                        ;
                if(used.add(name)) {
                    String codename = entry.getString("codes").toLowerCase().replace(' ', '-').replaceAll("\\b0+", "");
                    String cleaned = codename.replaceAll("-fe0f", "");
                    String charString = entry.getString("char") + ".png";
                    entry.get("name").set(name);
                    name += ".png";
                    entry.remove("codes");
                    FileHandle original = Gdx.files.local("../../scaled-mid/" + cleaned + ".png");
                    if (original.exists()) {
                        original.copyTo(Gdx.files.local("../../renamed-mid/emoji/" + charString));
                        original.copyTo(Gdx.files.local("../../renamed-mid/name/" + name));
                    }
                    else {
                        original = Gdx.files.local("../../scaled-mid/" + codename + ".png");
                        if (original.exists()) {
                            original.copyTo(Gdx.files.local("../../renamed-mid/emoji/" + charString));
                            original.copyTo(Gdx.files.local("../../renamed-mid/name/" + name));
                        }
                    }
                } else {
                    entry.remove();
                }
            }
            Gdx.files.local("emoji-info.json").writeString(json.toJson(JsonWriter.OutputType.json).replace("{", "\n{"), false);
        }
        else if("EMOJI_SMALL".equals(MODE)) {
            JsonValue json = reader.parse(Gdx.files.internal("emoji.json"));
            ObjectSet<String> used = new ObjectSet<>(json.size);
            for (JsonValue entry = json.child; entry != null; entry = entry.next) {
                String name = entry.getString("name")
//                        .replace(':', ',').replace('“', '\'').replace('”', '\'').replace('’', '\'').replace(".", "").replace("&", "and")
                        ;
                if(used.add(name)) {
                    String codename = entry.getString("codes").toLowerCase().replace(' ', '-').replaceAll("\\b0+", "");
                    String cleaned = codename.replaceAll("-fe0f", "");
                    String charString = entry.getString("char") + ".png";
                    name += ".png";
                    FileHandle original = Gdx.files.local("../../scaled-small/" + cleaned + ".png");
                    if (original.exists()) {
                        original.copyTo(Gdx.files.local("../../renamed-small/emoji/" + charString));
                        original.copyTo(Gdx.files.local("../../renamed-small/name/" + name));
                    }
                    else {
                        original = Gdx.files.local("../../scaled-small/" + codename + ".png");
                        if (original.exists()) {
                            original.copyTo(Gdx.files.local("../../renamed-small/emoji/" + charString));
                            original.copyTo(Gdx.files.local("../../renamed-small/name/" + name));
                        }
                    }
                } else {
                    entry.remove();
                }
            }
        }
        else if("EMOJI_LARGE".equals(MODE)) {
            JsonValue json = reader.parse(Gdx.files.internal("emoji.json"));
            ObjectSet<String> used = new ObjectSet<>(json.size);
            for (JsonValue entry = json.child; entry != null; entry = entry.next) {
                String name = entry.getString("name")
//                        .replace(':', ',').replace('“', '\'').replace('”', '\'').replace('’', '\'').replace(".", "").replace("&", "and")
                        ;
                if(used.add(name)) {
                    String codename = entry.getString("codes").toLowerCase().replace(' ', '-').replaceAll("\\b0+", "");
                    String cleaned = codename.replaceAll("-fe0f", "");
                    String charString = entry.getString("char") + ".png";
                    name += ".png";
                    FileHandle original = Gdx.files.local("../../individual/" + cleaned + ".png");
                    if (original.exists()) {  //&& !Gdx.files.local("../../renamed/emoji/" + charString).exists()
                        original.copyTo(Gdx.files.local("../../renamed/emoji/" + charString));
                        original.copyTo(Gdx.files.local("../../renamed/name/" + name));
                    }
                    else {
                        original = Gdx.files.local("../../individual/" + codename + ".png");
                        if (original.exists()) {
                            original.copyTo(Gdx.files.local("../../renamed/emoji/" + charString));
                            original.copyTo(Gdx.files.local("../../renamed/name/" + name));
                        }
                    }
                } else {
                    entry.remove();
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
                      "<a href=\"https://github.com/jdecked/twemoji/tree/v15.0.3?tab=readme-ov-file#attribution-requirements\">permissions granted for twemoji here</a>.</p>\n");
            sb.append("<p>Thanks to all the <a href=\"https://github.com/jdecked/twemoji?tab=readme-ov-file#committers-and-contributors\">" +
                      "WordPress, Discord, and ex-Twitter developers</a> who made this project possible!</p>\n");
            sb.append("<div class=\"box\">\n");
            for (JsonValue entry = json.child; entry != null; entry = entry.next) {
                String emojiChar = entry.getString("char");
                String name = entry.getString("name");
                String emojiFile = "name/" + name + ".png";
                sb.append("\t<div class=\"item\">\n" +
                                "\t\t<img src=\"")
                        .append(emojiFile).append("\" alt=\"").append(name).append("\" />\n")
                        .append("\t\t<p>").append(emojiChar).append("</p>\n")
                        .append("\t\t<p>").append(name).append("</p>\n")
                        .append("\t</div>\n");
            }
            sb.append("</div>\n</body>\n");
            sb.append("</html>\n");
            Gdx.files.local("index.html").writeString(sb.toString(), false, "UTF8");
        }
        else if("FLAG".equals(MODE)) {
            JsonValue json = reader.parse(Gdx.files.internal("emoji.json"));
            char[] buffer = new char[2];
            for (JsonValue entry = json.child; entry != null; entry = entry.next) {
                if(!"Flags (country-flag)".equals(entry.getString("category"))) continue;

                String codename = entry.getString("codes").toLowerCase().replace(' ', '-').replaceAll("\\b0+", "") + ".png";
                String charString = entry.getString("char") + ".png";
                String name = entry.getString("name")
//                        .replace(':', ',').replace('“', '\'').replace('”', '\'').replace('’', '\'').replace(".", "").replace("&", "and")
                        ;
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
    }

    private static final Pattern diacritics = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    /**
     * Removes accented characters from a string; if the "base" characters are non-English anyway then the result won't
     * be an ASCII string, but otherwise it probably will be.
     * <br>
     * This version can contain ligatures such as "æ" and "œ", but not with diacritics.
     * <br>
     * <a href="http://stackoverflow.com/a/1215117">Credit to StackOverflow user hashable</a>.
     *
     * @param str a string that may contain accented characters
     * @return a string with all accented characters replaced with their (possibly ASCII) counterparts
     */
    public static String removeAccents(String str) {
        String alteredString = Normalizer.normalize(str, Normalizer.Form.NFD);
        return diacritics.matcher(alteredString).replaceAll("");
    }

}