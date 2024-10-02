package com.igrvlhlb

import krusher.KrusherImpl
import krusher.image.KrusherImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    val src = File(args[0])
    val dst = File(args[1])
    println("input: ${src.path}; output: ${dst.path}")
    val img = ImageIO.read(src)
    val processed = KrusherImage(img)
        .setKrusher(KrusherImpl)
        .process(args[2].toInt())
        .crush()
    ImageIO.write(processed, "jpeg", dst)
}