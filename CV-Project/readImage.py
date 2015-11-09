'''
Created on May 14, 2015

@author: Mark
'''
def getImageName(imageNumber):
    """
    This method used to construct image name based on given 
    image number.
        int => str
    """
    
    imageAddress = 'trainingImage/image'
    imageExtensition = '.JPG';
    
    imageCount = getImageCount(imageNumber)
    
    imageName = ''.join((imageAddress, imageCount, imageExtensition));
    
    return imageName

def getImageCount(imageNumber):
    """
    This method used to construct image count base on given 
    image number, and make it is 3 digits.
        int => str
    """
    
    imageNumber = str(imageNumber)
    
    headString = ''
    
    if len(imageNumber) != 3:
        for count in xrange(3 - len(imageNumber)):
            headString = ''.join((headString, '0'))
    
    return headString + imageNumber

def main():
    
    print getImageName(100)

if __name__ == '__main__':
    main()