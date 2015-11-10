% for task-3: colour name recognition 

% clean un-relatived items
clc
clear

% read in the following colour image
img = imread('colourImage.png');
imgGray = rgb2gray(img);

% convert it to three images: H,S,V
imgHSV = rgb2hsv(img);

H = imgHSV(:,:,1);
S = imgHSV(:,:,2);
V = imgHSV(:,:,3);

% display the H,S,V images as greyscale images in a 2 x 2 panel
figure(1);
subplot(2,2,1), imshow(imgGray), title('Gray Figure');
subplot(2,2,2), imshow(H), title('H Figure');
subplot(2,2,3), imshow(S), title('S Figure');
subplot(2,2,4), imshow(V), title('V Figure');

% print the computed 12 H-values next to the 12 regions in the image
% consider of the background colour hue value and 12 hue value, apply
% saturation to get connect components (the same coordinate position in H S)
CC = bwconncomp(S);
regionsNum = CC.NumObjects;
s = regionprops(CC,'Centroid');
centroids = cat(1, s.Centroid);

inputImage = img;

for regionIndex = 1 : regionsNum
    
    region = CC.PixelIdxList{regionIndex};
    
    % get the central of this region
    X = round(centroids(regionIndex, 1));
    Y = round(centroids(regionIndex, 2));
    position = [X, Y];
    
    % using this centroid to get H value in this region
    HValue = H(Y, X);
    
    % insert the H value
    RGB = insertText(inputImage, position, HValue, 'FontSize', 40, 'BoxOpacity', 0.0, 'AnchorPoint', 'Center');
    
    % save the inserted output
    inputImage = RGB;
    
end

figure(2), imshow(RGB), title('Figure with H values');
