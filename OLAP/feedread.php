<?php
//============================================================
// original concept for stripped down XML reader was by Jaykul
// http://jaykul.huddledmasses.org/scripting/news_feed_parser.php
// .. not sure if it was based on anything prior.
//
//============================================================
// CHAITGEAR CG-FeedRead implemented by David Chait (cgcode@chait.net)
// http://www.chait.net/wp-plugins/feedread.php and XMLParser.php
// while based on the basic concept of simplified XML parsing, FR was
// completely rewritten to base off the new CG XMLParser library.
//
// overall, the code has grown in functionality greatly this year.
// it pre-parses the XML page into an array structure, then walks the
// proper parts of the structure to extract important fields.
// it is pretty TRIVIAL to add fields to the output if you want them.
//
//============================================================
// We ask if you find this useful to go to http://www.chait.net/index.php?type=14,
// grab one of the cool link graphics, and give us a link back!  Thanks!
//
//============================================================
// note that this file has NO particular dependence on WordPress
// and can really be used under any PHP website.
//
//============================================================

if (!isset($XML_CACHE_TIME))
	$XML_CACHE_TIME = 6 * 60 * 60; // hr * min/h * secs/m  -- you can change this for faster updates of the caches.

if (!isset($DebugFeed))
	$DebugFeed = 0;

if (!isset($CGTargetWindow)) $CGTargetWindow = 'NewWindow';
			
$FR_CACHE = "cache_feedread"; // the path to the cache folder.

$feedReadPath = dirname(__FILE__).'/';

require_once($feedReadPath."XMLParser.php");
require_once($feedReadPath."helper_fns.php");
require_once($feedReadPath."uni_fns.php");

/*
require_once("error-handler.php");

function mkdir_p($target)
{
if (is_dir($target)||empty($target)) return 1; // best case check first
if (file_exists($target) && !is_dir($target)) return 0;
if (mkdir_p(substr($target,0,strrpos($target,'/'))))
  return mkdir($target, 0755); // crawl back up & create dir tree
return 0;
}

function feedreadInstall()
{
	global $FR_CACHE;
	
	$frPathPrefix = dirname(__FILE__);
	// locate the wp path...
	if (FALSE===strpos($frPathPrefix, 'wp-content')) // plugin
		$backup = 1;
	else
		$backup = 3;

$old_umask = umask(0);
		
	for ($i=0; $i<$backup; $i++)
	{
		chmod($frPathPrefix, 0777);
		echo $frPathPrefix.'<br>';
		if (0==($pos = strrpos($frPathPrefix, '/')))
			$pos = strrpos($frPathPrefix, '\\');		
		$frPathPrefix = substr($frPathPrefix, 0, $pos);
	}
		
	$frPathPrefix .= '/'.$FR_CACHE;
		
	if (is_dir($frPathPrefix))
		echo("dir exists ($frPathPrefix)");
	if (file_exists($frPathPrefix) && !is_dir($frPathPrefix))
		echo("a file exists already as ($frPathPrefix) instead of a directory");
	$result = mkdir_p($frPathPrefix);
	if ($result)
		echo "we were able to create ($frPathPrefix)";
	else
		echo "we were NOT able to create ($frPathPrefix).  You'll need to do so manually.";
		
umask($old_umask);
}
*/


