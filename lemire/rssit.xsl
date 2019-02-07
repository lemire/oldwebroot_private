<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" /> 
	<xsl:variable name="title" select="/rss/channel/title"/>		
	<xsl:template match="/">
	<html>
		<head>
			<title><xsl:value-of select="$title"/> RSS Feed</title>
			<meta content="text/html; charset=iso-8859-1" http-equiv="Content-Type" />
		</head>	
		<xsl:apply-templates select="rss/channel"/>		
		</html>
	</xsl:template>
	
	<xsl:template match="channel">
		<body bgcolor="white">		
		<div class="top" style="width:10cm; float:right; margin:1em;padding:1em;background-color:#eee;">
			<h2>What is this page?</h2>
			<p>This is an  <a href="http://news.bbc.co.uk/1/hi/help/3223484.stm">RSS feed</a> called "<xsl:value-of select="$title"/>". To make it look readable in a browser, I use an XSLT file stolen from Richard Stallman's site. To subscribe to the feed, you will need a <a href="http://www.guardian.co.uk/online/story/0,3605,781838,00.html">news reader</a>.
Several Free Software examples are listed <a href="http://stallman.org/newsreaders.html">here</a>.
</p>
			
					<h2>Subscribe to this feed</h2>
					<p>You can subscribe to this RSS feed in a number of ways, including the following:</p>
					<ul>
					<li>Drag the 
					<a href="#">
					<img src="http://cgi.cbrody.com/images/xml.gif" title="RSS" alt="RSS" border="0" />
					</a> button into your news reader</li>
					<li>Drag the URL of the RSS feed into your news reader</li>
					<li>Cut and paste the URL of the RSS feed into your news reader</li>
					</ul>
					<h2>One-click subscriptions</h2>
					<p>If you use one of the following web-based news readers, click on the appropriate button to subscribe to the RSS feed.</p><p>
					<a href="javascript:location.href='http://add.my.yahoo.com/rss?url='+ location.href"><img height="17" width="91" hspace="5" border="0" alt="my yahoo" src="http://us.i1.yimg.com/us.yimg.com/i/us/my/addtomyyahoo4.gif" /></a>
	<a href="javascript:location.href='http://www.bloglines.com/sub/'+ location.href"><img height="18" width="91" hspace="5" border="0" alt="bloglines" src="http://www.bloglines.com/images/sub_modern1.gif" /></a>
	<a href="javascript:location.href='http://www.newsgator.com/ngs/subscriber/subext.aspx?url='+ location.href"><img height="17" width="91" hspace="5" border="0" alt="newsgator" src="http://www.newsgator.com/images/ngsub1.gif" /></a>
					</p>
		
		
		</div>
		<xsl:apply-templates select="item"/>
		
		<div id="footer">
			<xsl:value-of select="copyright"/>
			<br /><br />
			<a href="javascript:location.href='http://feedvalidator.org/check.cgi?url=' + location.href">Valid RSS</a>
		</div>
		</body>
	</xsl:template>
		
	<xsl:template match="item">
	<ul>
		<li>
			<h3><a href="{link}" class="StoryTitle">
			<xsl:value-of select="title"/>
			</a></h3>
			<div class="StoryContentColor">
			<xsl:value-of select="description" />					
			</div>	
			<br />
		</li>
	</ul>
	</xsl:template>

</xsl:stylesheet>
