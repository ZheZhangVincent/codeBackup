function [ inverseTargetImg ] = linearInterpolation( sourceImg, inverseTargetImg, sourcePosition, targetPosition )
%	This method used to perform linear interpolation appraoch

    % consider of the source position in the source image may
    % not exists (not int position), we should apply linear
    % interpolation method
    
    sourceRowIndex = sourcePosition(1);
    sourceColumnIndex = sourcePosition(2);
    
    targetRowIndex = targetPosition(1);
    targetColumnIndex = targetPosition(2);
        
    % firstly, we get the four point around source position.
    ltRGB = sourceImg(fix(sourceRowIndex), fix(sourceColumnIndex), :); % left top point
    ldRGB = sourceImg(fix(sourceRowIndex) + 1, fix(sourceColumnIndex), :); % left down point
    rtRGB = sourceImg(fix(sourceRowIndex), fix(sourceColumnIndex) + 1, :); % right top point
    rdRGB = sourceImg(fix(sourceRowIndex) + 1, fix(sourceColumnIndex) + 1, :); % right down point
        
    % get the weightings on rows and column line
    m = sourceRowIndex - fix(sourceRowIndex);
    n = sourceColumnIndex - fix(sourceColumnIndex);
        
    % get R value
    temp1 = m*ltRGB(:,:,1) + (1 - m)*ldRGB(:,:,1);
    temp2 = m*rtRGB(:,:,1) + (1 - m)*rdRGB(:,:,1);
    temp3 = n*temp1 + (1 - n)*temp2;
    inverseTargetImg(targetRowIndex, targetColumnIndex, 1) = temp3;
        
    % get G value
    temp1 = m*ltRGB(:,:,2) + (1 - m)*ldRGB(:,:,2);
    temp2 = m*rtRGB(:,:,2) + (1 - m)*rdRGB(:,:,2);
    temp3 = n*temp1 + (1 - n)*temp2;
    inverseTargetImg(targetRowIndex, targetColumnIndex, 2) = temp3;
        
     % get B value
     temp1 = m*ltRGB(:,:,3) + (1 - m)*ldRGB(:,:,3);
     temp2 = m*rtRGB(:,:,3) + (1 - m)*rdRGB(:,:,3);
     temp3 = n*temp1 + (1 - n)*temp2;
    inverseTargetImg(targetRowIndex, targetColumnIndex, 3) = temp3;

end

