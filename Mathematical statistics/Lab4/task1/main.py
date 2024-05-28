import pandas as pd
from scipy.stats import shapiro, kstest

data = pd.read_csv("cars93.csv", delimiter=",")

kstest_result = kstest(data['Price'], 'norm')

print("Критерий Колмогорова-Смирнова: ")
if kstest_result.pvalue < 0.05:
    print("Распределение цен отличается от нормального закона.")
else:
    print("Распределение цен можно аппроксимировать нормальным законом.")

shapiro_result = shapiro(data['Price'])

print("\nКритерий Шапиро-Уилка: ")
if shapiro_result.pvalue < 0.05:
    print("Распределение цен отличается от нормального закона.")
else:
    print("Распределение цен можно аппроксимировать нормальным законом.")
