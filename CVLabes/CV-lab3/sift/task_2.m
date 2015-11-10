
clc;
clear;

img = imreadbw('desk.jpg');
img = imresize(img, [512, 512]);

[frames1, descr1, gss1, dogss1] = sift(img);
[~,length] = size(descr1);
frames1 = frames1(:,length-14:length);
descr1 = descr1(:,length-14:length);

img2 = imresize(img, [768, 768]);
img2 = imrotate(img2, 45);

[rows2, cols2] = size(img2);
rect = [fix(cols2/2) - 255, fix(rows2/2) - 255, 511, 511];
img2 = imcrop(img2, rect);

[frames2, descr2, gss2, dogss2] = sift(img2);
[~,length] = size(frames2);
frames2 = frames2(:,length-14:length);
descr2 = descr2(:,length-14:length);

figure(1); clf ; colormap gray ;
subplot(1,2,1);
imagesc(img), title('Before Rotate'); hold on ; axis equal ;
result1 = plotsiftframe(frames1, 'style', 'circle') ;
subplot(1,2,2);
imagesc(img2), title('After Rotate') ; hold on ; axis equal ;
result2 = plotsiftframe(frames2, 'style', 'circle') ;

figure(2);
subplot(3,5,1);
plot(descr1(:,1)), title('SIFT 1');
subplot(3,5,2);
plot(descr1(:,2)), title('SIFT 2');
subplot(3,5,3);
plot(descr1(:,3)), title('SIFT 3');
subplot(3,5,4);
plot(descr1(:,4)), title('SIFT 4');
subplot(3,5,5);
plot(descr1(:,5)), title('SIFT 5');
subplot(3,5,6);
plot(descr1(:,6)), title('SIFT 6');
subplot(3,5,7);
plot(descr1(:,7)), title('SIFT 7');
subplot(3,5,8);
plot(descr1(:,8)), title('SIFT 8');
subplot(3,5,9);
plot(descr1(:,9)), title('SIFT 0');
subplot(3,5,10);
plot(descr1(:,10)), title('SIFT 10');
subplot(3,5,11);
plot(descr1(:,11)), title('SIFT 11');
subplot(3,5,12);
plot(descr1(:,12)), title('SIFT 12');
subplot(3,5,13);
plot(descr1(:,13)), title('SIFT 13');
subplot(3,5,14);
plot(descr1(:,14)), title('SIFT 14');
subplot(3,5,15);
plot(descr1(:,15)), title('SIFT 15');


figure(3);
subplot(3,5,1);
plot(descr2(:,1)), title('SIFT 1');
subplot(3,5,2);
plot(descr2(:,2)), title('SIFT 2');
subplot(3,5,3);
plot(descr2(:,3)), title('SIFT 3');
subplot(3,5,4);
plot(descr2(:,4)), title('SIFT 4');
subplot(3,5,5);
plot(descr2(:,5)), title('SIFT 5');
subplot(3,5,6);
plot(descr2(:,6)), title('SIFT 6');
subplot(3,5,7);
plot(descr2(:,7)), title('SIFT 7');
subplot(3,5,8);
plot(descr2(:,8)), title('SIFT 8');
subplot(3,5,9);
plot(descr2(:,9)), title('SIFT 0');
subplot(3,5,10);
plot(descr2(:,10)), title('SIFT 10');
subplot(3,5,11);
plot(descr2(:,11)), title('SIFT 11');
subplot(3,5,12);
plot(descr2(:,12)), title('SIFT 12');
subplot(3,5,13);
plot(descr2(:,13)), title('SIFT 13');
subplot(3,5,14);
plot(descr2(:,14)), title('SIFT 14');
subplot(3,5,15);
plot(descr2(:,15)), title('SIFT 15');
