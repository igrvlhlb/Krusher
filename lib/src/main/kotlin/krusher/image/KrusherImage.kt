package krusher.image

import krusher.*
import java.awt.image.BufferedImage

class KrusherImage(private var image: BufferedImage, krusher: Krusher = KrusherImpl): KrusherComposer {
    var krusherScope: KrusherScope = KrusherScope(krusher, image)
    fun setKrusher(krusher: Krusher) = apply { krusherScope = KrusherScope(krusher, image) }
    override fun toRgb(): KrusherComposer = apply { krusherScope.toRgb() }

    override fun compress(quality: ImgQuality) = apply { krusherScope.compress(quality) }

    override fun process(iterations: Int) = apply { krusherScope.process(iterations) }

    fun crush() = krusherScope.bufferedImage
}