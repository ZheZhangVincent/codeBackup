function [ denoiseImg ] = my_median_filter( noisyImg )
% This method used to denoise image based on median filter approach. In this
% approach, we get the pixel based on noisy image pixel neighbor median value.

    % construct a 3 * 3 kernel, with all value is 1
    medianKernel = ones(3);
    
    % in fact, the value in medianKernel all are 1, get its information
    [kernelRow, kernelColumn] = size(medianKernel);
    kenrnelMiddelIndex = ceil(kernelRow / 2);
    
    % get the element number in medianKernel
    kernelSize = kernelRow * kernelColumn;
    
    % get the distance from middle point to edge
    radius = kenrnelMiddelIndex - 1;
    
    % get the size of noise image
    [noisyIRow, noisyIColumn, ~] = size(noisyImg);
    
    % create the denoise image
    denoiseImg = uint8(zeros(size(noisyImg)));
    
    % enlarge input image
    valueImg = padarray(noisyImg, [radius, radius]);
    
    % create process index
    processGap = noisyIRow / 10;
    processIndex = 1;
    fprintf('Median Filter is Processing: ');

    % then, use the kernel to slid image and reassign their pixel value
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
           
           % get the median value from valueList
           result = imageMatrix .* medianKernel;
           
           % reshape the result to a row
           result = reshape(result, [1, kernelSize]);
           
           medianValue = median(result);
           
           % then, assign median value to denoise image
           denoiseImg(noisyIRowIndex, noisyIColumnIndex) =  medianValue;
           
        end
    end

end

