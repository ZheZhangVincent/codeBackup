function [ pixelValue ] = getPixelValue( RowIndex, ColumnIndex, noisyImg )
% This method used to get the pixel value from noisyImg, once
% RowIndex or ColumnIndex over the range of noisyImg, return 0
    [noisyIRow, noisyIColumn, ~] = size(noisyImg);
    
    if RowIndex < 1 || RowIndex > noisyIRow
        pixelValue = 0;
    elseif ColumnIndex < 1 || ColumnIndex > noisyIColumn
        pixelValue = 0;
    else
        pixelValue = noisyImg(RowIndex, ColumnIndex);

    end

end
