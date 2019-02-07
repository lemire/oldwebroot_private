
import urllib
import re

def numbertoletter(x):
  t=['d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z']
  s = ""
  while (x > 0):
    c = x % len(t)
    s+=t[c]
    x /= len(t)
  return s

print "Loading OPML file from downes.ca"
site = urllib.urlopen('http://www.downes.ca/cgi-bin/xml/feeds.cgi?feed=all&format=opml')
s = site.read()
print "File loaded"
reLong = re.compile('<outline(.*?)/>')
reArgs = re.compile('xmlUrl=\"(.*)\"')
xmlurl=[]
for match in reLong.finditer(s):
  args = match.group(1)
  print "found a match : ", args
  for m in reArgs.finditer(args):
    print "found a xml url : ", m
    xmlurl.append(m.group(1))
counter = 0
for u in xmlurl:
  counter += 1
  print "url"+numbertoletter(counter)+"=\""+u+"\""



