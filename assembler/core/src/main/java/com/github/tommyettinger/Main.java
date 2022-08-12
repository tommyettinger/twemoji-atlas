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
    @Override
    public void create() {
        JsonReader reader = new JsonReader();
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
//            String codename = entry.getString("codes").toLowerCase().replace(' ', '-') + ".png";
//            String charString = entry.getString("char") + ".png";
//            String name = entry.getString("name").replace(':', ',').replace(".", "").replace("&", "and") + ".png";
//            FileHandle original = Gdx.files.local("../../scaled-mid/" + codename);
//            if (original.exists()) {
//                original.copyTo(Gdx.files.local("../../renamed-mid/emoji/" + charString));
//                original.copyTo(Gdx.files.local("../../renamed-mid/name/" + name));
//            }
        }
    }
}