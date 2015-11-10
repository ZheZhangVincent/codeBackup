function [ FFTresult ] = getFFT( image )
% This method used to get the 2D FFT result based on the given image.
    
    F = fft2(image);
    
    % make the result transform from matlab to human habit
    F = fftshift(F);
    
    % get its log value to make display clearly
    FFTresult = log(abs(F));

end

