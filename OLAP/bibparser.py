#!/usr/bin/python
# -*- coding: ISO-8859-1 -*-
# bibparser.py
# This is a module that goes through a bib file and parses each entry
#
# This library requires the _bibtex module from the python-bibtex library
#
# Daniel Lemire, Ph.D.
# February 4th 2003

import _bibtex
import time
import cgi
import re

def parse(bibfilename, strict = 0):
  print "opening bibtex file ", bibfilename
  file = _bibtex.open_file(bibfilename,strict)
  entry = _bibtex.next(file) 
  counter = 0
  answer = {}
  while(entry <> None):
    counter += 1
    key = entry[0] # such as lemire-kaser2003
    type = entry[1] #article, proc...
    items = entry[4]
    myentry = {}
    myentry['type']=type
    for itemkey in items.keys():
      myentry[itemkey.lower()] = cgi.escape(_bibtex.expand(file, items[itemkey],-1)[2])
    answer[key]= myentry
    entry = _bibtex.next(file)
  print "parsed ", counter, " entries"
  return answer

def toHTML(bibfilename,output):
  entries = parse(bibfilename)
  print dir(output)
  output.write('<ul>\n')
  for entry in entries.values():
    print entry
    output.write('\t<li>'+entry['author']+'</li>\n')
  output.write('</ul>\n')
    

def byTopic(entries):
  bytopic = {}
  for entry in entries.values():
    try:
      if(not bytopic.has_key(entry['topic'])) :
        bytopic[entry['topic']] = {}
      if(not bytopic[entry['topic']].has_key(entry['subtopic'])):
        bytopic[entry['topic']][entry['subtopic']] = []
    except KeyError :
			print "Make sure topic and subtopic appears"
			print entry 
    bytopic[entry['topic']][entry['subtopic']].append(entry)
  return bytopic
def byDate(entries):
  bydate = {}
  for entry in entries.values():
    try:
      if(not bydate.has_key(entry['year'])) :
        bydate[entry['year']] = []
      bydate[entry['year']].append(entry)
    except KeyError :
			print "No year entry"
			#print entry 
  return bydate
def byAuthor(entries):
  byauthor = {}
  def extractlastname(name1):
    try:
      name1.index(",")
      sp= name1.split(",")
      sp = filter(lambda x:len(x) <> 0,sp)
      return sp[0]
    except ValueError: pass
    sp= name1.split(" ")
    sp = filter(lambda x:len(x) <> 0,sp)
    return sp[len(sp)-1]
  for entry in entries.values():
    try:
      list = entry["author"].split(" and ")
      for auth in map(extractlastname,list):
        if(not byauthor.has_key(auth)) :
          byauthor[auth] = []
        byauthor[auth].append(entry)
    except KeyError :
			print "No author entry"
			#print entry 
  return byauthor

def writeContent(bytopic,output,tag,content=0):
  topiccounter = 1
  output.write("<ol style='list-style: upper-roman outside; margin-top:0.5em;margin-bottom:0.2em;'>\n")
  def sorter(l):
    l.sort()
    return l
  for topic in sorter(bytopic.keys()):
    output.write("\t<li style='font-weight:bold'>\n")
    output.write("\t\t<a "+tag+"=\"")
    if(tag == "href"): output.write("#")
    output.write(str(topiccounter)+"\">")
    output.write(topic)
    topiccounter = topiccounter + 1
    output.write("</a>\n")
    output.write("\t\t<ol style='font-weight:normal; margin-top:0.5em;margin-bottom:0.2em;'>\n")
    subtopiccounter = 1
    for subtopic in sorter(bytopic[topic].keys()) :
      output.write("\t\t\t<li>")
      output.write("<a "+tag+"=\"")
      if(tag == "href"): output.write("#")
      output.write(str(topiccounter)+"_"+str(subtopiccounter)+"\"")
      if(tag == "name"): output.write(" style='background-color: #ffc; border:black solid 0.1em; ' ")
      output.write(" >")
      output.write(subtopic+"</a>")
      if(tag == "href"): 
        subtopicnumber = len(bytopic[topic][subtopic])
        output.write(" - <i>"+str(subtopicnumber))
        if(subtopicnumber > 1):
          output.write(" entries")
        else: 
          output.write(" entry")
        output.write("</i>")
      if(content): displayEntries(bytopic[topic][subtopic],output)    
      subtopiccounter = subtopiccounter + 1
      output.write("</li>\n")      
    output.write("\t\t</ol>\n")
    output.write("\t</li>\n")
  output.write("</ol>\n")
def writeContentByDate(bydate,output,tag,content=0):
  topiccounter = 1
  output.write("<ul style='margin-top:0.5em;margin-bottom:0.2em;'>\n")
  def sorter(l):
    l.sort()
    l.reverse()
    return l
  for topic in sorter(bydate.keys()):
    output.write("\t<li style='font-weight:bold'>\n")
    output.write("\t\t<a "+tag+"=\"")
    if(tag == "href"): output.write("#")
    output.write(str(topiccounter)+"\">")
    output.write(topic)
    topiccounter = topiccounter + 1
    output.write("</a>\n")
    if(content): displayEntries(bydate[topic],output)    
  output.write("</ul>\n")
