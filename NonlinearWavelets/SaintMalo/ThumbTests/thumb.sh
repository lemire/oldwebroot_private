echo "Make sure you have the latest version of HRschemes.tex in this directory"
pdflatex %1.tex
pdflatex %1.tex
pdflatex %1.tex
thumbpdf %1.pdf
pdflatex %1.tex
echo "Done."

