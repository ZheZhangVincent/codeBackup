% this script used for task-5, 2D-FFT process. 

clc
clear

% import a man made building and resize to 512 * 512
% img = imread('ANUbuilding.jpg');
img = imread('csit.jpg');
img = imresize(img, [512, 512]);
img = im2double(img);
imgGray = rgb2gray(img);

% then constructed kernel and get results

% kernel 1: [1,1,1; 1,1,1; 1,1,1]/9
kernel1 = [1,1,1; 1,1,1; 1,1,1]/9;

% kernel 2: [ 1,1,1; 0,0,0 ; -1,-1,-1]
kernel2 = [1,1,1; 0,0,0; -1,-1,-1];

% kernel 3: [ 1,0,-1; 1,0,-1; 1,0,-1] 
kernel3 = [1,0,-1; 1,0,-1; 1,0,-1];

% kernel 4: [ -1,-1, -1; -1 , 8, -1; -1,-1,-1] 
kernel4 = [-1,-1,-1; -1,8,-1; -1,-1,-1] ;

% kernel 5: [ 0 -1 0;  -1, 5,-1;  0, -1, 0]
kernel5 = [0,-1,0; -1,5,-1; 0,-1,0];

img1 = filter2(kernel1, imgGray, 'same');
img2 = filter2(kernel2, imgGray, 'same');
img3 = filter2(kernel3, imgGray, 'same');
img4 = filter2(kernel4, imgGray, 'same');
img5 = filter2(kernel5, imgGray, 'same');

figure('name', 'Filter Result');
subplot(2,3,1), imshow(imgGray), title('Original Figure');
subplot(2,3,2), imshow(img1), title('Filter By Kernel 1 Figure');
subplot(2,3,3), imshow(img2), title('Filter By Kernel 2 Figure');
subplot(2,3,4), imshow(img3), title('Filter By Kernel 3 Figure');
subplot(2,3,5), imshow(img4), title('Filter By Kernel 4 Figure');
subplot(2,3,6), imshow(img5), title('Filter By Kernel 5 Figure');

% then, dispaly the FFT result
FFT = getFFT(imgGray);
FFT1 = getFFT(img1);
FFT2 = getFFT(img2);
FFT3 = getFFT(img3);
FFT4 = getFFT(img4);
FFT5 = getFFT(img5);

figure('name', 'FFT result on Images');
subplot(2,3,1),imshow(FFT, []), title('Origianl Figures FFT');
subplot(2,3,2),imshow(FFT1, []), title('FFT After Kernel-1');
subplot(2,3,3),imshow(FFT2, []), title('FFT After Kernel-2');
subplot(2,3,4),imshow(FFT3, []), title('FFT After Kernel-3');
subplot(2,3,5),imshow(FFT4, []), title('FFT After Kernel-4');
subplot(2,3,6),imshow(FFT5, []), title('FFT After Kernel-5');
% colormap(jet(64));

