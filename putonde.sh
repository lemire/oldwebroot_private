# !/bin/bash -v
# putonde.sh
#
export PATH=/usr/local/bin:/usr/bin:/bin
COMMAND="-raCzv --rsh=\"ssh -C\" --exclude '*.bin' --exclude 'Milou/*' --exclude 'infertilite/*' --exclude 'WEB-INF/*' --exclude 'traduction/*' ~/CVS/webroot/  ondelette@ondelette.4java.net:~/jakarta-tomcat-4.1.18/webapps/ROOT/ --delete"
echo rsync $COMMAND
eval rsync  --dry-run  $COMMAND $@
eval rsync  $COMMAND $@
