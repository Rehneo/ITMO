import pandas as pd
from scipy.stats import wilcoxon, mannwhitneyu

data = pd.read_csv("cars93.csv", delimiter=",")

usa = data.loc[data['Origin'] == 'USA', 'Price']
non_usa = data.loc[data['Origin'] == 'non-USA', 'Price']
alpha = 0.05

print("По критерию Уилксона: ")

ulk_test = wilcoxon(usa, non_usa)

if ulk_test.pvalue > alpha:
    print("Принимаем нулевую гипотезу для цен")
else:
    print("Принимаем альтернативную гипотезу для цен")


usa = data.loc[data['Origin'] == 'USA', 'Horsepower']
non_usa = data.loc[data['Origin'] == 'non-USA', 'Horsepower']
print("\nПо U-критерию Манна-Уитни")

u_test = mannwhitneyu(usa, non_usa)

if u_test.pvalue > alpha:
    print("Принимаем нулевую гипотезу для мощности")
else:
    print("Принимаем альтернативную гипотезу для мощности")

print(f"\n{ulk_test}\n{u_test}")
