import pandas as pd
import numpy as np
from scipy.stats import t, f

data = pd.read_csv("cars93.csv")

X = data[["MPG.city", "MPG.highway", "Horsepower"]]
y = data["Price"].values

X = np.concatenate((np.ones((X.shape[0], 1), dtype=int), X), axis=1)

coefficients = np.linalg.inv(X.T @ X) @ X.T @ y
y_pred = X @ coefficients
residuals = y - y_pred

n = X.shape[0]
k = X.shape[1]
mse = np.sum(residuals ** 2) / (n - k - 1)
seb = np.sqrt(np.diagonal(mse * np.linalg.inv(X.T @ X)))

RSS = np.sum((y - y_pred) ** 2)
TSS = np.sum((y - np.mean(y)) ** 2)
R_squared = 1 - RSS / TSS

print("Коэффициент детерминации R^2 = ", R_squared)

alpha = 0.05
# Гипотеза №1: Чем больше мощность, тем больше цена.

t_s = coefficients[3] / seb[3]
p_val = 1 - t.cdf(t_s, n - k - 1)
if p_val < alpha:
    print("Принимаем нулевую гипотезу. Цена зависит от мощности ")
else:
    print("Отвергаем нулевую гипотезу. Цена не зависит от мощности")

# Гипотеза №2:  Цена изменяется в зависимости от расхода в городе
t_s = coefficients[1] / seb[1]
p_val = 1 - t.cdf(t_s, n - k - 1)
if p_val < alpha:
    print("Принимаем нулевую гипотезу. Цена зависит от расхода в городе.")
else:
    print("Отвергаем нулевую гипотезу. Цена не зависит от расхода в городе")

# Гипотеза №3

X = data[["Horsepower"]]
X = np.concatenate((np.ones((X.shape[0], 1), dtype=int), X), axis=1)
coefficients = np.linalg.inv(X.T @ X) @ X.T @ y
y_pred = X @ coefficients
RSS_S = np.sum((y - y_pred) ** 2)

f_stat = (RSS_S - RSS)*(n-k)/(RSS*2)
p_val = 1 - f.cdf(f_stat, 2, n - k)
if p_val < alpha:
    print("Принимаем нулевую гипотезу о равенстве одновременно нулю коэффициентов при расходе в городе и на шоссе")
else:
    print("Отвергаем нулевую гипотезу о равенстве одновременно нулю коэффициентов при расходе в городе и на шоссе")