//============================================================
function getSomeFeed($InUrl, $numberOfItems, $showDetails, $cacheName, $filterCat='',
										$tLimit = -1, $dLimit = -1, $noHTML = true,  // no HTML by default, ==2 might allow safe tags
										$showTime = false, $likeWPPosts = false, $noTitle = false,
										$showTimeGMT = false, $titleImages = false) 
{
	//Globals
	global $XML_CACHE_TIME, $FR_CACHE;
	global $XATTR,$XVALUE,$XTAG; // delimiters in the array breakdown...
	global $DebugFeed;
	global $CGTargetWindow;

	$siteTitle = ""; $siteLink = ""; $siteDescription = "";

	if ($XML_CACHE_TIME < 5) // require 5s minimum...
		$XML_CACHE_TIME = 5; // secs
	
	if ($numberOfItems<0 || $numberOfItems>255) // arbitrary cutoff...
		$numberOfItems = 255;
	
	if ($likeWPPosts)
		$ending = "_WP.html";
	else
		$ending = ".html";
		
	$links = true;
	$reCache = true;
	$doCache = true;
	
	$cacheDir = $FR_CACHE;
        if( ! $cacheDir) $cacheDir ="cache_feedread";
	$cachePath = dirname(__FILE__) . '/' . $cacheDir . '/' . $cacheName;
//dbglog("dir=".dirname(__FILE__));	
	if (empty($InUrl))
		return "CGFR: RSS/XML url string was empty. Please check the arguments to parseURL..";
	else
		$url = $InUrl;
	if (empty($cacheName)) // Don't bother caching.
		$doCache = false;
	else
	{
	$isThere = file_exists ( $cachePath.$ending );
		if ($isThere)
		{
			$filemod = filemtime( $cachePath.$ending );
			//echo "\n\n\n<!-- ===== $filemod - ".time()."  [Updated ".($filemod - time())." seconds ago -->";
			if( (time() - $filemod + rand(0,300) ) < $XML_CACHE_TIME ) // added 0-300s random so they don't all hit one pageload! ;)
{				$reCache = false;
} else {
//dbglog("it exists, but it is old ".(time()-$filemod));
//dbglog("cache time = ",$XML_CACHE_TIME);
}
		}     
	}
	
	// <!-- ===== START ===== RSS FEED OUTPUT ===== --/>
	$output = '';
	$lf = "\n";
	//$reCache = true;

	if ( $reCache )
	{
		//dbglog("CGFR: Recaching $cacheName ($url)...");
		$parser = new XMLParser();
		$ret = $parser->setSource($url, 'url');
		if (!$ret)
		{
			$reCache = false;
			if (empty($cacheName))
				return "CGFR: Unable to open feed [$url], and not caching.  Aborted.";
		}
		else
		{
		  $tree = $parser->getTree();
			if ($DebugFeed>1)
			{
				$rawarray = print_r($tree, TRUE);
				//dbg_log("Feed tree = ".$rawarray);
		
				if (0)
					die($rawarray);
				else
				{
					$fp = fopen($cachePath."RAW.PHP",'w');
					fwrite($fp, $rawarray, strlen($rawarray));
					fclose($fp);
				}
	//			die();
			}
			
			$feed = null;
			$itemTag = 'item';
		  if (IsSet($tree["rdf:RDF"])) // rdf 1.0 feed
		  {
			  $feedType = "RDF";
			  $feed = &$tree["rdf:RDF"];
				$siteTitle = $feed['channel']['title'];
				$siteLink = $feed['channel']['link'];
				$siteDescription = $feed['channel']['description'];
				$siteImage = '';
		  }
		  else
		  if (IsSet($tree["rss"])) // some kind of rss feed
		  {
			  $feedType = "RSS";
			  $feed = &$tree['rss']['channel'];
				$siteTitle = $feed['title'];
				$siteLink = $feed['link'];
				$siteDescription = $feed['description'];
				$siteImage = $feed['image']['url'];
		  }
		  else
		  if (IsSet($tree["feed"])) // a generic feed, likely to assume Atom
		  {
			  $feedType = "ATOM";
			  $feed = &$tree['feed'];
				$siteTitle = $feed['title'];
				$siteLink = $feed['link']['href'];
				$siteDescription = $feed['tagline'];
				$siteImage = '';
				
				$itemTag = 'entry';
		  }
		  else
		  {
			  $errout = "CGFR: Feed contains invalid format.";
			  dbglog($errout." [$url]");
			  
			  if (1)
				  foreach($tree as $leafname => $leaf)
				  	$errout .= $leafname;
			  	
			  return $errout;
		  }	  
			if ($DebugFeed>1) dbglog("CGFR: type = $feedType");
				  
			$siteLink = safehtmlentities($siteLink, ENT_QUOTES);
			if (empty($siteTitle)) $siteTitle = $siteLink;
			if (empty($siteDescription)) $siteDescription = $siteTitle;

			// try to detect unicode:
			if ($parser->uni && !uni_detect($siteTitle) && !uni_detect($siteDescription))
				$parser->uni = false; // force turn it off.
			if ($DebugFeed && $parser->uni) dbglog("CGFR: feed was multibyte...");
		
			// grrr.  people putting html into the site description should be...			
			if ($parser->uni)					
				$siteDescription = uni2str(uni_strip_tags(str2uni($siteDescription)));
			$siteDescription = strip_tags($siteDescription);
					
	//	$output .= "Parsing Feed = $siteTitle : $siteDescription @ $siteLink";
			$i=0;
			
			$items = &$feed[$itemTag];
			if (!is_array($items) || !isset($items[0])) // not an array, or single item collapsed so no [0] element...
			{
				//dbglog("CGFR: dealing with singlular-entry case...");
				$items = array(0 => $items); // in case a single entry...
			}
	
			if ($DebugFeed>1) dbglog("itemtag = $itemTag, count = ".count($items));
	
			foreach($items as $index=>$item)
			{
				if ($DebugFeed>2) dbglog("item $index:<br/>".$item['title']);
				$itemTitle = $item['title'];
	//			if (empty($itemTitle))
	//				$itemTitle = $siteTitle;
	
	/*
				if ($DebugFeed>2) dbglog("$index str title: ".str_replace('_',' ',$itemTitle));
				if ($DebugFeed>2) dbglog("$index clean title: ".cleanBadChars($itemTitle));
				if ($DebugFeed>2) dbglog("$index strip title: ".strip_tags($itemTitle));
				if ($DebugFeed>2) dbglog("$index special title: ".htmlspecialchars($itemTitle));
				if ($DebugFeed>2) dbglog("$index html title: ".htmlentities($itemTitle));
				if ($DebugFeed>2) dbglog("$index safe title: ".safehtmlentities($itemTitle));
	*/
				
				$itemTime = $item['pubDate']; // s:7:"pubDate";s:31:"Wed, 30 Jun 2004 04:54:55 +0000"
				if (!empty($itemTime)) // raw convert.
					$unixTime = strtotime($itemTime);
				else 
				{ // try alternate formats
					$itemTime = $item['dc:date']; // s:25:"2004-07-14T10:45:00-08:00"
					if (empty($itemTime))
						$itemTime = $item['modified']; // atom style //"2004-07-17T19:57:08Z" Z can be offset
					if (!empty($itemTime)) // parse it.
					{ // 1YYYY 2MM 3DD 4HH 5MM 6SS 7+|- 8Z|HH 9MM
						$result = preg_match("/(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})([+|-]*)([Z|\d{2}]+)/", $itemTime, $timeFields);
	//					if ($DebugFeed>2) dbglog($itemTime. " = " .serialize($timeFields));
						$dat['year']=intval($timeFields[1]);
						$dat['month']=intval($timeFields[2]);
						$dat['day']=intval($timeFields[3]);
						$dat['hour']=intval($timeFields[4]);
						$dat['min']=intval($timeFields[5]);
						$dat['sec']=intval($timeFields[6]);
						// need to add adj off of GMT when there is one...
						if ($timeFields[7] && $showTimeGMT) // emtpy if Zulu == [8]
						{
							if ($timeFields[7]=='-') // GMT - n, so ADD to get back to Zulu.
							{
								$dat['hour'] += intval($timeFields[8]);
								$dat['min'] += intval($timeFields[9]);
							}
							else // GMT + n, so SUB to get to Zulu.
							{
								$dat['hour'] -= intval($timeFields[8]);
								$dat['min'] -= intval($timeFields[9]);
							}
						}	
						$unixTime = mktime($dat['hour'], $dat['min'], $dat['sec'], $dat['month'], $dat['day'], $dat['year']);
	//					if ($DebugFeed>2) dbglog("time fields = [$unixTime] ".serialize($dat));
					}
				}
				
				$itemTime = date("n/j/Y g:ia", $unixTime);
				
				$itemCat = $item['category'];
				if (empty($itemCat))
					$itemCat = $item['dc:subject']; // atom, rss2
				
				if ($feedType=='ATOM')
					$itemLink = $item['link']['href']; // stupid tag attributes...
				else
					$itemLink = $item['link'];
				if (empty($itemLink))
					$itemLink = $item['guid'];
					
				if ($feedType=='ATOM')
					$itemDescription = $item['content'][$XVALUE];
				else
				if ($item['content:encoded']) // use instead of description?
					$itemDescription = $item['content:encoded'];
				else
					$itemDescription = $item['description'];
				if (empty($itemDescription))
					if ($item['summary'][$XVALUE])
						$itemDescription = $item['summary'][$XVALUE];
				if (empty($itemDescription))
				{
					$itemDescription = $siteDescription;
					//dbglog("CGFR: no feed item description = ".serialize($item));
				}
									
				if (!empty($filterCat))
				{
					if (empty($itemCat)) // then compare against the url
					{
						if (false==findstr($itemLink, $filterCat)) continue;
					}
					else
					{
						if (false==findstr($itemCat, $filterCat))	continue;
					}
				}
				
				$i++;
				if ($i>$numberOfItems)
					break;
					
				$itemCat = safehtmlentities($itemCat, ENT_QUOTES);
				$itemLink = safehtmlentities($itemLink, ENT_QUOTES);
				$linkTitle = '';
				if ($feedType=='ATOM')
					$linkTitle = $item['summary'][$XVALUE];
				
				if ($parser->uni)
				{
					if (!$showDetails)
						$itemDescription = str2uni($itemDescription, 64);
					else
					if ($dLimit>0)
						$itemDescription = uni_snippet(str2uni($itemDescription, $dLimit+8), $dLimit);
					else
						$itemDescription = str2uni($itemDescription);
					$stripDescript = uni_strip_tags($itemDescription);
					$snippedTitle = uni2str(uni_snippet($stripDescript, 40));
					$snippedTitle = strip_tags($snippedTitle);
//						dbglog(serialize($snippedTitle));
					$stripDescript = strip_tags(uni2str($stripDescript));
//						dbglog(serialize($stripDescript));
				}
				else
				{
					$itemDescription = cleanBadChars($itemDescription); // just in case...
					$stripDescript = strip_tags($itemDescription);
					if ($showDetails && $dLimit>0) $itemDescription = snippet($itemDescription, $dLimit);
					$snippedTitle = snippet($stripDescript, 40);
					$snippedTitle = strip_tags($snippedTitle);
				}

				if ($parser->uni)
				{
					if ($tLimit>0)	$itemTitle = str2uni($itemTitle, $tLimit+8);
					else						$itemTitle = str2uni($itemTitle);
					$itemTitle = uni_strip_tags($itemTitle, ENT_QUOTES);
					if (empty($itemTitle) || empty($linkTitle))
					{
						if (empty($itemTitle))		$itemTitle = $snippedTitle;
						if (empty($linkTitle))		$linkTitle = $snippedTitle;
					}
					else
					if (!empty($linkTitle)) $linkTitle = uni2str(uni_snippet(str2uni($linkTitle, 80), 64));
					
					if ($tLimit>0) $itemTitle = uni_snippet($itemTitle, $tLimit);
					$itemTitle = uni2str($itemTitle);
				}
				else
				{
					if (empty($itemTitle) || empty($linkTitle))
					{
						if (empty($itemTitle))		$itemTitle = $snippedTitle;
						if (empty($linkTitle))		$linkTitle = $snippedTitle;
					}
					else
					if (!empty($linkTitle)) $linkTitle = snippet($linkTitle, 64);
					if ($tLimit>0) $itemTitle = snippet($itemTitle, $tLimit);
				}
				
				$itemTitle = strip_tags($itemTitle, ENT_QUOTES);
				$itemTitle = safehtmlentities($itemTitle, ENT_QUOTES);
				$linkTitle = strip_tags($linkTitle, ENT_QUOTES);
				$linkTitle = safehtmlentities($linkTitle, ENT_QUOTES);
				
				if ($showDetails) 
				{
					if ($parser->uni)
					{
						if ($noHTML)
							$stripDescript = $stripDescript;
						else
							$itemDescription = uni2str($itemDescription);
					}
										
					if ($noHTML) // use the tag-stripped version, AND convert quotes...
						$itemDescription = safehtmlentities($stripDescript, ENT_QUOTES);
				}
				
/*
				if ($DebugFeed>2)
				{
					$chars = '';
					$tmp = str2uni($linkTitle);
					foreach($tmp as $achr)
					{
						$chars .= '['.$achr.'='.chr($achr).'] ';
					}
					dbglog("CGFR: link = ".$chars);
				}
*/
	
				if ($i==1) //start of feed output
				{
					$output .= $lf."<!-- ChaitGear CG-Feedread (www.chait.net) -->".$lf;
					$output .= "<!-- showing feed: $url -->".$lf;
				
					if (!$likeWPPosts && !$noTitle)
					{
						$output .= "<div class='feedDescription'>".$lf;
						if ($links)
						{
							$output .= '<a href="'.$siteLink.'" title="'.$siteDescription.'"';
							if ($CGTargetWindow) $output .= ' target="'.$CGTargetWindow.'"';
							$output .= '>'.$lf;
						}
						if ($titleImages && !empty($siteImage))
							$output .= '<img src="'.$siteImage.'" alt="'.$siteDescription.'" />';
						else
							$output .= $siteTitle;
						if ($links)
							$output .= "</a>";
						$output .= $lf;
						$output .= "</div>".$lf;
						
					}
					if (!$likeWPPosts)
						$output .= "<ul>".$lf;
				}
	
				if ($likeWPPosts)
					$output .= '<div class="post">'.$lf;
				
				if ($likeWPPosts)
					$output .= "<h3 class='storytitle'>".$lf;
				else
					$output .= "<li class='feedItem'>".$lf."<div class='feedItemTitle'>".$lf;
				
				if ($links)
					$output .= '<a href="'.$itemLink.'" title="'.$linkTitle.'" >'.$lf;
				$output .= $itemTitle;
				if ($links)
					$output .= "</a>";
				$output .= $lf;
				
				if ($likeWPPosts) /* close each title */
					$output .= "</h3>".$lf;
				else
					$output .= "</div>".$lf;
				
				if ($showDetails)
				{
					if ($likeWPPosts)
						$output .= "<div class='storycontent'>$itemDescription</div>".$lf;
					else
						$output .= "<p class='feedItemDescription'>$itemDescription</p>".$lf;
				}
				
				if ($showTime && !empty($itemTime))
					$output .= "<div class='meta'>".$itemTime."</div>".$lf;
				
				if ($likeWPPosts) /* close each item */
					$output .= '</div>'.$lf;
				else
					$output .= "</li>".$lf;
			}
			
			if ($i)
				$output .= "</ul>".$lf."<!-- end feed -->".$lf;
	
			// cache the PHP array for later reference			
			$cacheFileDat = fopen($cachePath.".DAT","w");
			flock($cacheFileDat, LOCK_EX);
			fwrite($cacheFileDat, /*base64_encode*/(serialize($tree)));
			flock($cacheFileDat, LOCK_UN);
			fclose($cacheFileDat);
	
			if (!$DebugFeed && $doCache)
			{
				$cacheFile = fopen($cachePath.$ending,"w");
				if ($cacheFile)
				{
					if (flock($cacheFile, LOCK_EX))
					{
						fwrite($cacheFile, $output);
						flock($cacheFile, LOCK_UN);
					}
				}
				else
					echo "CG-Feedread failed to save feed to disk -- couldn't write to the $FR_CACHE directory.";
		
				fclose($cacheFile);
			}
			else
					dbglog("CGFR: HTML caching disabled, debug=$DebugFeed, doCache=$doCache");
			
			return $output;
		}
	}	
	
	// else, we're pulling from cachefile...
	if (file_exists($cachePath.$ending))
		$output = file_get_contents($cachePath.$ending);
	else
	if (!$reCache && isset($parser)) // then we failed to load
	{
		$output = $parser->setSourceFailed;
		dbglog("CGFR: $output...");
	}
	
	return $output;
	// <!-- ===== END ===== RSS FEED OUTPUT ===== --/>   
}

?>