def writeContentByAuthor(byauthor,output,tag,content=0):
  topiccounter = 1
  output.write("<ol style='margin-top:0.5em;margin-bottom:0.2em;'>\n")
  def comp(name1,name2):
    splitted1 = name1.split(" ")
    splitted2 = name2.split(" ")
    return splitted1[len(splitted1)-1] < splitted2[len(splitted2)-1]
  def sorter(l):
    l.sort()
    return l
  def extractlastname(name1):
    try:
      sp=name1.split(",")
      sp = filter(lambda x:len(x) <> 0,sp)
      #if(len(sp[0]) <= 2):
      #  raise str(('comma',sp[0],name1,byauthor[name1]))
      return sp[0]
    except ValueError: pass
    sp= name1.split(" ")
    sp = filter(lambda x:len(x) <> 0,sp)
    #if(len(sp[len(sp)-1]) <= 2):
    #    raise str( ("nocomma",sp[len(sp)-1],name1,byauthor[name1]))
    return sp[len(sp)-1]
  for topic in sorter(map(extractlastname,byauthor.keys())):
    output.write("\t<li style='font-weight:bold'>\n")
    output.write("\t\t<a "+tag+"=\"")
    if(tag == "href"): output.write("#")
    output.write(str(topiccounter)+"\">")
    output.write(topic)
    topiccounter = topiccounter + 1
    output.write("</a>\n")
    if(content): displayEntries(byauthor[topic],output)    
  output.write("</ol>\n")

def displayEntries(entries,output):
  output.write("\t\t\t<ul style='list-style-type: circle; margin-right:5em;margin-top:0.5em;margin-bottom:1em;'>\n")
  def sortbyyear(e1,e2): 
    if(e1.has_key("year") and e2.has_key("year")):
      y1= int(e1["year"])
      y2= int(e2["year"])
      return y2-y1
    else:
      if(e1.has_key("year")): return -1
      if(e2.has_key("year")): return +1
      return e1["title"] > e2["title"]
  entries.sort(sortbyyear)
  for entry in entries: 
    output.write("\t\t\t\t<li>")
    display(entry,output)
    output.write("</li>\n")
  output.write("\t\t\t</ul>\n")

def preprocesstext(text):
  return re.sub("--","&#151;",text)

def display(entry,output):
  """ author, url, title, booktitle, journal, institution, year"""
  if(entry.has_key("author")): 
    list = entry["author"].split(" and ")
    if(len(list) == 1) : 
      output.write(list[0].strip())
    elif(len(list) == 2) :
      output.write(list[0].strip()+" and "+ list[1].strip())
    else :
      for auth in list[0:len(list)-1]:
        output.write(auth+", ")
      output.write(" and "+ list[len(list)-1].strip())
    output.write(", ")
  if(entry.has_key("url")):
    if(entry["url"].find("lemire")==-1):
      output.write("<a href=\""+entry["url"]+"\" rel =\"nofollow\" >")
    else:
      output.write("<a href=\""+entry["url"]+"\"  >")
  if(entry.has_key("chapter")):
    output.write(entry["chapter"]+" in ")
  if(entry.has_key("title")):
    output.write(preprocesstext(entry["title"]))
  if(entry.has_key("url")):
    output.write("</a>")
  if(entry.has_key("booktitle")):
    output.write(",  "+entry["booktitle"])
  if(entry.has_key("journal")):
    output.write(", "+entry["journal"])
  if(entry.has_key("institution")):
    output.write(", "+entry["institution"])
  if(entry.has_key("volume")):
    if(entry.has_key("number")):
      output.write(", <b>"+entry["volume"]+"</b>")
    else:
      output.write(", vol. "+entry["volume"])
  if(entry.has_key("number")):
    if(entry.has_key("volume")): 
      output.write(":"+entry["number"])
    else:
      output.write(", "+ entry["number"])
  if(entry.has_key("pages")):
    output.write(", "+preprocesstext(entry["pages"]))
  if(entry.has_key("year")):
    output.write(", "+entry["year"])
  if(entry.has_key("note")):
    output.write(" ("+entry["note"]+")")
  output.write(".") 


