PROJ = proj1

all: projekt1

projekt1: $(PROJ).tex
	latex $(PROJ).tex
	dvips $(PROJ).dvi
	ps2pdf $(PROJ).ps


clean:
	rm -f $(PROJ).dvi $(PROJ).ps $(PROJ).pdf $(PROJ).aux $(PROJ).log