package krusher.scrimage

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.filter.RGBFilter
import com.sksamuel.scrimage.nio.ImageWriter
import com.sksamuel.scrimage.nio.JpegWriter
import krusher.ImgQuality
import krusher.Krusher
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.logging.Logger

object KrusherScr: Krusher<ImmutableImage> {
    override fun toRgb(img: ImmutableImage): ImmutableImage {
        return img.filter(RGBFilter())
    }

    override fun compress(img: ImmutableImage, quality: ImgQuality): ImmutableImage {
        val writer = JpegWriter().withCompression(100 - quality)
        return ImmutableImage.loader().fromStream(ByteArrayInputStream(img.bytes(writer)))
    }

    override fun process(img: ImmutableImage, iterations: Int): ImmutableImage =
        (1 .. iterations).fold(img) { newImg, iteration ->
            val pct = 100 * iteration / iterations
            val quality = 100 - pct
            compress(newImg, quality)
        }

    override fun write(img: ImmutableImage, file: File) {
        val writer = JpegWriter()
        img.output(writer, file)
    }

    override fun fromAwt(img: BufferedImage): ImmutableImage = ImmutableImage.fromAwt(img)

    override fun toAwt(img: ImmutableImage): BufferedImage = img.awt()
}