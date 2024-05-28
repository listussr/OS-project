# Мобильное приложение WealthFamily
## Остальная часть проекта, выполненная нашей командой находится в репозитории: https://github.com/Codepairs/wf
### Учебный проект по предмету "Операционные системы"

#### Приложение написано на языке програмирования Kotlin (версия 1.9.0)

## Краткий user guide:

* ### Зайдя первый раз в приложение попадём на страницу регистрации: 
 ![alt text](<Снимок экрана 2024-05-28 200950.png>)
 ### Зарегистрироваться дважды с одной почты нельзя
* ### Пройдя регистрацию попадаем на страницу входа в приложение:
  ![alt text](<Снимок экрана 2024-05-28 200956.png>)
  ![alt text](<Снимок экрана 2024-05-28 201010.png>)
* ### После входа в приложение необходимо придумать PIN-код для входа в приложения без регистрации:
  ![alt text](<Снимок экрана 2024-05-28 202425.png>)
* ### Затем его надо будет подтвердить:
  ![alt text](<Снимок экрана 2024-05-28 202532.png>)
* ### Далее пользователя перекидывет на главную страницу приложения: 
  ![alt text](<Снимок экрана 2024-05-28 200641.png>)
* ### На главной странице есть навигационная панель, с помощью которой можно перемещаться по приложению
* ### На странице настроек можно выбрать тему оформления приложения (тёмную или светлую), также можно выбрать язык (русский или английский), сменить PIN-код или выйти из профиля 
  ![alt text](<Снимок экрана 2024-05-28 203145.png>)
  ![alt text](image.png)
  ![alt text](<Снимок экрана 2024-05-28 200843.png>)
* ### Также кроме основной страницы с круговыми диаграммами есть страница с таблицей всех расходов и доходов:
  ![alt text](<Снимок экрана 2024-05-28 200850.png>)
* ### При долгом нажатии на расход/доход появится окно, где можно изменить доход/расход (поменять параметр(ы) и нажать на задний фон) или удалить его, нажав на кнопку удаления:
  ![alt text](<Снимок экрана 2024-05-28 200859.png>)
* ### Соответственно доходы/расходы можно добавлять. Это можно сделать нажав на кнопку с "плюсом" в середине навигационной панели. При нжатии, пользователя перекидывает на страницу, где соответственно добавляются доходы/расходы.
  ![alt text](<Снимок экрана 2024-05-28 200923.png>)
### В данном разделе есть два необязательных к заполнению поля: тип дохода/расхода и получатель. Если не ввести что-то из остального, то программа не пропустит пользователя к даленейшим действиям.
* ### Также на навигационной панели есть раздел плана, который не функционирует, т.к. в API сервера пока не прописано взаимодействие с ним