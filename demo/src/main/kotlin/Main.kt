package com.igrvlhlb

import krusher.KrusherImpl
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val img = ImageIO.read(File("input/tmp.jpeg"))
    val processed = KrusherImpl.process(img, 40)
    ImageIO.write(processed, "jpeg", File("output.jpeg"))
}