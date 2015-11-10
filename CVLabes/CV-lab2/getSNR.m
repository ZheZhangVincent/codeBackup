function [ SNR ] = getSNR( originalImg, outputImg )
% this method used to get the SNR value based on given 

    newOriginalImg = im2double(originalImg);
    newOutputImg = im2double(outputImg);

    SNR = 20 * log(norm(newOriginalImg, 'fro') / norm(newOriginalImg - newOutputImg, 'fro'));

end

