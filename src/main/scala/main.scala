import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

import scala.annotation.tailrec
import scala.collection.mutable
import scala.util.Try

/**
 * a function for parsing a web document and get the elements in the format you want
 * @param doc web document
 * @return an Option of the result of the parsing or None if the result is empty
 */
def scraper(doc : Document) : Option[String] = ???

@tailrec
def crawler[A](sites : mutable.Queue[String], res : Map[String, Option[A]], f : Document => Option[A], i : Int = 1000) : Map[String, A] =
  if i == 0 then res.collect{case (a, Some(b)) => (a, b)}
  else if sites.isEmpty then res.collect{case (a, Some(b)) => (a, b)}
  else {
    val site = sites.dequeue()
    val visited = if res.contains(site) then res else {
      val doc = Try(Jsoup.connect(site).get)
      if doc.isFailure then res else {
        val links = doc.get.getElementsByTag("a[href]")
        links.forEach(zz => sites.enqueue(zz.attr("abs:href")))
        res + (site -> f(doc.get))
      }
    }
    crawler(sites, visited, f, i - 1)
  }

@main
def main(): Unit = {
  val sites = mutable.Queue[String]()
  sites.enqueue("https://www.site.com")
  val result = crawler(sites, Map(), scraper)
}