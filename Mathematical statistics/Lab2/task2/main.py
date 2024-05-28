import numpy as np
import matplotlib.pyplot as plt

sigma = b = 1
u = 0

n_array = np.array([100, 500, 1000, 5000, 10000, 50000, 100000])
experiments_count = 100
theta = np.random.normal(u, sigma, 1)[0]

threshold = 0.01

bias_list = []  # смещение
variance_list = []  # дисперсия
rmse_list = []  # ско
diff_count_list = []  # кол-во отличающихся оценок
diff_list = []  # все оценки которые отличаются на более чем заданный порог от реального

for n in n_array:
    diff = []
    for _ in range(experiments_count):
        X = np.random.normal(theta, b, n)
        theta_estimated = (X.mean() + (u * b ** 2) / (n * sigma ** 2)) / (1 + b ** 2 / (n * sigma ** 2))

        diff.append(abs(theta_estimated - theta))
        diff_list.append(diff)

    diff_array = np.array(diff)
    bias = np.mean(diff)
    variance = np.var(diff)
    rmse = np.sqrt(np.mean(diff_array ** 2))

    diff_count = np.sum(np.abs(diff) > threshold)

    bias_list.append(bias)
    variance_list.append(variance)
    rmse_list.append(rmse)
    diff_count_list.append(diff_count)

    print("Размер выборки:", n)
    print(f'Смещение: {bias:.4f}')
    print(f'Дисперсия: {variance:.4f}')
    print(f'Среднеквадратическая ошибка: {rmse:.4f}')
    print(f'Количество отличающихся оценок: {diff_count}')
    print()

plt.figure(figsize=(12, 6))
plt.subplot(1, 2, 1)
plt.plot(n_array, bias_list, marker='o')
plt.xlabel('Объем выборки')
plt.ylabel('Смещение')
plt.subplot(1, 2, 2)
plt.plot(n_array, variance_list, marker='o')
plt.xlabel('Объем выборки')
plt.ylabel('Дисперсия')
plt.show()

plt.figure(figsize=(12, 6))
plt.subplot(1, 2, 1)
plt.plot(n_array, rmse_list, marker='o')
plt.xlabel('Объем выборки')
plt.ylabel('Среднеквадратическая ошибка')
plt.subplot(1, 2, 2)
plt.plot(n_array, diff_count_list, marker='o')
plt.xlabel('Объем выборки')
plt.ylabel('Количество отличающихся оценок')
plt.show()
