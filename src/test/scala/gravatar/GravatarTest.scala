package gravatar

import java.io.FileOutputStream

import org.scalatest.FunSuite

class GravatarTest extends FunSuite {

  val email = "morten@andersen-gott.com"

  test("Simple Avatar URL") {
    val gravatar = Gravatar(email).withSSL.withDefault(Monster)
    assert(gravatar.defaultImage.isDefined)
  }

  test("All props are combined") {
    val gravatar = Gravatar(email).withSSL.withSize(100).withRating(R).withDefault(Monster).andForceIt.url
    assert(gravatar.contains("=monster"))
  }

  test("Download") {
    val fos = new FileOutputStream("//tmp/pic.jpg")
    fos.write(Gravatar(email).downloadImage)
  }

  test("Fails if size > 2048") {
    intercept[IllegalArgumentException] {
      Gravatar(email).withSize(2049)
    }
  }

  test("Fails if size < 0 ") {
    intercept[IllegalArgumentException] {
      Gravatar(email).withSize(-1)
    }
  }

  test("E-mail is trimmed and lower cased ") {
    val target = Gravatar(email)
    val upper  = Gravatar(email.toUpperCase)
    val space  = Gravatar(s" $email ")
    assert(target.emailHash == upper.emailHash)
    assert(target.emailHash == space.emailHash)
  }

  test("URL is encoded"){
    val target = Gravatar(email).withDefault(DefaultImage("http://example.com/image.jpg"))
    assert (target.defaultImage.get.value == "http%3A%2F%2Fexample.com%2Fimage.jpg")
  }

  test("URL is validated") {
    intercept[IllegalArgumentException]{
      DefaultImage("http://example.com}")
    }
  }


}
