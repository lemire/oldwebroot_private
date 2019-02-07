#!/usr/bin/python
# -*- coding: UTF-8 -*-
import urllib2,re,time
f= urllib2.urlopen("http://www.teluq.ca/siteweb/actualites/pilot/actualites.js")
entries= []
def removeHTML(s):
  s=re.sub("&","&amp;",s)
  s=re.sub("<","<!--",s)
  s=re.sub(">","-->",s)
  s=re.sub("\"","&quot;",s)
  s=re.sub("'","&apos;",s)
  return s
for line in f:
  m=re.match('.*add\("(.*?)","(.*?)","(.*?)","(.*?)","(.*?)".*',line)
  if(m <> None):
    madate = m.group(1)
    if(len(madate)==0): continue
    montime = time.strptime(madate,"%d-%m-%Y")
    montimestr = time.strftime("%a, %d %b %Y",montime)
    typedenews = m.group(2)
    montitre = m.group(3)
    jesaispastrop = m.group(4)
    monuri = m.group(5)
    if(not monuri.startswith('http')):
      monuri= 'http://www.teluq.uqam.ca'+monuri
    montitre = re.sub("\\\\","",montitre)
    montitre = re.sub("<.*?>","",montitre)
    if(len(montitre)==0): continue
    print montitre
    entries.append([montime,removeHTML(montitre),montimestr,removeHTML(monuri)])
entries.sort()
entries.reverse()
out = open("teluq.rss","w")
out.write("""<?xml version="1.0" encoding="ISO-8859-1"  ?>
  <?xml-stylesheet title='RSS_Formatted' type='text/xsl' href='http://www.daniel-lemire.com/fr/rssit.xsl'?>
  <!DOCTYPE rss PUBLIC "-//Netscape Communications//DTD RSS 0.91//EN" "http://my.netscape.com/publish/formats/rss-0.91.dtd">
  <rss version="0.91">
  <channel>
  <description />
  <language>fr</language>
  <title>Nouvelles de la TELUQ (UQAM)</title>
  <link>http://www.teluq.uqam.ca/</link>
  """)
for key in entries:
    out.write("""<item> 
    <title>%s</title>
    <pubDate>%s</pubDate>
    <description />
    <link>%s</link></item>
    """ %(key[1],key[2],key[3]))
out.write("</channel></rss>")
out.close()














