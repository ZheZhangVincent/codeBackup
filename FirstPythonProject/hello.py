def a():
    return 2
import math

def sd(list1):
    average = 0
    total = 0
    for n in range(len(list1)):
        total = total + list1[n]
    average = float(total) / len(list1)

    temp = 0
    for m in range(len(list1)):
        tem_sq = (list1[n] - average) ** 2
        temp = temp + tem_sq
        
        print m
        print tem_sq
    print 'temp: '
    print temp
    var = float(temp) / len(list1)
    
    sd = (var) ** 0.5

    print average
    print var
    print sd

sd((11, 11, 11, 12, 13, 15, 17, 18, 20, 21, 22, 22, 24, 26, 26, 26, 31, 34, 36, 41, 42, 52, 67, 83))

