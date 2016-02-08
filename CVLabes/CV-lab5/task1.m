% this method aims calcated camera calibration (3*4 camera matrix P)

clc;
clear;

% for the XYZ, it is constructed by ourselve, we use same XYZ system in two
% different images
XYZ = [0,7,7; 0,7,14; 0,14,14; 0,14,7; 0,21,7; 0,21,14;
       7,7,0; 14,7,0; 14,14,0; 7,14,0; 7,21,0; 14,21,0];

% load XYZ; 

% read image, and build uv system
imageName1 = 'stereo2012a.jpg';
img = imread(imageName1);

% imageName2 = 'stereo2012b.jpg';
% img = imread(imageName2);

% figure('name', imageName1);
% imshow(img);
% hold on
% 
% % select 12 points from this image
% [u1, v1] = ginput(12);

% change uv to input style
% uv = [u1, v1];
% save('stereo2012a.mat', 'uv');

% figure('name', imageName2);
% imshow(img2);
% hold on

% select 12 points from this image
% [u2, v2] = ginput(12);

% % change uv to input style
% uv = [u2, v2];
% save('stereo2012b.mat', 'uv');

% % get uv values from stereo2012a.mat
load('stereo2012a.mat');
% load('stereo2012b.mat');

P = calibrate(img, XYZ, uv);

[K, R, t] = vgg_KR_from_P(P, 1);
