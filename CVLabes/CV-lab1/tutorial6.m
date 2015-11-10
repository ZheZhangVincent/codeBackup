clc
clear all
close all

t_start = input('Please enter the starting time: ');
t_end = input('Please enter the ending time: ');

[t_axis, h_axis] = the_wave_tute6(t_start, t_end);

h_axis(h_axis < 80) = 80;

plot(t_axis, h_axis);