/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package krusher

import org.checkerframework.common.value.qual.IntRange
import java.awt.image.BufferedImage

typealias ImgQuality = @IntRange(from = 0, to = 100) Int

interface Krusher {
    fun toRgb(img: BufferedImage): BufferedImage
    fun compress(img: BufferedImage, quality: ImgQuality): BufferedImage
    fun process(img: BufferedImage, iterations: Int): BufferedImage
    fun compose(img: BufferedImage, block: KrusherComposer.() -> Unit): BufferedImage =
        KrusherScope(this, img).run { block(); bufferedImage }
}

interface KrusherComposer {
    fun toRgb(): KrusherComposer
    fun compress(quality: ImgQuality): KrusherComposer
    fun process(iterations: Int): KrusherComposer
}

abstract class KrusherComposerAbs(var bufferedImage: BufferedImage, protected var krusher: Krusher): KrusherComposer {
    protected fun BufferedImage.commit() {
        bufferedImage = this
    }
}

class KrusherScope(obj: Krusher, bufferedImage: BufferedImage): Krusher by obj, KrusherComposerAbs(bufferedImage, obj)  {
    override fun toRgb() = apply { toRgb(bufferedImage).commit() }

    override fun compress(quality: ImgQuality) = apply { compress(bufferedImage, quality).commit() }

    override fun process(iterations: Int) = apply { process(bufferedImage, iterations).commit() }
}

