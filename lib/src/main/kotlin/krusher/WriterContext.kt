package krusher

import java.awt.image.BufferedImage
import java.io.OutputStream
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import javax.imageio.ImageWriter

fun writerContext(outputStream: OutputStream, block: ImageWriter.() -> Unit) {
    val outStream = ImageIO.createImageOutputStream(outputStream)
    val writers = ImageIO.getImageWritersByFormatName("jpeg")

    if (!writers.hasNext()) throw IllegalStateException("No writers found")

    val writer = writers.next()

    writer.setOutput(outStream)

    writer.block()

    writer.dispose()
    outStream.close()
}

fun ImageWriter.write(img: BufferedImage, params: ImageWriteParam) {
    return write(null, IIOImage(img, null, null), params)
}