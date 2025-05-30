/**
 * Copyright (C) 2025 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.ancevt.d2d2.debug;

import com.ancevt.d2d2.event.CommonEvent;
import com.ancevt.d2d2.event.InputEvent;
import com.ancevt.d2d2.event.SceneEvent;
import com.ancevt.d2d2.scene.Color;
import com.ancevt.d2d2.scene.BasicGroup;
import com.ancevt.d2d2.scene.Root;
import com.ancevt.d2d2.scene.Sprite;
import lombok.Getter;
import lombok.Setter;

import static com.ancevt.d2d2.D2D2.root;

public class StarletSpace extends BasicGroup {

    private static StarletSpace starletSpace;
    private float velocityX = 30f;
    @Getter
    @Setter
    private int someVal = 5;

    public static int count = 100;

    public StarletSpace(int count) {
        for (int i = 0; i < count; i++) {
            createStarlet().setX((float) (Math.random() * root().getWidth()));
        }
    }

    private Starlet createStarlet() {
        Starlet starlet = new Starlet(this);
        starlet.setPosition(root().getWidth() + 16, (float) (Math.random() * root().getHeight()));
        addChild(starlet);
        return starlet;
    }

    public static StarletSpace haveFun() {
        return haveFun(true);
    }

    public static StarletSpace haveFun(boolean logo) {
        if (starletSpace != null) starletSpace.removeFromParent();

        Root stage = root();
        stage.setBackgroundColor(Color.of(0x000510));
        Sprite d2d2Title = Sprite.load("d2d2-core-demo-tileset.png", 0, 160, 512, 128);
        d2d2Title.setColor(Color.LIGHT_GRAY);
        starletSpace = new StarletSpace(count);
        if (logo) starletSpace.addChild(d2d2Title, (stage.getWidth() - d2d2Title.getWidth()) / 2, 45);
        stage.addChild(starletSpace);
        stage.addEventListener(CommonEvent.Resize.class, e -> {
            d2d2Title.setPosition((stage.getWidth() - d2d2Title.getWidth()) / 2, 45);
        });
        stage.addEventListener(InputEvent.MouseMove.class, e -> {
            float center = stage.getWidth() / 2;
            starletSpace.velocityX = center - e.getX();
        });
        return starletSpace;
    }

    private static class Starlet extends BasicGroup {

        private final Sprite sprite;
        private float t;
        private final StarletSpace starletSpace;


        public Starlet(StarletSpace starletSpace) {
            this.starletSpace = starletSpace;
            sprite = Sprite.load("d2d2-core-demo-tileset.png", 32, 144, 8, 8);
            sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
            addChild(sprite);

            Color color = new Color(0x88, 0x88, (int) (0x80 + (Math.random() * 0x80)));

            sprite.setColor(color);

            float scale = (float) (Math.random() * 3);
            setAlpha((float) Math.random());
            setScale(scale, scale);
        }

        @Override
        public void tick() {
            setAlpha(getAlpha() + t);
            t -= 0.01f;
            if (getAlpha() <= 0.0f) t += 0.1f;

            int oldX = (int) getX();
            moveX(-getScaleX() * starletSpace.velocityX / 75f);
            int newX = (int) getX();

            int step = oldX < newX ? 1 : -1;

            int x = oldX;
            while (x != newX) {
                x += step;

                if (x % starletSpace.someVal == 0) {
                    Sprite plume = sprite.cloneSprite();
                    plume.addEventListener(SceneEvent.Tick.class, e -> {
                        plume.setAlpha(plume.getAlpha() - 0.01f);
                        plume.moveY(0.05f);
                        plume.rotate(1f);
                        plume.scaleY(0.99f);
                        if (plume.getAlpha() <= 0) plume.removeFromParent();
                    });
                    plume.setColor(Color.WHITE);
                    plume.setPosition(x, getY());
                    plume.setAlpha(0.1f);
                    plume.move((-sprite.getWidth() / 2) * getScaleX(), (-sprite.getHeight() / 2) * getScaleY());
                    plume.setScale(getScaleX(), getScaleY());
                    getParent().addChild(plume);
                }
            }


            if (getX() < -16.0f) {
                setX(root().getWidth() + 16);
                setY((float) (root().getHeight() * Math.random()));
            }

            if (getX() > root().getWidth() + 16.0f) {
                setX(-16.0f);
                setY((float) (root().getHeight() * Math.random()));
            }
            if (getY() < -16.0f) {
                setY((float) (root().getWidth() * Math.random()));
                setY(root().getHeight() + 16);
            }

            if (getY() > root().getHeight() + 16.0f) {
                setY((float) (root().getWidth() * Math.random()));
                setY(-16.0f);
            }
        }
    }

}
