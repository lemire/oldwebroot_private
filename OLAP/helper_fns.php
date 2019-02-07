<?php

// try to include known custom CG files we will be
// implementing stubs of here if they don't exist...

$helpPath = dirname(__FILE__).'/';

if (file_exists($helpPath.'sideblock-fns.php'))
	@include_once($helpPath.'sideblock-fns.php');

if (file_exists($helpPath.'error-handler.php'))
	@include_once($helpPath.'error-handler.php');

	
//========================================
if (!function_exists("start_block"))
{
	function start_block($blockTitle, $blockID, $blockWrap='')
	{
		global $currBlockInnerTag;
		$le = "\n";
		$output = $le;
			
		$currBlockInnerTag = $blockWrap;
		if (!empty($currBlockInnerTag))
		{
			$output .= "<".$currBlockInnerTag." id='$blockID'>".$le;
		}
		
		if (!empty($blockTitle))
			$output .= $blockTitle.':';
		
		echo $output;
	}
}

if (!function_exists("end_block"))
{
	function end_block()
	{
		global $currBlockInnerTag, $wpstyle;
		$le = "\n";
		$output = $le;
		
		if (!empty($currBlockInnerTag))
		{
			$output .= "</".$currBlockInnerTag.">".$le;
		}		
		
		echo $output;
	}
}

//========================================
if (!function_exists("file_get_contents"))
{
  function file_get_contents($filename, $use_include_path = 0)
  {
   $data = ""; // just to be safe. Dunno, if this is really needed
   $file = @fopen($filename, "rb", $use_include_path);
   if ($file)
   {
     while (!feof($file)) $data .= fread($file, 4096);
     fclose($file);
   }
   return $data;
  }
}

//========================================
if (!function_exists('file_put_string'))
{
	function file_put_string($filename, $string)
	{
		$nr_of_bytes = false;
		$string .= "\n"; // add a linefeed automatically.
		
		if(($file = fopen($filename, "a")) !== false)
		{
			if (flock($file, LOCK_EX))
			{
					if(($nr_of_bytes = fwrite($file,$string,strlen($string))) === false) $nr_of_bytes = false;
					flock($file, LOCK_UN);
			}
			fclose($file);
		}
	
		return $nr_of_bytes;
	}
}

//========================================
if (!function_exists('dbglog'))
{
	function dbglog($string)
	{
		if (function_exists('dbg_log')) // then you have the cg-error system in place...
			dbg_log($string);
		else
		{
			$dbgWrite = false;
			$dbgEcho = true;
			// default is to just eat the errors.
			if ($dbgWrite) file_put_string(ABSPATH."cg-dbglog.log", $string."\n");
			if ($dbgEcho) echo $string."\n";
		}
	}
}

//========================================================
//returns TRUE if the haystack string contains one of the strings from needle array, if needle is not found returns FALSE (case-insensitive)
if (!function_exists('findstr'))
{
	function findstr($haystack='', $needle=array())
	{
		if (empty($haystack) || empty($needle))
			return(false);
			
		if (is_array($needle))
		{
			foreach($needle as $n)
			{
				if (stristr($haystack, $n) )
				{
					return $n;
				}
			}
		}
		else
		{
			if (stristr($haystack, $needle))
			{
				return $needle;
			}
		}
	
		return null;
	}
}

//========================================================
if (!function_exists('is_valid_IP_address'))
{
	function is_valid_IP_address($checkip)
	{ // from a sample on php.net
		if (eregi("^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$", $checkip))
		{
			for ($i = 1; $i <= 3; $i++)
			{
				if (!(substr($checkip, 0, strpos($checkip, ".")) >= "0" && substr($checkip, 0, strpos($checkip, ".")) <= "255"))
				{
					//echo "Block $i of the IP address isn't correct format."
					return false;
				}
				$checkip = substr($checkip, strpos($checkip, ".") + 1);
			}
	
			if (!($checkip >= "0" && $checkip <= "255"))
			{
				//echo "Block 4 of the IP address isn't correct format."
				return false;
			}
		}
		else
		{
			//echo "The IP address isn't correct format."
			return false;
		}
	
		return true;
	} 
}	

//============================================================
if (!function_exists('convert_to_ascii'))
{
	function convert_to_ascii($content)
	{
				$content = preg_replace('/['.chr(127).'-'.chr(255).']/', ' ', $content );
				return $content;
	}
}	


