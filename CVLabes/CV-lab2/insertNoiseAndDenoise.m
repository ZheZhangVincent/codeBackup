% This script used for task - 1, insert Gaussian and salt & pepper noise
% and task 2 and task 3 to denoise.

clc
clear

% 1. read you photo and resize 512*512
img = imread('photo_U5224340.JPG');
imgResized = imresize(img, [512, 512]);

% 2. convert it to a greyscale image
imgGray = rgb2gray(imgResized);

% add Gaussian noise with zero mean and variation of 0.1
imgGaussian = imnoise(imgGray, 'gaussian', 0, 0.1);

% add Salt & Pepper noise with noise density 0.1
imgSaltAndPepper = imnoise(imgGray, 'salt & pepper', 0.1);

% 4. display orginial and noise result
figure('name', 'Original Figure and Noise Result');
subplot(2, 2, 1), imshow(imgResized), title('Original Image');
subplot(2, 2, 2), imshow(imgGray), title('Grayscale Image');
subplot(2, 2, 3), imshow(imgGaussian), title('Gaussian Image');
subplot(2, 2, 4), imshow(imgSaltAndPepper), title('Salt and Pepper Image');

% the following are denoise by gaussian filter

% first of all, constructe 9*9 gaussian kernel
gaussKernel = fspecial('gaussian', [9 9], 1);

% then call my_Gauss_filter to get the denoise image
denoiseGaussImg = my_Gauss_filter( imgGaussian, gaussKernel );
denoiseSPImg = my_Gauss_filter( imgSaltAndPepper, gaussKernel );

figure('name', 'Gaussian Denoised Result Figure');
subplot(1,2,1), imshow(denoiseGaussImg), title('Denoise Gaussian Noise Result');
subplot(1,2,2), imshow(denoiseSPImg), title('Denoise Salt & Pepper Noise Result');


SNRGaussFull = getSNR(imgGray, imgGaussian);
SNRGauss = getSNR(imgGray, denoiseGaussImg);
fprintf('before denoise, the SNR for Gaussian Noise image is: %d\n', SNRGaussFull);
fprintf('after denoise, the SNR for Gaussian Noise image is: %d\n', SNRGauss);

SNRSPFull = getSNR(imgGray, imgSaltAndPepper);
SNRSP = getSNR(imgGray, denoiseSPImg);
fprintf('before denoise, the SNR for Salt and Pepper Noise image is: %d\n', SNRSPFull);
fprintf('after denoise, the SNR for Salt and Pepper Noise image is: %d\n', SNRSP);


% then, collect SNR values and variance values to plot

SNRArray = ones(1, 14);
varianceArray = ones(1, 14);
variance = 0.2;
index = 1;

while variance < 3
    
    gaussKernel = fspecial('gaussian', [9 9], variance);
    denoiseGaussImg = my_Gauss_filter( imgGaussian, gaussKernel );
    SNR = getSNR(imgGray, denoiseGaussImg);
    
    SNRArray(index) = SNR;
    varianceArray(index) = variance;
    
    index = index + 1;
    variance = variance + 0.2;
    
end

figure('name', 'Relationship between SNR and Variance');
plot(varianceArray, SNRArray);
figure('name', 'Finial Denoise Result');
imshow(denoiseGaussImg);

denoiseImgSalt = my_median_filter( imgSaltAndPepper );
denoiseImgGass = my_median_filter( imgGaussian );

figure('name', 'Denoise Noise Images By Median Filter');
subplot(1,2,1), imshow(denoiseImgSalt), title('Salt and Peper Noise Result');
subplot(1,2,2), imshow(denoiseImgGass), title('Gaussian Noise Result');

denoiseImgSaltMatlab = medfilt2(imgSaltAndPepper);
figure('name', 'Denoise Salt & Pepper');
subplot(1,2,1), imshow(denoiseImgSalt), title('Median Filter Result');
subplot(1,2,2), imshow(denoiseImgSaltMatlab), title('Matlab Method Result');

% get sobel kernel by applying fspecial function, the filter is 3 * 3
sobelKernel = fspecial('sobel') ;

edgeImg = my_sobel_edge_detector( imgGray, sobelKernel );

MatlabEdgeImg = edge(imgGray, 'sobel');
figure('name', 'Sobel Edge Detection Result');
subplot(1,2,1), imshow(edgeImg), title('My Vserion');
subplot(1,2,2), imshow(MatlabEdgeImg), title('Matlab Result');

