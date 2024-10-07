package krusher

import krusher.imageio.KrusherImpl
import krusher.scrimage.KrusherScr
import java.awt.image.BufferedImage
import java.io.File

class KrusherImage<T>(img: BufferedImage, val krusher: Krusher<T>): KrusherComposer {

    private var image = krusher.fromAwt(img)

    private val krusherScope = KrusherScope(krusher, image)

    override fun toRgb(): KrusherComposer = apply { krusherScope.toRgb() }

    override fun compress(quality: ImgQuality) = apply { krusherScope.compress(quality) }

    override fun process(iterations: Int) = apply { krusherScope.process(iterations) }

    fun write(file: File) {
        krusherScope.write(image, file)
    }

    fun crush() = krusher.toAwt(krusherScope.image)

    companion object {
        fun create(img: BufferedImage, type: KrusherType): KrusherImage<out Any> {
            return when (type) {
                KrusherType.IMAGEIO -> KrusherImage(img, KrusherImpl)
                KrusherType.SCRIMAGE -> KrusherImage(img, KrusherScr)
            }
        }
    }

    enum class KrusherType {
        IMAGEIO, SCRIMAGE
    }
}