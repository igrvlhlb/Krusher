package krusher

import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam

object KrusherImpl: Krusher {
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
            compose(newImg) { toRgb() ; compress(quality) }
        }
}