//============================================================
if (!function_exists('cleanBadChars'))
{
	function cleanBadChars($string)
	{
		$multichars = array(chr(226).chr(128).chr(147), chr(226).chr(128).chr(156), chr(226).chr(128).chr(157), chr(226).chr(128).chr(153));
		$cleanchars = array('-', '"', '"' ,"'");
		$string = str_replace($multichars,$cleanchars,$string);
		
		if (1)
		{
			$badwordchars=array(chr(145), chr(146), chr(147), chr(148), chr(151) );
			$fixedwordchars=array( "'", "'", '&quot;', '&quot;', '&mdash;' );
			$string = str_replace($badwordchars,$fixedwordchars,$string);
		}
		
		return $string;
	}
}

//============================================================
if (!function_exists('safehtmlentities'))
{
	function safehtmlentities($string, $style=ENT_QUOTES)
	{
		if (empty($string)) return("");
		//if (is_array($string)) die("was array: ".serialize($string));
		if (is_array($string)) 
		{
			$string = implode(", ", $string);
	//		die ("multiple entities: ".$string);
		}
		
		// get rid of extra chars...
		$badwordchars=array("\r", "\n", "\t");
		$fixedwordchars=array( ' ',' ',' ' );
		$string = str_replace($badwordchars,$fixedwordchars,$string);
		
		if (0)
			$string = cleanBadChars($string);
		if (0) // for now, don't use htmlentities, as it ruins UTF-8 data!!!!
			$string = htmlentities($string, $style);
		else
			$string = htmlspecialchars($string, $style);
		$string = preg_replace("/&amp;#(\d+);/",  "&#\\1;",  $string);
		return $string;
	}
}

//============================================================
if (!function_exists('safeunhtmlentities'))
{
	function safeunhtmlentities ($string)
	{
	  $trans_tbl = get_html_translation_table (HTML_ENTITIES);
	  $trans_tbl = array_flip ($trans_tbl);
	  $ret = strtr ($string, $trans_tbl);
	  return preg_replace('/&#(\d+);/me',  "chr('\\1')",  $ret);
	}
}

//============================================================
if (!function_exists('safeAddSlashes'))
{
	function safeAddSlashes($string)
	{
/*
		if (get_magic_quotes_gpc())
		{
			echo "magic quotes active<br>";
			return $string;
		}
		else
*/
		{
			if (is_array($string))
			{
				foreach($string as $key => $arrval)
					$string[$key] = safeAddSlashes($arrval);
				return($string);
			}
			else
				return addslashes($string);
		}
	}
}

//============================================================
if (!function_exists('safeStripSlashes'))
{
	function safeStripSlashes($string)
	{
//		if (get_magic_quotes_gpc())
//			return $string;
//		else
		{
			if (is_array($string))
			{
				foreach($string as $key => $arrval)
					$string[$key] = safeStripSlashes($arrval);
				return($string);
			}
			else
				return stripslashes($string);
		}
	}
}

//============================================================
if (!function_exists('array_fill'))
{
	function array_fill($start, $count, $fill)
	{
		$result = array();
		for ($i=$start; $i<=$start+$count; $i++)
			$result[$i] = $fill;
		return($result);
	}
}

//============================================================
if (!function_exists('snippet'))
{
	//============================================================
	// this is for getting the first $length characters, but not chopping words:
	function snippet($text, $length, $tail="&#8230;")
	{
		global $DebugFeed;
		
		$inLen = strlen($text);
		
	//	if ($DebugFeed>1) dbglog("SNIPPET ($length, $inLen): $text");
			
		// TBD!!!!! ENHANCE THIS TO NOT BADLY-TERMINATE OPEN HTML TAGS!  CLOSE AUTOMATICALLY... MAYBE USE
		// THE WP FUNCTION balanceTags?
		// to preserve the substr() ability to use -1 to mean "everything"
		if( $length > 0 && $inLen > $length)
		{	
			if (0)
			{
				// trim.
				// we insist on a minimum length of 4
				$length = ($length > 4) ? ($length - 2) : 2;
				$pattern = "/^(.{1,$length}[A-z0-9\#_\$]{1,2})[^A-z0-9\#_\$]+.*/i";
				$replacement = "\\1";
				$text = preg_replace($pattern, $replacement, $text);
			}
			else // safer...
			{
				while($length>4)
				{
					$char = $text[--$length];
					if ($char == ' '
					||	$char == ','
					||	$char == '.'
					||	$char == '-'
					||	$char == "\t"
					||	$char == "\n"
					||	$char == "\r"
						)
						break;
				}
				$text = substr($text, 0, $length);
			}
	
			// maybe add a tail:
			if( $inLen > strlen($text) ) $text .= $tail;
		}
		
	//	if ($DebugFeed>1) dbglog("SNIPPED = $text");
			
		return $text;
	}
}

?>