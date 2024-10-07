package krusher.imageio

import krusher.ImgQuality
import krusher.Krusher
import krusher.write
import krusher.writerContext
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam

object KrusherImpl: Krusher<BufferedImage> {

    init {
        ImageIO.setUseCache(false)
    }

    override fun toRgb(img: BufferedImage): BufferedImage {
        val rgbImg = BufferedImage(img.width, img.height, TYPE_INT_RGB)
        rgbImg.createGraphics().drawImage(img, 0, 0, null)
        return rgbImg
    }

    override fun compress(img: BufferedImage, quality: ImgQuality): BufferedImage {
        val byteOutStream = ByteArrayOutputStream()

        writerContext(byteOutStream) {
            val param = defaultWriteParam

            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(quality / 100.0f);
            } else {
                throw IllegalStateException("Cannot write compressed")
            }

            write(img, param)
        }

        val compressed = ImageIO.read(byteOutStream.toByteArray().inputStream())

        return compressed
    }

    override fun process(img: BufferedImage, iterations: Int) =
        (0..iterations).fold(img) { newImg, iteration ->
            val pct = 100 * iteration / iterations
            val quality = 100 - pct
            compress(newImg, quality)
        }

    override fun write(img: BufferedImage, file: File) {
        ImageIO.write(img, "jpeg", file)
    }

    override fun fromAwt(img: BufferedImage) = img

    override fun toAwt(img: BufferedImage) = img
}