# Crawler

---

a scala project for crawling and scraping websites that utilizes the Jsoup library at it's core



```scala
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
```

the main function of the project, it takes a list of websites to be crawled, this list is also expanded with the links in each web page and doesn't end until all the sites are crawled or until a ccertan number is reached

scraping is done with a function that is supplied to the function which donverts a document to an Option of type A, A is any type that we want the data that is parsed from the web page to presented as

finally before returning the function removes the None values from the result


