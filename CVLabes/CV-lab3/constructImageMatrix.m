 function [ imageMatrix ] = constructImageMatrix( sourceRowIndex, sourceColumnIndex, valueImg, radius )
% This method used to return a matrix based on the given image and kernel
% radius. Once the wanted matrix over the range of source image, set the
% value to 0.

    matrixRowIndexUp = sourceRowIndex;
    matrixRowIndexDown = sourceRowIndex + 2 * radius;
    
    matrixColumnIndexLeft = sourceColumnIndex;
    matrixColumnIndexRight = sourceColumnIndex + 2 * radius;
    
    imageMatrix = double(valueImg(matrixRowIndexUp:matrixRowIndexDown, matrixColumnIndexLeft:matrixColumnIndexRight));
    
end