defaultheader = """
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
      <title>
        Data Warehousing and OLAP Bibliography
      </title>
            <style type="text/css">#rss * {font-size:1em;margin-left:0em;}
            #rss li {margin-top:0.5em;margin-left:0em;list-style-type:decimal;}
.RSS {
 font-family: arial, helvetica;
 font-size: 0.6em;
 font-weight: bold;
 text-decoration: none;
 border: 1px solid;
 padding: 0px 2px 0px 2px;
 margin: 0px;
 vertical-align: top;
 }


.RSS, .RSS:link, .RSS:visited, .RSS:active {
 color: #FF9966;
 background-color: white;
 border-color: #FF9966;
 }


.RSS:hover {
 color: white;
 background-color: #FF6600;
 border-color: #FFC8A4 #7D3302 #3F1A01 #FF9A57;
 }
</style>
      <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

</head>
    <body text="#000000" bgcolor="#ffffff" link="#0000ee" vlink="#990000" alink="#ff6633" style="width:600px;margin-left:auto;margin-right:auto;">



    
    <h1> Data Warehousing and OLAP  </h1>
    <h2> A Research-Oriented Bibliography  </h2>

</div>
<script type="text/javascript">displayRSS("http://www.daniel-lemire.com/blog/archives/category/information-technology/data-warehousing-and-olap/feed/")</script>
<div style="width:300px\">
    <p>A comprehensive list of Data Warehouse and OLAP papers (with links to full papers), links, and conferences.</p>
    <p>Browse by <a href="dwbib.html" rel =\"nofollow\">Topic</a> (default), by <a href="dwbib_date.html" rel =\"nofollow\">Date</a> or by <a href="dwbib_author.html" rel =\"nofollow\">Author's last name</a>.</p>
    <p><a href="#sendupdates">Please submit your papers and links!</a></p>
    </div>
    """
defaultfooter= """
    <hr />
        <h2> About this bibliography </h2>
        <p>
          This bibliography was previously maintained by <a
          href="http://www.cs.toronto.edu/%7Emendel" rel =\"nofollow\">Alberto
          Mendelzon</a> up until 1997, then by <a
          href="http://www.dcc.uchile.cl/~churtado/" rel =\"nofollow\">Carlos
          Hurtado</a> up until 2001.
          It is currently maintained by <a
          href="http://www.daniel-lemire.com/en/">Daniel Lemire</a>
          (professor, Université du Québec à Montréal, UQAM).
          
	  
	  This bibliography is dedicated to Alberto Mendelzon who 
	  died in 2005.
	  </p>
                 <p>Further reading:  <a href="http://scholar.google.com/scholar?num=100&amp;hl=en&amp;lr=&amp;q=link:yZeZieQtzToJ:scholar.google.com/" rel =\"nofollow\">papers citing "Data warehousing and OLAP: a research-oriented bibliography by A. Mendelzon, C. Hurtado, and D. Lemire"</a> according Google Scholar.</p>
 <h2>Can I get the BibTeX file?</h2>
        <p>
          You can download the <a href="dw.bib" rel =\"nofollow\">bibtex file here</a>.</p>
        <br />
        <h2> <a name="sendupdates">Help us keep this bibliography current and complete!</a> </h2>
 
        <p>Please send comments or updates to 
          <a href="mailto:lemireATacmDOTorg">lemireATacmDOTorg</a>.
          Please include links to PDF files when applicable.
          We try to link mostly to freely available resources. New conference
          or journal papers are especially welcome. Please include bibtex 
          together with suggested classification in the taxonomy. Submitting 
          your own papers to the list is <strong>encouraged</strong>.</p>
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
  <p>
      <a href="http://validator.w3.org/check?uri=referer" rel =\"nofollow\"><img
          src="http://www.w3.org/Icons/valid-xhtml10"
          alt="Valid XHTML 1.0!" height="31" width="88" /></a>
    </p>
      </body>
    </html>"""
header = {"bytopic":defaultheader, "bydate":defaultheader,"byauthor":defaultheader, "rss":defaultheader}

def toCleanHTML(bibfilename, output, layout="bytopic"):
    entries = parse(bibfilename)
    if(layout == "bytopic"):
      bytopic = byTopic(entries)
    elif(layout == "bydate"):
      bydate = byDate(entries)
    elif(layout == "byauthor"):
      byauthor = byAuthor(entries)
    output.write(header[layout])
    output.write("<p style=\"width:250px;color:#888;font-weight:bold\">Number of links: "+str(len(entries))+",  last updated on "+
    time.strftime('%B  %Y')+". </p>")
    output.write("\n<div style=\"width:350px;background-color:#ccc;\"><h2>Table of content</h2>\n")
    if(layout=="bytopic"):
      writeContent(bytopic,output,"href",content=0) 
    elif(layout=="bydate"):
      writeContentByDate(bydate,output,"href",content=0) 
    elif(layout=="byauthor"):
      writeContentByAuthor(byauthor,output,"href",content=0) 
    output.write("\n</div>")
    output.write("\n<h2>Content</h2>\n")
    if(layout=="bytopic"):
      writeContent(bytopic,output,"name",content=1) 
    elif(layout=="bydate"):
      writeContentByDate(bydate,output,"name",content=1) 
    elif(layout=="byauthor"):
      writeContentByAuthor(byauthor,output,"name",content=1) 
    output.write(defaultfooter)


import sys
if(__name__ == '__main__'):
  args= sys.argv[1:]
  answer = parse(args[0])
  output = open("dwbib.html",'w')
  output2 = open("dwbib_date.html",'w')
  output3 = open("dwbib_author.html",'w')
  output4 = open("dwbib.rss",'w')
  toCleanHTML(args[0],output)
  toCleanHTML(args[0],output2,layout="bydate")
  toCleanHTML(args[0],output3,layout="byauthor")
  toCleanHTML(args[0],output4,layout="rss")
  output.close()
  output2.close()
  output3.close()
  output4.close()

