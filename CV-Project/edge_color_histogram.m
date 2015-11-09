function hist = edge_color_histogram(imgPath)
    
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % histogram matrix:
    %               color level 1 -> n
    % horizontal
    % vertical
    % non-directional
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    % Read image.
    image = imread(imgPath);

    % Establish color model.
    colorModel = rgb2hsv(image);
    
    % Eliminate pixels with low color value.
    thresh = 0.1;
    for i = 1:3
        mat = colorModel(:, :, i);
        maxValue = max(max(mat));
        mat(mat < thresh * maxValue) = 0;
        colorModel(:, :, i) = mat;
    end
    colorModel(:, :, 1) = ceil(colorModel(:, :, 1) * 12);
    colorModel(:, :, 2) = ceil(colorModel(:, :, 2) * 2);
    colorModel(:, :, 4) = ceil(colorModel(:, :, 3) * 32);
    colorModel(:, :, 3) = ceil(colorModel(:, :, 3) * 3);
    
    % Detect edge in different directions.
    grayImage = rgb2gray(image);
    edgeBW(:, :, 1) = edge(grayImage,'Prewitt','horizontal');
    edgeBW(:, :, 2) = edge(grayImage,'Prewitt','vertical');
    nonEdge = zeros(size(image, 1), size(image, 2));
    nonEdge((edgeBW(:, :, 1) + edgeBW(:, :, 2)) == 0) = 1;
    edgeBW(:, :, 3) = nonEdge;    
    
    % Compute histograms for different channels.
    % H
    hHist = conPr(colorModel(:, :, 1), 12, edgeBW);
      
    % S
    sHist = conPr(colorModel(:, :, 2), 2, edgeBW);
       
    % V 
    vHist = conPr(colorModel(:, :, 3), 3, edgeBW);
      
    % G
    gHist = conPr(colorModel(:, :, 4), 32, edgeBW);
    
    hist{1} = hHist;
    hist{2} = sHist;
    hist{3} = vHist;
    hist{4} = gHist;
end