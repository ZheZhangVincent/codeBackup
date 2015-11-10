function [ img ] = setSplatting( img, columnIndex, rowIndex, RGB )
% This method used to process an image hole by splatting appraoch
%   The splatting appraoch assign one point's around points has same value

    columnIndex = fix(columnIndex);
    rowIndex = fix(rowIndex);
        
    if columnIndex == 0
       columnIndex = 1;
    end
        
    if rowIndex == 0
       rowIndex = 1;
    end
    
    img(columnIndex, rowIndex, 1) = RGB(:,:,1);
    img(columnIndex, rowIndex, 2) = RGB(:,:,2);
    img(columnIndex, rowIndex, 3) = RGB(:,:,3);
        
    img(columnIndex + 1, rowIndex, 1) = RGB(:,:,1);
    img(columnIndex + 1, rowIndex, 2) = RGB(:,:,2);
    img(columnIndex + 1, rowIndex, 3) = RGB(:,:,3);
        
    img(columnIndex, rowIndex + 1, 1) = RGB(:,:,1);
    img(columnIndex, rowIndex + 1, 2) = RGB(:,:,2);
    img(columnIndex, rowIndex + 1, 3) = RGB(:,:,3);
        
    img(columnIndex + 1, rowIndex + 1, 1) = RGB(:,:,1);
    img(columnIndex + 1, rowIndex + 1, 2) = RGB(:,:,2);
    img(columnIndex + 1, rowIndex + 1, 3) = RGB(:,:,3);


end

