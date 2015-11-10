% for task 5: Projective transformation

% clean un-relatived items
clc
clear

% read image
sourceImg = imread('photo_U5224340.JPG');

% reduce the size of source image to save processing time
sourceImg = imresize(sourceImg, 0.25);
[rows, columns, ~] = size(sourceImg);

% get the transformation matrix struct
theta = 10;
tform = projective2d([cosd(theta) -sind(theta) 0.001; sind(theta) cosd(theta) 0.01; 0 0 1]);

% perform projective transformation by Matlab function
matlabImage = imwarp(sourceImg, tform);

% create an empty image for output
forwardTargetImg = uint8(zeros(size(sourceImg)));

% forward mapping approach
for sourceRowIndex = 1 : rows
    for sourceColumnIndex = 1 : columns
        
        % get the source pixel RGB values
        RGB = sourceImg(sourceColumnIndex, sourceRowIndex, :);
        
        % get the target position
        [targetRowIndex, targetColumnIndex] = transformPointsForward(tform, sourceRowIndex, sourceColumnIndex);
        targetColumnIndex = targetColumnIndex + rows*sind(theta);
        
        forwardTargetImg = setSplatting(forwardTargetImg, targetColumnIndex, targetRowIndex, RGB);

    end
end

[targetRows, targetColumns, channels] = size(forwardTargetImg);

% inverse mapping approach
inverseTargetImg = uint8(zeros(size(forwardTargetImg)));

for targetRowIndex = 1 : targetRows
    for targetColumnIndex = 1 : targetColumns
        
        actualColumnIndex = targetColumnIndex;
        actualColumnIndex = actualColumnIndex - rows*sind(theta);
        
        [sourceRowIndex, sourceColumnIndex] = transformPointsInverse(tform, targetRowIndex, actualColumnIndex);
        
        % before get value, we should make sure the source position in the
        % source image (onece not in the source image, continue it)
        sourceRowIndex = round(sourceRowIndex);
        sourceColumnIndex = round(sourceColumnIndex);
        
        if sourceRowIndex <= 0
            continue
        elseif sourceRowIndex >= rows
            continue
        elseif sourceColumnIndex <= 0
            continue
        elseif sourceColumnIndex >= columns
            continue
        end
        
        % get their locations
        sourcePosition = [sourceColumnIndex, sourceRowIndex];
        targetPosition = [targetColumnIndex, targetRowIndex];
        
        % perfrom linear interpolation appraoch based on my function
        inverseTargetImg = linearInterpolation(sourceImg, inverseTargetImg, sourcePosition, targetPosition);
    
    end
end

% Then, deal with these two image, to reduce their size to regular as
% Matlab

% set the threhold as small as possible, only keep the not black part
threshold =0.1;

% reduce the black areas
forwardTargetImg = getPureFigure(forwardTargetImg, threshold);
inverseTargetImg = getPureFigure(inverseTargetImg, threshold);

figure(1);
subplot(2,2,1), imshow(sourceImg), title('Original Figure');
subplot(2,2,2), imshow(forwardTargetImg), title('Forward Figure');
subplot(2,2,3), imshow(inverseTargetImg), title('Inversed Rotated Figure');
subplot(2,2,4), imshow(matlabImage), title('Matlab Figure');

bilinearImg = imwarp(sourceImg, tform, 'bilinear');
bicubicImg = imwarp(sourceImg, tform, 'bicubic');

figure(2), 
figure('name', 'Matlab Bilinear and Bicubic Appraoch Result');
subplot(1, 2, 1), imshow(bilinearImg), title('Matlab Method Bilinear Result');
subplot(1, 2, 2), imshow(bicubicImg), title('Matlab Method Bicubic Result');
