package com.igrvlhlb

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.enum
import com.github.ajalt.clikt.parameters.types.int
import com.sksamuel.scrimage.ImmutableImage
import krusher.Krusher
import krusher.imageio.KrusherImpl
import krusher.KrusherImage
import krusher.scrimage.KrusherScr
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    try {
        KrusherDemo().main(args)
    } catch (e: IOException) {
        println(e.message)
    }
}

class KrusherDemo: CliktCommand() {
    val src: File by argument("source")
        .help("Source image path")
        .convert { File(it) }
    val dst: File by argument()
        .help("Destination image path")
        .convert { File(it) }
    val iter: Int by option("-i")
        .help("Number of iterations")
        .int()
        .default(50)
    val impl: KImpl by option("--krusher", "-k")
        .enum<KImpl> { it.value }
        .default(KImpl.DEFAULT)

    override fun run() {
        println("input: ${src.path}; output: ${dst.path}")
        val img = tryReadImage(src)
        val krusherType = when (impl) {
            KImpl.SCRIMAGE -> KrusherImage.KrusherType.SCRIMAGE
            KImpl.DEFAULT -> KrusherImage.KrusherType.IMAGEIO
        }
        val krusherImg = KrusherImage.create(img, krusherType)
        val crushed = krusherImg.process(iter).crush()
        ImageIO.write(crushed, "jpeg", dst)
    }

    fun tryReadImage(file: File): BufferedImage =
        try {
            ImageIO.read(file)
        } catch (e: IOException) {
            throw IOException("${e.message} [${file.absolutePath}]")
        }
}

enum class KImpl(val value: String) {
    SCRIMAGE("scr"), DEFAULT("default")
}