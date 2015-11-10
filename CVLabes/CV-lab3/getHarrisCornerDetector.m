function [ rws, cols ] = getHarrisCornerDetector( bw, sigma, thresh, sze, disp )
% This method will use to detect the corn of an image based on Harris
% Approach.

% get the size of bw image
[rowNum, columnNum] = size(bw);

% Derivative Masks
dy = [-1 0 1; -1 0 1; -1 0 1];
dx = dy'; %dx is the transpose matrix of dy

% perform filter in x and y coordinate
Ix = conv2(im2double(bw), dx, 'same');
Iy = conv2(im2double(bw), dy, 'same');

% Calcuating the gradient of the image lx and ly, the larger size of Gaussian
% kernal, the less corners will detects
g = fspecial('gaussian', max(1, fix(6*sigma)), sigma);

% Smoothed image derivatives
Ix2 = conv2(Ix.^2, g, 'same'); 
Iy2 = conv2(Iy.^2, g, 'same');
Ixy = conv2(Ix.*Iy, g, 'same');

% enlarge these bw image with (sze - 1)/2 '0' to constructe image
radius = (sze - 1)/2;
Ix2 = padarray(Ix2, [radius, radius]);
Iy2 = padarray(Iy2, [radius, radius]);
Ixy = padarray(Ixy, [radius, radius]);

% Compute the cornerness. R(i,j) = det(M)-k*(trace(M))^2;
% M is [Ix2, Ixy; Ixy, Iy2]
% k is k – empirical constant, k = 0.04 to 0.06, and Matlab default is 0.04
cornerness = zeros(size(bw));

for rowIndex = 1 : rowNum
    for columnIndex = 1 : columnNum
        
        M = getM(rowIndex, columnIndex, Ix2, Iy2, Ixy, sze);
        cornerness(rowIndex, columnIndex) = det(M) - 0.04*(trace(M))^2;
    
    end
end

% Now we need to perform non-maximum suppression and threshold
% get the local max value within sze
maxValues = ordfilt2(cornerness, sze^2, ones(sze));

% filter cornerness based on thresh and maxValue
for rowIndex = 1 : rowNum
    for columnIndex = 1 : columnNum
        value = cornerness(rowIndex, columnIndex);
        if value > thresh && value == maxValues(rowIndex, columnIndex)
            cornerness(rowIndex, columnIndex) = 1;
        end
    end
end

[rws, cols] = find(cornerness == 1); % find row, col coords. clf

if disp == 1
    
    imshow(bw);
    hold on;
    p = [cols, rws];
    plot(p(:,1), p(:,2), 'or');
    title('\bf Harris Corners');
    
end

end

