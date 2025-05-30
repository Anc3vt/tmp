/**
 * Copyright (C) 2025 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ancevt.d2d2.scene.texture;

import com.ancevt.d2d2.D2D2;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TextureFactory {

    private final static Map<String, TextureRegion> textureCacheFiles = new HashMap<>();

    public static TextureRegion getTexture(String asset, int textureX, int textureY, int textureWidth, int textureHeight) {
        return
            textureCacheFiles.computeIfAbsent(
                "%s_%d".formatted(
                    asset,
                    Objects.hash(
                        asset,
                        textureX,
                        textureY,
                        textureWidth,
                        textureHeight
                    )),
                key -> D2D2.textureManager()
                    .loadTexture(asset)
                    .createTextureRegion(textureX, textureY, textureWidth, textureHeight)
            );
    }

    public static void clearCache() {
        textureCacheFiles.clear();
    }
}
