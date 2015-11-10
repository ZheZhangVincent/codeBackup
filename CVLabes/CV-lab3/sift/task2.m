% for task 2: perform with SIFT detector and SIFT descriptors

% clean un-relatived items
clc;
clear;

descriptNum = 15; % save 15 strongest description

% read a image, convert to gray, then resize it to 512 * 512
% img = imreadbw('data/img3.jpg');
img = imreadbw('cat.jpg');
img = imresize(img, [512, 512]);

% get SIFT related information
[frames, descr, gss, dogss] = improvedSIFT(img, descriptNum);

% % then, draw features in the photo
% figure('name', 'SIFT Result before Rotated'); clf ; colormap gray ;
% imagesc(img) ; hold on ; axis equal ;
% featureResult = plotsiftframe(frames, 'style', 'circle') ;

% resize to 768*768, rotate 45
newImg = imresize(img, [768, 768]);
newImg = imrotate(newImg, 45);

% then, crop out central part with 512*512
[newRows, newColumns] = size(newImg);
rect = [fix(newColumns/2) - 255, fix(newRows/2) - 255, 511, 511];
newImg = imcrop(newImg, rect);

% then, get SIFT related information again
[newFrames, newDescr, newGss, newDogss] = improvedSIFT(newImg, descriptNum);

figure('name', 'SIFT Results'); clf ; colormap gray ;
subplot(1,2,1);
imagesc(img), title('Before Rotate'); hold on ; axis equal ;
featureResult1 = plotsiftframe(frames, 'style', 'circle') ;
subplot(1,2,2);
imagesc(newImg), title('After Rotate') ; hold on ; axis equal ;
featureResult2 = plotsiftframe(newFrames, 'style', 'circle') ;

% display SIFT descriptors
figure('name', 'SIFT Descriptors before Rotate Display');
subplot(2,2,1);
plot(descr(:,1:4)), title('SIFT descriptors from #1 to #4');
legend('#1', '#2', '#3', '#4');
subplot(2,2,2);
plot(descr(:,5:8)), title('SIFT descriptors from #5 to #8');
legend('#5', '#6', '#7', '#8');
subplot(2,2,3);
plot(descr(:,9:12)), title('SIFT descriptors from #9 to #12');
legend('#9', '#10', '#11', '#12');
subplot(2,2,4);
plot(descr(:,13:15)), title('SIFT descriptors from #13 to #15');
legend('#13', '#14', '#15');

figure('name', 'SIFT Descriptors after Rotate Display');
subplot(2,2,1);
plot(newDescr(:,1:4)), title('SIFT descriptors from #1 to #4');
legend('#1', '#2', '#3', '#4');
subplot(2,2,2);
plot(newDescr(:,5:8)), title('SIFT descriptors from #5 to #8');
legend('#5', '#6', '#7', '#8');
subplot(2,2,3);
plot(newDescr(:,9:12)), title('SIFT descriptors from #9 to #12');
legend('#9', '#10', '#11', '#12');
subplot(2,2,4);
plot(newDescr(:,13:15)), title('SIFT descriptors from #13 to #15');
legend('#13', '#14', '#15');

fprintf('Computing matches.\n') ;
% By passing to integers we greatly enhance the matching speed (we use
% the scale factor 512 as Lowe's, but it could be greater without
% overflow)
descr1=uint8(512*descr) ;
descr2=uint8(512*newDescr) ;
tic ;
matches=siftmatch( descr1, descr2 ) ;
fprintf('Matched in %.3f s\n', toc) ;

figure('name', 'Matched Results') ; clf ;
plotmatches(img, newImg, frames(1:2,:), newFrames(1:2,:), matches) ;
drawnow ;

figure('name', 'Detected Features Result'); clf ; colormap gray ; 
imagesc(img), title('Discover 15 Strongest Features'); hold on ; axis equal ;
featureResult1 = plotsiftframe(frames, 'style', 'circle') ;
