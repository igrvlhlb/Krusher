package krusher

import com.sksamuel.scrimage.ImmutableImage
import krusher.imageio.KrusherImpl
import krusher.scrimage.KrusherScr
import java.awt.image.BufferedImage
import java.io.File

sealed class KrusherImage<T>(img: BufferedImage, private val krusher: Krusher<T>): Krusher<T> by krusher {
    private var image: T = krusher.fromAwt(img)

    fun crush() = toAwt(image)
    fun toRgb() = apply { toRgb(image).commit() }
    fun compress(quality: ImgQuality) = apply { compress(image, quality).commit() }
    fun process(iterations: Int) = apply { process(image, iterations).commit() }
    fun write(file: File) = apply { write(image, file) }

    protected fun T.commit() {
        image = this
    }

    companion object {
        fun create(img: BufferedImage, type: KrusherType = KrusherType.IMAGEIO): KrusherImage<out Any> {
            return when (type) {
                KrusherType.IMAGEIO -> KrusherImageDefault(img)
                KrusherType.SCRIMAGE -> KrusherImageScr(img)
            }
        }
    }

    enum class KrusherType {
        IMAGEIO, SCRIMAGE
    }
}

class KrusherImageDefault(img: BufferedImage) : KrusherImage<BufferedImage>(img, KrusherImpl)
class KrusherImageScr(img: BufferedImage) : KrusherImage<ImmutableImage>(img, KrusherScr)

