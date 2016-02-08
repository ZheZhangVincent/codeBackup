function [ P ] = calibrate( im, XYZ, uv )
% CALIBRATE
%
% Function to perform camera calibration
%
% Usage:   P = calibrate(im, XYZ, uv)
%
%   Where:   im  - is the image of the calibration target.
%            XYZ - is a N x 3 array of  XYZ coordinates
%                  of the calibration target points. 
%            uv  - is a N x 2 array of the image coordinates
%                  of the calibration target points.
%            P   - is the 3 x 4 camera calibration matrix.
%  The variable N should be an integer greater than or equal to 6.
%
%  This function plots the uv coordinates onto the image of
%  the calibration target.  It also projects the XYZ coordinates
%  back into image coordinates using the  calibration matrix
%  and plots these points too as a visual check on the accuracy of
%  the calibration process.  
%  Lines from the origin to the vanishing points in the X, Y and 
%  Z directions are overlaid on the image.
%  The mean squared error between the  positions of the uv coordinates 
%  and the projected XYZ coordinates is also reported.
%
%  The function should also report the error in satisfying the 
%  camera calibration matrix constraints.
    
    %In this method, the main task is constract matrix A, then get p 
    [pointNum, ~] = size(uv);
    
    A = zeros(pointNum*2, 12);

    for AIndex = 1 : pointNum
    
        X = XYZ(AIndex, 1);
        Y = XYZ(AIndex, 2);
        Z = XYZ(AIndex, 3);
        
        u = uv(AIndex, 1);
        v = uv(AIndex, 2);
        
        A(AIndex*2 - 1, :) = [X, Y, Z, 1, 0, 0, 0, 0, -u*X, -u*Y, -u*Z, -u];
        A(AIndex*2, :) = [0, 0, 0, 0, X, Y, Z, 1, -v*X, -v*Y, -v*Z, -v];
        
    end

    
    [~, ~, V] = svd(A,0);    
    P = reshape(V(:,end), 4, 3)';
    
    % then, write a method to get reprojection error, get our transformed
    % points
    XYZone = [XYZ, ones(pointNum, 1)]'; % construct XYZone with four columns
    uvTrans = P*XYZone; % get transformed uv values
    uvTrans(1,:) = uvTrans(1,:)./uvTrans(3,:); % make transformed uv third row is 1
    uvTrans(2,:) = uvTrans(2,:)./uvTrans(3,:);

    % get Euclidean distance between re-projected points and original points
    reprojectError = sqrt(sum(sum((uvTrans(1:2,:) - uv').^2)));
    
    fprintf('The mean reprojection error is: %d.\n', reprojectError/pointNum);
    
    % also show our select points in image, with coordinate calcuated by P
    uvTrans = uvTrans';
    
    figure('Name', 'Selection Points Compare with Calcuated Points');
    imshow(im);
    hold on;
    plot(uv(:,1),uv(:,2),'g+');
    plot(uvTrans(:,1),uvTrans(:,2),'ro');
    hold off;
    
end

