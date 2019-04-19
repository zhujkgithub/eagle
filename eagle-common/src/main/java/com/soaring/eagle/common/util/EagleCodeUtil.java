package com.soaring.eagle.common.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.soaring.eagle.common.exception.NormalException;
import org.apache.commons.lang3.StringUtils;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.paint.EAN8TextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: doublelifeke
 * Email: hautxxxyzjk@163.com
 * DateTime: 2019/4/18 10:59
 * Description: 二维码、条形码 生成
 * <p>
 * Barcode简介
 * 　　Barcode是由一组按一定编码规则排列的条,空符号，用以表示一定的字符,数字及符号组成的,一种机器可读的数据表示方式。
 * 　　Barcode的形式多种多样，按照它们的外观分类：
 * 　　　　Linear barcode(一维码):它的信息存储量小，仅能存储一个代号，使用时通过这个代号调取计算机网络中的数据。
 * 　　　　Matrix barcode(二维码)。二维码是近几年发展起来的，它能在有限的空间内存储更多的信息，包括文字、图象、指纹、签名等，并可脱离计算机使用。
 * <p>
 * 我们通常所说的二维码，只是Matrix barcode的一种，叫做QR code。
 * <p>
 * 　　Barcode种类繁多，有些编码格式并不常用，即使是ZXing也没有做到所有格式的支持，开发者只需了解就好。
 * 其中包括：
 * <p>
 * 一维条码编码格式:
 * Code39码（标准39码）、Codabar码（库德巴码）、Code25码（标准25码）、ITF25码（交叉25码）、Matrix25码（矩阵25码）、UPC-A码、UPC-E码、
 * EAN-13码（EAN-13国际商品条码）、EAN-8码（EAN-8国际商品条码）、中国邮政码（矩阵25码的一种变体）、Code-B码、MSI码、Code11码、Code93码、ISBN码、ISSN码、
 * Code128码（Code128码，包括EAN128码）、Code39EMS（EMS专用的39码）等
 * <p>
 * 二维条码编码格式：
 * PDF417码、Code49码、Code 16k码、Date Matrix码、MaxiCode码(包括 QR Code码)等。
 * <p>
 * Code 39、Code 128、EAN、UPC、QR Code是我们生活中能经常见到的几种编码格式，同时ZXing对几种格式都有比较好的支持。
 * <p>
 * 其中，UPC-A是一种国际通用的编码格式，由12个数字构成，EAN-13是在UPC-A基础上的一种扩充(多了一个数字)。快数一数你身边的薯片的编码是不是13位!如果是的话，它最前边的两个数字是不是“69”?
 * <p>
 * 在EAN-13的编码体系中，前三位数字表示商品生产商的国家（并不是商品所属公司的国家），中国的编码是690~699，美国是（000~019、030~039、060~139），日本是（450~459、490~499），and so on。
 * <p>
 * 不同的编码格式通常用在不同的领域，如果你看到了一个Code 39或者Code 128的Barcode，那么这很就可能是一个快递编码，这个时候你就可以去那些提供快递查询的网站查询一下你的快递信息，如果有API提供出来那就更是再好不过了。
 * <p>
 * 至于QR Code，就是我们经常用手机扫一扫的二维码，表示的信息更是多种多样，并不仅仅是一个url那么简单
 */
public class EagleCodeUtil {

    // ============ 条形码的生成与解码(生成的条形码不显示 本身的条码含义，即：条码下方没有数字字母等。如有需要，自行拼接) =============

