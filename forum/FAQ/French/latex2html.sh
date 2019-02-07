rm *.aux
rm *.log
rm *.toc
latex faqondelette.tex
latex faqondelette.tex
latex faqondelette.tex
dvipdf faqondelette.dvi
 latex2html --image_type png -noinfo -noaccent_images -local_icons -notop_navigation faqondelette.tex



