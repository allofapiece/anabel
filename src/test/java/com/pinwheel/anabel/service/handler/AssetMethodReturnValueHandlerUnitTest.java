package com.pinwheel.anabel.service.handler;

import com.pinwheel.anabel.external.category.Unit;
import com.pinwheel.anabel.service.module.asset.AssetManager;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Category(Unit.class)
public class AssetMethodReturnValueHandlerUnitTest {
    @Mock
    private AssetManager assetManager;

    @Test
    public void shouldRetrieveFromRedirect() {
        var manager = new AssetsMethodReturnValueHandler(assetManager);

        assertEquals("user/login", manager.retrieveTemplateFromRedirect("redirect:/user/login"));
        assertEquals("user/login", manager.retrieveTemplateFromRedirect("user/login"));
    }
}
