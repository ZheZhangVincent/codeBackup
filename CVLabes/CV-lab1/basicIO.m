% for task-1: basic I/O image

% clean un-relatived items
clc
clear

% 1. Take a frontal face photo of yourself
% 2. Save this photo into an image file  named "photo_UId.jpg"

% 3. Read image
img = imread('photo_U5224340.JPG');
[rows, columns, ~] = size(img);

% get which one is smaller
if rows > columns
    length = columns;
else
    length = rows;
end

% crop the image to make sure row and column are same
img = imcrop(img,[1 1 length length]);

% rescale the image to size of 1024 x 1024
imgScaled = imresize(img, [1024, 1024]);

% 4. display this rescaled colour image on screen
figure(1), imshow(imgScaled),title('Rescaled Colour Image');

% 5. convert the colour image into three grayscale channel
R = imgScaled(:,:,1);
G = imgScaled(:,:,2);
B = imgScaled(:,:,3);

% display the three channel greyscale images separately%
figure(2);
subplot(1,3,1), imshow(R), title('Grayscale image on R'); 
subplot(1,3,2), imshow(G), title('Grayscale image on G'); 
subplot(1,3,3), imshow(B), title('Grayscale image on B');


% 6. Compute the 3 histograms of these grayscale images
bins = 50;

[Rcounts, RbinPos] = imhist(R, bins);
[Gcounts, GbinPos] = imhist(G, bins);
[Bcounts, BbinPos] = imhist(B, bins);

%display them
figure(3);
subplot(1,3,1), stem(RbinPos,Rcounts), title('Histograms on R');
subplot(1,3,2), stem(GbinPos,Gcounts), title('Histograms on G');
subplot(1,3,3), stem(BbinPos,Bcounts), title('Histograms on B');

% 7. Apply histogram equalization
REq = histeq(R, bins);
GEq = histeq(G, bins);
BEq = histeq(B, bins);

% then repeat the above step 6
[REqcounts, REqbinPos] = imhist(REq, bins);
[GEqcounts, GEqbinPos] = imhist(GEq, bins);
[BEqcounts, BEqbinPos] = imhist(BEq, bins);

figure(4);
subplot(1,3,1), stem(REqbinPos,REqcounts), title('Histograms on Equalized R');
subplot(1,3,2), stem(GEqbinPos,GEqcounts), title('Histograms on Equalized G');
subplot(1,3,3), stem(BEqbinPos,BEqcounts), title('Histograms on Equalized B');
