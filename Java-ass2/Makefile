JFX_PATH = /Library/Java/JavaVirtualMachines/jdk1.7.0_51.jdk/Contents/Home/jre/lib/jfxrt.jar
JFXP = ${JFX_PATH}
SOURCES = $(wildcard src/*.java src/*/*.java)
FONTS = $(wildcard svgfonts/*.svg)
FONTCOUNT=$(shell wc -l  < ./svgfonts.properties)
# i=$(shell r=$(($RANDOM % ${FONTCOUNT})); )
# grep font${i} svgfonts.properties | cut -d'=' -f2
# FONT = $(shell (i=$(($RANDOM % ${FONTCOUNT})) ; grep font${i} svgfonts.properties | cut -d'=' -f2))
FONT = $(shell ./random_font.sh)

all: compile doc

compile: $(SOURCES)
	@@javac -d bin -cp .:$(JFXP) $(SOURCES)
	
doc: $(SOURCES)
	@@javadoc @doc_args $(SOURCES)

run: compile
	@@echo using the font ${FONT}
	@@java -cp bin:$(JFXP) captchafx.CaptchaFX ./svgfonts/${FONT}  &

clean:
	@@echo "Deleting all generated files"
	@@rm -rf bin/*
	@@rm -rf docs/*

diag:
	@@echo $(JFXP)
	@@echo $(SOURCES)
	@@echo ${FONTCOUNT}
	@@echo ${FONT}

