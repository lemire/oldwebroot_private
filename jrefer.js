
function writeRef(key) {
        document.write("<script type=\"text/javascript\" src =\"http://www.ondelette.com/servlet/JReferServlet?KEY="+escape(key)+"&REFER="+escape(top.document.referrer)+"&EXCLUDE=http://www.spammer.com\">");
        document.write("</script>");
}