    /**
     * 条形码编码
     *
     * @param contents
     * @param width
     * @param height
     * @param imgPath
     */
    public static void encode(String contents, int width, int height, String imgPath) {
        //保证最小为70*25的大小
        int codeWidth = Math.max(70, width);
        int codeHeight = Math.max(25, height);
        try {
            //使用EAN_13编码格式进行编码
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.EAN_13, codeWidth, codeHeight, null);
            //生成png格式的图片保存到imgPath路径
            MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(imgPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析条形码
     *
     * @param imgPath
     * @return
     */
    public static String decode(String imgPath) {
        BufferedImage image;
        Result result;
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                throw new NormalException("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            result = new MultiFormatReader().decode(bitmap, null);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ====== 在web项目中很少会用到解码，一般而言是生成条形码后显示到页面或者打印出来，使用扫码枪扫码出来条形码的信息进行进一步的处理 ======

    /**
     * 根据字符串生成条形码
     *
     * @param code
     * @return
     */
    public static void getShapeCode(String code, String path) {
        // 编码条形码
        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "GBK");
        BitMatrix matrix;
        try {
            // 使用code_128格式进行编码生成100*80的条形码
            matrix = new MultiFormatWriter().encode(code, BarcodeFormat.CODE_128, 100, 80, hints);
            BufferedImage buff = MatrixToImageWriter.toBufferedImage(matrix);
            ImageIO.write(buff, "png", new File(path));
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    // =========== 二维码的生成与解码 =========

    /**
     * 生成二维码
     *
     * @param contents
     * @param width
     * @param height
     * @param imgPath
     */
    public static void encodeQR(String contents, int width, int height, String imgPath) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        // 指定显示格式为GBK
        hints.put(EncodeHintType.CHARACTER_SET, "GBK");
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
            //生成png格式的图片保存到imgPath路径位置
            MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(imgPath));
            System.out.println("QR Code encode success! the image's path is " + imgPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析二维码
     *
     * @param imgPath
     * @return
     */
    public static String decodeQR(String imgPath) {
        BufferedImage image;
        Result result;
        try {
            //读取图片
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                throw new NormalException("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
            //设置显示格式为GBK
            hints.put(DecodeHintType.CHARACTER_SET, "GBK");
            //进行解码
            result = new MultiFormatReader().decode(bitmap, hints);
            //返回结果信息
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // =========== 使用org.jbarcode 生成条形码(条形码下方包含  本身的含义，即：条码下方有数字字母等) ============

    /**
     * 支持EAN13, EAN8, UPCA, UPCE, Code 3 of 9, Codabar, Code 11, Code 93, Code 128,
     * MSI/Plessey, Interleaved 2 of PostNet等
     *
     * @param content
     * @param imgPath
     * @param with
     * @param height
     */
    public static void encode(String content, String imgPath, int with, int height) {
        try {
            JBarcode jBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN8TextPainter.getInstance());
            BufferedImage bufferedImage = jBarcode.createBarcode(content);
            FileOutputStream outputStream = new FileOutputStream(imgPath);
            ImageUtil.encodeAndWrite(bufferedImage, "jpeg", outputStream, with, height);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== barcode4j开源Java条形码生成库。支持多种编码格式，比如：code-39，code-128等 =============

    /**
     * 生成条形码，显示内容
     *
     * @param content 内容
     * @param path    路径
     */
    public static void encodeBarCode(String content, String path) {
        File file = new File(path);
        FileOutputStream ous;
        try {
            ous = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new NormalException(e.getMessage());
        }
        if (StringUtils.isBlank(content)) {
            throw new NormalException("the content can't be empty.");
        }
        Code39Bean bean = new Code39Bean();
        // 精细度
        final int dpi = 150;
        // module宽度
        final double moduleWidth = UnitConv.in2mm(1.0f / dpi);
        // 配置对象
        bean.setModuleWidth(moduleWidth);
        bean.setWideFactor(3);
        bean.doQuietZone(false);

        String format = "image/png";

        // 输出到流
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        // 生成条形码
        bean.generateBarcode(canvas, content);
        // 结束绘制
        try {
            canvas.finish();
        } catch (IOException e) {
            throw new NormalException(e.getMessage());
        }

    }


    public static void main(String[] args) {

        /*String encodeImgPath = "D:/2.png";
        String contents = "6923450657713";
        int width = 150, height = 40;
        EagleCodeUtil.encode(contents, width, height, encodeImgPath);
        String decodeImgPath = "D:/2.png";
        System.out.println(EagleCodeUtil.decode(decodeImgPath));*/

        /*getShapeCode("TG201603280003", "D://1.png");*/

        /*EagleCodeUtil.encodeQR("123456", 150, 150, "D:/二维码1.png");
        System.out.println(EagleCodeUtil.decodeQR("D:/二维码1.png"));*/

//        EagleCodeUtil.encode("110120231234", "D:/EAN8.jpg", 1000, 800);

        EagleCodeUtil.encodeBarCode("123456789", "D:/barcode.png");

    }

}
