function [ M ] = getM(rowIndex, columnIndex, Ix2, Iy2, Ixy, sze)
% This method used to get the M matrix based on the windoews size and bw
% image. Once the index over the range of bw, set the value is 0.

    radius = (sze - 1)/2;
    Ix2Matrix = constructImageMatrix(rowIndex, columnIndex, Ix2, radius);
    sumIx2 = sum(sum(Ix2Matrix));
    
    Iy2Matrix = constructImageMatrix(rowIndex, columnIndex, Iy2, radius);
    sumIy2 = sum(sum(Iy2Matrix));
    
    IxyMatrix = constructImageMatrix(rowIndex, columnIndex, Ixy, radius);
    sumIxy = sum(sum(IxyMatrix));
    
    M = [sumIx2, sumIxy; sumIxy, sumIy2];
    
end

