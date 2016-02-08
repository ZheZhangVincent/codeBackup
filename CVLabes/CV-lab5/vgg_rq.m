% [K, R] = vgg_rq(H)  Just like qr but the other way around.
%
% If [K,R] = vgg_rq(H), then R is upper-triangular, R is orthogonal, and X == K*R.
% Moreover, if H is a real matrix, then det(R)>0

function [ K, R ] = vgg_rq( H )
% This method used to calcuated the K and R value based on given homography
% matrix.

    H = H';
    [R, K] = qr(H(end:-1:1,end:-1:1)); % perform Orthogonal-triangular decomposition.
    R = R';
    R = R(end:-1:1,end:-1:1);
    K = K';
    K = K(end:-1:1,end:-1:1);

    if det(R)<0
        K(:,1) = -K(:,1);
        R(1,:) = -R(1,:);
    end


end

