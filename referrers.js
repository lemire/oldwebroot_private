  
function write_ref() {
   document.write("<script language='Javascript' src='http://www.ondelette.com/cgi-bin/referrers.cgi?in=" + document.referrer + "&out=" + document.location + "'>");
   document.write("</");
   document.write("script>");
}
write_ref();
