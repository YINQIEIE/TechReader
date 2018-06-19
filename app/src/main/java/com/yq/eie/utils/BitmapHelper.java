package com.yq.eie.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yinqi on 2017/9/9.
 */

public class BitmapHelper {

    public static Bitmap getCircleBmp(Resources resources, int resId, int radius) {
        Bitmap src = BitmapFactory.decodeResource(resources, resId);
        return getRoundedCornerBitmap(src, radius);
    }

    /**
     * 获取圆角矩形或圆形图片的方法
     *
     * @param bitmap 待处理 bitmap 对象
     * @param pixels 圆角半径，x 和 y 相等
     * @return 返回圆角或者圆形 bitmap
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        //取 bitmap 较短的一边和 200 比较，
        // 不足 200 像素取实际值，超过取200，
        // 保证图片不会在分辨率较大的额情况下显示为圆角矩形
        int size = (bitmap.getWidth() < bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight()) > 200 ? 200 : bitmap.getWidth();
        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final Rect desRect = new Rect(0, 0, size, size);
        final RectF rectF = new RectF(desRect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        //柱：一定要先画圆角矩形
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, desRect, paint);
        return output;
    }

    /**
     * 获取待边框的圆角矩形或圆形图片方法
     *
     * @param resources          {@link Resources}
     * @param resId              对应资源 id
     * @param borderCornerRadius 边框圆角半径，x 和 y 相等
     * @return 圆角矩形或圆形 bitmap 对象
     */
    public static Bitmap getRoundCornerBitmapWithBorder(Resources resources, int resId, int borderCornerRadius) {
        Bitmap src = BitmapFactory.decodeResource(resources, resId);
        Bitmap temp_out = getRoundedCornerBitmap(src, 0);//图片默认为矩形的
        Bitmap out = Bitmap.createBitmap(temp_out.getWidth(), temp_out.getHeight(), Bitmap.Config.ARGB_8888);
        //新建画布
        Canvas canvas = new Canvas(out);
        //原始大小的矩形
        Rect rect = new Rect(0, 0, temp_out.getWidth(), temp_out.getHeight());
        //矩形宽高的两边都缩小 10
        Rect insetRect = new Rect(rect);
        //外面白色变宽宽度 默认为 10
        final int borderWidth = 8;
        //缩小矩形
        insetRect.inset(borderWidth, borderWidth);

        Paint paint = new Paint();
        //把图像信息画在缩小后的矩形区域内
        canvas.drawBitmap(temp_out, rect, insetRect, paint);

        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);
        //画边框线
        canvas.drawRoundRect(new RectF(insetRect), borderCornerRadius, borderCornerRadius, paint);
        return out;
    }


    /**
     * 根据资源 id 高斯模糊
     *
     * @param context    上下文
     * @param resId      资源 ID
     * @param blurFactor 模糊程度
     * @return
     */

    public static Bitmap getBluredBitmap(Context context, int resId, float blurFactor) {
        Bitmap src = BitmapFactory.decodeResource(context.getResources(), resId);
        return blurBitmap(context, src.copy(Bitmap.Config.ARGB_8888, true), blurFactor);
    }

    //图片缩放比例
    private static final float BITMAP_SCALE = 0.4f;

    /**
     * 模糊图片的具体方法
     *
     * @param context 上下文对象
     * @param image   需要模糊的图片
     * @return 模糊处理后的图片
     */
    public static Bitmap blurBitmap(Context context, Bitmap image, float blurRadius) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // 计算图片缩小后的长宽
            int width = Math.round(image.getWidth() * BITMAP_SCALE);
            int height = Math.round(image.getHeight() * BITMAP_SCALE);

            // 将缩小后的图片做为预渲染的图片
            Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
            // 创建一张渲染后的输出图片
            Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
            // 创建RenderScript内核对象
            RenderScript rs = RenderScript.create(context);
            // 创建一个模糊效果的RenderScript的工具对象
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
            // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
            Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
            Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

            // 设置渲染的模糊程度, 25f是最大模糊度
            blurScript.setRadius(blurRadius);
            // 设置blurScript对象的输入内存
            blurScript.setInput(tmpIn);
            // 将输出数据保存到输出内存中

            blurScript.forEach(tmpOut);

            // 将数据填充到Allocation中
            tmpOut.copyTo(outputBitmap);
            return outputBitmap;
        }
        return image;
    }

    public static File saveBitmap(Context context, Bitmap bitmap) {
        if (null == bitmap) return null;
        File picFile = null;
        try {
            File dir = new File(Environment.getExternalStorageDirectory(), "beauty");
            if (!dir.exists()) {
                Log.i("saveBitmap: ", "mkdirs = " + dir.mkdirs());
            }
            picFile = new File(dir, System.currentTimeMillis() + ".jpg");
            FileOutputStream fos = new FileOutputStream(picFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picFile;
    }

}
