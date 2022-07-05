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
//        int count = json.size;
        for (JsonValue entry = json.child; entry != null; entry = entry.next) {
            String codename = entry.getString("codes").toLowerCase().replace(' ', '-') + ".png";
            String charString = entry.getString("char") + ".png";
            String name = entry.getString("name").replace(':', ',').replace(".", "").replace("&", "and") + ".png";
            FileHandle original = Gdx.files.local("../../scaled/" + codename);
            if (original.exists()) {
                original.copyTo(Gdx.files.local("../../renamed-scaled/emoji/" + charString));
                original.copyTo(Gdx.files.local("../../renamed-scaled/name/" + name));
            }
        }
    }
}