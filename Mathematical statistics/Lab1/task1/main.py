import math

import numpy as np
import matplotlib.pyplot as plt
import scipy.stats
import scipy.stats as stats

samples = []
means = []
variances = []
quantiles = []
arr_x_2 = []
arr_x_n = []
n = 2000
r = 1 + 3.32 * math.log(n)
ot = []
for i in range(n):
    samples.append(np.random.normal(175, 10, n))
    np.sort(samples[i])
    

    means.append(samples[i].mean())
    variances.append(samples[i].var())
    quantiles.append(np.quantile(samples[i], 0.5))

fig, axs = plt.subplots(3, 3, figsize=(10, 8))
plt.subplots_adjust(hspace=0.5)

axs[0, 0].hist(means, density=True, bins=30)
axs[0, 0].set_title('Гистограмма выборочного среднего')

axs[0, 1].hist(variances, density=True, bins=30)
axs[0, 1].set_title('Гистограмма выборочной дисперсии')

axs[1, 0].hist(quantiles, density=True, bins=30)
axs[1, 0].set_title('Гистограмма выборочной квантили порядка 0.5')

axs[1, 1].hist(arr_x_2, density=True, bins=30)
axs[1, 1].set_title('Гистограмма nF(x_2)')

axs[2, 0].hist(arr_x_n, density=True, bins=30)
axs[2, 0].set_title('Гистограмма n(1 - F(x_n))')

plt.show()
