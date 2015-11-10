function [ purerws, purecols ] = getPureCorners( rws, cols, fourImageCorners )
% This method used to ignore the corners which located in imageEdge. This
% method will perform with assumption there is no corners in image edges.

    lengths = length(rws);
    purerws = [];
    purecols = [];
    
    for cornerIndex = 1 : lengths
        element = [cols(cornerIndex,1), rws(cornerIndex,1)];

        if element == [fourImageCorners(1,2), fourImageCorners(1,1)]
            continue
        elseif element == [fourImageCorners(2,2), fourImageCorners(2,1), ]
            continue
        elseif element == [fourImageCorners(3,2), fourImageCorners(3,1)]
            continue
        elseif element == [fourImageCorners(4,2), fourImageCorners(4,1)]
            continue
        else
            purerws = [purerws; rws(cornerIndex)];
            purecols = [purecols; cols(cornerIndex)];
        end 
    end
        
end



