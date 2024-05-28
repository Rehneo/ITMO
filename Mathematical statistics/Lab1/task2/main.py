import matplotlib.pyplot as plt
import pandas as pd

df = pd.read_csv("resources/sex_bmi_smokers.csv")

smokers = df[df["smoker"] == "yes"]
male_smokers = smokers[smokers["sex"] == "male"]
female_smokers = smokers[smokers["sex"] == "female"]
male_non_smokers = df[(df["smoker"] == "no") & (df["sex"] == "male")]
female_non_smokers = df[(df["smoker"] == "no") & (df["sex"] == "female")]

print("Количество курящих мужчин: ", len(male_smokers))
print("Количество некурящих женщин: ", len(female_non_smokers))

s = ["Для всех наблюдателей", "Для курящих мужчин", "Для некурящих мужчин", "Для курящих женщин",
     "Для некурящих женщин"]
i = 0
for group in [df, male_smokers, male_non_smokers, female_smokers, female_non_smokers]:
    print()
    print(s[i])
    print("\tВыборочное среднее:", group["bmi"].mean())
    print("\tВыборочная дисперсия:", group["bmi"].var())
    print("\tВыборочная медиана:", group["bmi"].median())
    print("\tВыборочная квантиль порядка 3/5:", group["bmi"].quantile(3 / 5))
    i += 1

i = 0
for group in [df, male_smokers, male_non_smokers, female_smokers, female_non_smokers]:
    fig, axs_all = plt.subplots(2, 2, figsize=(10, 8))
    plt.subplots_adjust(hspace=0.5)
    fig.suptitle(s[i], fontsize=20)
    axs_all[0, 0].hist(group['bmi'], cumulative=True, density=True, bins=30)
    axs_all[0, 0].set_title('Эмпирическая функция распределения ИМТ')
    axs_all[0, 1].hist(group['bmi'], density=True, bins=30)
    axs_all[0, 1].set_title('Гистограмма ИМТ')
    axs_all[1, 0].boxplot(group["bmi"])
    axs_all[1, 0].set_title("Box-Plot ИМТ")
    i += 1

plt.show()
