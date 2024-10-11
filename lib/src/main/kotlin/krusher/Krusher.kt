package krusher

import org.checkerframework.common.value.qual.IntRange
import java.awt.image.BufferedImage
import java.io.File

typealias ImgQuality = @IntRange(from = 0, to = 100) Int

interface Krusher<T> {
    fun toRgb(img: T): T
    fun compress(img: T, quality: ImgQuality): T
    fun process(img: T, iterations: Int): T
    fun write(img: T, file: File)
    fun fromAwt(img: BufferedImage): T
    fun toAwt(img: T): BufferedImage
}
