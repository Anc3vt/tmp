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
import com.ancevt.d2d2.asset.Assets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//TODO: remove
@Deprecated
class TextureDataInfoReadHelper {

    private TextureDataInfoReadHelper() {
    }

    private static Texture currentTexture;

    public static void readTextureDataInfoFile(String assetPath) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Assets.getAsset(assetPath).getInputStream()));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            parseLine(line);
        }
    }

    private static void parseLine(String line) {
        line = line.trim();
        line = line.replaceAll("\\s+", " ");

        if (line.length() == 0) return;

        char firstChar = line.charAt(0);
        if (firstChar == ':') {
            String tileSetName = line.substring(1);
            currentTexture = D2D2.textureManager().loadTexture(tileSetName);
            return;
        }

        String[] splitted = line.split(" ");

        String textureKey = splitted[0];
        int x = Integer.parseInt(splitted[1]);
        int y = Integer.parseInt(splitted[2]);
        int w = Integer.parseInt(splitted[3]);
        int h = Integer.parseInt(splitted[4]);

        TextureRegion textureRegion = currentTexture.createTextureRegion(x, y, w, h);
        D2D2.textureManager().addTextureRegion(textureKey, textureRegion);
    }

}
