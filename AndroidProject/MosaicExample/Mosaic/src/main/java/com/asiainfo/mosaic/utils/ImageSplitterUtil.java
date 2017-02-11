package com.asiainfo.mosaic.utils;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年02月11日14点58分 描述:
 */
public class ImageSplitterUtil {

    /**
     * 描述:传入Bitmap,切成Piece*piece快,返回List
     * 创建时间:2/11/17/14:59 作者:小木箱 邮箱:yangzy3@asiainfo.com
     */

    public static List<ImagePiece> splitImage(Bitmap bitmap,int piece){

        List<ImagePiece> imgPieces = new ArrayList<>();

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int piecewidth = Math.max(width, height)/piece;

        for (int i = 0; i < piece; i++) {

            for (int j = 0; j < piece; j++) {

                ImagePiece imgp = new ImagePiece();

                imgp.setIndex(j + i*piece);

                int x = j * piece;
                int y = i * piece;

                imgp.setBitmap(Bitmap.createBitmap(bitmap,x,y,piecewidth,piecewidth));

                imgPieces.add(imgp);

            }

        }

        return imgPieces;

    }

}
