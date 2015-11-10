function [t,h] = the_wave_tute6(start_t, end_t)

t = start_t:0.001:end_t;

h = 50*sin(pi.*t/6) + 100;

end