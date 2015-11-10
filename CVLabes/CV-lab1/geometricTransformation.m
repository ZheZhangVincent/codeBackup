% for task-4: geometric transformation

% clean un-relatived items
clc
clear

% 1. implement your own Matlab function for image rotation by an arbitrary 
% angle 0=< theta = <90; (use counter-clockwise rotation)

% this part is for the forward mapping approach

% firstly, get the arbitrary angle
angle = randi([0, 90]);

% then, for each pixel in origianl image, act as source, get its target
sourceImg = imread('photo_U5224340.JPG');

% then, save the information of source image to rows, columns and channels
% and reduce the image size to save process time
[rows, columns, ~] = size(sourceImg);
sourceImg = imresize(sourceImg, [rows/4,columns/4]);

[rows, columns, channels] = size(sourceImg);

% create a new image which used to save created image, consider of the
% rotated image, the size of target image should bigger than source one
targetRows = ceil(rows*cosd(angle) + columns*sind(angle));
targetColumns = ceil(columns*cosd(angle) + rows*sind(angle));

%create target image
forwardTargetImg = uint8(zeros(targetRows , targetColumns , 3));

% create the rotation matrix
tform = projective2d([cosd(angle) sind(angle) 0; -sind(angle) cosd(angle) 0; 0 0 1]);

% rotationMatrix = [cosd(angle) -sind(angle); sind(angle) cosd(angle)];
% rotationMatrix = tform.T;

% create rotated image by Matlab
matlabImage = imrotate(sourceImg, angle);

for sourceRowIndex = 1: rows
    for sourceColumnIndex = 1 : columns

        % get the three R, G, B value from origianl image
        RGB = sourceImg(sourceRowIndex, sourceColumnIndex, :);

        [targetRowIndex, targetColumnIndex] = transformPointsForward(tform, sourceRowIndex, sourceColumnIndex);
        
        % consider of the difference on sizes of source image and target image,
        % the target image's position value is not equal to source one to avoid
        % negative situation. Because its anti-clock rotation, then modify
        % column to match to the target image position
        targetRowIndex = targetRowIndex + columns*sind(angle);
                       
        forwardTargetImg = setSplatting(forwardTargetImg, targetRowIndex, targetColumnIndex, RGB);
        
    end
end

% This part is for inverse appraoch

% create inverse image
inverseTargetImg = uint8(zeros(targetRows , targetColumns , 3));

% then, perform inverse mapping approach
for targetRowIndex = 1 : targetRows
    for targetColumnIndex = 1 : targetColumns
        
        actualRowIndex = targetRowIndex;
        actualRowIndex = actualRowIndex - columns*sind(angle);
        
        [sourceRowIndex, sourceColumnIndex] = transformPointsInverse(tform,actualRowIndex, targetColumnIndex);
  
        % before get value, we should make sure the source position in the
        % source image (onece not in the source image, continue it)
        if fix(sourceRowIndex) <= 0
            continue
        elseif fix(sourceRowIndex) >= rows
            continue
        elseif fix(sourceColumnIndex) <= 0
            continue
        elseif fix(sourceColumnIndex) >= columns
            continue
        end
        
        % get their locations
        sourcePosition = [sourceRowIndex, sourceColumnIndex];
        targetPosition = [targetRowIndex, targetColumnIndex];
        
        % perfrom linear interpolation appraoch based on my function
        inverseTargetImg = linearInterpolation(sourceImg, inverseTargetImg, sourcePosition, targetPosition);
    
    end
end

figure(1);
subplot(2,2,1), imshow(sourceImg), title('Orginial Figure');
subplot(2,2,2), imshow(forwardTargetImg), title('Forward Rotated Figure');
subplot(2,2,3), imshow(inverseTargetImg), title('Inversed Rotated Figure');
subplot(2,2,4), imshow(matlabImage), title('Matlab Rotated Figure');

fprintf('There rotated angle is %d.\n', angle);





% % get the source image source image position
% sourcePosition = [sourceRowIndex; sourceColumnIndex];
%         
% % get the three R, G, B value from origianl image
% RGB = sourceImg(sourceRowIndex, sourceColumnIndex, :);
%         
% % then, get their target R, G B value to targe image
% targetPosition = rotationMatrix*sourcePosition;
% targetRowIndex = targetPosition(1,:);
% targetColumnIndex = targetPosition(2,:);
%         
% % assign R value
% forwardTargetImg(targetRowIndex, targetColumnIndex, 1) = RGB(:,:,1);
% forwardTargetImg(targetRowIndex + 1, targetColumnIndex, 1) = RGB(:,:,1);
% forwardTargetImg(targetRowIndex, targetColumnIndex + 1, 1) = RGB(:,:,1);
% forwardTargetImg(targetRowIndex + 1, targetColumnIndex + 1, 1) = RGB(:,:,1);
