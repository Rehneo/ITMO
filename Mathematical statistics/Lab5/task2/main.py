import pandas as pd
import numpy as np
from scipy.stats import f

data = pd.read_csv('C:/Users/Microsoft/Desktop/Current/Fourth semester/Mathematical '
                   'statistics/Lab5/task2/resources/iris.csv')

setosa = data[data['Species'] == 'setosa']
versicolor = data[data['Species'] == 'versicolor']
virginica = data[data['Species'] == 'virginica']
setosa_area = (setosa['Sepal.Length'] * setosa['Sepal.Width'] +
               setosa['Petal.Length'] * setosa['Petal.Width'])
versicolor_area = (versicolor['Sepal.Length'] * versicolor['Sepal.Width'] +
                   versicolor['Petal.Length'] * versicolor['Petal.Width'])
virginica_area = (virginica['Sepal.Length'] * virginica['Sepal.Width'] +
                  virginica['Petal.Length'] * virginica['Petal.Width'])

virginica_area_mean = np.mean(virginica_area)
setosa_area_mean = np.mean(setosa_area)
versicolor_area_mean = np.mean(versicolor_area)

total_mean = np.mean(np.concatenate([virginica_area,
                                     versicolor_area,
                                     setosa_area]))

ssw_setosa_area = np.sum((setosa_area - setosa_area_mean)**2)
ssw_virginica_area = np.sum((virginica_area - virginica_area_mean)**2)
ssw_versicolor_area = np.sum((versicolor_area - versicolor_area_mean)**2)
ssw = ssw_versicolor_area + ssw_virginica_area + ssw_setosa_area

ssb = (len(setosa_area)*(setosa_area_mean - total_mean)**2
       + len(virginica_area)*(virginica_area_mean - total_mean)**2
       + len(versicolor_area)*(versicolor_area_mean - total_mean)**2)

df_between = 2
df_within = len(data) - 3
msb = ssb/df_between
msw = ssw/df_within

f_val = msb/msw

p_val = 1 - f.cdf(f_val, df_between, df_within)

print('F-value:', f_val)
print('p-value:', p_val)
