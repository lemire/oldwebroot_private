#!/usr/bin/env python

import re

def removeHTML(s):
  #s=re.sub("&","&amp;",s)
  s=re.sub("<","<!--",s)
  s=re.sub(">","-->",s)
  s=re.sub("\\n"," \\n ",s)
  #s=re.sub("\"","&quot;",s)
  return s

language=["fr","en"]
description=["""Daniel Lemire s'intéresse particulièrement au forage de données, aux grandes bases de données (OLAP) ainsi
  qu'au filtrage collaboratif. """,""" Daniel Lemire is especially interested in Data Mining, very large databases (OLAP) as
  well as in collaborative filtering. """]

news_title= ["Nouvelles concernant Daniel Lemire, chercheur et professeur à l'UQÀM ","News from Daniel Lemire, researcher and professor at UQAM"]
news_url= ["http://www.daniel-lemire.com/fr/nouvelles.html","http://www.daniel-lemire.com/en/news.html"]
publications_title= ["Publications choisies par Daniel Lemire, chercheur et professeur à l'UQÀM ","Significant publications by Daniel Lemire, researcher and professor at UQAM"]
publications_url= ["http://www.daniel-lemire.com/fr/publications.html","http://www.daniel-lemire.com/en/publications.html"]



def parseNews(filein,fileout,lang=0):
  out= open(fileout,"w")
  out.write("""<?xml version="1.0" encoding="ISO-8859-1"  ?>
  <?xml-stylesheet title='RSS_Formatted' type='text/xsl' href='http://www.daniel-lemire.com/fr/rssit.xsl'?>
  <!DOCTYPE rss PUBLIC "-//Netscape Communications//DTD RSS 0.91//EN" "http://my.netscape.com/publish/formats/rss-0.91.dtd">

  <rss version="0.91">

  <channel>

  <description>%s</description>

  <language>%s</language>

  <title>%s</title>
  <link>%s</link>

  """ %(description[lang],language[lang],news_title[lang],news_url[lang]))
  f = open(filein,"r")
  data = f.read()
  f.close()
  p = re.compile(r'<li>.*?<p>(.*?)</p>.*?(<p.*?>.*?)</li>',re.IGNORECASE + re.MULTILINE + re.DOTALL)
  count = 0
  for m in p.finditer(data):
    out.write("<item>\n")
    out.write("\t<title> "+removeHTML(m.groups()[0])+"</title>\n")
    out.write("\t<description>"+removeHTML(m.groups()[1])+"</description>\n")
    out.write("\t<link>"+news_url[lang]+"</link>\n")
    out.write("</item>\n\n\n")
    count = count + 1
    if(count > 15): break
  out.write("""
  </channel>
  </rss>""")
  out.close()

def parsePublications(filein,fileout,lang=0) :
  out= open(fileout,"w")
  out.write("""<?xml version="1.0" encoding="ISO-8859-1"  ?>
  <?xml-stylesheet title='RSS_Formatted' type='text/xsl' href='http://www.daniel-lemire.com/fr/rssit.xsl'?>
  <!DOCTYPE rss PUBLIC "-//Netscape Communications//DTD RSS 0.91//EN" "http://my.netscape.com/publish/formats/rss-0.91.dtd">

  <rss version="0.91">

  <channel>

  <description>%s</description>

  <language>%s</language>

  <title>%s</title>
  <link>%s</link>

  """ %(description[lang],language[lang],publications_title[lang],publications_url[lang]))
  f = open(filein,"r")
  data = f.read()
  f.close()
  count = 0
  p = re.compile(r'<li>([^<>\(]*?)<a.*?href.*?=.*?"(.*?)">(.*?)</a>(.*?)</li>',re.IGNORECASE + re.MULTILINE + re.DOTALL)
  for m in p.finditer(data):
    matches = m.groups()
    authors = matches[0]
    url = matches[1]
    if(not url.find("abstracts")): break
    print "Url : " , url
    title = matches[2]
    rest = matches[3]
    out.write("<item>\n")
    out.write("\t<title>"+removeHTML(title+rest)+"</title>\n")
    out.write("\t<description>"+getAbstract(url)+"</description>\n")
    out.write("\t<link>"+url+"</link>\n")
    out.write("</item>\n\n\n")
    count = count + 1
    if(count > 12): break
  out.write("""
  </channel>
  </rss>""")
  out.close()

def getAbstract(url):
  splitted = url.split("/")
  f = open("abstracts/"+splitted[len(splitted)-1],"r")
  data = f.read()
  f.close()
  return removeHTML(re.search("<p>.*?</p>",data,re.IGNORECASE+re.DOTALL+re.MULTILINE).group())

if(__name__=='__main__'):
  parseNews("../acadia/news.html","../acadia/news.rss",1)  
  parsePublications("../acadia/publications.html","../acadia/publications.rss",1)
  parseNews("nouvelles.html","nouvelles.rss",0)  
  parsePublications("publications.html","publications.rss",0)

