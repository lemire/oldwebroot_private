import httplib
import urllib
from BeautifulSoup import BeautifulSoup
import re

class GoogleScholarSearch:
	"""
	@brief This class searches Google Scholar (http://scholar.google.com)

	Search for articles and publications containing terms of interest.
	
	Usage example:\n
	<tt>
	> from google_search import *\n
	> searcher = GoogleScholarSearch()\n
	> searcher.search(['breast cancer', 'gene'])
	</tt>
	"""
	def __init__(self):
		"""
		@brief Empty constructor.
		"""
		self.SEARCH_HOST = "scholar.google.com"
		self.SEARCH_BASE_URL = "/scholar"

	def search(self, url):
		"""
		@brief This function searches Google Scholar using the specified terms.
		
		Returns a list of dictionarys. Each
		dictionary contains the information related to the article:
			"URL"		: link to the article/n
			"Title"		: title of the publication/n
			"Authors"	: authors (example: DF Easton, DT Bishop, D Ford)/n
			"JournalYear" 	: journal name & year (example: Nature, 2001)/n
			"JournalURL"	: link to the journal main website (example: www.nature.com)/n
			"Abstract"	: abstract of the publication/n
			"NumCited"	: number of times the publication is cited/n
			"Terms"		: list of search terms used in the query/n

		@return List of results, this is the empty list if nothing is found
		"""
		#params = urllib.urlencode({'q': "+".join(terms), 'num': limit})
		headers = {'User-Agent': 'Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)'}

		#url = self.SEARCH_BASE_URL+"?"+params
		conn = httplib.HTTPConnection(self.SEARCH_HOST)
		conn.request("GET", url, {}, headers)
    
		resp = conn.getresponse()      
        
		if resp.status==200:
			html = resp.read()
			results = []
			html = html.decode('ascii', 'ignore')
                        
			# Screen-scrape the result to obtain the publication information
			soup = BeautifulSoup(html)
			citations = 0
			for record in soup('p', {'class': 'g'}):
             
				# Includeds error checking
				topPart = record.first('span', {'class': 'w'})                                
                
				pubURL = topPart.a['href']
				# Clean up the URL, make sure it does not contain '\' but '/' instead
				pubURL = pubURL.replace('\\', '/')

				pubTitle = ""
                
				for part in topPart.a.contents:
					pubTitle += str(part)
                
				if pubTitle == "":
					match1 = re.findall('<b>\[CITATION\]<\/b><\/font>(.*)- <a',str(record))
					if(len(match1)>citations): 
						match2 = re.split('- <a',match1[citations])
						pubTitle = re.sub('<\/?(\S)+>',"",match2[0])
						citations = citations + 1
               
				#authorPart = record.first('font', {'color': 'green'}).string
				authorPart = record.first('span', {'class': 'a'}).string
				if(str(authorPart)=='Null'):
				  #print str(record)
				  m = re.findall('<span class="a">(.*?)</span>', str(record))
				  if len(m)>0:
						authorPart = m[0]
				  else:
				    print "failed ", record
				    continue
				#if str(authorPart)=='Null':	
				#	authorPart = ''
				#	# Sometimes even BeautifulSoup can fail, fall back to regex
				#	m = re.findall('<font color="green">(.*)</font>', str(record))
				#	if len(m)>0:
				#		authorPart = m[0]
				num = authorPart.count(" - ")
				# Assume that the fields are delimited by ' - ', the first entry will be the
				# list of authors, the last entry is the journal URL, anything in between
				# should be the journal year
				idx_start = authorPart.find(' - ')
				idx_end = authorPart.rfind(' - ')
				#print idx_start, idx_end, authorPart
				pubAuthors = authorPart[:idx_start]				
				pubJournalYear = authorPart[idx_start + 3:idx_end]
				pubJournalURL = authorPart[idx_end + 3:]
				# If (only one ' - ' is found) and (the end bit contains '\d\d\d\d')
				# then the last bit is journal year instead of journal URL
				if pubJournalYear=='' and re.search('\d\d\d\d', pubJournalURL)!=None:
					pubJournalYear = pubJournalURL
					pubJournalURL = ''
                               
				# This can potentially fail if all of the abstract can be contained in the space
				# provided such that no '...' is found
				delimiter = record.firstText("...").parent
				pubAbstract = ""
				while str(delimiter)!='Null' and (str(delimiter)!='<b>...</b>' or pubAbstract==""):
					pubAbstract += str(delimiter)
					delimiter = delimiter.nextSibling
				pubAbstract += '<b>...</b>'
                
				match = re.search("Cited by ([^<]*)", str(record))
				pubCitation = ''
				if match != None:
					pubCitation = match.group(1)
				results.append({
					"URL": pubURL,
					"Title": pubTitle,
					"Authors": pubAuthors,
					"JournalYear": pubJournalYear,
					"JournalURL": pubJournalURL,
					"Abstract": pubAbstract,
					"NumCited": pubCitation
				})
			return results
		else:
			print "ERROR: ",
			print resp.status, resp.reason
			return []
import PyRSS2Gen, datetime

def writeRSS(name,pubs):
    title = name
    link = ""
    description = ""
    lastBuildDate = datetime.datetime.now()
    try:
      items = [PyRSS2Gen.RSSItem(title = p["Title"],link = p["URL"],description = p["Abstract"],guid = PyRSS2Gen.Guid(link)) for p in pubs]
    except KeyError:
      print KeyError
      pass
    rss=PyRSS2Gen.RSS2(title=title,link=link,description=description,lastBuildDate=lastBuildDate,items=items)
    rss.write_xml(open(name+".xml", "w"))


if __name__ == '__main__':
    list = [("bitmapindex","http://scholar.google.com/scholar?as_q=bitmap+index&num=100&btnG=Recherche+Scholar&as_epq=&as_oq=&as_eq=&as_occt=any&as_sauthors=&as_publication=&as_ylo=2008&as_yhi=&hl=fr&lr="),("citingme", "http://scholar.google.com/scholar?num=100&hl=en&lr=&q=%22Daniel+Lemire%22+OR+%22D.+Lemire%22+OR+%22Lemire%2C+D.%22&as_ylo=2008&as_yhi=&btnG=Search"), ("lbkeogh","http://scholar.google.com/scholar?num=100&hl=en&lr=&q=LB_Keogh&as_ylo=2008&as_yhi=&btnG=Search"),("nndtw","http://scholar.google.com/scholar?num=100&hl=fr&lr=&q=%22nearest+neighbor%22+%22dynamic+time+warping+%22&as_ylo=2008&as_yhi=&btnG=Rechercher&lr=")]
    for x in list:  
      search = GoogleScholarSearch()
      print x[0]
      pubs = search.search(x[1])
      if(pubs ):
        writeRSS(x[0],pubs)
      else :
        print "failed with ",x
