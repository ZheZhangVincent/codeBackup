function [ denoisedImg ] = my_Gauss_filter( noisyImg, gaussKernel )
% This method used to perform gaussian filter by kernel, and return
% the denoised iamge.

    % before start, rotate 180 degree on gaussKernel, which rotate 90
    % degree twice
    gaussKernel = rot90(gaussKernel, 2);
    
    % located the centride of gaussKernel
    [kernelRow, ~] = size(gaussKernel);
    kenrnelMiddelIndex = ceil(kernelRow / 2);
    
    % get the distance from middle point to edge
    radius = kenrnelMiddelIndex - 1;
    
    % get the size of noise image
    [noisyIRow, noisyIColumn, ~] = size(noisyImg);
    
    % create the denoise image
    denoisedImg = uint8(zeros(size(noisyImg)));
    
    % get the tatal values in gaussian kernel
    totalGaussianKernelValue = sum(sum(gaussKernel));
    
    % enlarge input image by radius number, with 0 value
    valueImg = padarray(noisyImg, [radius, radius]);
    
    % create process index
    processGap = noisyIRow / 10;
    processIndex = 1;
    fprintf('Gaussian Filter is Processing: ');
    
    % then, match the middlePoint to left top of noise image, if the index
    % over the range of image, set value to 0
    for noisyIRowIndex = 1 : noisyIRow
        
        if noisyIRowIndex > processIndex * processGap
            fprintf('*');
            processIndex = processIndex + 1;
        end
        
        if noisyIRowIndex == noisyIRow
            fprintf('*\n');
        end
        
        for noisyIColumnIndex = 1 : noisyIColumn
            
            imageMatrix = constructImageMatrix( noisyIRowIndex, noisyIColumnIndex, valueImg, radius );
            
            totalPixel = sum(sum(gaussKernel .* imageMatrix));
            
            % after sum all value, assign the new total RGB value to image
            denoisedImg(noisyIRowIndex, noisyIColumnIndex) =  totalPixel / (totalGaussianKernelValue);
            
        end
    end
    
end

