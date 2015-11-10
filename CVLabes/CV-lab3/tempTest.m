clc;
clear;
rws = [1;2;1;1;1;3];

cols = [1;3;1;1;1;4];
fourImageCorners = [[1,1];[1,1];[1,1];[1,1]];


[pureRws, pureCols] = getPureCorners( rws, cols, fourImageCorners );