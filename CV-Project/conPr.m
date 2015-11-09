function [conPr] = conPr(colorModel, level, edgeBW)
    [x y] = size(colorModel);
    
    % Calculate conditional probability.
    for edgeCount = 1:2
        for levelCount = 1:level
            colorBW = zeros(x, y);
            jtBW = zeros(x, y);
            colorBW(colorModel == levelCount) = 1;
            sumBW = colorBW + edgeBW(:, :, edgeCount);
            jtBW(sumBW == 2) = 1;
            jtPr = sum(sum(jtBW));
            edgePr = sum(sum(edgeBW(:, :, edgeCount)));
            conPr(levelCount, edgeCount) = jtPr / edgePr;
        end
    end
    
    % Normalization
    conPr = conPr / norm(conPr);
    
    conPr = conPr';
end