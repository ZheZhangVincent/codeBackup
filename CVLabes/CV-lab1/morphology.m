% for task 2: Morphology

% clean un-relatived items
clc
clear

% Load in "text.png"
img = imread('text3.png');
% img = imread('text2.png');
% img = imread('text.png');

% resize it to size of 1024x1024
img = imresize(img, [1024, 1024]);

% 2. display the histogram of img
I = rgb2gray(img);
bins = 50;

figure(1), imhist(I, bins), title('Text Image Historgram');

% 3. threshold the histogram to obtain a binary image

% firstly, get the threshold from build-in method: graythresh
% http://au.mathworks.com/help/images/ref/graythresh.html
% threshold = graythresh(imgScaled);

threshold = 0.3; % text3
% threshold = 0.55; % text2
% threshold = 0.6; % text

imgThr = im2bw(img, threshold);

% 4. count the number of white and black pixels
whitePixelNum = 0;
blackPixelNum = 0;

for row = 1 : size(imgThr,1)
    for col = 1 : size(imgThr,2)
        pix = imgThr(row, col);
        
        % consider of in the imgThr, there are only two kinds of pix value
        if pix == 0
            whitePixelNum = whitePixelNum + 1;
        else
            blackPixelNum = blackPixelNum + 1;
        end
        
    end
end

fprintf('There are %d white pixels in this image.\n', whitePixelNum);
fprintf('There are %d black pixels in this image.\n', blackPixelNum);

% 5. testing the effects of applying morphological operators of "erosion", 
% "dilation", "opening" and "closing" to the binary image, 
% and visually inspect the effects

% first of all, get the Structuring Element, get it by 
% function 'strel': http://au.mathworks.com/help/images/ref/strel.html
SE = strel('disk',28); % text3
% SE = strel('disk',4); % text2
% SE = strel('disk',20); % text

erosion = imerode(imgThr, SE);
dilation = imdilate(imgThr, SE);
opening = imopen(imgThr, SE);
closing = imclose(imgThr, SE);

figure(2);
subplot(2,2,1), imshow(erosion), title('Erosion Result');
subplot(2,2,2), imshow(dilation), title('Dilation Result');
subplot(2,2,3), imshow(opening), title('Opening Result');
subplot(2,2,4), imshow(closing), title('Closing Result');

% 6. Design and program a morphology-based algorithm that can automatically 
% segment each of the text lines from the image. In other words, input the 
% particular given image "text.png",  your algorithm will automatically 
% extract five sub-images containing the five text lines. 

% based on the paper from Pratheeba (2010 ,et al), we perform closing,
% openning, then difference two figures, finnally, perform cca, to get the
% subfigures.
opening = imopen(imgThr, SE);
closing = imclose(imgThr, SE);

difference = imabsdiff(closing, opening);

SE = strel('line', 300, 0); % text3
% SE = strel('line', 400, 0); % text2
% SE = strel('line', 70, 0); % text3

resultFigure = imdilate(difference, SE);

CC = bwconncomp(resultFigure);
textLineNum = CC.NumObjects;
s = regionprops(CC,'BoundingBox');
boundingBoxs = cat(1, s.BoundingBox);

figure(3),
imshow(resultFigure);

for textLineIndex = 1 : textLineNum
    
    testLine = boundingBoxs(textLineIndex, :);
    a = ceil(testLine(1));
    b = ceil(testLine(2));
    c = ceil(testLine(3));
    d = ceil(testLine(4));
    imgCrop = imcrop(img,[a b c d]);
    
    figure(3 + textLineIndex);
    imshow(imgCrop);
    
end


