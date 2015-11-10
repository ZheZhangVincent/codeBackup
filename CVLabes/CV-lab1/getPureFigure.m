function [ reducedFigure ] = getPureFigure( img, threshold )
% This method used to get the 'pure figure' from a figure with many black
%area
% e.g. img (3/4 area are black) => reducedImg (left non-black part)

imgThr = im2bw(img, threshold);

CC = bwconncomp(imgThr);

s = regionprops(CC,'BoundingBox');
boundingBoxs = cat(1, s.BoundingBox);

BB = boundingBoxs(1, :);
a1 = ceil(BB(1));
b1 = ceil(BB(2));
c1 = ceil(BB(3));
d1 = ceil(BB(4));

reducedFigure = imcrop(img,[a1 b1 c1 d1]);

end

