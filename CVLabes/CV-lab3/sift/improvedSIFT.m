function [ frames, descr, gss, dogss ] = improvedSIFT( img, descriptNum )
% This method used to get SIFT with defined descriptor number
    
    img = img-min(img(:)) ;
    img = img/max(img(:)) ;
     
    fprintf('Computing frames and descriptors.\n') ;

    Threshold = 0.01; % default threhold of SIFT is 0.01
    theta = 0.01; % set theta is 0.01 to used to modify threshold value

    while 1 % run while loop until find a correct threshold to find given feature number

        [frames, descr, gss, dogss] = sift( img, 'Verbosity', 0, 'Threshold', Threshold ) ;
        [~ , descrNum] = size(descr);

        if descrNum == descriptNum
            break;
        elseif descrNum < descriptNum
            Threshold = Threshold - theta; % go back to last step threshold number
            theta = theta/2;
        end
        
        fprintf('Try threshold is %d\n', Threshold);
        fprintf('Get features are %d\n', descrNum);
        Threshold = Threshold + theta;

    end
    
    fprintf('When set threshold is %d\n', Threshold);
    fprintf('Find features is %d, and equals to our defined\n', descrNum);

end

