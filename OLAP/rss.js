///////////////////////////////////////////////////
// script by Daniel Lemire with some bits from   //
// all over the web                              //
// http://www.daniel-lemire.com/                 // 
///////////////////////////////////////////////////
function displayRSS(URI,displaytitle) {
var xmlhttp=false;
var mydiv = document.getElementById("rss");
/*@cc_on @*/
/*@if (@_jscript_version >= 5)
// JScript gives us Conditional compilation, we can cope with old IE versions.
// and security blocked creation of the objects.
 try {
  xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
 } catch (e) {
  try {
   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
  } catch (E) {
   xmlhttp = false;
  }
 }
@end @*/
if (!xmlhttp && typeof XMLHttpRequest!='undefined') {
  xmlhttp = new XMLHttpRequest();
}
xmlhttp.open("GET", URI,true);
ptag = document.createElement("p");
ptag.appendChild(document.createTextNode("Loading RSS feed..."));
mydiv.appendChild(ptag);
xmlhttp.onreadystatechange=function() {
if (xmlhttp.readyState==2) {
 // mydiv.appendChild(ptag);
  }
if (xmlhttp.readyState==4) {
  
  xmlDoc=xmlhttp.responseXML;
  items=xmlDoc;
  formatRSS();
  mydiv.removeChild(ptag);
  }
 }
 xmlhttp.send(null);

	function formatRSS() {
		var items_count=items.getElementsByTagName('item').length;
                if(items_count == 0) {
       			ptag = document.createElement("p");
                        ptag.appendChild(document.createTextNode("Empty RSS feed or unsupported feed format (Atom is not supported!)"));
			mydiv.appendChild(ptag);
			return;
                }
		link=new Array(), title=new Array()
		for(var i=0; i<items_count; i++) {
			if(items.getElementsByTagName('item')[i].getElementsByTagName('link').length==1)
				link[i]=items.getElementsByTagName('item')[i].getElementsByTagName('link')[0];
			if(items.getElementsByTagName('item')[i].getElementsByTagName('title').length==1)
				title[i]=items.getElementsByTagName('item')[i].getElementsByTagName('title')[0];
		}
		if(title.length==0) return false;
		if(! displaytitle) {
			h2=document.createElement("h2");
			bloga = document.createElement("a");
			bloga.appendChild(document.createTextNode(xmlDoc.getElementsByTagName('title')[0].firstChild.nodeValue));
			bloga.setAttribute("href",xmlDoc.getElementsByTagName("link")[0].firstChild.nodeValue);
			h2.appendChild(bloga);
			h2.appendChild(document.createTextNode(" "));
        	        rssa = document.createElement("a");
			h2.appendChild(rssa);
			rssa.setAttribute("href",URI);
			rssa.setAttribute("class","RSS");
			rssa.appendChild(document.createTextNode("RSS"));
			mydiv.appendChild(h2);
		}
		ul = document.createElement("ul");
		mydiv.appendChild(ul);
		for(var i=0; i<items_count; i++) {
			var title_w, link_w;
			title_w=(title.length>0)?title[i].firstChild.nodeValue:"<i>Untitled</i>";
			link_w=(link.length>0)?link[i].firstChild.nodeValue:"";
			litag = document.createElement("li");
                        linktag =document.createElement("a");
			linktag.setAttribute("href",link_w);
			linktag.appendChild(document.createTextNode(title_w));
			
			litag.appendChild(linktag);
			ul.appendChild(litag);
		}
	}
}


