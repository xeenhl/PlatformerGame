package com.platformer.utils;

/**
 * Created by Oleh on 04.12.2014.
 */
public class Utils {

    public static final int HEIGHT = 600;
    public static final int WIDTH = 600;

    //Convert a JBox2D x coordinate to a JavaFX pixel x coordinate
    public static float toPixelPosX(float posX) {
        float x = WIDTH*posX / 100.0f;
        //float x = (posX + offsetX)*PixelMeeterRatio;
        return x;
    }

    //Convert a JavaFX pixel x coordinate to a JBox2D x coordinate
    public static float toPosX(float posX) {
        float x =   (posX*100.0f*1.0f)/WIDTH;
        //float x = (posX/PixelMeeterRatio) - offsetX;
        return x;
    }

    //Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
    public static float toPixelPosY(float posY) {
        float y = HEIGHT - (1.0f*HEIGHT) * posY / 100.0f;
        //float y = (-posY + offsetY)*PixelMeeterRatio;
        return y;
    }

    //Convert a JavaFX pixel y coordinate to a JBox2D y coordinate
    public static float toPosY(float posY) {
        float y = 100.0f - ((posY * 100*1.0f) /HEIGHT) ;
        //float y = (- posY/PixelMeeterRatio) - offsetY;
        return y;
    }

    //Convert a JBox2D width to pixel width
    public static float toPixelWidth(float width) {
        return WIDTH*width / 100.0f;
    }

    //Convert a JBox2D height to pixel height
    public static float toPixelHeight(float height) {
        return HEIGHT * height / 100.0f;
    }

    public static float toJBoxWidth(float width) {
        return 100.0f * width / WIDTH;
    }

    public static float toJBoxHeight(float height) {
        return 100.0f * height / WIDTH;
    }

}
