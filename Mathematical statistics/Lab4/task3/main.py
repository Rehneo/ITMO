import math

import pandas as pd
import scipy.stats as stats

data = pd.read_csv("cars93.csv", delimiter=",")

alpha = 0.05

price = data["Price"]

s = set(price)

horsepower = data["Horsepower"]
rank_price = price.rank()
rank_horsepower = horsepower.rank()
d = rank_price - rank_horsepower
sum_square = (sum(d ** 2))
n = len(price)

r_s = 1 - (6 * sum_square) / (n * (n**2 - 1))

t_s = r_s * math.sqrt(n - 2) / math.sqrt(1 - r_s**2)
p_value = 2 * (1 - stats.t.cdf(abs(t_s), n - 2))

print("Значение t-статистики:", t_s)
print("Значение p-value:", p_value)

if p_value < alpha:
    print("Отклоняем нулевую гипотезу")
else:
    print("Принимаем  нулевую гипотезу")

corr, _ = stats.kendalltau(price, horsepower)

t_s = corr * math.sqrt(n - 2) / math.sqrt(1 - corr**2)
p_value = 2 * (1 - stats.t.cdf(abs(t_s), n - 2))

print("Значение t-статистики:", t_s)
print("Значение p-value:", p_value)

if p_value < alpha:
    print("Отклоняем нулевую гипотезу")
else:
    print("Принимаем  нулевую гипотезу")

