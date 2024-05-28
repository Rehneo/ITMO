import math
import numpy as np
from scipy.stats import norm

p = 0.7
alpha = 0.05

n_samples = [25, 10000]
n_experiments = 1000


def confidence_interval(x_sample):
    n = len(x_sample)
    mean = x_sample.mean()
    a = mean ** 2
    b = (2 * mean - norm.ppf(1 - alpha/2) ** 2 / n)
    c = (1 - norm.ppf(1 - alpha/2) ** 2 / n)
    m = b / (2*a)
    h = math.sqrt(m ** 2 - c / a)
    return m - h, m + h


for num in n_samples:
    covered = 0
    for _ in range(n_experiments*10):
        X = np.random.geometric(p, num)
        interval = confidence_interval(X)
        if interval[0] <= p <= interval[1]:
            covered += 1
    coverage_probability = covered / n_experiments
    print(f"Для выборки объемом {num:}")
    print(f"Вероятность покрытия реального значения параметра: {coverage_probability}")
