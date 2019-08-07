package com.pinwheel.anabel.service.module.asset;

import com.pinwheel.anabel.external.category.Unit;
import com.pinwheel.anabel.service.module.asset.entity.Css;
import com.pinwheel.anabel.service.module.asset.entity.Js;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@Category(Unit.class)
class MapAssetStorageUnitTest {
    private MapAssetStorage storage;

    @BeforeEach
    public void init() {
        storage = new MapAssetStorage(new HashMap<>() {{
            put("default", new HashMap<>() {{
                put("js", new ArrayList<>() {{
                    add(Js.builder()
                            .name("js")
                            .template("default")
                            .src("defaultJsFile")
                            .build());
                    add(Js.builder()
                            .name("js")
                            .template("default")
                            .src("defaultJsFile2")
                            .build());
                }});
                put("css", new ArrayList<>() {{
                    add(Css.builder()
                            .name("css")
                            .template("default")
                            .rel("defaultCssFile")
                            .build());
                }});
            }});
            put("main", new HashMap<>() {{
                put("js", new ArrayList<>() {{
                    add(Js.builder()
                            .name("js")
                            .template("main")
                            .src("mainJsFile")
                            .build());
                }});
                put("css", new ArrayList<>() {{
                    add(Css.builder()
                            .name("css")
                            .template("main")
                            .rel("mainCssFile")
                            .build());
                }});
            }});
        }});
    }

    @Test
    public void shouldAddNewAssetForTemplate() {
        storage.add(Css.builder()
                .name("css")
                .template("default")
                .rel("defaultCssFile2")
                .build(), "default");

        assertEquals(2, storage.getMap().get("default").get("css").size());
        assertEquals("defaultCssFile2", ((Css) storage.getMap().get("default").get("css").get(1)).getRel());
    }

    @Test
    public void shouldAddNewAsset() {
        storage.add(Css.builder()
                .name("css")
                .template("default")
                .rel("defaultCssFile2")
                .build());

        assertEquals(2, storage.getMap().get("default").get("css").size());
        assertEquals("defaultCssFile2", ((Css) storage.getMap().get("default").get("css").get(1)).getRel());
    }

    @Test
    public void shouldRemoveAsset() {
        storage.remove(Js.builder()
                .name("js")
                .template("main")
                .src("mainJsFile")
                .build());

        assertEquals(0, storage.getMap().get("main").get("js").size());
    }

    @Test
    public void shouldGetAllByTemplateWithoutDefault() {
        var map = storage.getAllByTemplate("main", false);

        assertEquals(1, map.get("css").size());
        assertEquals("mainCssFile", ((Css) map.get("css").get(0)).getRel());

        assertEquals(1, map.get("js").size());
        assertEquals("mainJsFile", ((Js) map.get("js").get(0)).getSrc());
    }

    @Test
    public void shouldGetAllByTemplateWithDefault() {
        var map = storage.getAllByTemplate("main");

        assertEquals(2, map.get("css").size());
        assertTrue(map.get("css").stream().anyMatch(asset -> ((Css) asset).getRel().equals("mainCssFile")));
        assertTrue(map.get("css").stream().anyMatch(asset -> ((Css) asset).getRel().equals("defaultCssFile")));

        assertEquals(3, map.get("js").size());
        assertTrue(map.get("js").stream().anyMatch(asset -> ((Js) asset).getSrc().equals("defaultJsFile")));
        assertTrue(map.get("js").stream().anyMatch(asset -> ((Js) asset).getSrc().equals("defaultJsFile2")));
        assertTrue(map.get("js").stream().anyMatch(asset -> ((Js) asset).getSrc().equals("mainJsFile")));
    }

    @Test
    public void shouldGetDefaultByPassedTemplate() {
        var map = storage.getAllByTemplate("default");

        assertEquals(1, map.get("css").size());
        assertTrue(map.get("css").stream().anyMatch(asset -> ((Css) asset).getRel().equals("defaultCssFile")));

        assertEquals(2, map.get("js").size());
        assertTrue(map.get("js").stream().anyMatch(asset -> ((Js) asset).getSrc().equals("defaultJsFile")));
        assertTrue(map.get("js").stream().anyMatch(asset -> ((Js) asset).getSrc().equals("defaultJsFile2")));
    }

    @Test
    public void shouldGetDefault() {
        var map = storage.getDefault();

        assertEquals(1, map.get("css").size());
        assertEquals(2, map.get("js").size());
        assertTrue(map.get("js").stream().anyMatch(asset -> ((Js) asset).getSrc().equals("defaultJsFile")));
        assertTrue(map.get("js").stream().anyMatch(asset -> ((Js) asset).getSrc().equals("defaultJsFile2")));
    }

    @Test
    public void shouldGetGroupedWithDefault() {
        var list = storage.getGrouped("main", "css");

        assertEquals(2, list.size());
        assertTrue(list.stream().anyMatch(asset -> ((Css) asset).getRel().equals("defaultCssFile")));
        assertTrue(list.stream().anyMatch(asset -> ((Css) asset).getRel().equals("mainCssFile")));

        list = storage.getGrouped("main", "js");

        assertEquals(3, list.size());
        assertTrue(list.stream().anyMatch(asset -> ((Js) asset).getSrc().equals("defaultJsFile")));
    }

    @Test
    public void shouldGetGroupedWithoutDefault() {
        var list = storage.getGrouped("main", "css", false);

        assertEquals(1, list.size());
        assertTrue(list.stream().anyMatch(asset -> ((Css) asset).getRel().equals("mainCssFile")));

        list = storage.getGrouped("main", "js", false);

        assertEquals(1, list.size());
        assertTrue(list.stream().anyMatch(asset -> ((Js) asset).getSrc().equals("mainJsFile")));
    }
}
