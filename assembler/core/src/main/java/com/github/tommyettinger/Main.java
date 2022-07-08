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
        for (JsonValue entry = json.child; entry != null; entry = entry.next) {
            String codename = entry.getString("codes").toLowerCase().replace(' ', '-') + ".png";
            String charString = entry.getString("char") + ".png";
            String name = entry.getString("name").replace(':', ',').replace(".", "").replace("&", "and") + ".png";
            FileHandle original = Gdx.files.local("../../scaled-mid/" + codename);
            if (original.exists()) {
                original.copyTo(Gdx.files.local("../../renamed-mid/emoji/" + charString));
                original.copyTo(Gdx.files.local("../../renamed-mid/name/" + name));
            }
        }
    }
}