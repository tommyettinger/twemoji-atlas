package com.github.tommyettinger;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class Main extends ApplicationAdapter {
    @Override
    public void create() {
        JsonReader reader = new JsonReader();
        JsonValue json = reader.parse(Gdx.files.internal("emoji.json"));
        int count = json.size;
        System.out.println(count);
    }
}