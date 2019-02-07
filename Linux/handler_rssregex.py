######################################################################
#
#   Module:  handler_rss.py
#   Version: $Id: handler_rssregex.py,v 1.1 2004/03/08 00:33:28 lemire Exp $
#   Author:  Daniel Lemire
#   Targets: Win32, Unix
#   Web:     http://www.ondelette.com/acadia/
#
# Special purposed RSS feed module.  Aggregate several RSS URLS, but only
# output those for which title or description matches a regular expression.
# For example, this could be used to aggregate several feeds and only
# keep the content related to .NET or Java, say.
#
# For example, to only gather the entries containing the word 'the' out of two
# blogs, you could do the following (yes, it is a stupid example):
#
# <source name="rssregex" regex="the" urla="http://it.sweeting.net/index.rdf" 
#    urlb="http://sqljunkies.com/WebLog/orfda/Rss.aspx">
#      <li><a href="%(link)s">%(title)s</a> - %(description)s</li>
#  </source>
#
#
# -------------------------------------------------------------------
#
# $Log: handler_rssregex.py,v $
# Revision 1.1  2004/03/08 00:33:28  lemire
# Improved things...
#
#
# -------------------------------------------------------------------
#
# Copyright (c) 2004, Daniel Lemire All rights reserved. 
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions 
# are met: 
#
# * Redistributions of source code must retain the above copyright notice, 
#   this list of conditions and the following disclaimer. 
# * Redistributions in binary form must reproduce the above copyright 
#   notice, this list of conditions and the following disclaimer in the 
#   documentation and/or other materials provided with the distribution. 
# * Neither the name of the Bluesine nor the names of its contributors 
#   may be used to endorse or promote products derived from this software 
#   without specific prior written permission. 
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
# "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED 
# TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
# PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
# CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
# EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
# PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
# PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
# LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
# NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
# SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#
##########################################################################
from BaseHandler import BaseHandler

import os
import feedparser
import re

class handler_rssregex(BaseHandler):
    def __init__(self, regex ,**urls):
        self._urls = urls
        self._p = re.compile(regex,re.IGNORECASE + re.MULTILINE)
    def get(self):
        matches = []
        for url in self._urls.values():
          rss = feedparser.parse(url)
          items = rss['items']
          for l in items:
            if(self._p.search(l['title']) or self._p.search(l['description'])):
              l['title'] = "["+str(rss['channel']['title'])+"] "+l['title']  
              matches.append(l)
        return matches
    def outputDefault(self, data):
        out = []
        for row in data:
            out.append(self.format('<p><a href="%(link)s">%(title)s</a><br>%(description)s</p>', row))
        return '\n'.join(out)
    def format(self,mask,dict):
        dictTemp = dict.copy()
        for key in dictTemp:
          dictTemp[key] = self._p.sub(r'<b>\g<0></b>',dict[key])
        return mask % dictTemp
 

if __name__=='__main__':
    import sys
    h = handler_rss(sys.argv[1])
    print h.outputDefault(h.get())
