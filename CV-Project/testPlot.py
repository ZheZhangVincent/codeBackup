'''
Created on May 16, 2015

@author: Mark
'''

import matplotlib.pyplot as plt

def main():

    
    k = [200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100]
    v = [54, 64, 74, 68, 80, 78, 68, 78, 82, 78]
    
    plt.plot(k, v, 'ro')
    
    plt.show()
    

if __name__ == '__main__':
    main()