% for task 1: implement your own Harris Corner Detector

% clean un-relatived items
clc;
clear;

% set relative arguments
sigma = 2; thresh = 0.1; sze = 9; disp = 0;

% read image and transform to bw format
image = imread('ANUbuilding2.jpg');
bw = rgb2gray(image);
[rows, columns] = size(bw); % get pixel number to set max possible corners
raidus = (sze - 1) / 2;
fourImageCorners = double([[1 + raidus, 1 + raidus];[1 + raidus, columns - raidus];[rows - raidus, 1 + raidus];[rows - raidus, columns - raidus]]); % delete them

% get harrize corner detector results
[rws, cols] = getHarrisCornerDetector(bw, sigma, thresh, sze, disp);

[newrws, newclos] = getPureCorners(rws, cols, fourImageCorners);

p = [newclos, newrws];
[cornersNum, ~] = size(newclos);
fprintf('Before Rotated, My Method Detects Corners Num is %d\n', cornersNum);

% then, perform Matlab corner method to get result
MatlabC = corner(bw, 'Harris', rows*columns);
[cornersNum, ~] = size(MatlabC(:,1));
fprintf('Before Rotated, Matlab Method Detects Corners Num is %d\n', cornersNum);

% then, show the display image and add corner data
figure('name', 'Harris Corner Dectector Results: Before Rotate');
subplot(1,2,1);
imshow(image);
hold on;
plot(p(:,1), p(:,2), 'or');
title('\bf My Result')
hold off

subplot(1,2,2);
imshow(image);
hold on
plot(MatlabC(:,1), MatlabC(:,2), 'or')
title('\bf Matlab Result')
hold off

% then, rotate the image
imgRotate = imrotate(image, 45);

% then perform Harris Corner Dectector Algorithm again, display results
bwRotate = rgb2gray(imgRotate);
[rows, columns] = size(bwRotate);

% get harrize corner detector results
[rws, cols] = getHarrisCornerDetector(bwRotate, sigma, thresh, sze, disp);
pRotate = [cols, rws];

[cornersNum, ~] = size(cols);
fprintf('After Rotated, My Method Detects Corners Num is %d\n', cornersNum);

% then, perform Matlab corner method to get result
MatlabCRotate = corner(bwRotate, 'Harris', rows*columns);
[cornersNum, ~] = size(MatlabCRotate(:,1));
fprintf('After Rotated, Matlab Method Detects Corners Num is %d\n', cornersNum);

% then, show the display image and add corner data
figure('name', 'Harris Corner Dectector Results: Before Rotate');
subplot(1,2,1);
imshow(imgRotate);
hold on;
plot(pRotate(:,1), pRotate(:,2), 'or');
title('\bf My Result')
hold off

subplot(1,2,2);
imshow(imgRotate);
hold on
plot(MatlabCRotate(:,1), MatlabCRotate(:,2), 'or')
title('\bf Matlab Result')
hold off

figure(3);
imshow(image);

figure(4);
imshow(imgRotate);
