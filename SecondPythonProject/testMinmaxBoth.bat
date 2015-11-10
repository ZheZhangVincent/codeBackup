#!/bin/sh

python red_bird.py -p MinimaxAgent -l %1 -a depth=%{2} -b MinimaxAgent --frame_time 0.05
