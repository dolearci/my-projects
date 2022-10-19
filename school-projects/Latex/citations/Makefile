NAME = proj4

$(NAME).pdf: $(NAME).ps
	ps2pdf $(NAME).ps

$(NAME).ps: $(NAME).dvi
	dvips -sPAPERSIZE=a4 $(NAME).dvi

$(NAME).dvi: $(NAME).tex
	latex $(NAME).tex
	make bibtex
	latex $(NAME).tex
	latex $(NAME).tex

bibtex: $(NAME).bib
	bibtex $(NAME)

pdflatex: $(NAME).tex
	pdflatex $(NAME).tex
	make bibtex
	pdflatex $(NAME).tex
	pdflatex $(NAME).tex

c: clean
clean:
	rm -f $(NAME).dvi $(NAME).log $(NAME).blg $(NAME).bbl $(NAME).toc $(NAME).aux $(NAME).out $(NAME).lof $(NAME).ptc $(NAME).ps


