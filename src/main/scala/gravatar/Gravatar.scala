package gravatar

import java.net.{URI, URLEncoder, URL}

/**
 * Immutable (thread safe) class used to generate Gravatar URLs.
 *
 * @author Morten Andersen-Gott - code@andersen-gott.com
 */
case class Gravatar(
  email: String,
  ssl: Boolean = false,
  forceDefault: Boolean = false,
  defaultImage: Option[DefaultImage] = None,
  rating: Option[Rating] = None,
  size: Option[Int] = None) {

  // Validation
  if(! size.forall(isValidSize))
    throw new IllegalArgumentException("Size must be positive and cannot exceed 2048")

  // Helpers
  val emailHash = Md5.hash(email.trim.toLowerCase)

  // Options
  def withSSL = copy(ssl = true)
  def withSize(size: Int) = copy(size = Some(size))
  def withRating(rating: Rating) = copy(rating = Some(rating))
  def withDefault(default: DefaultImage) = copy(defaultImage = Some(default))
  def andForceIt = copy(forceDefault = true)

  /**
   * Builds the Gravatar URL.
   *
   * @return gravatar URL as String
   */
  def url: String = {
    initUriBuilder.segments("avatar", emailHash)
      .queryParam("d", defaultImage.map(_.value))
      .queryParam("r", rating.map(_.value))
      .queryParam("s", size.map(_.toString))
      .build()
      .toString
  }

  /**
   * Download image.
   *
   * @return the image downloaded
   */
  def downloadImage: Array[Byte] = {
    Stream.continually(new URL(url).openStream.read).takeWhile(-1 !=).map(_.toByte).toArray
  }

  // Helper
  private def initUriBuilder: URIBuilder = {
    val gravatarBase = "www.gravatar.com"
    val gravatarSSLBase = "secure.gravatar.com"
    val urlBuilder =
      if(ssl) URIBuilder.empty.withHost(gravatarSSLBase).withScheme("https")
      else URIBuilder.empty.withHost(gravatarBase).withScheme("http")
    if(forceDefault)
      urlBuilder.queryParam("forcedefault","y")
    else urlBuilder
  }

  // Helpers
  private def isValidSize(size: Int) = size > 0 && size <= 2048

}

// Image
sealed abstract class DefaultImage(val value: String)
case object Monster extends DefaultImage("monsterid")
case object MysteryMan extends DefaultImage("mm")
case object IdentIcon extends DefaultImage("identicon")
case object Wavatar extends DefaultImage("wavatar")
case object Retro extends DefaultImage("retro")
case object FourOFour extends DefaultImage("404")
case class CustomImage(url: String) extends DefaultImage(URLEncoder.encode(URI.create(url).toString, "UTF-8"))

object DefaultImage{
  def apply(value: String): DefaultImage = value match {
    case Monster.value    => Monster
    case MysteryMan.value => MysteryMan
    case IdentIcon.value  => IdentIcon
    case Wavatar.value    => Wavatar
    case Retro.value      => Retro
    case FourOFour.value  => FourOFour
    case x                => CustomImage(x)
  }
}

// Rating
sealed abstract class Rating(val value: String)
case object G extends Rating("g")
case object PG extends Rating("pg")
case object R extends Rating("r")
case object X extends Rating("x")

object Rating{
  def apply(value: String): Rating = value match{
    case G.value  => G
    case PG.value => PG
    case R.value  => R
    case X.value  => X
    case x        => throw new IllegalArgumentException(x + " is not a valid rating")
  }
}

