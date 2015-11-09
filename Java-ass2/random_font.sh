#!/bin/bash

# $RANDOM returns a different random integer at each invocation.
# Nominal range: 0 - 32767 (signed 16-bit integer).

FONTCOUNT=$(wc -l  < ./svgfonts.properties)
#echo $FONTCOUNT
i=$(($RANDOM % $FONTCOUNT))
grep font${i} svgfonts.properties | cut -d'=' -f2


