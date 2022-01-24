package app

import com.redis.RedisClient

import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.imageio.ImageIO

object Main extends App {
  val redisClient = new RedisClient("localhost", 6379)

  val now = LocalDateTime.now
  val formattedNow = now.format(DateTimeFormatter.ISO_DATE_TIME)

  val image = new BufferedImage(512, 512, BufferedImage.TYPE_3BYTE_BGR)
  val g = image.createGraphics()
  g.drawString(s"こんにちは！こんにちは！現在の日時は${formattedNow}です！", 10, 100)

  val baos = new ByteArrayOutputStream()
  ImageIO.write(image, "png", baos)

  redisClient.set("my-pic", baos.toByteArray)

  val myPic = redisClient.get("my-pic")

  redisClient.close()

  // 正しく保存されているかファイルに書き出してみる
  // redis-cli get my-pic > my-pic.png
}
