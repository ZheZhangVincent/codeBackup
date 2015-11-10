 function [ edgeImg ] = my_sobel_edge_detector( sourceImg, sobelKernel )
% This method used to return a image with the edge which come from the
% sobel edge detector apporoach.
    
    threshold = 150;

    % get the matrix X and matrix Y
    sobelKernelX = rot90(sobelKernel, 3);
    sobelKernelY = sobelKernel;
    
    % get the kernel information
    [kernelRow, ~] = size(sobelKernelX);
    kenrnelMiddelIndex = ceil(kernelRow / 2);
    
    % get the distance from middle point to edge
    radius = kenrnelMiddelIndex - 1;
    
    % get the size of noise image
    [sourceRow, sourceColumn, ~] = size(sourceImg);
    
    % create the denoise image
    edgeImg = uint8(zeros(size(sourceImg)));
    
    % enlarge input image by radius number with value 0
    valueImg = padarray(sourceImg, [radius, radius]);
    
    % create process index
    processGap = sourceRow / 10;
    processIndex = 1;
    fprintf('Sobel Edge is Processing: ');
    
    for sourceRowIndex = 1 : sourceRow

        if sourceRowIndex > processIndex * processGap
            fprintf('*');
            processIndex = processIndex + 1;
        end
        
        if sourceRowIndex == sourceRow
            fprintf('*\n');
        end
        
        for sourceColumnIndex = 1 : sourceColumn
            
            imageMatrix = constructImageMatrix(sourceRowIndex, sourceColumnIndex, valueImg, radius);
            totalPixelValueX = double(sum(sum(imageMatrix.*sobelKernelX)));
            totalPixelValueY = double(sum(sum(imageMatrix.*sobelKernelY)));
            
            sumPixelValue = sqrt((totalPixelValueX)^2 + (totalPixelValueY)^2);
            
            if sumPixelValue > threshold
                sumPixelValue = 255;
            else
                sumPixelValue = 0;
            end
            
            % after sum all value, assign the pixel value to image
            edgeImg(sourceRowIndex, sourceColumnIndex) =  sumPixelValue;
            
        end
    end

end

