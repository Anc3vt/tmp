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

package com.ancevt.d2d2.scene;

import com.ancevt.d2d2.event.CommonEvent;
import com.ancevt.d2d2.event.SceneEvent;
import lombok.Getter;

public class Root extends BasicGroup implements Resizable {
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;

    @Getter
    private float width;

    @Getter
    private float height;

    @Getter
    private Color backgroundColor;

    public Root() {
        setName("_" + getClass().getSimpleName() + getNodeId());
        setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBackgroundColor(int rgb) {
        setBackgroundColor(Color.of(rgb));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "name=" + getName() +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        dispatchEvent(CommonEvent.Resize.create(width, height));
    }

    public void setWidth(float width) {
        this.width = width;
        dispatchEvent(CommonEvent.Resize.create(width, height));
    }

    public void setHeight(float height) {
        this.height = height;
        dispatchEvent(CommonEvent.Resize.create(width, height));
    }

    static void dispatchAddToStage(Node node) {
        if (node.isOnScreen()) {
            node.dispatchEvent(SceneEvent.AddToScene.create());
            if (node instanceof Group group) {
                for (int i = 0; i < group.getNumChildren(); i++) {
                    dispatchAddToStage(group.getChild(i));
                }
            }
        }
    }

    static void dispatchRemoveFromStage(Node node) {
        if (node.isOnScreen()) {
            node.dispatchEvent(SceneEvent.RemoveFromScene.create());

            if (node instanceof Group group) {
                for (int i = 0; i < group.getNumChildren(); i++) {
                    dispatchRemoveFromStage(group.getChild(i));
                }
            }
        }
    }
}
