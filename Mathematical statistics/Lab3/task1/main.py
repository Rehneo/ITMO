import numpy as np
from scipy.stats import f

u1 = 0
u2 = 0

sigma1_sq = 2
sigma2_sq = 1

a = 0.05

n_samples = [25, 10000]

n_experiments = 1000

tau = sigma1_sq / sigma2_sq


def confidence_interval(x_sample, y_sample):
    n = len(x_sample)
    m = len(y_sample)
    numerator = 0
    denominator = 0
    for x in x_sample:
        numerator += (x - u1) ** 2
    for y in y_sample:
        denominator += (y - u2) ** 2
    numerator *= m
    denominator *= n

    lower_bound = f.ppf(1 - a / 2, m, n) * (numerator / denominator)
    upper_bound = f.ppf(a / 2, m, n) * (numerator / denominator)

    return lower_bound, upper_bound


for num in n_samples:
    covered = 0
    for _ in range(n_experiments):
        X = np.random.normal(u1, np.sqrt(sigma1_sq), num)
        Y = np.random.normal(u2, np.sqrt(sigma2_sq), num)
        interval = confidence_interval(X, Y)
        if interval[1] <= tau <= interval[0]:
            covered += 1
    coverage_probability = covered / n_experiments
    print(f"Для выборки объемом {num:}")
    print(f"Вероятность покрытия реального значения параметра: {coverage_probability}")
