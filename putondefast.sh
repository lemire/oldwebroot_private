# !/bin/bash -v
# putonde.sh
#
export PATH=/usr/local/bin:/usr/bin:/bin
COMMAND="-rCzv --rsh=\"ssh -C\" --exclude '*.bin' --exclude 'WEB-INF/*' --exclude 'Milou/*' --exclude 'infertilite/*' --exclude 'traduction/*' ~/CVS/webroot/  ondelette@ondelette.com:~/jakarta-tomcat/webapps/ROOT/ --delete"
echo rsync $COMMAND
#eval rsync  --dry-run  $COMMAND $@
eval rsync  $COMMAND $@
