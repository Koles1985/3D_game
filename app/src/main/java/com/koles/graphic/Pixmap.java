package com.koles.graphic;

import com.koles.graphic.Graphics.PixmapFormat;

public interface Pixmap {
    int getWidth();
    int getHeight();
    PixmapFormat getFormat();
    void dispose();
}
