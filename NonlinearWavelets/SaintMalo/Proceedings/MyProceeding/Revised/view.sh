tex paper.tex
dvips -t a4 -o paper.ps paper.dvi
psresize -pletter paper.ps paper.letter.ps
gzip -9 --force paper.ps
gzip -9 --force paper.letter.ps
kghostview paper.letter.ps.gz
